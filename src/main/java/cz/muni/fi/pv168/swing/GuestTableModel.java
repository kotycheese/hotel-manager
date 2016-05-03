package cz.muni.fi.pv168.swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import cz.muni.fi.pv168.Guest;

/**
 *
 * @author Pavel Kotala, 437164
 */
public class GuestTableModel extends AbstractTableModel {

    private List<Guest> guests = new ArrayList<>();
    
    @Override
    public int getRowCount() {
        return guests.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Guest guest = guests.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return guest.getId();
            case 1:
                return guest.getName();
            case 2:
                return guest.getBorn();
            case 3:
                return guest.getEmail();
            default:
                throw new IllegalArgumentException("Illegal column index: " + columnIndex);
        }
    }
    
}
