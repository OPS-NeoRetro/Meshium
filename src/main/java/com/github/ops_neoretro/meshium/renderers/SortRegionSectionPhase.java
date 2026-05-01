package com.github.ops-neoretro.renderers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.github.ops-neoretro.gl.shader.Shader;
import com.github.ops-neoretro.mixin.minecraft.LightMapAccessor;
import com.github.ops-neoretro.sodiumCompat.ShaderLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GL45C;

import static com.github.ops-neoretro.RenderPipeline.GL_DRAW_INDIRECT_ADDRESS_NV;
import static com.github.ops-neoretro.gl.shader.ShaderType.*;
import static org.lwjgl.opengl.GL11C.GL_NEAREST;
import static org.lwjgl.opengl.GL11C.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL33.glGenSamplers;
import static org.lwjgl.opengl.GL43C.glDispatchCompute;
import static org.lwjgl.opengl.NVMeshShader.glMultiDrawMeshTasksIndirectNV;
import static org.lwjgl.opengl.NVVertexBufferUnifiedMemory.glBufferAddressRangeNV;

public class SortRegionSectionPhase extends Phase {
    private final Shader shader = Shader.make()
            .addSource(COMPUTE, ShaderLoader.parse(Identifier.of("meshium", "sorting/region_section_sorter.comp")))
            .compile();

    public SortRegionSectionPhase() {
    }

    public void dispatch(int sortingRegionCount) {
        shader.bind();
        glDispatchCompute(sortingRegionCount, 1, 1);
    }

    public void delete() {
        shader.delete();
    }
}
