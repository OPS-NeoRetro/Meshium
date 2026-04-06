package me.cortex.meshium.mixin.sodium;

import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import me.cortex.meshium.Meshium;
import me.cortex.meshium.MeshiumWorldRenderer;
import me.cortex.meshium.managers.AsyncOcclusionTracker;
import me.cortex.meshium.sodiumCompat.IMeshiumWorldRendererGetter;
import me.cortex.meshium.sodiumCompat.IMeshiumWorldRendererSetter;
import me.cortex.meshium.sodiumCompat.IRenderSectionExtension;
import me.cortex.meshium.sodiumCompat.IrisCheck;
import me.jellysquid.mods.sodium.client.gl.device.CommandList;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkRenderMatrices;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkUpdateType;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSection;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSectionManager;
import me.jellysquid.mods.sodium.client.render.chunk.region.RenderRegionManager;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import me.jellysquid.mods.sodium.client.render.viewport.Viewport;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Mixin(value = RenderSection.class, remap = false)
public class MixinRenderSection implements IRenderSectionExtension {
    @Unique private volatile boolean isEnqueued;
    @Unique private volatile boolean isSeen;

    @Override
    public boolean isSubmittedRebuild() {
        return isEnqueued;
    }

    @Override
    public void isSubmittedRebuild(boolean state) {
        isEnqueued = state;
    }

    @Override
    public boolean isSeen() {
        return isSeen;
    }

    @Override
    public void isSeen(boolean state) {
        isSeen = state;
    }
}
