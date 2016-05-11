
package cz.muni.fi.pv168;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author David
 */
public class Room {
    private Long id;
    private int number;
    private int beds;
    private BigDecimal pricePerNight;
    private String note;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + this.number;
        hash = 71 * hash + this.beds;
        hash = 71 * hash + Objects.hashCode(this.pricePerNight);
        hash = 71 * hash + Objects.hashCode(this.note);
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
        final Room other = (Room) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        if (this.beds != other.beds) {
            return false;
        }
        if (!Objects.equals(this.pricePerNight, other.pricePerNight)) {
            return false;
        }
        return Objects.equals(this.note, other.note);
    }

    @Override
    public String toString() {
        return number + "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    
}
