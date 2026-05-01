package com.github.ops_neoretro;

import com.github.ops_neoretro.config.MeshiumConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Util;
import org.lwjgl.opengl.GL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Meshium {
    public static final String MOD_VERSION;
    public static final Logger LOGGER = LoggerFactory.getLogger("Meshium");
    public static boolean IS_COMPATIBLE = false;
    public static boolean IS_ENABLED = false;
    public static boolean IS_DEBUG = System.getProperty("meshium.isDebug", "false").equals("TRUE");
    public static boolean SUPPORTS_PERSISTENT_SPARSE_ADDRESSABLE_BUFFER = true;
    public static boolean FORCE_DISABLE = false;

    public static MeshiumConfig config = MeshiumConfig.loadOrCreate();

    static {
        ModContainer mod = (ModContainer)FabricLoader.getInstance().getModContainer("meshium").orElseThrow(NullPointerException::new);
        var version = mod.getMetadata().getVersion().getFriendlyString();
        var commit = mod.getMetadata().getCustomValue("commit").getAsString();
        MOD_VERSION = version+"-"+commit;
    }

    public static void checkSystemIsCapable() {
        var cap = GL.getCapabilities();
        boolean supported = cap.GL_EXT_mesh_shader &&
                cap.GL_EXT_uniform_buffer_unified_memory &&
                cap.GL_EXT_vertex_buffer_unified_memory &&
                cap.GL_EXT_representative_fragment_test &&
                cap.GL_ARB_sparse_buffer &&
                cap.GL_EXT_bindless_multi_draw_indirect;
        IS_COMPATIBLE = supported;
        if (IS_COMPATIBLE) {
            LOGGER.info("All capabilities met");
        } else {
            LOGGER.warn("Not all requirements met, disabling meshium");
        }
        if (IS_COMPATIBLE && Util.getOperatingSystem() == Util.OperatingSystem.LINUX) {
            LOGGER.warn("Linux currently uses fallback terrain buffer due to driver inconsistencies, expect increased vram usage");
            SUPPORTS_PERSISTENT_SPARSE_ADDRESSABLE_BUFFER = false;
        }

        if (IS_COMPATIBLE) {
            LOGGER.info("Enabling Meshium");
        }
        IS_ENABLED = IS_COMPATIBLE;
    }
}
