package cz.muni.fi.pv168.swing;

import cz.muni.fi.pv168.Guest;
import cz.muni.fi.pv168.GuestManager;
import cz.muni.fi.pv168.GuestManagerImpl;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Pavel Kotala, 437164
 */
public class GuestSwingWorker extends SwingWorker<List<Guest>, Void> {
    private final GuestManager guestManager = GuestManagerImpl.getInstance();
    private Guest guest;
    private boolean delete;

    public GuestSwingWorker(Guest guest, boolean delete) {
        super();
        this.guest = guest;
        this.delete = delete;
    }
    
    @Override
    protected List<Guest> doInBackground() throws Exception {
        
        if(guest.getId() == null) {
            if(delete) {
                throw new RuntimeException("this should not happen, cannot delete guest with null id");
            } else {
                guestManager.createGuest(guest);
            }
        } else {
            if(delete) {
                guestManager.deleteGuest(guest);
            } else {
                guestManager.updateGuest(guest);
            }
        }
        return guestManager.findAllGuests();
    }
}
