package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class  HotelResource {

    private static final HotelResource instance = new HotelResource();
    private HotelResource() {}
    public static HotelResource getInstance() {
        return instance;
    }

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    // get customer
    public Customer getCustomer(String email){
        email = email.trim().toLowerCase();
        return customerService.getCustomer(email);
    }

    // add new customer
    public void createACustomer(String firstName, String lastName, String email){
        email = email.trim().toLowerCase();
        customerService.addCustomer(firstName, lastName, email);
    }

    // validate customer
    public boolean validateCustomer(String email){
        email = email.trim().toLowerCase();
        return customerService.customerExists(email);
    }

    //get room
    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }


    // book a room
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        customerEmail = customerEmail.trim().toLowerCase();
        try{
            Customer customer = getCustomer(customerEmail);
            if(customer == null) {
                return null;
            }
            return reservationService.reserveRoom(customer, room, checkInDate, checkOutDate);
        }catch (Exception exception){
            System.out.println("Error during room booking: "+exception.getMessage());
            return null;
        }

    }

    // get customers reservations
    public Collection<Reservation> getCustomersReservations(String customerEmail){
        customerEmail = customerEmail.trim().toLowerCase();
        Customer customer = getCustomer(customerEmail);
        if(customer == null) return null;
        return reservationService.getCustomersReservation(customer);
    }

    //find a room
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return reservationService.findRooms(checkIn, checkOut);
    }
}
