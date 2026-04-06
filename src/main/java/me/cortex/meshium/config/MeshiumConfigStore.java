package me.cortex.meshium.config;

import me.cortex.meshium.Meshium;
import me.cortex.meshium.config.MeshiumConfig;
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
