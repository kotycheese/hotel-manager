package cz.muni.fi.pv168.swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import cz.muni.fi.pv168.Guest;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

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
    
    @Override
    public String getColumnName(int columnIndex) {
        ResourceBundle bundle = ResourceBundle.getBundle("texty", Locale.getDefault());
        switch (columnIndex) {
            case 0:
                return bundle.getString("id");
            case 1:
                return bundle.getString("name");
            case 2:
                return bundle.getString("born");
            case 3:
                return bundle.getString("email");
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    public void setGuests(List<Guest> guests) {
        this.guests = guests;
        fireTableRowsInserted(0, guests.size()-1);
    }
    
    public Guest getGuestAt(int i) {
        return guests.get(i);
    }
    public List<Guest> getGuests() {
        return Collections.unmodifiableList(guests);
    }
}
