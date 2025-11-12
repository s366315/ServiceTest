package com.servicetest;

import static com.servicetest.App.notificationChannelID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MainService extends Service {
    private int duration = Integer.MAX_VALUE;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new Notification.Builder(this, notificationChannelID)
                .setContentTitle("Заголовок")
                .setContentText("Описание")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(() -> {
            try {
                for (int i = duration; i > 0; i--) {
                    System.out.println("Осталось времени: " + i + " секунд");
                    Thread.sleep(1000);
                }
                System.out.println("Время вышло!");
            } catch (InterruptedException e) {
                System.out.println("Таймер прерван.");
            }
        }).start();
    }
}
