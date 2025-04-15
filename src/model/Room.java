package model;

public class Room implements IRoom {
    public final String roomNumber;
    public final Double price;
    public final RoomType enumeration;

    public Room(String roomNumber , Double price , RoomType enumeration){
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }
    @Override
    public String toString(){
        return "The room Number is " + roomNumber + " with price " + price + " and room type " + enumeration;
    }
    @Override
    public final String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public final Double getRoomPrice() {
        return price;
    }

    @Override
    public final RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public final boolean isFree() {
        return (price == 0);
    }
}
