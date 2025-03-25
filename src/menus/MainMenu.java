package menus;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import static java.util.regex.Pattern.matches;

public class MainMenu {

    private static final MainMenu instance = new MainMenu();
    private MainMenu() {}
    public static MainMenu getInstance() {
        return instance;
    }

    private static final Scanner input = new Scanner(System.in);
    private static final HotelResource hotelResource = HotelResource.getInstance();

    public void showMenu(){
        boolean isRunning = true;

        while(isRunning){
            System.out.println("\nMain Menu");
            System.out.println("1 : Find and reserve a room");
            System.out.println("2 : See my reservations");
            System.out.println("3 : Create an account");
            System.out.println("4 : Admin");
            System.out.println("5 : Exit");

            //Get user choice
            System.out.println("\nPlease choose an option : ");
            String choice = input.nextLine().trim();

            //Handle user choice
            switch (choice) {
                case "1" :
                    findAndReserveRoom();
                    break;
                case "2" :
                    seeReservations();
                    break;
                case "3" :
                    createAccount();
                    break;
                case "4" :
                    adminMenu();
                    break;
                case "5" :
                    exitApplication();
                    isRunning = false;
                    break;
                default :
                    System.out.println("Invalid choice, please try again.");
            }

        }
    }

    private Date getDateAfterSevenDays(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return calendar.getTime();
    }

    // find and reserve room
    private void findAndReserveRoom() {
        try {
            System.out.println("\nEnter CheckIn Date yyyy-MM-dd example 2025-01-22");
            Date checkInDate = enterDate();
            System.out.println("Enter CheckOut Date yyyy-MM-dd example 2025-01-31");
            Date checkOutDate = enterDate();

            Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
            if(availableRooms.isEmpty()){
                System.out.println("\nNo available rooms for the given dates.");

                System.out.println("Do you want to view the reservations for the next week? (y/n)");
                if(!confirmationMethod()) return;
                checkInDate = getDateAfterSevenDays(checkInDate);
                checkOutDate = getDateAfterSevenDays(checkOutDate);
                SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("Recommended room for Check-in: "+setDateFormat.format(checkInDate)+", Check-out: " +setDateFormat.format(checkOutDate));
                availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
                if(availableRooms.isEmpty()){
                    System.out.println("\nNo rooms available.\n");
                    return;
                }
            }

            System.out.println("Available Rooms:");
            for(IRoom room : availableRooms){
                System.out.println(room);
            }

            System.out.println("\nWould you like to book a room? y/n");
            boolean roomBookingConfirmation = confirmationMethod();
            if(roomBookingConfirmation){
                System.out.println("\nDo you have an account with us? y/n");
                boolean isExistingCustomer = confirmationMethod();
                if(!isExistingCustomer){
                    System.out.println("Please create an account before proceeding.");
                    return;
                }
                System.out.println("Enter Email format: name@domain.extension");
                String email = input.nextLine();
                if(!validateCustomer(email)){
                    System.out.println("The email address doesn't exist. Please create a new account.");
                    return;
                }
                System.out.println("Enter the room number you'd like to reserve:");
                String roomNumber;
                boolean isRoomAvailableInList = false;
                 do{
                    roomNumber = input.nextLine().trim();
                    for(IRoom room : availableRooms){
                        if(room.getRoomNumber().equals(roomNumber)){
                            isRoomAvailableInList = true;
                            break;
                        }
                    }
                    if(!isRoomAvailableInList) System.out.println("Room Number not in the List, Reenter the room Number.");
                }while(!isRoomAvailableInList);
                IRoom room = hotelResource.getRoom(roomNumber);
                Reservation reservation = hotelResource.bookARoom(email, room, checkInDate, checkOutDate);

                if (reservation == null) throw new NullPointerException();

                System.out.println("Reservation successful");
                System.out.println(reservation);
            }
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }

    }

    // Validate customer is existing with this email or not
    private boolean validateCustomer(String email){
        return hotelResource.validateCustomer(email);
    }

    // Take an input as string validate and parse it to the date
    private Date enterDate() {
        String inputDate = input.nextLine().trim();
        try {
            final String dateRegex = "^(?:19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
            // If the input doesn't match the expected date format
            if (!inputDate.matches(dateRegex)) throw new IllegalArgumentException("Invalid date format");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(inputDate);  // Successfully parsed date
        } catch (Exception exception) {
            System.out.println("Wrong Date Pattern. Please enter the date in yyyy-MM-dd format.");
            return enterDate();
        }
    }

    // Get all the reservation of the customer
    private void seeReservations() {
        System.out.println("Enter your email address: ");
        String email = input.nextLine();
        if(!validateCustomer(email)){
            System.out.println("The email address doesn't exist.");
            return;
        }
        Collection<Reservation> reservationList = hotelResource.getCustomersReservations(email);
        for(Reservation reservation : reservationList){
            System.out.println(reservation.getRoom());
            System.out.println("Check In date : "+reservation.getCheckInDate());
            System.out.println("Check out date : "+reservation.getCheckOutDate());
        }
    }

    //Create a customer account
    private void createAccount() {
        System.out.println("\nEnter First Name:");
        String firstName = input.nextLine().trim();
        System.out.println("Enter Last Name:");
        String lastName = input.nextLine().trim();
        System.out.println("Enter Email:");
        String email = validateEmail();
        if(validateCustomer(email)){
            System.out.println("This email is already registered. Please try another email address.");
            return;
        }
        try {
            hotelResource.createACustomer(firstName, lastName, email);
            System.out.println("Customer created Successfully");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // validate email
    private String validateEmail(){
        final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        String email = input.nextLine().trim().toLowerCase();
        if(email.matches(emailRegex)){
            return email;
        }
        System.out.println("Please Enter a valid email. example : name@domin.extension");
        return validateEmail();
    }

    // go to admin menu
    private void adminMenu() {
        AdminMenu.getInstance().showMenu();
    }

    // Exit application method
    private void exitApplication() {
        System.out.println("Thank you for using your application.");
    }

    // Confirmation method to get user response
    private boolean confirmationMethod(){
        String response = input.nextLine().trim().toLowerCase();
        if(response.equals("y")){
            return true;
        }
        if(response.equals("n")){
            return false;
        }
        System.out.println("Please enter Y (yes) or N (No)");
        return confirmationMethod();
    }
}
