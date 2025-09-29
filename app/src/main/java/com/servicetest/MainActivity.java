package com.servicetest;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.servicetest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Intent serviceIntent = new Intent(this.getApplicationContext(), MainService.class);
//        startService(serviceIntent);

        try {
            ApplicationInfo ainfo = this.getApplicationContext().getPackageManager().getApplicationInfo(
                    "com.servicetest",
                    PackageManager.GET_SHARED_LIBRARY_FILES
            );
            Log.v("TAG", "native library dir " + ainfo.nativeLibraryDir);

            NativeWrapper nativeWrapper = new NativeWrapper();
            nativeWrapper.createLogicalObject();

        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
