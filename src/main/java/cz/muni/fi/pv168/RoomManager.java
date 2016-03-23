package cz.muni.fi.pv168;

import java.util.List;

public interface RoomManager {
    
    void createRoom(Room room);
    
    void updateRoom(Room room);
    
    void deleteRoom(Room room);
    
    List<Room> findAllRooms();
    
    Room findRoomById(Long id);
    
}
