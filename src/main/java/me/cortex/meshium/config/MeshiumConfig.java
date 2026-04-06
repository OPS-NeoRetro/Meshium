package me.cortex.meshium.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.cortex.meshium.Meshium;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

public class MeshiumConfig {
    //The options
    public int extra_rd = 100;
    public boolean enable_temporal_coherence = true;
    public int max_geometry_memory = 2048;
    public boolean automatic_memory = true;

    public boolean async_bfs = true;

    public int region_keep_distance = 32;

    public boolean render_fog = true;
    public TranslucencySortingLevel translucency_sorting_level = TranslucencySortingLevel.QUADS;

    public StatisticsLoggingLevel statistics_level = StatisticsLoggingLevel.NONE;


    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .excludeFieldsWithModifiers(Modifier.PRIVATE)
            .create();

    private MeshiumConfig() {}
    public static MeshiumConfig loadOrCreate() {
        var path = getConfigPath();
        if (Files.exists(path)) {
            try (FileReader reader = new FileReader(path.toFile())) {
                return GSON.fromJson(reader, MeshiumConfig.class);
            } catch (IOException e) {
                Meshium.LOGGER.error("Could not parse config", e);
            }
        }
        return new MeshiumConfig();
    }

    public void save() {
        //Unsafe, todo: fixme! needs to be atomic!
        try {
            Files.writeString(getConfigPath(), GSON.toJson(this));
        } catch (IOException e) {
            Meshium.LOGGER.error("Failed to write config file", e);
        }
    }

    private static Path getConfigPath() {
        return FabricLoader.getInstance()
                .getConfigDir()
                .resolve("meshium-config.json");
    }
}
