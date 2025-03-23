package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;
import service.CustomerService;
import service.ReservationService;
import service.RoomService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class  HotelResource {

    // get customer
    public static Customer getCustomer(String email){
        return CustomerService.getCustomer(email);
    }

    // add new customer
    public static void createACustomer(String email, String firstName, String lastName){
        CustomerService.addCustomer(firstName, lastName, email);
    }

    //get room
    public static IRoom getRoom(String roomNumber){
        return RoomService.getARoom(roomNumber);
    }


    // book a room
    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer = getCustomer(customerEmail);
        return ReservationService.reserveRoom(customer, room, checkInDate, checkOutDate);
    }

    // get customers reservations
    public static Collection<Reservation> getCustomersReservations(String customerEmail){
        List<Reservation> customerReservations = new ArrayList<>();
        Customer customer = getCustomer(customerEmail);
        for(Reservation reservation : ReservationService.getCustomersReservation(customer)){
            if(reservation.getCustomer().getEmail().equals(customer.getEmail())){
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    //find a room
    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return ReservationService.findRooms(checkIn, checkOut);
    }
}
