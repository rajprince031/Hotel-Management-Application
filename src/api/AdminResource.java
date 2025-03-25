package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static final AdminResource instance = new AdminResource();
    private AdminResource() {}
    public static AdminResource getInstance() {
        return instance;
    }

    private final static CustomerService customerService = CustomerService.getInstance();
    private final static ReservationService reservationService = ReservationService.getInstance();

    //get customer
    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    // add room
    public void addRoom(List<IRoom> rooms){
        reservationService.addRoom(rooms);
    }

    // Check if room is present or not
    public boolean isRoomExists(String roomNumber){
        return reservationService.isRoomExists(roomNumber);
    }

    // get a room by room number
    public IRoom getARoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    // get all room
    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    //get all customer
    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    //display all reservations
    public void displayAllReservations(){
        reservationService.printAllReservation();
    }
}
