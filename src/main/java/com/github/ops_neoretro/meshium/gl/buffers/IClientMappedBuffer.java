package com.github.ops_neoretro.gl.buffers;

public interface IClientMappedBuffer extends Buffer {
    long clientAddress();
}
