package io.timperry.tetherapp;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowWifiManager;

import static org.assertj.core.api.Assertions.assertThat;

@Config(emulateSdk=18, shadows=TetherableShadowWifiManager.class)
@RunWith(RobolectricTestRunner.class)
public class TetherStatusMonitorTest {

    private final Context appContext = Robolectric.buildActivity(Activity.class).create().get();

    @Test
    public void tetherStatusShouldBeFalseNormally() {
        TetherStatusMonitor monitor = new TetherStatusMonitor(appContext);

        TetherStatus status = monitor.getCurrentStatus();

        assertThat(status.isActive()).as("Tethering should not be enabled initially").isFalse();
    }

    @Test
    public void tetherStatusShouldBeTrueIfTheSystemSaysSo() {
        TetherStatusMonitor monitor = new TetherStatusMonitor(appContext);

        enableTethering();
        TetherStatus status = monitor.getCurrentStatus();

        assertThat(status.isActive()).as("Tethering status should be 'active' if tethering is enabled").isTrue();
    }

    private void enableTethering() {
        WifiManager wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);

        TetherableShadowWifiManager shadowWifiManager = (TetherableShadowWifiManager) Robolectric.shadowOf(wifiManager);
        shadowWifiManager.setWifiApEnabled(true);
    }

}
