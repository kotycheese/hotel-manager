package cz.muni.fi.pv168;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomManagerImpl implements RoomManager {
    private DataSource ds;
    private static RoomManager instance;
    private final static Logger log = LoggerFactory.getLogger(RoomManagerImpl.class);

    private RoomManagerImpl(DataSource ds) {
        this.ds = ds;
    }

    public static void setDataSource(DataSource dataSource) {
        if(instance != null) {
            log.error("instance != null");
            throw new ServiceFailureException("instance already initialized");
        }
        instance = new RoomManagerImpl(dataSource);
    }
    
    public static RoomManager getInstance() {
        if(instance == null) {
            log.error("instance == null");
            throw new EntityNotFoundException("instance not initialized, call getInstance() first");
        }
        return instance;
    }
    
    public static void deleteInstance() {
        instance = null;
    }

    @Override
    public void createRoom(Room room) {
        log.debug("creating room: " + room);
        validateRoom(room);
        if(room.getId() != null) {
            log.error("room.getId() != null");
            throw new IllegalArgumentException("room not yet created must have null id");
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO room (number, beds, pricePerNight, note) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, room.getNumber());
            preparedStatement.setInt(2, room.getBeds());
            preparedStatement.setBigDecimal(3, room.getPricePerNight());
            preparedStatement.setString(4, room.getNote());

            int changed = preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (changed != 1) {
                log.error("changed != 1");
                throw new ServiceFailureException("more lines (" + changed + ") were added while creating room " + room);
            }

            Long key = getKey(keys);
            room.setId(key);
        } catch (SQLException e) {
            log.error("createRoom()", e);
            throw new ServiceFailureException("error when inserting room: " + room, e);
        }
        log.debug("room " +room+ " created");
    }

    @Override
    public void updateRoom(Room room) {
        log.debug("updating room " + room);
        validateRoom(room);
        if(room.getId() == null) {
            log.error("room.getId() == null");
            throw new IllegalArgumentException("room to be updated must not have null id");
        }

        try(Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE room SET number = ?, beds = ?, pricePerNight = ?, note = ? WHERE id = ?")) {

            preparedStatement.setInt(1, room.getNumber());
            preparedStatement.setInt(2, room.getBeds());
            preparedStatement.setBigDecimal(3, room.getPricePerNight());
            preparedStatement.setString(4, room.getNote());
            preparedStatement.setLong(5, room.getId());

            int changed = preparedStatement.executeUpdate();

            if (changed != 1) {
                log.error("changed != 1");
                throw new ServiceFailureException("more lines (" + changed + ") were changed while updating room " + room);
            }
        } catch (SQLException e) {
            log.error("updateRoom()", e);
            throw new ServiceFailureException("error when updating room: " + room, e);
        }
        log.debug("room " + room + " updated");
    }

    @Override
    public void deleteRoom(Room room) {
        log.debug("deleting room " + room);
        if(room == null) {
            log.error("room == null");
            throw new IllegalArgumentException("room to delete must not be null");
        }
        if(room.getId() == null) {
            log.error("room.getId() == null");
            throw new IllegalArgumentException("room to delete must not have null ID");
        }

        try(Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM room WHERE id = ?")) {

            preparedStatement.setLong(1, room.getId());

            int changed = preparedStatement.executeUpdate();

            if (changed == 0) {
                log.error("changed == 0");
                throw new EntityNotFoundException("room " + room + " not found in database");
            }
            if (changed != 1) {
                log.error("changed != 1");
                throw new ServiceFailureException("more lines (" + changed + ") were deleted while deleting room " + room);
            }
        } catch (SQLException e) {
            log.error("deleteRoom()", e);
            throw new ServiceFailureException("error when deleting room: " + room, e);
        }
        log.debug("room " + room + " deleted");
    }

    @Override
    public List<Room> findAllRooms() {
        log.debug("finding all rooms");
        try(Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, number, beds, pricePerNight, note FROM room")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Room> rooms = new ArrayList<>();

            while(resultSet.next()) {
                rooms.add(getRoom(resultSet));
            }

            log.debug("all rooms founded");
            return rooms;
        } catch (SQLException e) {
            log.error("findAllRooms()", e);
            throw new ServiceFailureException("error when retrieving all rooms", e);
        }
    }

    @Override
    public Room findRoomById(Long id) {
        log.debug("finding room with id: " + id);
        try(Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, number, beds, pricePerNight, note FROM room WHERE id = ?")) {

            preparedStatement.setLong(1, id);

            Room room;
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                room = getRoom(resultSet);
            } else {
                log.warn("returning null");
                return null;
            }

            if(resultSet.next()) {
                log.error("resultSet.next()");
                throw new ServiceFailureException("more than one room found with id: " + id);
            }

            log.debug("room with id "+ id + " founded");
            return room;
        } catch (SQLException e) {
            log.error("findRoomById()", e);
            throw new ServiceFailureException("error when retrieving room with id: " + id, e);
        }
    }

    public void validateRoom(Room room) {
        if(room == null) {
            log.error("room == null");
            throw new IllegalArgumentException("room to be created must not be null");
        }

        if(room.getBeds() < 1) {
            log.error("room.getBeds() < 1");
            throw new IllegalArgumentException("number of beds must be positive integer");
        }

        if(room.getPricePerNight().signum() < 1) {
            log.error("room.getPricePerNight().signum() < 1");
            throw new IllegalArgumentException("price per night must be positive");
        }
    }

    private Long getKey(ResultSet keys) throws SQLException {
        Long key;
        if(keys.next()) {
            if(keys.getMetaData().getColumnCount() != 1) {
                log.error("keys.getMetaData().getColumnCount() != 1");
                throw new ServiceFailureException("multiple part key found");
            } else {
                key = new Long(keys.getLong(1));
            }

            if(keys.next()) {
                log.error("keys.next()");
                throw new ServiceFailureException("more keys found");
            }
        } else {
            log.error("no keys found");
            throw new ServiceFailureException("no keys found");
        }

        return key;
    }

    private Room getRoom(ResultSet resultSet) throws SQLException {
        Room room = new Room();

        room.setId(resultSet.getLong("id"));
        room.setNumber(resultSet.getInt("number"));
        room.setBeds(resultSet.getInt("beds"));
        room.setPricePerNight(resultSet.getBigDecimal("pricePerNight"));
        room.setNote(resultSet.getString("note"));

        return room;
    }
}
