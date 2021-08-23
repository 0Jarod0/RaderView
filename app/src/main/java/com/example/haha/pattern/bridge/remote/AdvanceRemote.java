package com.example.haha.pattern.bridge.remote;

import com.example.haha.pattern.bridge.devices.Device;

public class AdvanceRemote extends BasicRemote{
    public AdvanceRemote(Device device) {
        super.device = device;
    }

    public void mute() {
        System.out.println("Remote: mute");
        device.setVolume(0);
    }
}
