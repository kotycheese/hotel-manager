package cz.muni.fi.pv168;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class GuestManagerImplTest {
    private GuestManager manager;
    private final LocalDate DT = LocalDate.of(2005, 4, 3);
    private DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        dataSource = prepareDataSource();
        DBUtils.executeSqlScript(dataSource, GuestManagerImpl.class.getResource("createTables.sql"));
        GuestManagerImpl.deleteInstance();
        GuestManagerImpl.setDataSource(dataSource);
        manager = GuestManagerImpl.getInstance();

    }

    private static DataSource prepareDataSource() {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:guestmngr-test");
        dataSource.setCreateDatabase("create");

        return dataSource;
    }

    @After
    public void tearDown() throws Exception {
        DBUtils.executeSqlScript(dataSource, GuestManagerImpl.class.getResource("dropTables.sql"));
    }


    @Test
    public void createGuest() {
        Guest guest = newGuest("Josef Slota", DT, "slota@josef.com");
        manager.createGuest(guest);

        Long graveId = guest.getId();
        assertThat("saved guest has null id", guest.getId(), is(not(equalTo(null))));

        Guest result = manager.findGuestById(graveId);
        assertThat("loaded guest differs from the saved one", result, is(equalTo(guest)));
        assertThat("loaded guest is the same instance", result, is(not(sameInstance(guest))));
        assertDeepEquals(guest, result);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNull() throws Exception {
        manager.createGuest(null);
    }
    
    @Test
    public void createGuestWithWrongValues() {
        LocalDate dt = LocalDate.of(2005, 4, 3);
        Guest guest = newGuest("Josef Slota", DT, "slotajosef.com");
        try {
            manager.createGuest(guest);
            fail("should refuse wrong format of email");
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        guest = newGuest("Josef Slota", DT, "slota@neco.");
        try {
            manager.createGuest(guest);
            fail("should refuse wrong format of email");
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        guest = newGuest("Josef Slota", DT, "slota@josef.com");
        guest.setId(1L);
        try {
            manager.createGuest(guest);
            fail("should refuse assigned id");
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        guest = newGuest(null, DT, "slota@josef.com");
        try{
            manager.createGuest(guest);
            fail("should refuse null name");
        } catch(IllegalArgumentException ex){
            //OK
        }
        
    }
    
    @Test
    public void deleteGuest() {
        LocalDate dtl = LocalDate.of(2000, 2, 1);

        Guest g1 = newGuest("Josef Slota", DT, "slota@josef.com");
        Guest g2 = newGuest("Dano Drevo", dtl, "dano.drevo@neco.com");
        manager.createGuest(g1);
        manager.createGuest(g2);

        assertNotNull(manager.findGuestById(g1.getId()));
        assertNotNull(manager.findGuestById(g2.getId()));

        manager.deleteGuest(g1);

        assertNull(manager.findGuestById(g1.getId()));
        assertNotNull(manager.findGuestById(g2.getId()));

    }
    
    @Test
    public void updateGuestWithWrongAttributes() {

        Guest guest = newGuest("Josef Slota", DT, "slota@josef.com");
        manager.createGuest(guest);
        Long guestId = guest.getId();

        try {
            manager.updateGuest(null);
            fail("should refuse null guest");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            guest = manager.findGuestById(guestId);
            guest.setId(null);
            manager.updateGuest(guest);
            fail("should refuse guest with null id");
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    @Test
    public void findAllRoomsTest() {

        Guest guest1 = newGuest("Josef Slota", DT, "slota@josef.com");
        Guest guest2 = newGuest("Nikdo Nic", DT, "slota@josef.com");

        manager.createGuest(guest1);
        manager.createGuest(guest2);

        List<Guest> guests = manager.findAllGuests();

        assertThat("returned collection size was expected to be 2, found: " + guests.size(), guests.size(), is(equalTo(2)));
    }
    
    private static Guest newGuest(String name, LocalDate born, String email){
        Guest guest = new Guest();
        guest.setBorn(born);
        guest.setEmail(email);
        guest.setName(name);
        return guest;
    }
    
    private void assertDeepEquals(Guest expected, Guest actual) {
        assertEquals("id value is not equal",expected.getId(), actual.getId());
        assertEquals("born dates are not equal",expected.getBorn(), actual.getBorn());
        assertEquals("emails are not equal",expected.getEmail(), actual.getEmail());
        assertEquals("names are not equal",expected.getName(), actual.getName());
    }
}
