package com.servicetest;

public class TANativeWrapper {
    static {
        System.loadLibrary("ta-native");
    }

    public native double sma(double[] input, int period);
}
