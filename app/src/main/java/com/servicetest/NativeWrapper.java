package com.servicetest;

public class NativeWrapper {
    static {
//        System.loadLibrary("ssl");
//        System.loadLibrary("ssl_3");
//        System.loadLibrary("crypto");
//        System.loadLibrary("crypto_3");
        System.loadLibrary("myqtlib_arm64-v8a");
    }

    public native void createLogicalObject();
}
