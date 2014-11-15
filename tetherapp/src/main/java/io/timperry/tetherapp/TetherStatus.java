package io.timperry.tetherapp;

public class TetherStatus {

    private final boolean tethering;

    public TetherStatus(boolean tethering) {
        this.tethering = tethering;
    }

    public boolean isActive() {
        return tethering;
    }

}
