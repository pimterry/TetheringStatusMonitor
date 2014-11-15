package io.timperry.tetherapp;

import android.net.wifi.WifiManager;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowWifiManager;

@Implements(WifiManager.class)
public class TetherableShadowWifiManager extends ShadowWifiManager {

    private boolean wifiApEnabled;
    private RuntimeException wifiApException;

    @Implementation
    public boolean isWifiApEnabled() throws RuntimeException {
        if (wifiApException != null) {
            throw wifiApException;
        } else {
            return wifiApEnabled;
        }
    }

    void setWifiApEnabled(boolean wifiApEnabled) {
        this.wifiApEnabled = wifiApEnabled;
        this.wifiApException = null;
    }

    void setWifiApException(RuntimeException exception) {
        this.wifiApException = exception;
    }
}
