package com.servicetest;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.servicetest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        System.loadLibrary("TestLib_arm64-v8a");

        setSupportActionBar(binding.toolbar);

        Intent serviceIntent = new Intent(this.getApplicationContext(), MainService.class);
//        startService(serviceIntent);
//        TestLib classLib;
//        changeValueTo();
        try {
            ApplicationInfo ainfo = this.getApplicationContext().getPackageManager().getApplicationInfo(
                    "com.servicetest",
                    PackageManager.GET_SHARED_LIBRARY_FILES
            );
            Log.v("TAG", "native library dir " + ainfo.nativeLibraryDir );
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    native void changeValueTo(int in);
}
