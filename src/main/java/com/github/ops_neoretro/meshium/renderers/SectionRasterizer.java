package com.github.ops_neoretro.meshium.renderers;

import com.github.ops_neoretro.meshium.gl.shader.Shader;
import com.github.ops_neoretro.meshium.sodiumCompat.ShaderLoader;
import me.jellysquid.mods.sodium.client.gl.shader.ShaderParser;
import net.minecraft.util.Identifier;

import static com.github.ops_neoretro.meshium.gl.shader.ShaderType.*;
import static org.lwjgl.opengl.NVMeshShader.glDrawMeshTasksNV;

public class SectionRasterizer extends Phase {

    private final Shader shader = Shader.make()
            .addSource(TASK, ShaderLoader.parse(Identifier.of("meshium", "occlusion/section_raster/task.glsl")))
            .addSource(MESH, ShaderLoader.parse(Identifier.of("meshium", "occlusion/section_raster/mesh.glsl")))
            .addSource(FRAGMENT, ShaderLoader.parse(Identifier.of("meshium", "occlusion/section_raster/fragment.glsl"))).compile();

    public void raster(int regionCount) {
        shader.bind();
        glDrawMeshTasksNV(0,regionCount);
    }

    public void delete() {
        shader.delete();
    }
}
