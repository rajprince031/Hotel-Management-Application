package service;

import exception.CustomerIsNullException;
import exception.NoItemsInListException;
import exception.RoomNotFoundException;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static final ReservationService instance = new ReservationService();
    private ReservationService(){}
    public static ReservationService getInstance() {
        return instance;
    }

    // List that store all the reservations
    private final static List<Reservation> reservationList = new ArrayList<>();
    private static final HashMap<String,IRoom> roomList = new HashMap<>();

    // Add room a list of room
    public void addRoom(List<IRoom> rooms) {
        try {
            for (IRoom room : rooms) {
                roomList.put(room.getRoomNumber(), room);
            }
        }catch (Exception exception){
            System.out.println("Error adding room: "+exception.getMessage());
        }

    }

    //Get Room by roomNumber
    public IRoom getARoom(String roomId) {
        try{
            if(roomList.containsKey(roomId)){
                return roomList.get(roomId);
            }
            throw new RoomNotFoundException("room not found: " + roomId);
        } catch (RoomNotFoundException exception) {
            System.out.println("Error adding room: "+exception.getMessage());
            return null;
        }
    }

    // Get all roomList
    public Collection<IRoom> getAllRooms() {
        Collection<IRoom> rooms = roomList.values();
        try{
            if(rooms.isEmpty()) throw new NoItemsInListException("\nThere are no rooms in this hotel.");
            return rooms;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }

    // reserve room
    public Reservation reserveRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        try{
            if(customer == null) throw new CustomerIsNullException("Customer cannot be null.");
            if(room == null) throw new RoomNotFoundException("Room cannot be null");
            Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
            reservationList.add(newReservation);
            return newReservation;
        }catch (Exception exception){
            System.out.println("Error during reservation: "+exception.getMessage());
            return null;
        }

    }

    // find all available rooms
    public Collection<IRoom> findRooms( Date checkInDate, Date checkOutDate){
        Collection<IRoom> availableRooms = new ArrayList<>(roomList.values());

        for (Reservation reservation : reservationList) {
            Date checkIn = reservation.getCheckInDate();
            Date checkOut = reservation.getCheckOutDate();

            // Remove booked rooms
            if (!(checkOutDate.before(checkIn) || checkInDate.after(checkOut))) {
                availableRooms.remove(reservation.getRoom());
            }
        }
        return availableRooms;
    }


    // get all reservations by customer
    public Collection<Reservation> getCustomersReservation(Customer customer){

        Collection<Reservation> customerReservation = new ArrayList<>();
        for(Reservation reservation : reservationList){
            if(reservation.getCustomer().equals(customer)){
                customerReservation.add(reservation);
            }
        }
        if (customerReservation.isEmpty()) {
            System.out.println("No reservations found.");
            return customerReservation;
        }

        System.out.println("\nCustomer Reservation List");
        return customerReservation;
    }

    //print all reservations
    public void printAllReservation(){
        try {
            if (reservationList.isEmpty()) {
                System.out.println("\nNo reservations found.");
                return;
            }
            System.out.println("\nAll Reservations");
            for (Reservation reservation : reservationList) {
                System.out.println(reservation);
                System.out.println();
            }
        }catch (Exception exception){
            System.out.println("Error during printing the reservations: "+exception.getMessage());
        }
    }


    //check if the room is present or not
    public boolean isRoomExists(String roomNumber) {
        return roomList.containsKey(roomNumber);
    }
}
