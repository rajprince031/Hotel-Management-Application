package model;

public class Room implements IRoom{
    private final String roomNumber;
    private final Double price;
    private RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        return this.price == 0;
    }

    @Override
    public String toString(){
        return ("Room Number: " + this.roomNumber +" Room Type: " + this.roomType+" Room Price: $" + this.price);
    }
}
