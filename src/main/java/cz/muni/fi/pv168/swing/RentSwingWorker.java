package cz.muni.fi.pv168.swing;

import cz.muni.fi.pv168.Rent;
import cz.muni.fi.pv168.RentManager;
import cz.muni.fi.pv168.RentManagerImpl;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Pavel Kotala, 437164
 */
public class RentSwingWorker extends SwingWorker<List<Rent>, Void> {
    private final RentManager rentManager = RentManagerImpl.getInstance();
    private Rent rent;
    private boolean delete;

    public RentSwingWorker(Rent rent, boolean delete) {
        super();
        this.rent = rent;
        this.delete = delete;
    }
    
    @Override
    protected List<Rent> doInBackground() throws Exception {
        if(rent.getId() == null) {
            if(delete) {
                throw new RuntimeException("this should not happen, cannot delete rent with null id");
            } else {
                rentManager.createRent(rent);
            }
        } else {
            if(delete) {
                rentManager.deleteRent(rent);
            } else {
                rentManager.updateRent(rent);
            }
        }

        return rentManager.findAllRents();
    }
}
