package cz.muni.fi.pv168;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;

public class GuestManagerImpl implements GuestManager {
    
    private final DataSource dataSource;
    private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public GuestManagerImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void createGuest(Guest guest) {
        if(guest==null){
            throw new IllegalArgumentException("guest should not be null");
        }
        if(guest.getId()!=null) {
            throw new IllegalArgumentException("guest id should not be assigned prior saving");
        }
        if(!emailValidator(guest.getEmail())) {
            throw new IllegalArgumentException("guest email is in wrong form");
        }
        //test born?
        if(guest.getBorn() == null) {
            throw new IllegalArgumentException("guest date of birth cannot be null");
        }
        if(guest.getName() == null) {
            throw new IllegalArgumentException("guest name cannot be null");
        }
        if(guest.getName().length()==0) {
            throw new IllegalArgumentException("guest name cannot be empty");
        }
        
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "INSERT INTO GUEST (name,born,email) VALUES (?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            Date date = Date.valueOf(guest.getBorn());
            st.setString(1, guest.getName());
            st.setDate(2, date);
            st.setString(3, guest.getEmail());
            
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("Internal Error: More rows ("
                        + addedRows + ") inserted when trying to insert guest " + guest);
            }
            
            ResultSet keyRS = st.getGeneratedKeys();
            guest.setId(getKey(keyRS, guest));
        
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting guest " + guest, ex);
        }
    }
    
    private Long getKey(ResultSet keyRS, Guest guest) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert guest " + guest
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert guest " + guest
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert guest " + guest
                    + " - no key found");
        }
    }
    
    private boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
	pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void updateGuest(Guest guest) {
        if(guest==null){
            throw new IllegalArgumentException("guest should not be null");
        }
        if(guest.getId()==null) {
            throw new IllegalArgumentException("guest id should not be null");
        }
        if(!emailValidator(guest.getEmail())) {
            throw new IllegalArgumentException("guest email is in wrong form");
        }
        //test born?
        if(guest.getBorn() == null) {
            throw new IllegalArgumentException("guest date of birth cannot be null");
        }
        if(guest.getName() == null) {
            throw new IllegalArgumentException("guest name cannot be null");
        }
        if(guest.getName().length()==0) {
            throw new IllegalArgumentException("guest name cannot be empty");
        }
        
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "UPDATE Guest SET name = ?, born = ?, email = ? WHERE id = ?")) {
            st.setString(1, guest.getName());
            st.setString(3, guest.getEmail());
            Date date = Date.valueOf(guest.getBorn());
            st.setDate(2, date);
            st.setLong(4, guest.getId());
            
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("Internal Error: More rows ("
                        + addedRows + ") inserted when trying to insert guest " + guest);
            } else if (addedRows != 1) {
                throw new ServiceFailureException("Invalid updated rows count detected "
                        + "(one row should be updated): " + addedRows);
            }
            
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when updating guest " + guest, ex);
        }
    }

    @Override
    public void deleteGuest(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException("guest is null");
        }
        if (guest.getId() == null) {
            throw new IllegalArgumentException("guest id is null");
        }
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement("DELETE FROM guest WHERE id = ?")) {

            st.setLong(1, guest.getId());

            int count = st.executeUpdate();
            if (count == 0) {
                throw new IllegalArgumentException("Guest " + guest + " was not found in database!");
            } else if (count != 1) {
                throw new ServiceFailureException("Invalid deleted rows count detected (one row should be updated): " + count);
            }
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when updating guest " + guest, ex);
        }
    }

    @Override
    public List<Guest> findAllGuests() {
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "SELECT id,name,born,email FROM guest")) {

            ResultSet rs = st.executeQuery();

            List<Guest> result = new ArrayList<>();
            while (rs.next()) {
                Guest guest = new Guest();
                guest.setId(rs.getLong("id"));
                Date date = rs.getDate("born");
                guest.setBorn(date.toLocalDate());
                guest.setEmail(rs.getString("email"));
                guest.setName("name");
                result.add(guest);
            }
            return result;

        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving all guests", ex);
        }
    }

    @Override
    public Guest findGuestById(Long id) {
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(
                "SELECT id,name,born,email FROM guest WHERE id = ?")) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Guest guest = new Guest();
                guest.setId(rs.getLong("id"));
                Date date = rs.getDate("born");
                guest.setBorn(date.toLocalDate());
                guest.setEmail(rs.getString("email"));
                guest.setName(rs.getString("name"));

                if (rs.next()) {
                    throw new ServiceFailureException("Internal error: More entities with the same id found ");
                }
                return guest;
            } else {
                return null;
            }
        
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when retrieving guest with id " + id, ex);
        }
    }
    
}
