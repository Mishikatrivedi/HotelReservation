package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class HotelResource {
    private static final HotelResource reference = new HotelResource();
    private HotelResource(){}
    public static HotelResource getInstance(){
        return reference;
    }

    public static Customer getCustomer(String email) throws InstanceNotFoundException {
        return CustomerService.getCustomer(email);
    }

    public static void createACustomer(String email , String firstName , String lastName){
        CustomerService.addCustomer(email , firstName , lastName);
    }

    public static IRoom getRoom(String roomNumber){
        return ReservationService.getARoom(roomNumber);
    }

    public static Reservation bookARoom(String customerEmail , IRoom room , Date checkInDate , Date checkOutDate) throws InstanceNotFoundException{
        Customer cust ;
        try{
            cust = getCustomer(customerEmail);
        }catch (InstanceNotFoundException ex){
            System.out.println(ex.getLocalizedMessage());
             throw new InstanceNotFoundException("");
        }
            return ReservationService.reserveARoom(cust , room , checkInDate , checkOutDate);
    }
    public static Collection<Reservation> getCustomersReservations(String customerEmail) throws InstanceNotFoundException{
        Customer cust ;
        try{
            cust = getCustomer(customerEmail);
        }catch (InstanceNotFoundException ex){
            System.out.println(ex.getLocalizedMessage());
            throw new InstanceNotFoundException("");
        }
            return ReservationService.getCustomersReservation(cust);
    }

    public static Collection<IRoom> findARoom(Date checkIn , Date checkOut){
        return ReservationService.findRooms(checkIn , checkOut);
    }
    public static Collection<IRoom> recommendRooms(Date checkIn , Date checkOut){
        return ReservationService.recommendRooms(checkIn , checkOut);
    }
    public static Date future7Day(Date date){
        return ReservationService.future7Day(date);
    }
}
