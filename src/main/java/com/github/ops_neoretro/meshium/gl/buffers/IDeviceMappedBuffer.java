package com.github.ops_neoretro.meshium.gl.buffers;

public interface IDeviceMappedBuffer extends Buffer {
    long getDeviceAddress();
}
