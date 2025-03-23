package model;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber,0.0, roomType);
        super.setAvailable(true);
    }

    @Override
    public String toString(){
        return ("Room Number : " + getRoomNumber() +
                " Room Type : " + getRoomType()+
                " Room Price : " + getPrice());
    }
}
