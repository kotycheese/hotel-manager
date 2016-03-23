package cz.muni.fi.pv168.hotelManager;

import java.util.List;

/**
 * Created by PavelKotala on 9.3.2016.
 */
public class RentManagerImpl implements RentManager {
    @Override
    public void createRent(Rent rent) {

    }

    @Override
    public void updateRent(Rent rent) {

    }

    @Override
    public void deleteRent(Rent rent) {

    }

    @Override
    public List<Rent> findAllRents() {
        return null;
    }

    @Override
    public Rent getRentById(Long id) {
        return null;
    }

    @Override
    public List<Rent> findRentsForRoom(Room room) {
        return null;
    }

    @Override
    public List<Rent> findRentsForGuest(Guest guest) {
        return null;
    }

    @Override
    public boolean isRoomAvailable(Room room) {
        return false;
    }

    @Override
    public List<Room> findAvailableRooms() {
        return null;
    }
}
