package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class AdminResource {
    private static final AdminResource reference = new AdminResource();
    private AdminResource(){}
    public static AdminResource getInstance(){
        return reference;
    }

    public Customer getCustomer(String email) throws InstanceNotFoundException {
        return CustomerService.getCustomer(email);
    }

    public static boolean roomExists(String roomId){
        if(ReservationService.roomNumberMap.containsKey(roomId))
            return true;
        else return false;
    }

    public static void addRoom(List<IRoom> rooms){
        for(IRoom room : rooms){
            ReservationService.addRoom(room);
            System.out.println(room);
        }
    }

    public static Collection<IRoom> getAllRooms(){
        Collection<IRoom> allRooms = new LinkedHashSet<>();
        for(String keyRoomId : ReservationService.roomNumberMap.keySet()){
            allRooms.add(ReservationService.roomNumberMap.get(keyRoomId));
        }
        return allRooms;
    }

    public static Collection<Customer> getALlCustomers(){
        return CustomerService.getAllCustomers();
    }

    public static void displayAllReservations(){
        ReservationService.printAllReservation();
    }
}
