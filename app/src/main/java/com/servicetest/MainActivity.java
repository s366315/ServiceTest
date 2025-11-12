package com.servicetest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.servicetest.databinding.ActivityMainBinding;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MainActivity extends Activity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);
        setActionBar(binding.toolbar);

        Intent serviceIntent = new Intent(this.getApplicationContext(), MainService.class);
//        startService(serviceIntent);

        try {
            ApplicationInfo ainfo = this.getApplicationContext().getPackageManager().getApplicationInfo(
                    "com.servicetest",
                    PackageManager.GET_SHARED_LIBRARY_FILES
            );
            Log.v("TAG", "native library dir " + ainfo.nativeLibraryDir);

            NativeWrapper nativeWrapper = new NativeWrapper();
            nativeWrapper.open();

//            nativeWrapper.createLogicalObject();

        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://stream.bybit.com/v5/private").build();
        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                System.out.println("Connection opened");
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                System.out.println("Message received");
                System.out.println(text);
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                System.out.println("Connection closing");
                System.out.println(reason);
            }

            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                System.out.println("Connection closed");
                System.out.println(reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                System.out.println("Connection failure");
                System.out.println(t.getMessage());
            }
        });
    }
}
