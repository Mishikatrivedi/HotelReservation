package model;

public class FreeRoom extends Room{
    FreeRoom(String roomId , RoomType enumeration){
        super(roomId , (double) 0 , enumeration);
    }
    @Override
    public final String toString(){
        return super.toString();
    }
}
