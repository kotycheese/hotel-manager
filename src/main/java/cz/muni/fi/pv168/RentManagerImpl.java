package cz.muni.fi.pv168;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class RentManagerImpl implements RentManager {
    
    private final DataSource dataSource;
    private GuestManager guestManager;
    private RoomManager roomManager;
    private static RentManager instance;
    private final static Logger log = LoggerFactory.getLogger(RentManagerImpl.class);
    
    private RentManagerImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }
    
     public static void setDataSource(DataSource dataSource) {
        if(instance != null) {
            log.error("instance != null");
            throw new ServiceFailureException("instance already initialized");
        }
        instance = new RentManagerImpl(dataSource);
    }
    
    public static RentManager getInstance() {
        if(instance == null) {
            log.error("instance == null");
            throw new EntityNotFoundException("instance not initialized, call getInstance() first");
        }
        return instance;
    }
    
    public static void deleteInstance() {
        instance = null;
    }
    
    private void validateRent(Rent rent){
        if(rent == null){
            log.error("rent == null");
            throw new IllegalArgumentException("rent to be created must not be null");
        }
        if(rent.getGuest() == null){
            log.error("rent.getGuest() == null");
            throw new IllegalArgumentException("guest must not be null");
        }
        if(rent.getRoom() == null){
            log.error("rent.getRoom() == null");
            throw new IllegalArgumentException("room must not be null");
        }
        if(rent.getRoom().getId()==null){
            log.error("rent.getRoom().getId()==null");
            throw new IllegalArgumentException("room id must not be null");
        }
        if(rent.getGuest().getId()==null){
            log.error("rent.getGuest().getId()==null");
            throw new IllegalArgumentException("guest id must not be null");
        }
        if(rent.getStartDate() == null){
            log.error("rent.getStartDate() == null");
            throw new IllegalArgumentException("start time must not be null");
        }
        if(rent.getEndDate() == null){
            log.error("rent.getEndDate() == null");
            throw new IllegalArgumentException("end time must not be null");
        }
        if(rent.getStartDate().isAfter(rent.getEndDate())){
            log.error("rent.getStartDate().isAfter(rent.getEndDate())");
            throw new IllegalArgumentException("start time must be lesser that end time");
        }
        if(rent.getPrice() == null){
            log.error("rent.getPrice() == null");
            throw new IllegalArgumentException("rent price must not be null");
        }
        if(rent.getPrice().intValue() < 0){
            log.error("rent.getPrice().intValue() < 0");
            throw new IllegalArgumentException("rent price must not be lesser than zero");
        }
    }

    @Override
    public void createRent(Rent rent) {
        log.debug("creating rent: ", rent);
        validateRent(rent);
        if(rent.getId() != null){
            log.error("rent.getId() != null");
            throw new IllegalArgumentException("rent id should not be assigned prior saving");
        }
        
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "INSERT INTO RENT (price,guestId,roomId,startTime,endTime) VALUES (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            Date start = Date.valueOf(rent.getStartDate());
            Date end = Date.valueOf(rent.getEndDate());
            st.setBigDecimal(1, rent.getPrice());
            st.setLong(2, rent.getGuest().getId());
            st.setLong(3, rent.getRoom().getId());
            st.setDate(4, start);
            st.setDate(5, end);
            
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                log.error("addedRows != 1");
                throw new ServiceFailureException("Internal Error: More rows ("
                        + addedRows + ") inserted when trying to insert rent " + rent);
            }
            
            ResultSet keyRS = st.getGeneratedKeys();
            rent.setId(getKey(keyRS, rent));
            
        } catch (SQLException ex) {
            log.error("createRent()", ex);
            throw new ServiceFailureException("Error when inserting rent " + rent, ex);
        }
        log.debug("rent: " + rent + " created");
    }
    
    private Long getKey(ResultSet keyRS, Rent rent) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                log.error("generated key failed when trying to insert rent");
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert rent " + rent
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                log.error("generated key failed when trying to insert rent");
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert rent " + rent
                        + " - more keys found");
            }
            return result;
        } else {
            log.error("generated key failed when trying to insert rent");
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert rent " + rent
                    + " - no key found");
        }
    }

    @Override
    public void updateRent(Rent rent) {
        log.debug("updating rent: " + rent);
        validateRent(rent);
        if(rent.getId() == null){
            log.error("rent.getId() == null");
            throw new IllegalArgumentException("Rent id should not be null");
        }
        
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "UPDATE RENT SET price = ?, guestId = ?, roomId = ?, startTime = ?, endTime = ? WHERE id = ?",
                Statement.RETURN_GENERATED_KEYS)) {
            Date start = Date.valueOf(rent.getStartDate());
            Date end = Date.valueOf(rent.getEndDate());
            st.setBigDecimal(1, rent.getPrice());
            st.setLong(2, rent.getGuest().getId());
            st.setLong(3, rent.getRoom().getId());
            st.setDate(4, start);
            st.setDate(5, end);
            st.setLong(6, rent.getId());
            
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                log.error("addedRows != 1");
                throw new ServiceFailureException("Internal Error: More rows ("
                        + addedRows + ") inserted when trying to insert rent " + rent);
            } else if (addedRows != 1) {
                log.error("addedRows != 1");
                throw new ServiceFailureException("Invalid updated rows count detected "
                        + "(one row should be updated): " + addedRows);
            }
            
        } catch (SQLException ex) {
            log.error("updateRent()", ex);
            throw new ServiceFailureException("Error when updating rent " + rent, ex);
        }
        log.debug("rent " + rent + " updated");
    }

    @Override
    public List<Rent> findRentForRoom(Room room) {
        log.debug("finding rent for room: " + room);
        if(room.getId() == null){
            log.error("room.getId() == null");
            throw new IllegalArgumentException("Room id should not be null");
        }
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "SELECT id, price, guestId, roomId, startTime, endTime FROM rent WHERE roomId = ?")) {
            st.setLong(1, room.getId());
            ResultSet rs = st.executeQuery();

            log.debug("rent for room :" + room + " founded");
            return getRentsFromResultSet(rs);
            
        } catch (SQLException ex) {
            log.error("findRentForRoom()", ex);
            throw new ServiceFailureException(
                    "Error when retrieving rents", ex);
        }
    }

    @Override
    public List<Rent> findRentForGuest(Guest guest) {
        log.debug("finding rent for guest: ", guest);
        if(guest.getId() == null){
            log.error("guest.getId() == null");
            throw new IllegalArgumentException("Guest id should not be null");
        }
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "SELECT id, price, guestId, roomId, startTime, endTime FROM rent WHERE guestId = ?")) {
            st.setLong(1, guest.getId());
            ResultSet rs = st.executeQuery();

            log.debug("rent for guest :" + guest + " founded");
            return getRentsFromResultSet(rs);
            
        } catch (SQLException ex) {
            log.error("findRentForGuest()", ex);
            throw new ServiceFailureException(
                    "Error when retrieving rents", ex);
        }
    }

    @Override
    public List<Rent> findAllRents() {
        log.debug("finding all rents");
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "SELECT id, price, guestId, roomId, startTime, endTime FROM rent")) {

            ResultSet rs = st.executeQuery();

            log.debug("all rents founded");
            return getRentsFromResultSet(rs);

        } catch (SQLException ex) {
            log.error("findAllRents()", ex);
            throw new ServiceFailureException(
                    "Error when retrieving all rents", ex);
        }
    }

    @Override
    public void deleteRent(Rent rent) {
        log.debug("deleting rent: " + rent);
        if (rent.getId() == null) {
            log.error("rent.getId() == null");
            throw new IllegalArgumentException("rent id is null");
        }
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM RENT WHERE id = ?")) {

            st.setLong(1, rent.getId());

            int count = st.executeUpdate();
            if (count == 0) {
                log.error("count == 0");
                throw new EntityNotFoundException("rent " + rent + " was not found in database!");
            } else if (count != 1) {
                log.error("count != 1");
                throw new ServiceFailureException("Invalid deleted rows count detected (one row should be updated): " + count);
            }
            log.debug("rent: " + rent + " deleted");
        } catch (SQLException ex) {
            log.error("deleteRent()", ex);
            throw new ServiceFailureException(
                    "Error when deleting rent " + rent, ex);
        }
    }

    @Override
    public Rent findRentById(Long id) {
        log.debug("finding rent with id: " + id);
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "SELECT id, price, guestId, roomId, startTime, endTime FROM rent WHERE id = ?")) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Rent rent = new Rent();
                Guest guest;
                Room room;
                
                guestManager = GuestManagerImpl.getInstance();
                roomManager = RoomManagerImpl.getInstance();
                guest = guestManager.findGuestById(rs.getLong("guestId"));
                room = roomManager.findRoomById(rs.getLong("roomId"));
                
                rent.setGuest(guest);
                rent.setRoom(room);
                rent.setId(rs.getLong("id"));
                rent.setPrice(rs.getBigDecimal("price"));
                Date start = rs.getDate("startTime");
                Date end = rs.getDate("endTime");
                rent.setStartDate(start.toLocalDate());
                rent.setEndDate(end.toLocalDate());

                if (rs.next()) {
                    log.error("rs.next()");
                    throw new ServiceFailureException("Internal error: More entities with the same id found ");
                }
                log.debug("rent with id: " + id + " founded");
                return rent;
            } else {
                log.warn("returned null");
                return null;
            }
        
        } catch (SQLException ex) {
            log.error("findRentById()", ex);
            throw new ServiceFailureException("Error when retrieving rent with id " + id, ex);
        }
    }

    private List<Rent> getRentsFromResultSet(ResultSet rs) throws SQLException {
        List<Rent> result = new ArrayList<>();
        while (rs.next()) {
            Rent rent = new Rent();
            Guest guest;
            Room room;

            guestManager = GuestManagerImpl.getInstance();
            roomManager = RoomManagerImpl.getInstance();
            guest = guestManager.findGuestById(rs.getLong("guestId"));
            room = roomManager.findRoomById(rs.getLong("roomId"));

            rent.setGuest(guest);
            rent.setRoom(room);
            rent.setId(rs.getLong("id"));
            rent.setPrice(rs.getBigDecimal("price"));
            Date start = rs.getDate("startTime");
            Date end = rs.getDate("endTime");
            rent.setStartDate(start.toLocalDate());
            rent.setEndDate(end.toLocalDate());

            result.add(rent);
        }

        return result;
    }
}
