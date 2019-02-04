package datafiles;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {

    static MediaPlayer mp = null;
    static MediaPlayer answer;

    public static void play(Context context, int resources, boolean playSound){
        if(playSound) {
            if (mp == null) {
                stop(context);
                mp = MediaPlayer.create(context, resources);
                //mp.setVolume(0.6f, 0.6f);
                mp.setLooping(true);
                mp.start();
            }
        }
    }

    public static void stop(Context context){
        if(mp!=null){
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    public static void like(Context context, int resources){
        if(answer !=null && answer.isPlaying()) {
            answer.stop();
            answer.reset();
        }
        answer = MediaPlayer.create(context, resources);
        answer.setVolume(0.6f, 0.6f);
        answer.setLooping(false);
        answer.start();
    }
}
