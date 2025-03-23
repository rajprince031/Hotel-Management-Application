import menus.MainMenu;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.RoomService;

import java.util.ArrayList;
import java.util.List;


public class HotelApplication {
    public static void main(String[] args) {

        Room room1 = new Room("101",1000.0, RoomType.DOUBLE);
        Room room2 = new Room("102",2000.0, RoomType.SINGLE);
        Room room3 = new Room("103",3000.0, RoomType.DOUBLE);
        Room room4 = new Room("104",4000.0, RoomType.SINGLE);
        Room room5 = new Room("105",5000.0, RoomType.SINGLE);

        List<IRoom> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);

        RoomService.addRoom(rooms);
        MainMenu.showMenu();
    }
}
