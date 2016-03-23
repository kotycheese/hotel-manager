package cz.muni.fi.pv168;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuestManagerImpl implements GuestManager {
    
    private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void createGuest(Guest guest) {
        if(guest==null) throw new IllegalArgumentException("guest should not be null");
        if(guest.getId()!=null) throw new IllegalArgumentException("guest id should not be assigned prior saving");
        if(!emailValidator(guest.getEmail())) throw new IllegalArgumentException("guest email is in wrong form");
        if(guest.getBorn() == null) throw new IllegalArgumentException("guest date of birth cannot be null");
        if(guest.getName() == null) throw new IllegalArgumentException("guest name cannot be null");
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
        
    }

    @Override
    public void deleteGuest(Guest guest) {
        
    }

    @Override
    public List<Guest> findAllGuests() {
        return null;
    }

    @Override
    public Guest findGuestById(Long id) {
        return null;
    }
    
}
