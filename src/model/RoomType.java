package model;

public enum RoomType {
    SINGLE("Single"),
    DOUBLE("Double");

    private final String roomType;

    // Constructor to set the room type name
    RoomType(String roomType) {
        this.roomType = roomType;
    }

    // Getter method to retrieve the room type name
    public String getRoomType() {
        return this.roomType;
    }

}
