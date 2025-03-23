package menus;

import api.AdminResource;
import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final Scanner input = new Scanner(System.in);
    public static void showMenu(){
        boolean isRunning = true;

        while(isRunning){
            System.out.println("\nAdmin Menu");
            System.out.println("1 : See all Customers");
            System.out.println("2 : See all Rooms");
            System.out.println("3 : See all Reservations");
            System.out.println("4 : Add a Room");
            System.out.println("5 : Back to Main Menu");

            //Get user choice
            System.out.println("Please choose an option : ");
            int choice = input.nextInt();

            //Handle user choice
            switch (choice) {
                case 1 :
                    seeCustomers();
                    break;
                case 2 :
                    seeRooms();
                    break;
                case 3 :
                    seeReservations();
                    break;
                case 4 :
                    addARoom();
                    break;
                case 5 :
                    goToMainMenu();
                    isRunning = false;
                    break;
                default :
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void seeRooms() {
        Collection<IRoom> rooms = AdminResource.getAllRooms();
        System.out.println("\nList of all Rooms");
        for(IRoom room : rooms){
            System.out.println(room);
        }
    }

    private static void addARoom() {
        Scanner input = new Scanner(System.in);
        boolean isRunning = true;
        List<IRoom> rooms = new ArrayList<>();
        while(isRunning) {
            System.out.println("\nEnter room number");
            String roomNumber = input.next();
            double price;
            try {
                System.out.println("Enter price per night");
                price = input.nextDouble();
            } catch (Exception e) {
                System.out.println("Error, Invalid price");
                return;
            }
            System.out.println("Enter room type: 1 for single bed, 2 for double bed");
            int chooseBed = input.nextInt();
            RoomType roomType;
            if (chooseBed == 1) {
                roomType = RoomType.SINGLE;
            } else if(chooseBed == 2) {
                roomType = RoomType.DOUBLE;
            }else{
                System.out.println("Invalid option, try again!");
                return;
            }
            Room room = new Room(roomNumber, price, roomType);
            rooms.add(room);

            System.out.println("Would you like to add another room y/n");
            char response = input.next().charAt(0);
            if (response == 'y' || response == 'Y') {
                continue;
            } else if (response == 'n' || response == 'N') {
                isRunning = false;
                break;
            } else {
                while (true) {
                    System.out.println("Please enter Y (yes) or N (No");
                    response = input.next().charAt(0);
                    if (response == 'y' || response == 'Y') {
                        break;
                    } else if (response == 'n' || response == 'N') {
                        isRunning = false;
                        break;
                    }
                }
            }
        }
        AdminResource.addRoom(rooms);
    }

    private static void goToMainMenu() {
        MainMenu.showMenu();
    }

    private static void seeCustomers() {
        System.out.println("\nAll Customers");
        for(Customer customer : AdminResource.getAllCustomers()){
            System.out.println(customer);
        }
    }
    

    private static void seeReservations() {
        System.out.println("\nAll Reservations");
       AdminResource.displayAllReservations();
    }

}
