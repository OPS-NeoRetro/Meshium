package com.github.ops_neoretro.meshium.gl.buffers;

import com.github.ops_neoretro.meshium.gl.IResource;

public interface Buffer extends IResource {
    int getId();
    long getSize();
}
