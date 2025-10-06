package com.servicetest;

public class NativeWrapper {
    static {
//        System.loadLibrary("myqtlib_arm64-v8a");
        System.loadLibrary("bybit_balancer");
    }

//    public native void createLogicalObject();
    public native void open();
    public native void close();
}
