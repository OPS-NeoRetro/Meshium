package com.github.ops_neoretro.config;

import com.github.ops_neoretro.Meshium;
import com.github.ops_neoretro.config.MeshiumConfig;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;

public class MeshiumConfigStore implements OptionStorage<MeshiumConfig> {
    private final MeshiumConfig config;

    public MeshiumConfigStore() {
        config = Meshium.config;
    }

    @Override
    public MeshiumConfig getData() {
        return config;
    }

    @Override
    public void save() {
        config.save();
    }
}
