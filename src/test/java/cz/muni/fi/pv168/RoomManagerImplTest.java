package cz.muni.fi.pv168;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by PavelKotala on 12.3.2016.
 */
public class RoomManagerImplTest {

    private DataSource dataSource;
    private RoomManager manager;

    @Before
    public void setUp() throws Exception {
        dataSource = prepareDataSource();
        DBUtils.executeSqlScript(dataSource, RoomManagerImpl.class.getResource("createTables.sql"));
        RoomManagerImpl.deleteInstance();
        RoomManagerImpl.setDataSource(dataSource);
        manager = RoomManagerImpl.getInstance();

    }

    private static DataSource prepareDataSource() {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:roommngr-test");
        dataSource.setCreateDatabase("create");

        return dataSource;
    }

    @After
    public void tearDown() throws Exception {
        DBUtils.executeSqlScript(dataSource, RoomManagerImpl.class.getResource("dropTables.sql"));
    }

    @Test
    public void createRoomTest() {
        Room room = newRoom(2, 203, new BigDecimal(300), null);

        manager.createRoom(room);
        Long roomId = room.getId();
        assertThat("saved room has null id", roomId, is(not(equalTo(null))));

        Room retrieved = manager.findRoomById(roomId);
        assertThat("retrieved room is not equal to saved one", retrieved, is(equalTo(room)));
        assertThat("retrieved room is the same instance as saved one", retrieved, is(not(sameInstance(room))));

    }

    @Test (expected = IllegalArgumentException.class)
    public void createRoomWithNegativeBedsTest() {
        Room room = newRoom(-2, 203, new BigDecimal(300), null);

        manager.createRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void createRoomWithZeroBedsTest() {
        Room room = newRoom(0, 203, new BigDecimal(300), null);

        manager.createRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void createRoomWithNegativePriceTest() {
        Room room = newRoom(2, 203, new BigDecimal(-300), null);

        manager.createRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void createRoomWithZeroPriceTest() {
        Room room = newRoom(2, 203, new BigDecimal(0), null);

        manager.createRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void createRoomWithSetIdTest() {
        Room room = newRoom(2, 203, new BigDecimal(300), null);
        room.setId(20L);

        manager.createRoom(room);
        }

    @Test
    public void deleteRoomTest() {
        Room room1 = newRoom(2, 203, new BigDecimal(300), null);
        Room room2 = newRoom(3, 303, new BigDecimal(4203), "expensive");

        manager.createRoom(room1);
        manager.createRoom(room2);

        Long id1 = room1.getId();
        Long id2 = room2.getId();

        assertThat("retrieved room should not be null", manager.findRoomById(id1), is(not(equalTo(null))));
        assertThat("retrieved room should not be null", manager.findRoomById(id2), is(not(equalTo(null))));

        manager.deleteRoom(room1);


        assertThat("retrieved deleted room should be null", manager.findRoomById(id1), is(equalTo(null)));
        assertThat("retrieved room should not be null", manager.findRoomById(id2), is(not(equalTo(null))));
    }

    @Test (expected = IllegalArgumentException.class)
    public void deletRoomWithNullIdTest() {
        Room room1 = newRoom(2, 203, new BigDecimal(300), null);

        manager.deleteRoom(room1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void deleteNullRoomTest() {
        manager.deleteRoom(null);
    }

    @Test
    public void updateRoomTest() {

        Room room1 = newRoom(2, 203, new BigDecimal(300), null);
        Room room2 = newRoom(3, 303, new BigDecimal(4203), "expensive");

        manager.createRoom(room1);
        manager.createRoom(room2);

        Long id1 = room1.getId();
        Long id2 = room2.getId();

        Room found1 = manager.findRoomById(id1);
        Room found2 = manager.findRoomById(id2);

        assertThat("retrieved room should not be null", found1, is(not(equalTo(null))));
        assertThat("retrieved room should not be null", found2, is(not(equalTo(null))));

        room2.setBeds(4);
        room2.setNote("nice");
        room2.setNumber(23);
        room2.setPricePerNight(new BigDecimal(234));
        manager.updateRoom(room2);

        Room found2updated = manager.findRoomById(id2);
        Room found1updated = manager.findRoomById(id1);

        assertThat("retrieved updated room differs from its source", found2updated, is(equalTo(room2)));
        assertThat("update changed other room", found1updated, is(equalTo(room1)));
    }

    @Test (expected = IllegalArgumentException.class)
    public void updateRoomWithZeroBedsTest() {
        Room room = newRoom(2, 203, new BigDecimal(300), null);
        manager.createRoom(room);

        room.setBeds(0);
        manager.updateRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void updateRoomWithNegativeBedsTest() {
        Room room = newRoom(2, 203, new BigDecimal(300), null);
        manager.createRoom(room);

        room.setBeds(-3);
        manager.updateRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void updateRoomWithNegativePricePerNightTest() {
        Room room = newRoom(2, 203, new BigDecimal(300), null);
        manager.createRoom(room);

        room.setPricePerNight(new BigDecimal(-300));
        manager.updateRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void updateRoomWithNullIdTest() {
        Room room = newRoom(2, 203, new BigDecimal(300), null);
        manager.createRoom(room);

        room.setId(null);
        manager.updateRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void updateNullRoomTest() {
        manager.updateRoom(null);
    }

    @Test
    public void findAllRoomsTest() {

        Room room1 = newRoom(2, 203, new BigDecimal(300), null);
        Room room2 = newRoom(3, 303, new BigDecimal(4203), "expensive");

        manager.createRoom(room1);
        manager.createRoom(room2);

        List<Room> rooms = manager.findAllRooms();

        assertThat("returned collection size was expected to be 2, found: " + rooms.size(), rooms.size(), is(equalTo(2)));
        assertThat("returned collection does not contain room: " + room1, rooms, hasItem(room1));
        assertThat("returned collection does not contain room: " + room2, rooms, hasItem(room2));
    }

    private Room newRoom(int beds, int number, BigDecimal pricePerNight, String note) {
        Room room = new Room();
        room.setNote(note);
        room.setPricePerNight(pricePerNight);
        room.setBeds(beds);
        room.setNumber(number);

        return room;
    }
}