package cz.muni.fi.pv168.hotelManager;

import java.util.List;

/**
 * Created by PavelKotala on 9.3.2016.
 */
public interface GuestManager {
    /**
     *
     * @param guest
     */
    public void createGuest(Guest guest);

    /**
     *
     * @param guest
     */
    public void updateGuest(Guest guest);

    /**
     *
     * @param guest
     */
    public void deleteGuest(Guest guest);

    /**
     *
     * @return
     */
    public List<Guest> findAllGuests();

    /**
     *
     * @param id
     * @return
     */
    public Guest getGuestById(Long id);
}
