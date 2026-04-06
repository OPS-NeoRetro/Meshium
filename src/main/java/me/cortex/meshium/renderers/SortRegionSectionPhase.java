package me.cortex.meshium.renderers;

import com.mojang.blaze3d.platform.GlStateManager;
import me.cortex.meshium.gl.shader.Shader;
import me.cortex.meshium.mixin.minecraft.LightMapAccessor;
import me.cortex.meshium.sodiumCompat.ShaderLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GL45C;

import static me.cortex.meshium.RenderPipeline.GL_DRAW_INDIRECT_ADDRESS_NV;
import static me.cortex.meshium.gl.shader.ShaderType.*;
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
