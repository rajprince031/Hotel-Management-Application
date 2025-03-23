package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ReservationService {

    // List that store all the reservations
    static List<Reservation> reservationArrayList = new ArrayList<>();

    // Add room
    public static void addRoom(IRoom room){
        RoomService.addRoom(room);
    }

    // get room
    public static IRoom getARoom(String roomId){
        return RoomService.getARoom(roomId);
    }

    // reserve room
    public static Reservation reserveRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationArrayList.add(newReservation);
        return newReservation;
    }

    // find all available rooms
    public static Collection<IRoom> findRooms(Date checkOutDate, Date checkInDate){
        List<IRoom> reservedRooms = new ArrayList<>();
        for (Reservation reservation : reservationArrayList) {
            if(reservation.getCheckInDate().before(checkOutDate) || reservation.getCheckOutDate().after(checkInDate)){
                reservedRooms.add(reservation.getRoom());
            }
        }
        List<IRoom> availableRooms = new ArrayList<>();
        for(IRoom room : RoomService.getAllRooms()){
            boolean isAvailable = true;
            for(IRoom bookedRoom : reservedRooms){
                if(room.getRoomNumber().equals(bookedRoom.getRoomNumber())){
                    isAvailable = false;
                    break;
                }
            }
            if(isAvailable) availableRooms.add(room);
        }
        return availableRooms;
    }

    // get all reservations by customer
    public static Collection<Reservation> getCustomersReservation(Customer customer){
        Collection<Reservation> customerReservation = new ArrayList<>();
        for(Reservation reservation : reservationArrayList){
            if(reservation.getCustomer().equals(customer)){
                customerReservation.add(reservation);
            }
        }
        return customerReservation;
    }

    //print all reservations
    public static void printAllReservation(){
        for(Reservation reservation : reservationArrayList){
            System.out.println(reservation);
        }
    }
}
