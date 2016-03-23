package cz.muni.fi.pv168.hotelManager;

import java.math.BigDecimal;

/**
 * Created by PavelKotala on 9.3.2016.
 */
public class Room {
    private Long id;
    private int number;
    private int floor;
    private int numBeds;
    private BigDecimal pricePerNight;
    private boolean kitchen;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (number != room.number) return false;
        if (floor != room.floor) return false;
        if (numBeds != room.numBeds) return false;
        if (kitchen != room.kitchen) return false;
        if (!id.equals(room.id)) return false;
        return pricePerNight.equals(room.pricePerNight);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + number;
        result = 31 * result + floor;
        result = 31 * result + numBeds;
        result = 31 * result + pricePerNight.hashCode();
        result = 31 * result + (kitchen ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number=" + number +
                ", floor=" + floor +
                ", numBeds=" + numBeds +
                ", pricePerNight=" + pricePerNight +
                ", kitchen=" + kitchen +
                '}';
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }
}
