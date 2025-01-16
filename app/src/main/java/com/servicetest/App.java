package com.servicetest;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

public class App extends Application {
    public static final String notificationChannelID = "ServiceTest";
    private static final String notificationChannelName = "App Channel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                notificationChannelID,
                notificationChannelName,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }
}
