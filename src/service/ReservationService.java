package service;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static ReservationService reference = new ReservationService();
    private ReservationService(){}
    public static ReservationService getReference(){
        return reference;
    }

    public static Map<String , IRoom> roomNumberMap = new TreeMap<>();
    private static Collection<Reservation> reserveCollection = new HashSet<>() ;
    private static Map<Customer ,List<Reservation>> customerReservationMap = new HashMap<>();

    public static void addRoom(IRoom room){
        roomNumberMap.put(room.getRoomNumber() , room);
    }

    public static IRoom getARoom(String roomId){
        if(roomNumberMap.containsKey(roomId))
        return roomNumberMap.get(roomId);
        else {
            System.out.println("This room Id doesnt exists !!! ");
            return null;
        }
    }

    public static Reservation reserveARoom(Customer customer , IRoom room , Date checkInDate , Date checkOutDate){
        Reservation roomReservation = new Reservation(customer , room , checkInDate , checkOutDate);
        reserveCollection.add(roomReservation);
        List<Reservation> reserveList;
        if(customerReservationMap.containsKey(customer)){
            reserveList = customerReservationMap.get(customer);
        }else {
            reserveList = new ArrayList<>();
        }
        reserveList.add(roomReservation);
        customerReservationMap.put(customer , reserveList);
        return roomReservation;
    }

    public static Collection<IRoom> findRooms(Date checkInDate , Date checkOutDate){
        Collection<IRoom> allRooms = AdminResource.getAllRooms();
        Collection<IRoom> copyAllRooms = filteringRooms(checkInDate , checkOutDate , allRooms);
        return copyAllRooms;
    }

    public static  Collection<IRoom> recommendRooms(Date checkInDate , Date checkOutDate ){
        Collection<IRoom> allRooms = AdminResource.getAllRooms();

        Date addedCheckIn = future7Day(checkInDate);
        Date addedCheckOut = future7Day(checkOutDate);

        Collection<IRoom> recommendation = filteringRooms(addedCheckIn , addedCheckOut , allRooms);
        if(!recommendation.isEmpty())
            System.out.println("On Your current Booking dates there are no rooms available , thus recommending for next 7 days ");
        return recommendation;
    }

    public static Date future7Day(Date date){
        Calendar c_date = Calendar.getInstance();
        c_date.setTime(date);
        c_date.add(Calendar.DAY_OF_MONTH , 7);
        Date addedDate = c_date.getTime();
        return addedDate;
    }

    private static Collection<IRoom> filteringRooms(Date checkInDate , Date checkOutDate , Collection<IRoom> allRooms){
        Collection<IRoom> copyAllRooms = new LinkedHashSet<>(allRooms);
        for(Reservation res : reserveCollection){
            Date resCheckIn = res.checkInDate;
            Date resCheckOut = res.checkOutDate;
            if(!(resCheckIn.after(checkOutDate)||resCheckOut.before(checkInDate))){
                copyAllRooms.remove(res.room);
            }
        }
        return copyAllRooms;
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer){
        return customerReservationMap.get(customer);
    }

    public static void printAllReservation(){
        if(reserveCollection.isEmpty())
            System.out.println("There are No Reservations yet!!");
        for(Reservation res : reserveCollection){
            System.out.println(res);
        }
    }

}
