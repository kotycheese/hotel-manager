package cz.muni.fi.pv168.hotelManager;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by PavelKotala on 9.3.2016.
 */
public class Rent {
    private Long id;
    private Room room;
    private Guest guest;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rent rent = (Rent) o;

        if (!id.equals(rent.id)) return false;
        if (!room.equals(rent.room)) return false;
        if (!guest.equals(rent.guest)) return false;
        if (!price.equals(rent.price)) return false;
        if (!startDate.equals(rent.startDate)) return false;
        return endDate.equals(rent.endDate);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + room.hashCode();
        result = 31 * result + guest.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", room=" + room +
                ", guest=" + guest +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
