package cz.muni.fi.pv168;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.sql.DataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.*;

/**
 * Created by PavelKotala on 23.3.2016.
 */
public class RentManagerImplTest {

    DataSource ds;
    RentManager rentManager;

    @Before
    public void setUp() throws Exception {
        ds = new EmbeddedDataSource();
        try(BufferedReader br = new BufferedReader(new FileReader("create_rent.sql"));
            Connection connection = ds.getConnection();
            PreparedStatement ps = connection.prepareStatement(br.readLine())) {
            ps.executeUpdate();
        }
        rentManager = new RentManagerImpl();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateRent() throws Exception {

    }

    @Test
    public void testUpdateRent() throws Exception {

    }

    @Test
    public void testFindRentForRoom() throws Exception {

    }

    @Test
    public void testFindRentForGuest() throws Exception {

    }

    @Test
    public void testFindAllRents() throws Exception {

    }

    @Test
    public void testDeleteRent() throws Exception {

    }

    @Test
    public void testIsRoomAvailable() throws Exception {

    }

    @Test
    public void testFindRentById() throws Exception {

    }
}