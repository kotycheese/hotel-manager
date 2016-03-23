package cz.muni.fi.pv168.hotelManager;

import java.util.List;

/**
 * Created by PavelKotala on 9.3.2016.
 */
public interface RentManager {
    /**
     *
     * @param rent
     */
    public void createRent(Rent rent);

    /**
     *
     * @param rent
     */
    public void updateRent(Rent rent);

    /**
     *
     * @param rent
     */
    public void deleteRent(Rent rent);

    /**
     *
     * @return
     */
    public List<Rent> findAllRents();

    /**
     *
     * @param id
     * @return
     */
    public Rent getRentById(Long id);

    /**
     *
     * @param room
     * @return
     */
    public List<Rent> findRentsForRoom(Room room);

    /**
     *
     * @param guest
     * @return
     */
    public List<Rent> findRentsForGuest(Guest guest);

    /**
     *
     * @param room
     * @return
     */
    public boolean isRoomAvailable(Room room);

    /**
     *
     * @return
     */
    public List<Room> findAvailableRooms();
}
