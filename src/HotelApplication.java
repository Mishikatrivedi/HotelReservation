import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class HotelApplication {
    public static void main(String[] args) {

        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------");
        callingMainMenu();
    }
    public static void callingMainMenu(){
        MainMenu.displayMainMenu(); // main menu display
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Please select a number for the menu option");
        int val = verificationOfMenu();
        MainMenu.selectedOption(val);
    }
    public static void callingAdminMenu(){
        AdminMenu.displayAdminMenu(); // main menu display
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Please select a number for the menu option");
        int val = verificationOfMenu();
        AdminMenu.selectedOption(val);
    }
    public static int verificationOfMenu(){
        Scanner sc = new Scanner(System.in);
        boolean correctInput = false;
        int val = 0 ;
        while(!correctInput) {
            try {
                val = sc.nextInt();
                System.out.println("-------------------------------------------------------------------------");
                if(val > 0 && val <= 5 ){
                    correctInput = true;
                }else if(val <= 0 || val > 5 ){
                    System.out.println("Enter number between 1 - 5 ");
                }
            } catch (Exception ex) {
                System.out.println("Error : Invalid Input ; Enter number again ");
                sc.nextLine(); // Clear invalid input
            }
        }
        return val;
    }

}