package com.github.ops_neoretro.gl.buffers;

public interface IDeviceMappedBuffer extends Buffer {
    long getDeviceAddress();
}
