package cz.muni.fi.pv168;

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
    private GuestManagerImpl guestManager;
    private RoomManagerImpl roomManager;
    
    public RentManagerImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    private void validateRent(Rent rent){
        if(rent == null){
            throw new IllegalArgumentException("rent to be created must not be null");
        }
        if(rent.getGuest() == null){
            throw new IllegalArgumentException("guest must not be null");
        }
        if(rent.getRoom() == null){
            throw new IllegalArgumentException("room must not be null");
        }
        if(rent.getRoom().getId()==null){
            throw new IllegalArgumentException("room id must not be null");
        }
        if(rent.getGuest().getId()==null){
            throw new IllegalArgumentException("guest id must not be null");
        }
        //if(this.roomManager.findRoomById(rent.getRoom().getId()) == null){
        //    throw new IllegalArgumentException("room must not be null");
        //}
        if(rent.getStartTime() == null){
            throw new IllegalArgumentException("start time must not be null");
        }
        if(rent.getEndTime() == null){
            throw new IllegalArgumentException("end time must not be null");
        }
        if(rent.getStartTime().isAfter(rent.getEndTime())){
            throw new IllegalArgumentException("start time must be lesser that end time");
        }
        if(rent.getPrice() == null){
            throw new IllegalArgumentException("rent price must not be null");
        }
        if(rent.getPrice().intValue() < 0){
            throw new IllegalArgumentException("rent price must not be lesser than zero");
        }
    }

    @Override
    public void createRent(Rent rent) {
        validateRent(rent);
        if(rent.getId() != null){
            throw new IllegalArgumentException("rent id should not be assigned prior saving");
        }
        
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "INSERT INTO RENT (price,guestId,roomId,startTime,endTime) VALUES (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            Date start = Date.valueOf(rent.getStartTime());
            Date end = Date.valueOf(rent.getEndTime());
            st.setBigDecimal(1, rent.getPrice());
            st.setLong(2, rent.getGuest().getId());
            st.setLong(3, rent.getRoom().getId());
            st.setDate(4, start);
            st.setDate(5, end);
            
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("Internal Error: More rows ("
                        + addedRows + ") inserted when trying to insert rent " + rent);
            }
            
            ResultSet keyRS = st.getGeneratedKeys();
            rent.setId(getKey(keyRS, rent));
            
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting rent " + rent, ex);
        }
    }
    
    private Long getKey(ResultSet keyRS, Rent rent) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert rent " + rent
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert rent " + rent
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert rent " + rent
                    + " - no key found");
        }
    }

    @Override
    public void updateRent(Rent rent) {
        validateRent(rent);
        if(rent.getId() == null){
            throw new IllegalArgumentException("Rent id should not be null");
        }
        
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "UPDATE RENT SET price = ?, guestId = ?, roomId = ?, startTime = ?, endTime = ? WHERE id = ?",
                Statement.RETURN_GENERATED_KEYS)) {
            Date start = Date.valueOf(rent.getStartTime());
            Date end = Date.valueOf(rent.getEndTime());
            st.setBigDecimal(1, rent.getPrice());
            st.setLong(2, rent.getGuest().getId());
            st.setLong(3, rent.getRoom().getId());
            st.setDate(4, start);
            st.setDate(5, end);
            st.setLong(6, rent.getId());
            
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("Internal Error: More rows ("
                        + addedRows + ") inserted when trying to insert rent " + rent);
            } else if (addedRows != 1) {
                throw new ServiceFailureException("Invalid updated rows count detected "
                        + "(one row should be updated): " + addedRows);
            }
            
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when updating rent " + rent, ex);
        }
    }

    @Override
    public List<Rent> findRentForRoom(Room room) {
        if(room.getId() == null){
            throw new IllegalArgumentException("Room id should not be null");
        }
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "SELECT id, price, guestId, roomId, startTime, endTime FROM rent WHERE roomId = ?")) {
            st.setLong(1, room.getId());
            ResultSet rs = st.executeQuery();

            return getRentsFromResultSet(rs);
            
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving rents", ex);
        }
    }

    @Override
    public List<Rent> findRentForGuest(Guest guest) {
        if(guest.getId() == null){
            throw new IllegalArgumentException("Guest id should not be null");
        }
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "SELECT id, price, guestId, roomId, startTime, endTime FROM rent WHERE guestId = ?")) {
            st.setLong(1, guest.getId());
            ResultSet rs = st.executeQuery();

            return getRentsFromResultSet(rs);
            
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving rents", ex);
        }
    }

    @Override
    public List<Rent> findAllRents() {
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "SELECT id, price, guestId, roomId, startTime, endTime FROM rent")) {

            ResultSet rs = st.executeQuery();

            return getRentsFromResultSet(rs);

        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving all rents", ex);
        }
    }

    @Override
    public void deleteRent(Rent rent) {
        if (rent.getId() == null) {
            throw new IllegalArgumentException("rent id is null");
        }
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM RENT WHERE id = ?")) {

            st.setLong(1, rent.getId());

            int count = st.executeUpdate();
            if (count == 0) {
                throw new EntityNotFoundException("rent " + rent + " was not found in database!");
            } else if (count != 1) {
                throw new ServiceFailureException("Invalid deleted rows count detected (one row should be updated): " + count);
            }
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when deleting rent " + rent, ex);
        }
    }

    @Override
    public Rent findRentById(Long id) {
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
                
                guestManager = new GuestManagerImpl(dataSource);
                roomManager = new RoomManagerImpl(dataSource);
                guest = guestManager.findGuestById(rs.getLong("guestId"));
                room = roomManager.findRoomById(rs.getLong("roomId"));
                
                rent.setGuest(guest);
                rent.setRoom(room);
                rent.setId(rs.getLong("id"));
                rent.setPrice(rs.getBigDecimal("price"));
                Date start = rs.getDate("startTime");
                Date end = rs.getDate("endTime");
                rent.setStartTime(start.toLocalDate());
                rent.setEndTime(end.toLocalDate());

                if (rs.next()) {
                    throw new ServiceFailureException("Internal error: More entities with the same id found ");
                }
                return rent;
            } else {
                return null;
            }
        
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when retrieving rent with id " + id, ex);
        }
    }

    private List<Rent> getRentsFromResultSet(ResultSet rs) throws SQLException {
        List<Rent> result = new ArrayList<>();
        while (rs.next()) {
            Rent rent = new Rent();
            Guest guest;
            Room room;

            guestManager = new GuestManagerImpl(dataSource);
            roomManager = new RoomManagerImpl(dataSource);
            guest = guestManager.findGuestById(rs.getLong("guestId"));
            room = roomManager.findRoomById(rs.getLong("roomId"));

            rent.setGuest(guest);
            rent.setRoom(room);
            rent.setId(rs.getLong("id"));
            rent.setPrice(rs.getBigDecimal("price"));
            Date start = rs.getDate("startTime");
            Date end = rs.getDate("endTime");
            rent.setStartTime(start.toLocalDate());
            rent.setEndTime(end.toLocalDate());

            result.add(rent);
        }

        return result;
    }
}
