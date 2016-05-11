package cz.muni.fi.pv168.swing;

import cz.muni.fi.pv168.Room;
import cz.muni.fi.pv168.RoomManager;
import cz.muni.fi.pv168.RoomManagerImpl;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Pavel Kotala, 437164
 */
public class RoomSwingWorker extends SwingWorker<List<Room>, Void> {
    private final RoomManager roomManager = RoomManagerImpl.getInstance();
    private Room room;
    private boolean delete;

    public RoomSwingWorker(Room room, boolean delete) {
        super();
        this.room = room;
        this.delete = delete;
    }
    
    @Override
    protected List<Room> doInBackground() throws Exception {
        if(room.getId() == null) {
            if(delete) {
                throw new RuntimeException("this should not happen, cannot delete room with null id");
            } else {
                roomManager.createRoom(room);
            }
        } else {
            if(delete) {
                roomManager.deleteRoom(room);
            } else {
                roomManager.updateRoom(room);
            }
        }

        return roomManager.findAllRooms();
    }
}
