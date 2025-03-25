package menus;

import api.AdminResource;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private static final AdminMenu instance = new AdminMenu();
    private AdminMenu(){}
    public static AdminMenu getInstance(){
        return instance;
    }

    private static final Scanner input = new Scanner(System.in);
    private static final AdminResource adminResource = AdminResource.getInstance();


    public void showMenu(){

        while(true){
            System.out.println("\nAdmin Menu");
            System.out.println("1 : See all Customers");
            System.out.println("2 : See all Rooms");
            System.out.println("3 : See all Reservations");
            System.out.println("4 : Add a Room");
            System.out.println("5 : Back to Main Menu");

            //Get user choice
            System.out.println("\nPlease choose an option : ");
            String choice = input.nextLine().trim();

            //Handle user choice
            switch (choice) {
                case "1" :
                    seeCustomers();
                    break;
                case "2" :
                    seeRooms();
                    break;
                case "3" :
                    seeReservations();
                    break;
                case "4" :
                    addARoom();
                    break;
                case "5" :
                    goToMainMenu();
                    return;
                default :
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void seeCustomers() {
        for(Customer customer : adminResource.getAllCustomers()){
            System.out.println(customer);
        }
    }

    private void seeRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if(rooms == null) return;
        System.out.println("\nList of all Rooms");
        for(IRoom room : rooms){
            System.out.println(room);
        }
    }


    private void seeReservations() {
        adminResource.displayAllReservations();
    }

    private boolean roomValidation(String roomNumber){
        return adminResource.isRoomExists(roomNumber);
    }
    private void addARoom() {
        List<IRoom> addRooms = new ArrayList<>();
        while(true) {
            System.out.println("\nEnter room number");
            String roomNumber = input.nextLine().trim().toUpperCase();
            boolean isRoomInTheList = addRooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber));
            if(roomValidation(roomNumber) || isRoomInTheList){
                System.out.println("This room number already exists.");
            }else {
                System.out.println("Enter price per night");
                double price = isPriceValid();
                RoomType roomType;
                System.out.println("Enter room type: 1 for single bed or 2 for double bed");
                while (true) {
                    String chooseBed = input.nextLine().trim();
                    if (chooseBed.equals("1")) {
                        roomType = RoomType.SINGLE;
                        break;
                    } else if (chooseBed.equals("2")) {
                        roomType = RoomType.DOUBLE;
                        break;
                    } else {
                        System.out.println("Invalid option, choose 1 for single bed or 2 for double bed");
                    }
                }

                Room room = new Room(roomNumber, price, roomType);
                addRooms.add(room);
            }

            System.out.println("Would you like to add another room y/n");
            boolean response = confirmationMethod();
            if(!response) break;
        }
        adminResource.addRoom(addRooms);
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

    private void goToMainMenu() {
        System.out.println("Returning Back to Main Menu.");
    }

    private double isPriceValid(){
            try {
                String priceInString =  input.nextLine().trim();
                double price = Double.parseDouble(priceInString);
                if(price < 0) throw new IllegalArgumentException();
                return price;
            } catch (Exception e) {
                System.out.println("Error: Enter a valid price.");
                return isPriceValid();
            }
    }
}
