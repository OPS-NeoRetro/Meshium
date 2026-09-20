package com.github.ops_neoretro.meshium.gl.buffers;

public interface IClientMappedBuffer extends Buffer {
    long clientAddress();
}
