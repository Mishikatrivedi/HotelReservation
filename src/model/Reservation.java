package model;

import java.util.Date;

public class Reservation {
    public final Customer customer;
    public final IRoom room;
    public final Date checkInDate;
    public final Date checkOutDate;

    public Reservation(Customer customer , IRoom room , Date checkInDate , Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
    @Override
    public final String toString(){
        return " Customer " + customer + " \n Booking of Room : " + room  + " \n CheckIn date : " + checkInDate + " \n CheckOut date : " + checkOutDate;
    }
}
