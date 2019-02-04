package datafiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Cache {
    private static String userID;
    static boolean vipSub;
    static boolean roomSub;
    static SharedPreferences prefs;
    static SharedPreferences.Editor editor;
    Context context;

    public Cache(){}

    public Cache(Context context){
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
    }

    public Cache(String userID){
        this.userID = userID;
    }


    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        Cache.userID = userID;
        editor.putString("UserId", userID).apply();
    }


    public static boolean getVipsub(){
        return vipSub;
    }

    public static void setVipSub(boolean vipSub) {
        Cache.vipSub = vipSub;
    }


    public static void setRoomSub(boolean roomSub) {
        Cache.roomSub = roomSub;
    }

    public static boolean getRoomSub(){return roomSub;}

}
