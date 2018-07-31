package datafiles;

public class Cache {
    private static String userID;

    static String room;

    public Cache(){}

    public Cache(String userID){
        this.userID = userID;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        Cache.userID = userID;
    }

    public static String getRoom() {
        return room;
    }

    public static void setRoom(String room) {
        Cache.room = room;
    }
}
