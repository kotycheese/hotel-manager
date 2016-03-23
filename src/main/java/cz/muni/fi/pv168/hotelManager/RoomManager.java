package cz.muni.fi.pv168.hotelManager;

import java.util.List;

/**
 * Created by PavelKotala on 9.3.2016.
 */
public interface RoomManager {
    /**
     *
     * @param room
     */
    public void createRoom(Room room);

    /**
     *
     * @param room
     */
    public void updateRoom(Room room);

    /**
     *
     * @param room
     */
    public void deleteRoom(Room room);

    /**
     *
     * @return
     */
    public List<Room> findAllRooms();

    /**
     *
     * @param id
     * @return
     */
    public Room getRoomById(Long id);
}
