package io.timperry.tetherapp;

import android.net.wifi.WifiManager;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowWifiManager;

@Implements(WifiManager.class)
public class TetherableShadowWifiManager extends ShadowWifiManager {

    private boolean wifiApEnabled;

    @Implementation
    public boolean isWifiApEnabled() {
        return wifiApEnabled;
    }

    void setWifiApEnabled(boolean wifiApEnabled) {
        this.wifiApEnabled = wifiApEnabled;
    }
}
