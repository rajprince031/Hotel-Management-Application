package menus;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;
import service.RoomService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    public static void showMenu(){
        boolean isRunning = true;

        while(isRunning){
            System.out.println("\nMain Menu");
            System.out.println("1 : Find and reserve a room");
            System.out.println("2 : See my reservations");
            System.out.println("3 : Create an account");
            System.out.println("4 : Admin");
            System.out.println("5 : Exit");

            //Get user choice
            System.out.println("Please choose an option : ");
            int choice = input.nextInt();

            //Handle user choice
            switch (choice) {
                case 1 :
                    findAndReserveRoom();
                    break;
                case 2 :
                    seeReservations();
                    break;
                case 3 :
                    createAccount();
                    break;
                case 4 :
                    adminMenu();
                    break;
                case 5 :
                    exitApplication();
                    isRunning = false;
                    break;
                default :
                    System.out.println("Invalid choice, please try again.");
            }

        }
    }

    private static void exitApplication() {
        System.out.println("Thank you for using your application.");
    }

    private static void adminMenu() {
        AdminMenu.showMenu();
    }

    private static void createAccount() {
        System.out.println("\nEnter First Name:");
        String firstName = input.next();
        System.out.println("Enter Last Name:");
        String lastName = input.next();
        System.out.println("Enter Email:");
        String email = input.next();
        if(validateCustomer(email)){
            System.out.println("This email is already registered. Please try another email address.");
            return;
        }
        try {
            Customer customer = CustomerService.addCustomer(firstName, lastName, email);
            System.out.println("Customer created: " + customer);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void seeReservations() {
        System.out.println("\nReservations:");
        System.out.println("Enter Email:");
        String email = input.next();
        if(!validateCustomer(email)){
            System.out.println("The email address doesn't exist.");
            return;
        }
        Customer customer = CustomerService.getCustomer(email);
        Collection<Reservation> reservationList = ReservationService.getCustomersReservation(customer);
        for(Reservation reservation : reservationList){
            System.out.println(reservation);
        }
    }

    private static void findAndReserveRoom() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("\nEnter CheckIn Date dd/mm/yyyy example 01/03/2025");
            Date checkInDate = dateFormat.parse(input.next());
            System.out.println("Enter CheckOut Date dd/mm/yyyy example 21/03/2025");
            Date checkOutDate = dateFormat.parse(input.next());

            for(IRoom room : HotelResource.findARoom(checkInDate, checkOutDate)){
                String roomNumber = room.getRoomNumber();
                RoomType roomType = room.getRoomType();
                double roomPrice = room.getRoomPrice();
                System.out.println("Room Number: "+roomNumber+" Room Type: "+roomType +" Price: "+roomPrice);
            }

            System.out.println("\nWould you like to book a room? y/n");
            boolean roomBookingConfirmation = confirmationMethod();
            if(roomBookingConfirmation){
                System.out.println("\nDo you have an account with us? y/n");
                boolean isExistingCustomer = confirmationMethod();
                if(isExistingCustomer){
                    System.out.println("Enter Email format: name@domain.extension");
                    String email = input.next();
                    if(!validateCustomer(email)){
                        System.out.println("The email address doesn't exist. Please create a new account.");
                        return;
                    }
                    System.out.println("What room number would you like to reserve");
                    String roomNumber = input.next();

                    Customer customer = CustomerService.getCustomer(email);
                    IRoom room = RoomService.getARoom(roomNumber);
                    Reservation reservation = ReservationService.reserveRoom(customer, room, checkInDate, checkOutDate);
                    System.out.println(reservation);
                }else{
                    System.out.println("Please create an account before proceeding.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }


    }

    private static boolean validateCustomer(String email){
        return CustomerService.customerExists(email);
    }

    private static boolean confirmationMethod(){
        char response = input.next().charAt(0);
        if(response == 'y' || response == 'Y'){
            return true;
        }
        if(response == 'n' || response == 'N'){
            return false;
        }
        while(true) {
            System.out.println("Please enter Y (yes) or N (No");
            response = input.next().charAt(0);
            if (response == 'y' || response == 'Y') {
                return true;
            }
            if (response == 'n' || response == 'N') {
                return false;
            }
        }
    }
}
