package com.ivs.util;


public class DriverManagerFactory {

    public enum DriverType {
        ANDROID,
        IOS;

    }


    public static DriverManager getManager(DriverType type) {

        DriverManager driverManager = null;

        switch (type) {
            case ANDROID:
                driverManager = new AndroidManager();
                break;
            case IOS:
                driverManager = new IOSManager();
                break;
            default:
                driverManager = new AndroidManager();
                break;
        }
        return driverManager;

    }




}
