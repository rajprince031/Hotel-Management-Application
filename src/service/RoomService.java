package service;

import model.IRoom;
import model.Reservation;
import model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RoomService {
    private static final List<IRoom> roomArrayList = new ArrayList<>();

    // Add room a list of room
    public static void addRoom(List<IRoom> rooms) {
        roomArrayList.addAll(rooms);
    }

    // Add a single room
    public static void addRoom(IRoom room) {
        roomArrayList.add(room);
    }

    // Get all rooms
    public static List<IRoom> getAllRooms() {
        return roomArrayList;
    }

    //Get Room by roomNumber
    public static IRoom getARoom(String roomId){
        for(IRoom room : roomArrayList){
            if(Objects.equals(room.getRoomNumber(), roomId)){
                return room;
            }
        }
        return null;
    }
}
