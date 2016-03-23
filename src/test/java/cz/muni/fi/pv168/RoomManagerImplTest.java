package cz.muni.fi.pv168;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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
        try(Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("CREATE TABLE room ("
                    + "id bigint primary key generated always as identity,"
                    + "number int,"
                    + "beds int,"
                    + "pricePerNight decimal,"
                    + "note varchar(255))").executeUpdate();
        }
        manager = new RoomManagerImpl(dataSource);
    }

    private static DataSource prepareDataSource() {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("memory:roommngr-test");
        ds.setCreateDatabase("create");

        return ds;
    }

    @After
    public void tearDown() throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("DROP TABLE room").executeUpdate();
        }
    }

    @Test
    public void createRoomTest() {
        Room room = new Room();
        room.setBeds(4);
        room.setNumber(203);
        room.setPricePerNight(new BigDecimal(300));

        manager.createRoom(room);
        Long roomId = room.getId();
        assertThat("saved room has null id", roomId, is(not(equalTo(null))));

        Room retrieved = manager.findRoomById(roomId);
        assertThat("retrieved room is not equal to saved one", retrieved, is(equalTo(room)));
        assertThat("retrieved room is the same instance as saved one", retrieved, is(not(sameInstance(room))));

    }

    @Test (expected = IllegalArgumentException.class)
    public void createRoomWithNegativeBedsTest() {
        Room room = new Room();
        room.setBeds(-1);
        room.setNumber(203);
        room.setPricePerNight(new BigDecimal(300));

        manager.createRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void createRoomWithZeroBedsTest() {
        Room room = new Room();
        room.setNumber(203);
        room.setPricePerNight(new BigDecimal(300));
        room.setBeds(0);

        manager.createRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void createRoomWithNegativePriceTest() {
        Room room = new Room();
        room.setNumber(203);
        room.setBeds(2);
        room.setPricePerNight(new BigDecimal(-1));

        manager.createRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void createRoomWithZeroPriceTest() {
        Room room = new Room();
        room.setBeds(2);
        room.setNumber(203);
        room.setPricePerNight(new BigDecimal(0));

        manager.createRoom(room);
    }

    @Test (expected = IllegalArgumentException.class)
    public void createRoomWithSetIdTest() {
        Room room = new Room();
        room.setBeds(2);
        room.setNumber(203);
        room.setPricePerNight(new BigDecimal(300));
        room.setId(20L);

        manager.createRoom(room);
        }

    @Test
    public void deleteRoomTest() {
        Room room1 = new Room();
        room1.setBeds(2);
        room1.setNumber(203);
        room1.setPricePerNight(new BigDecimal(300));

        Room room2 = new Room();
        room2.setBeds(3);
        room2.setNumber(303);
        room2.setPricePerNight(new BigDecimal(300));

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

    @Test
    public void updateRoomTest() {

    }
}