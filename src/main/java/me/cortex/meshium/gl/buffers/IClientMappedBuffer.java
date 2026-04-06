package me.cortex.meshium.gl.buffers;

public interface IClientMappedBuffer extends Buffer {
    long clientAddress();
}
