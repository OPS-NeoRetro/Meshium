package me.cortex.meshium.gl.buffers;

import me.cortex.meshium.gl.IResource;

public interface Buffer extends IResource {
    int getId();
    long getSize();
}
