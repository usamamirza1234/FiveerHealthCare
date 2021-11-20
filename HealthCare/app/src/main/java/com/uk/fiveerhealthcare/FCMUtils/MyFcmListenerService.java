package com.uk.fiveerhealthcare.FCMUtils;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import com.uk.fiveerhealthcare.AppConfig;
import com.uk.fiveerhealthcare.MainActivity;
import com.uk.fiveerhealthcare.R;



public class MyFcmListenerService extends FirebaseMessagingService {
    private static final String TAG = "MyFcmListenerService";
    public static final String MY_INSTANCE_IDLS = "MyInstanceIDLS";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Log.d(MY_INSTANCE_IDLS,"messg: "+remoteMessage.getMessageId());




        try {

            String myMessage = "";
            Map data = remoteMessage.getData();


            myMessage = data.get("data").toString();

            Log.d(MY_INSTANCE_IDLS,"messg: "+myMessage);
            Log.d("apppppppppppppppppp","messg: "+myMessage);
            sendNotification( "Thrift Plan", myMessage);
        }
        catch (Exception e)
        {
            Log.d(MY_INSTANCE_IDLS,"Exception: "+e.getMessage());
        }




    }


    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param _title   FCM message title received.
     * @param _message FCM message received.
     */
    private void sendNotification(String _title, String _message) {
        AppConfig.getInstance().nFCMCounter++;


        if (AppConfig.getInstance().isAppRunnig) {
            //App is running
//            AppConfig.getInstance().mUpdateFlag.setAllHomeStatus(true);
//            AppConfig.getInstance().mUpdateFlag.setAllMyAdsStatus(true);
//            AppConfig.getInstance().mUpdateFlag.isNotifications = true;
//            AppConfig.getInstance().updateNotifications();
        }

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setClass(this, MainActivity.class);

        PendingIntent notifyPIntent = PendingIntent.getActivity(this, AppConfig.getInstance().nFCMCounter /* Request code */,
                intent, PendingIntent.FLAG_ONE_SHOT);//


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "ThriftPlan_Channel")
                .setSmallIcon(getNotificationIcon())
                .setContentTitle(_title)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(Html.fromHtml(_message)))
                .setContentText(Html.fromHtml(_message))
                .setSound(defaultSoundUri)
                .setChannelId("ThriftPlan_Channel")
                .setContentIntent(notifyPIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            notificationBuilder.setColor(getResources().getColor(R.color.thm_light_navy_blue));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "ThriftPlan_Channel", _title, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(_message);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(AppConfig.getInstance().nFCMCounter /* ID of notification */,
                notificationBuilder.build());

    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.icn_push_silhouette : R.drawable.icn_push_colored;
    }
}