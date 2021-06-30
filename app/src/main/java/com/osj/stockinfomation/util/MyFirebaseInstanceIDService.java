package com.osj.stockinfomation.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.activity.MainActivity;
import com.osj.stockinfomation.activity.SplashActivity;
import com.osj.stockinfomation.base.BaseApplication;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]

    private PushPayloadDAO payload;
    private final int ICON_COLOR = Color.parseColor("#3e2b2e");
    //Noti 매니저
    private NotificationManager mNotificationManager = null;
    //Noti 빌더
    private Notification.Builder notifyBuilder;
    NotificationCompat.Builder mBuilder;
    private final String NOTIFICATION_CHANNEL_ID = "gosutv";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]

    @Override
    public void onNewToken(@NonNull String token) {
        sendRegistrationToServer(token);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(final String token) {
        Log.d("osj", "getToken :: " + token);
        PreferencesUtil.putString(getApplicationContext(), PreferencesUtil.PreferenceKey.PREF_TOKEN,token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        if (PreferencesUtil.getBoolean(getApplicationContext(), PreferencesUtil.PreferenceKey.PREF_PUSH_NEW)) {//푸시 동의 시
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                sendNotificationChannel(getApplicationContext(), remoteMessage);
            else
                sendNotification(getApplicationContext(), remoteMessage);
//        }

    }
    // [END receive_message]

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotificationChannel(Context context, RemoteMessage remoteMessage)
    {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notifyBuilder = new Notification.Builder(context,NOTIFICATION_CHANNEL_ID);
        notifyBuilder.setPriority(Notification.PRIORITY_MAX);
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_ID, importance);
        channel.setDescription(NOTIFICATION_CHANNEL_ID);

        mNotificationManager.createNotificationChannel(channel);
        payload = new PushPayloadDAO(remoteMessage);

        Intent intent = null;
        if(!((BaseApplication) getApplication()).isMainActivityLive())
        {
            intent = new Intent(context, SplashActivity.class);
            intent.putExtra(C.PUSH_PAYLOAD, payload);
            intent.setAction(Long.toString(System.currentTimeMillis()));

            mBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(payload.getTitle())
                    .setContentText(payload.getContents())
                    .setAutoCancel(true);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
//            PreferencesUtil.putString(getApplication(), PreferencesUtil.PreferenceKey.LINK, payload.getLinkUrl());
        }
        else
        {
            mBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(payload.getTitle())
                    .setContentText(payload.getContents())
                    .setAutoCancel(true);

            try {
//                if(payload.getLinkUrl().length() >= 8){
                    intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra(C.PUSH_PAYLOAD,payload.getLinkUrl());
                    intent.setAction(Long.toString(System.currentTimeMillis()));
                    intent.putExtra(C.PUSH_PAYLOAD, payload);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                    mBuilder.setContentIntent(pendingIntent);
//                }
            } catch (Exception e){
                Log.d("osj", "Exception e :: " + e);
            }
        }

            mNotificationManager.notify((int) (System.currentTimeMillis() / 1000), mBuilder.build());
    }
    /**
     * FCM을 수신하여 알림 발송
     *
     * @param context
     * @param remoteMessage
     */
    public void sendNotification(Context context, final RemoteMessage remoteMessage) {

        payload = new PushPayloadDAO(remoteMessage);

        PendingIntent pendingIntent = null;

        Intent intent = null;
        if(!((BaseApplication) getApplication()).isMainActivityLive())
        {
            intent = new Intent(context, SplashActivity.class);
            intent.putExtra(C.PUSH_PAYLOAD, payload);
//            PreferencesUtil.putString(getApplication(), PreferencesUtil.PreferenceKey.LINK, payload.getLinkUrl());
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else
        {
            try {
//                if(payload.getLinkUrl().length() >= 8){
                    intent = new Intent(context, MainActivity.class);
//                    intent.putExtra(C.PUSH_PAYLOAD,payload.getLinkUrl());
//                intent.putExtra(C.TOP_BTN_TYPE,TitleBaseActivity.TYPE_RIGHT_CLOSE);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(C.PUSH_PAYLOAD, payload);
                    pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//                    PreferencesUtil.putString(getApplication(), PreferencesUtil.PreferenceKey.LINK, payload.getLinkUrl());
//                }
            } catch (Exception e){
                Log.d("osj", "Exception e :: " + e);
            }
        }
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifyBuilder = new Notification.Builder(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notifyBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            notifyBuilder.setColor(ICON_COLOR);
        } else {
            notifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }

        notifyBuilder.setWhen(System.currentTimeMillis());
        notifyBuilder.setContentTitle(payload.getTitle());
//        notifyBuilder.setTicker(payload.getTitle());
        notifyBuilder.setContentText(payload.getContents());
        notifyBuilder.setDefaults(Notification.DEFAULT_SOUND);

        if(pendingIntent != null)
            notifyBuilder.setContentIntent(pendingIntent);
        notifyBuilder.setAutoCancel(true);
        notifyBuilder.setPriority(Notification.PRIORITY_MAX);

        mNotificationManager.notify((int) (System.currentTimeMillis() / 1000), notifyBuilder.build());
    }

}