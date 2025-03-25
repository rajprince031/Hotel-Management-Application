package model;

import java.util.Date;

public class Reservation {
    private final Customer customer;
    private IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public void setRoom(IRoom room) {
        this.room = room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString(){
        return (
                this.customer+
                "\nRoom: " + this.room+
                "\nCheck in date: " + this.checkInDate+
                "\nCheck out date: " + this.checkOutDate
        );
    }
}
