package com.example.saajan.navigation_implementation;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel1=new NotificationChannel(
                    CHANNEL_1_ID,"Warning", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is Warning");
            channel1.enableLights(true);


            NotificationChannel channel2=new NotificationChannel(
                    CHANNEL_2_ID,"Promotion", NotificationManager.IMPORTANCE_LOW);
            channel2.setDescription("This is Promotion");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);


        }
    }

}
