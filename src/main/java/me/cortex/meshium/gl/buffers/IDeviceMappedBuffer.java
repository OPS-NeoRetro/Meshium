package me.cortex.meshium.gl.buffers;

public interface IDeviceMappedBuffer extends Buffer {
    long getDeviceAddress();
}
