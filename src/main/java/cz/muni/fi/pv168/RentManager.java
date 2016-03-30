
package cz.muni.fi.pv168;

import java.util.List;

public interface RentManager {
    
    void createRent(Rent rent);
    
    void updateRent(Rent rent);
    
    List<Rent> findRentForRoom(Room room);
    
    List<Rent> findRentForGuest(Guest guest);
    
    List<Rent> findAllRents();
    
    void deleteRent(Rent rent);
    
    Rent findRentById(Long id);
    
    
    
}
