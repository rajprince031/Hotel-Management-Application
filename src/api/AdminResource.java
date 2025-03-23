package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;
import service.RoomService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    //get customer
    public static Customer getCustomer(String email){
        return CustomerService.getCustomer(email);
    }

    // add room
    public static void addRoom(List<IRoom> rooms){
        RoomService.addRoom(rooms);
    }

    // get all room
    public static Collection<IRoom> getAllRooms(){
        return RoomService.getAllRooms();
    }

    //get all customer
    public static Collection<Customer> getAllCustomers(){
        return CustomerService.getAllCustomers();
    }

    //display all reservations
    public static void displayAllReservations(){
        ReservationService.printAllReservation();
    }
}
