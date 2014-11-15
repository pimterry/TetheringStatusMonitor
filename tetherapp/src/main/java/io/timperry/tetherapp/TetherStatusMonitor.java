package io.timperry.tetherapp;

import android.content.Context;
import android.net.wifi.WifiManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TetherStatusMonitor {

    private final Logger logger = LoggerFactory.getLogger(TetherStatusMonitor.class);
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
            logger.info("No 'isWifiApEnabled' method available", e);
        } catch (InvocationTargetException e) {
            logger.info("isWifiApEnabled threw an exception", e);
        } catch (IllegalAccessException e) {
            logger.info("Access to 'isWifiApEnabled' is not available", e);
        }
        return false;
    }

}
