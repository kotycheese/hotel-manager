package cz.muni.fi.pv168.swing;

import cz.muni.fi.pv168.Room;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Pavel Kotala, 437164
 */
public class RoomTableModel extends AbstractTableModel {

    private List<Room> rooms = new ArrayList<>();
    
    @Override
    public int getRowCount() {
        return rooms.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Room room = rooms.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return room.getId();
            case 1:
                return room.getNumber();
            case 2:
                return room.getBeds();
            case 3:
                return room.getPricePerNight();
            case 4:
                return room.getNote();
            default:
                throw new IllegalArgumentException("Illegal column index: " + columnIndex);
        }
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        ResourceBundle bundle = ResourceBundle.getBundle("texty");
        switch (columnIndex) {
            case 0:
                return bundle.getString("id");
            case 1:
                return bundle.getString("number");
            case 2:
                return bundle.getString("beds");
            case 3:
                return bundle.getString("price_per_night");
            case 4:
                return bundle.getString("note");
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
        fireTableRowsInserted(0, rooms.size()-1);
    }
    
    public Room getRoomAt(int i) {
        return rooms.get(i);
    }
    
    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }
}
