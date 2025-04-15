import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import javax.management.InstanceNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class MainMenu {
    static Scanner sc = new Scanner(System.in);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static void displayMainMenu(){
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
    }
    public static void selectedOption(int val){
        switch (val){
            // find rooms and reserve them
            // dates sequence to be added =>  done
            // finding done
            // reserving done
            case 1: {
                boolean errorFound = true;
                while (errorFound) {
                    Date checkInDate;
                    Date checkOutDate;
                    do {
                        System.out.println("CheckOut Date must be after CheckIn Date");
                        System.out.println("Enter CheckIn Date dd-MM-yyyy format : ");
                        checkInDate = checkDateFormat();
                        System.out.println("Enter CheckOut Date dd-MM-yyyy format : ");
                        checkOutDate = checkDateFormat();
                    }while(!checkOutDate.after(checkInDate));

                    Collection<IRoom> roomsFound = HotelResource.findARoom(checkInDate, checkOutDate);
                    Collection<IRoom> recommendationRooms = null ;
                    boolean roomORrecommend = true; // when roomsFound
                    if(roomsFound.isEmpty()){
                        recommendationRooms = HotelResource.recommendRooms(checkInDate , checkOutDate);
                        checkInDate = HotelResource.future7Day(checkInDate);
                        checkOutDate = HotelResource.future7Day(checkOutDate);
                        roomORrecommend = false; // when recommend taken
                    }
                    if (!roomsFound.isEmpty() || !recommendationRooms.isEmpty()) {
                        Collection<IRoom> roomVariableList = new LinkedHashSet<>(roomORrecommend ? roomsFound : recommendationRooms);
                        for (IRoom roomFound : roomVariableList) {
                            System.out.println(roomFound);
                        }

                        System.out.println("Would you like to book a room ? y/n");
                        String bookOrNot = enterOnlyYorN();

                        if (bookOrNot.equalsIgnoreCase("y")) {
                            System.out.println("Do you have a account with us ? y/n");
                            String accountOrNot = enterOnlyYorN();
                            if (accountOrNot.equalsIgnoreCase("n")) {
                                createAnAccount();
                            }
                            String email3 = "";
                            boolean incorrectEmail = true;
                            while(incorrectEmail){
                                try {
                                    System.out.println("Enter email format : name@domain.com ");
                                    email3 = sc.nextLine();
                                    Customer enteredCust = HotelResource.getCustomer(email3);
                                    incorrectEmail = false;
                                } catch (InstanceNotFoundException e) {
                                    System.out.println(e.getLocalizedMessage());
                                    System.out.println("Enter 1 : Enter Email again , 2 : Create an Account first !");
                                    boolean defaultVal = true;
                                    while(defaultVal){
                                        int input = sc.nextInt();
                                        sc.nextLine();
                                        switch (input) {
                                            case 1:
                                                incorrectEmail = true;
                                                defaultVal = false;
                                                break;
                                            case 2:
                                                createAnAccount();
                                                defaultVal = false;
                                                break;
                                            default:
                                                System.out.println("Enter 1 or 2 Please");
                                        }
                                    }
                                }
                            }
                            boolean roomCorrectId = false;
                            while(!roomCorrectId){
                                try {
                                    System.out.println("What room number would you like to reserve: ");
                                    String roomNumberToReserve = sc.nextLine();
                                    IRoom roomToReserve = HotelResource.getRoom(roomNumberToReserve);
                                    if(!roomVariableList.contains(roomToReserve)){
                                        System.out.println("The room is not available for booking , Enter available Room :");
                                        roomCorrectId = false;
                                    }
                                    else if (roomToReserve != null) {
                                        System.out.println(HotelResource.bookARoom(email3, roomToReserve, checkInDate, checkOutDate));
                                        roomCorrectId = true;
                                    } else {
                                        System.out.println("Enter valid Room ID : ");
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex.getLocalizedMessage());
                                }
                            }
                        }
                        errorFound = false;
                    }
                    else {
                        System.out.println("There are No Rooms for these dates and later 7 days also ");
                        System.out.println("Do you wanna check for any other dates ?? Enter Y or N :");
                        String otherDates = enterOnlyYorN();
                        if (otherDates.equalsIgnoreCase("y"))
                            errorFound = true;
                        else errorFound = false;
                    }
                }
                HotelApplication.callingMainMenu();
                break;
            }

            // viewing reservations , and verifying mail also
                // single check , filled check done
            case 2: {
                boolean errorFound = true;
                while(errorFound){
                    try{
                        System.out.println("Enter your Email ID : ");
                        String email1 = sc.nextLine();
                        Customer cust = HotelResource.getCustomer(email1);
                        Collection<Reservation> custReserve = HotelResource.getCustomersReservations(email1);
                        if(custReserve!= null && !custReserve.isEmpty()){
                            for (Reservation res : custReserve)
                                System.out.println(res);
                        }else{
                            System.out.println("There are no Reservations right now , please make the Reservations!!");
                            System.out.println("-------------------------------------------------------------------------");
                        }
                        errorFound = false;
                    }catch (InstanceNotFoundException ex){
                        System.out.println(ex.getLocalizedMessage());
                        do{
                            System.out.println("Enter 1 : Enter email again , 2 : Create an account First !");
                            int input = sc.nextInt();
                            sc.nextLine();
                            switch (input) {
                                case 1:
                                    break;
                                case 2:
                                    errorFound = false;
                                    break;
                                default:System.out.println("Enter 1 or 2 only");
                            }
                            if(input == 1 || input == 2)
                                break;
                        }while(true);
                    }
                }
                HotelApplication.callingMainMenu();
                break;
            }
            // creating an account , thus requires email , first name and last name
                // double check done , error check done , proper check and storage check done
            case 3: {
                createAnAccount();
                HotelApplication.callingMainMenu();
                break;
            }

            // going to the admin page , no further inputs taken.
            case 4: {
                HotelApplication.callingAdminMenu();
                System.out.println("-------------------------------------------------------------------------");
                HotelApplication.callingMainMenu();
                break;
            }
            // Exit the app , no further inputs taken.
            case 5: {
                break;
            }
        }
    }

    public static Date checkDateFormat(){
        Date date ;
        String d ;
        Date today = new Date();
        while(true){
            try{
                d = sc.nextLine();
                date = dateFormat.parse(d);

                if(date.after(today))
                    return date;
                else System.out.println("Date must be after today , Enter it again : ");
            }catch (ParseException ex){
                System.out.println("Invalid date format! Enter again : ");
            }
        }
    }

    public static String enterOnlyYorN(){
        String enterYorN = sc.nextLine();
        while(!(enterYorN.equalsIgnoreCase("y")|| enterYorN.equalsIgnoreCase("n"))){
            System.out.println("Enter Y or N only !");
            enterYorN = sc.nextLine();
        }
        return enterYorN;
    }

    public static void createAnAccount(){
        System.out.println("Create an account : ");
        boolean error_found = true;
        while(error_found){
            try {
                System.out.println("Enter Email format : name@domain.com ");
                String email2 = sc.nextLine();
                System.out.println("Enter first Name : ");
                String firstName = sc.nextLine();
                System.out.println("Enter LastName : ");
                String lastName = sc.nextLine();
                HotelResource.createACustomer(email2, firstName, lastName);
                error_found = false;
            } catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
                error_found = true;
            }
        }
    }
}
