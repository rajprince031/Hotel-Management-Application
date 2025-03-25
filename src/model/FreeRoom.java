package model;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber,0.0, roomType);
    }

    @Override
    public Double getRoomPrice(){
        return 0.0;
    }

    @Override
    public String toString(){
        return ("Room Number: " + getRoomNumber() +" Room Type: " + getRoomType()+" Room Price: $" + getRoomPrice());
    }

}
