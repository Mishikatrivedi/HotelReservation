import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    static Scanner sc = new Scanner(System.in);
    public static void displayAdminMenu(){
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
    }
    public static void selectedOption(int val){
        switch (val){
            // see all customers , no further values entered
            // double check done
            case 1: {
                Collection<Customer> allCust = AdminResource.getALlCustomers();
                System.out.println("Total Customers Registered : " + allCust.size());
                for(Customer cust : allCust){
                    System.out.println(cust.toString());
                }
                System.out.println("-------------------------------------------------------------------------");
                HotelApplication.callingAdminMenu();
                break;
            }
            // see all rooms , no further values entered
                // double check done , filled and empty
            case 2: {
                Collection<IRoom> allRooms = AdminResource.getAllRooms();
                if (allRooms.isEmpty())
                    System.out.println("There are no Rooms in the hotel");
                for(IRoom addedRoom : allRooms){
                    System.out.println(addedRoom);
                }
                System.out.println("-------------------------------------------------------------------------");
                HotelApplication.callingAdminMenu();
                break;
            }
            // see all reservations , no further values entered
                // double check , filled check done
            case 3: {
                AdminResource.displayAllReservations();
                System.out.println("-------------------------------------------------------------------------");
                HotelApplication.callingAdminMenu();
                break;
            }
            // add a room
                // room Id uniqueness added
                // all values entered constraints added
            case 4: {
                List<IRoom> roomsList = new ArrayList<>();
                List<String> roomsIdList = new ArrayList<>();
                String yesNo ="";
            while(!yesNo.equalsIgnoreCase("n")) {
                String roomId;
                System.out.println("list size " + roomsIdList.size());
                while(true){
                    System.out.println("Enter Room Number : ");
                    roomId = numberCheck();
                    if(roomsIdList.contains(roomId) || AdminResource.roomExists(roomId)){
                        System.out.println("Room Id already exists , Enter another ID : ");
                        continue;
                    }
                    roomsIdList.add(roomId);
                    break;
                }
                boolean validPrice = false;
                Double price = 0.0;
                System.out.println("Enter price per night :");
                while(!validPrice){
                    try {
                        price = Double.parseDouble(sc.nextLine());
                        validPrice = true;
                    } catch (Exception ex) {
                        System.out.println("Enter valid price (only numbers) : ");
                    }
                }

                System.out.println("Enter Room Type : 1 for SINGLE bed , 2 for DOUBLE bed : ");
                RoomType roomType = null;
                boolean defaultVal = true;
                int roomTypeNum = 0;
                while(defaultVal){
                    try{
                        roomTypeNum = sc.nextInt();
                        defaultVal = false;
                        switch (roomTypeNum) {
                            case 1:
                                roomType = RoomType.SINGLE;
                                break;
                            case 2:
                                roomType = RoomType.DOUBLE;
                                break;
                            default: System.out.println("Enter either 1 or 2 Only!!");
                                defaultVal = true;
                        }
                    }catch (Exception ex){
                        defaultVal = true;
                        sc.nextLine();
                        System.out.println("Enter either 1 or 2 , Please!!");
                    }

                }


                Room roomCreated = new Room(roomId, price, roomType);
                roomsList.add(roomCreated);

                sc.nextLine(); // consumes leftovers from nextInt()

                System.out.println("Would you like to add another room y/n : ");
                yesNo = enterOnlyYorN();
            }
            AdminResource.addRoom(roomsList);
            System.out.println("-------------------------------------------------------------------------");
            HotelApplication.callingAdminMenu();
                break;
            }
            // return to main menu , no further values entered
            case 5: {
                System.out.println("-------------------------------------------------------------------------");
                HotelApplication.callingMainMenu();
                System.out.println("-------------------------------------------------------------------------");
                break;
            }
        }
    }

    public static String numberCheck(){
        String number ;
        boolean isNotNumber = true;
        while(isNotNumber){
            number = sc.nextLine();
            if(number.matches("\\d+")) // checks it contains only numbers
                return number;
            else {
                System.out.println("Enter valid Number !!");
                isNotNumber = true;
            }
        }
        return null;
    }
    public static String enterOnlyYorN(){
        String enterYorN = sc.nextLine();
        while(!(enterYorN.equalsIgnoreCase("y")|| enterYorN.equalsIgnoreCase("n"))){
            System.out.println("Enter Y or N only !");
            enterYorN = sc.nextLine();
        }
        return enterYorN;
    }
}
