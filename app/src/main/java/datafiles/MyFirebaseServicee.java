package datafiles;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import secured.tips.DrawActivity;
import secured.tips.FreeActivity;
import secured.tips.MainActivity;
import secured.tips.PremiumActivity;
import secured.tips.R;
import secured.tips.RoomsPageActivity;

public class MyFirebaseServicee extends FirebaseMessagingService {

    String TAG = "Dame";
    private static final String ADMIN_CHANNEL_ID ="admin_channel";
    private static final String NOTIFICATION_TITLE="title";
    private static final String NOTIFICATION_MESSAGE ="message";
    private static final String NOTIFICATION_INTENT ="intent";
    private static final String INTENT_APP ="main";
    private static final String INTENT_TIPZONE ="tipzone";
    private static final String INTENT_FREE ="free";
    private static final String INTENT_VIP ="vip";
    private static final String INTENT_DRAW ="draw";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        createNotification(remoteMessage);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager){
        CharSequence adminChannelName = "Important Update";
        String adminChannelDescription = "Instant update once there are important tips";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    private void createNotification(RemoteMessage remoteMessage){
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationID = new Random().nextInt(3000);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels(notificationManager);
        }

        String received_intent = remoteMessage.getData().get(NOTIFICATION_INTENT);
        if(received_intent==null){
            received_intent="main";
        }
        Intent intent = new Intent(this, MainActivity.class);
        switch (received_intent){
            case INTENT_APP:
                intent = new Intent(this, MainActivity.class);
                break;
            case INTENT_FREE:
                intent = new Intent(this, FreeActivity.class);
                break;
            case INTENT_TIPZONE:
                intent = new Intent(this, RoomsPageActivity.class);
                break;
            case INTENT_VIP:
                intent = new Intent(this, PremiumActivity.class);
                break;
            case INTENT_DRAW:
                intent = new Intent(this, DrawActivity.class);
                break;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.notification_icon_large);

        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(largeIcon)
                .setContentTitle(remoteMessage.getData().get(NOTIFICATION_TITLE))
                .setContentText(remoteMessage.getData().get(NOTIFICATION_MESSAGE))
                .setAutoCancel(true)
                .setSound(notificationSoundUri)
                .setContentIntent(resultIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        notificationManager.notify(notificationID, notificationBuilder.build());
    }

}
