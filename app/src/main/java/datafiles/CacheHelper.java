package datafiles;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;

public class CacheHelper {

    static int cacheLifeHour = 7 * 24;

    public static String getCacheDirectory(Context context){

        return context.getCacheDir().getPath();
    }

    public static void save(Context context, String key, String value) {

        try {

            key = URLEncoder.encode(key, "UTF-8");

            File cache = new File(getCacheDirectory(context) + "/" + key + ".srl");

            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(cache));
            out.writeUTF(value);
            out.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void save(Context context, String key, String value, String identifier) {

        save(context, key + identifier, value);
    }

    public static String retrieve(Context context, String key, String identifier) {

        return retrieve(context, key + identifier);
    }


    public static String retrieve(Context context, String key) {

        try {

            key = URLEncoder.encode(key, "UTF-8");

            File cache = new File(getCacheDirectory(context) + "/" + key + ".srl");

            if (cache.exists()) {
                /*
                Date lastModDate = new Date(cache.lastModified());
                Date now = new Date();

                long diffInMillisec = now.getTime() - lastModDate.getTime();
                long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);

                diffInSec /= 60;
                diffInSec /= 60;
                long hours = diffInSec % 24;

                if (hours > cacheLifeHour) {
                    cache.delete();
                    return "";
                }
                */

                ObjectInputStream in = new ObjectInputStream(new FileInputStream(cache));
                String value = in.readUTF();
                in.close();

                return value;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "";
    }
}