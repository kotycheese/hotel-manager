package cz.muni.fi.pv168.swing;

import cz.muni.fi.pv168.Rent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Pavel Kotala, 437164
 */
public class RentTableModel extends AbstractTableModel {

    List<Rent> rents = new ArrayList<>();
    @Override
    public int getRowCount() {
        return rents.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rent rent = rents.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return rent.getId();
            case 1:
                return rent.getGuest();
            case 2:
                return rent.getRoom();
            case 3:
                return rent.getStartDate();
            case 4:
                return rent.getEndDate();
            case 5:
                return rent.getPrice();
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
                return bundle.getString("guest");
            case 2:
                return bundle.getString("room");
            case 3:
                return bundle.getString("start_date");
            case 4:
                return bundle.getString("end_date");
            case 5:
                return bundle.getString("price");
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    public void setRents(List<Rent> rents) {
        this.rents = rents;
        fireTableRowsInserted(0, rents.size()-1);
    }
    
    public Rent getRentAt(int i) {
        return rents.get(i);
    }
}
