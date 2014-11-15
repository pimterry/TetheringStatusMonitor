package io.timperry.tetherapp;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@Config(emulateSdk=18, shadows=TetherableShadowWifiManager.class, manifest="src/main/AndroidManifest.xml")
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

    @Test
    public void tetherStatusShouldBeFalseIfTheApiFails() {
        TetherStatusMonitor monitor = new TetherStatusMonitor(appContext);

        makeTetheringCheckFailWith(new IllegalStateException("Wifi is on fire"));
        TetherStatus status = monitor.getCurrentStatus();

        assertThat(status.isActive()).as("Tethering status should not be 'active' if tethering is broken").isFalse();
    }

    private void enableTethering() {
        getShadowWifiManager().setWifiApEnabled(true);
    }

    private void makeTetheringCheckFailWith(RuntimeException e) {
        getShadowWifiManager().setWifiApException(e);
    }

    private TetherableShadowWifiManager getShadowWifiManager() {
        WifiManager wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);

        return (TetherableShadowWifiManager) Robolectric.shadowOf(wifiManager);
    }

}
