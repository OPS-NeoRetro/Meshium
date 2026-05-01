package com.github.ops_neoretro.gl.buffers;

import com.github.ops_neoretro.gl.IResource;

public interface Buffer extends IResource {
    int getId();
    long getSize();
}
