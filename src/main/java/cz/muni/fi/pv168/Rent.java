
package cz.muni.fi.pv168;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Rent {
    private BigDecimal price;
    private Guest guest;
    private Room room;
    private LocalDate startTime;
    private LocalDate endTime;
    private boolean active;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Rent{" + "price=" + price + ", guest=" + guest + ", room=" + room + ", startTime=" + startTime + ", endTime=" + endTime + ", active=" + active + ", id=" + id + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.price);
        hash = 97 * hash + Objects.hashCode(this.guest);
        hash = 97 * hash + Objects.hashCode(this.room);
        hash = 97 * hash + Objects.hashCode(this.startTime);
        hash = 97 * hash + Objects.hashCode(this.endTime);
        hash = 97 * hash + (this.active ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rent other = (Rent) obj;
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.guest, other.guest)) {
            return false;
        }
        if (!Objects.equals(this.room, other.room)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        if (this.active != other.active) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }
    
}
