package cz.muni.fi.pv168;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.sql.DataSource;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by PavelKotala on 23.3.2016.
 */
public class RentManagerImplTest {

    private DataSource ds;
    private RentManager rentManager;
    private RoomManager roomManager;
    private GuestManager guestManager;
    private final LocalDate date1 = LocalDate.of(2005, 4, 3);
    private final LocalDate date2 = LocalDate.of(2006, 6, 7);
    private final LocalDate date3 = LocalDate.of(2016, 1, 3);

    @Before
    public void setUp() throws Exception {
        ds = prepareDataSource();
        DBUtils.executeSqlScript(ds, new URL("createTables.sql"));
        rentManager = new RentManagerImpl(ds);
        roomManager = new RoomManagerImpl(ds);
        guestManager = new GuestManagerImpl(ds);
    }

    private static DataSource prepareDataSource() {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("memory:rentmngr-test");
        ds.setCreateDatabase("create");

        return ds;
    }

    @After
    public void tearDown() throws Exception {
        DBUtils.executeSqlScript(ds, new URL("dropTables.sql"));
    }

    @Test
    public void testCreateRent() throws Exception {
        Room room = newRoom(4, 203, new BigDecimal(300), null);
        roomManager.createRoom(room);

        Guest guest = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest);

        Rent rent = newRent(new BigDecimal(4000), guest, room, date1, date2);
        rentManager.createRent(rent);

        Long rentId = rent.getId();
        Rent found = rentManager.findRentById(rentId);

        assertThat("saved rent has null id", rentId, is(not(equalTo(null))));
        assertThat("retrieved room differs from its source", found, is(equalTo(rent)));
        assertThat("retrieved room is the same instance as its source", found, is(not(sameInstance(rent))));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateRentWithUnsavedRoom() throws Exception {
        Room room = newRoom(4, 203, new BigDecimal(300), null);

        Guest guest = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest);

        Rent rent = newRent(new BigDecimal(4000), guest, room, date1, date2);
        rentManager.createRent(rent);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateRentWithUnsavedGuest() throws Exception {
        Room room = newRoom(4, 203, new BigDecimal(300), null);
        roomManager.createRoom(room);

        Guest guest = newGuest("Josef Slota", date1, "slota@josef.com");

        Rent rent = newRent(new BigDecimal(4000), guest, room, date1, date2);
        rentManager.createRent(rent);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateRentWithNegativePrice() throws Exception {
        Room room = newRoom(4, 203, new BigDecimal(300), null);
        roomManager.createRoom(room);

        Guest guest = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest);

        Rent rent = newRent(new BigDecimal(-4000), guest, room, date1, date2);
        rentManager.createRent(rent);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateRentWithNullPrice() throws Exception {
        Room room = newRoom(4, 203, new BigDecimal(300), null);
        roomManager.createRoom(room);

        Guest guest = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest);

        Rent rent = newRent(null, guest, room, date1, date2);
        rentManager.createRent(rent);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateRentWithOppositeDates() throws Exception {
        Room room = newRoom(4, 203, new BigDecimal(3800), null);
        roomManager.createRoom(room);

        Guest guest = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest);

        Rent rent = newRent(new BigDecimal(4000), guest, room, date2, date1);
        rentManager.createRent(rent);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateRentWithNullGuest() throws Exception {
        Room room = newRoom(4, 203, new BigDecimal(300), null);
        roomManager.createRoom(room);

        Rent rent = newRent(new BigDecimal(4000), null, room, date1, date2);
        rentManager.createRent(rent);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateRentWithNullRoom() throws Exception {
        Guest guest = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest);

        Rent rent = newRent(new BigDecimal(4000), guest, null, date1, date2);
        rentManager.createRent(rent);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateRentWithNullStartDate() throws Exception {
        Room room = newRoom(4, 203, new BigDecimal(300), null);
        roomManager.createRoom(room);

        Guest guest = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest);

        Rent rent = newRent(new BigDecimal(400), guest, room, null, date2);
        rentManager.createRent(rent);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateRentWithNullEndDate() throws Exception {
        Room room = newRoom(4, 203, new BigDecimal(30), null);
        roomManager.createRoom(room);

        Guest guest = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest);

        Rent rent = newRent(new BigDecimal(4000), guest, room, date1, null);
        rentManager.createRent(rent);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCreateRentWithSameDates() throws Exception {
        Room room = newRoom(4, 208, new BigDecimal(300), null);
        roomManager.createRoom(room);

        Guest guest = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest);

        Rent rent = newRent(new BigDecimal(4000), null, room, date1, date1);
        rentManager.createRent(rent);
    }

    @Test
    public void testUpdateRent() throws Exception {
        Room room1 = newRoom(4, 209, new BigDecimal(300), null);
        roomManager.createRoom(room1);
        Guest guest1 = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest1);
        Rent rent1 = newRent(new BigDecimal(4000), guest1, room1, date1, date2);
        rentManager.createRent(rent1);

        Room room2 = newRoom(6, 509, new BigDecimal(3300), "expensive");
        roomManager.createRoom(room2);
        Guest guest2 = newGuest("Karel Karel", date3, "kare@l.com");
        guestManager.createGuest(guest2);
        Rent rent2 = newRent(new BigDecimal(3200), guest2, room2, date2, date3);
        rentManager.createRent(rent2);

        rent2.setPrice(new BigDecimal(20));
        rent2.setRoom(room1);
        rent2.setGuest(guest1);
        rent2.setEndTime(date2);
        rent2.setStartTime(date1);

        rentManager.updateRent(rent2);

        Rent found2updated = rentManager.findRentById(rent2.getId());
        Rent found1updated = rentManager.findRentById(rent1.getId());

        assertThat("retrieved updated rent differs from its source", found2updated, is(equalTo(rent2)));
        assertThat("update changed other rent", found1updated, is(equalTo(rent1)));
    }

    @Test
    public void testFindRentForRoom() throws Exception {
        Room room1 = newRoom(4, 209, new BigDecimal(300), null);
        roomManager.createRoom(room1);
        Guest guest1 = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest1);
        Rent rent1 = newRent(new BigDecimal(4000), guest1, room1, date1, date2);
        rentManager.createRent(rent1);

        Room room2 = newRoom(6, 509, new BigDecimal(3300), "expensive");
        roomManager.createRoom(room2);
        Guest guest2 = newGuest("Karel Karel", date3, "kare@l.com");
        guestManager.createGuest(guest2);
        Rent rent2 = newRent(new BigDecimal(3200), guest2, room2, date2, date3);
        rentManager.createRent(rent2);

        List<Rent> found = rentManager.findRentForRoom(room1);

        assertThat("returned collection size was expected to be 1, found: " + found.size(), found.size(), is(equalTo(1)));
        assertThat("returned collection does not contain rent: " + rent1, found, hasItem(rent1));
    }

    @Test
    public void testFindRentForGuest() throws Exception {
        Room room1 = newRoom(4, 209, new BigDecimal(300), null);
        roomManager.createRoom(room1);
        Guest guest1 = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest1);
        Rent rent1 = newRent(new BigDecimal(4000), guest1, room1, date1, date2);
        rentManager.createRent(rent1);

        Room room2 = newRoom(6, 509, new BigDecimal(3300), "expensive");
        roomManager.createRoom(room2);
        Guest guest2 = newGuest("Karel Karel", date3, "kare@l.com");
        guestManager.createGuest(guest2);
        Rent rent2 = newRent(new BigDecimal(3200), guest2, room2, date2, date3);
        rentManager.createRent(rent2);

        List<Rent> found = rentManager.findRentForGuest(guest1);

        assertThat("returned collection size was expected to be 1, found: " + found.size(), found.size(), is(equalTo(1)));
        assertThat("returned collection does not contain rent: " + rent1, found, hasItem(rent1));
    }

    @Test
    public void testFindAllRents() throws Exception {
        Room room1 = newRoom(4, 209, new BigDecimal(300), null);
        roomManager.createRoom(room1);
        Guest guest1 = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest1);
        Rent rent1 = newRent(new BigDecimal(4000), guest1, room1, date1, date2);
        rentManager.createRent(rent1);

        Room room2 = newRoom(6, 509, new BigDecimal(3300), "expensive");
        roomManager.createRoom(room2);
        Guest guest2 = newGuest("Karel Karel", date3, "kare@l.com");
        guestManager.createGuest(guest2);
        Rent rent2 = newRent(new BigDecimal(3200), guest2, room2, date2, date3);
        rentManager.createRent(rent2);

        List<Rent> found = rentManager.findAllRents();

        assertThat("returned collection size was expected to be 2, found: " + found.size(), found.size(), is(equalTo(2)));
        assertThat("returned collection does not contain rent: " + rent1, found, hasItem(rent1));
        assertThat("returned collection does not contain rent: " + rent2, found, hasItem(rent2));
    }

    @Test
    public void testDeleteRent() throws Exception {
        Room room1 = newRoom(4, 209, new BigDecimal(300), null);
        roomManager.createRoom(room1);
        Guest guest1 = newGuest("Josef Slota", date1, "slota@josef.com");
        guestManager.createGuest(guest1);
        Rent rent1 = newRent(new BigDecimal(4000), guest1, room1, date1, date2);
        rentManager.createRent(rent1);

        Room room2 = newRoom(6, 509, new BigDecimal(3300), "expensive");
        roomManager.createRoom(room2);
        Guest guest2 = newGuest("Karel Karel", date3, "kare@l.com");
        guestManager.createGuest(guest2);
        Rent rent2 = newRent(new BigDecimal(3200), guest2, room2, date2, date3);
        rentManager.createRent(rent2);

        rentManager.deleteRent(rent1);

        Rent found2updated = rentManager.findRentById(rent2.getId());
        Rent found1updated = rentManager.findRentById(rent1.getId());

        assertThat("returned deleted rent should be null", found1updated, is(equalTo(null)));
        assertThat("delete changed other room", found2updated, is(equalTo(rent1)));
    }

    @Test
    public void testIsRoomAvailable() throws Exception {

    }

    private static Guest newGuest(String name, LocalDate born, String email){
        Guest guest = new Guest();
        guest.setEmail(email);
        guest.setBorn(born);
        guest.setName(name);

        return guest;
    }

    private static Room newRoom (int beds, int number, BigDecimal pricePerNight, String note){
        Room room = new Room();
        room.setBeds(beds);
        room.setNumber(number);
        room.setPricePerNight(pricePerNight);
        room.setNote(note);

        return room;
    }

    private static Rent newRent (BigDecimal price, Guest guest, Room room, LocalDate startTime,  LocalDate endTime){
        Rent rent = new Rent();
        rent.setPrice(price);
        rent.setGuest(guest);
        rent.setRoom(room);
        rent.setStartTime(startTime);
        rent.setEndTime(endTime);

        return rent;
    }
}