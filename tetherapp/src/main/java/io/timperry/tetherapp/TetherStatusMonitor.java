package io.timperry.tetherapp;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TetherStatusMonitor {

    private final Context appContext;

    public TetherStatusMonitor(Context context) {
        this.appContext = context;
    }

    public TetherStatus getCurrentStatus() {
        WifiManager wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);

        boolean activeStatus = getWifiApStatus(wifiManager);

        return new TetherStatus(activeStatus);
    }

    private boolean getWifiApStatus(WifiManager wifiManager) {
        try {
            Method apCheckMethod = wifiManager.getClass().getDeclaredMethod("isWifiApEnabled");
            apCheckMethod.setAccessible(true);
            return (boolean) apCheckMethod.invoke(wifiManager);
        } catch (NoSuchMethodException e) {
            // TODO: Log
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            // TODO: Log
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            // TODO: Log
            e.printStackTrace();
            return false;
        }
    }

}
