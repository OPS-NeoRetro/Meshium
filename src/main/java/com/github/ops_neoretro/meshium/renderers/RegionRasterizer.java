package com.github.ops_neoretro.renderers;

import com.github.ops_neoretro.gl.shader.Shader;
import com.github.ops_neoretro.sodiumCompat.ShaderLoader;
import me.jellysquid.mods.sodium.client.gl.shader.ShaderParser;
import net.minecraft.util.Identifier;

import static com.github.ops_neoretro.gl.shader.ShaderType.FRAGMENT;
import static com.github.ops_neoretro.gl.shader.ShaderType.MESH;
import static org.lwjgl.opengl.NVMeshShader.glDrawMeshTasksNV;

public class RegionRasterizer extends Phase {
    private final Shader shader = Shader.make()
                    .addSource(MESH, ShaderLoader.parse(Identifier.of("meshium", "occlusion/region_raster/mesh.glsl")))
                    .addSource(FRAGMENT, ShaderLoader.parse(Identifier.of("meshium", "occlusion/region_raster/fragment.frag")))
                    .compile();

    public void raster(int regionCount) {
        shader.bind();
        glDrawMeshTasksNV(0,regionCount);
    }

    public void delete() {
        shader.delete();
    }
}
