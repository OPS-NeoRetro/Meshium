package com.github.ops_neoretro.meshium.mixin.sodium;

import com.github.ops_neoretro.meshium.Meshium;
import com.github.ops_neoretro.meshium.sodiumCompat.IRepackagedResult;
import com.github.ops_neoretro.meshium.sodiumCompat.SodiumResultCompatibility;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildOutput;
import me.jellysquid.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import me.jellysquid.mods.sodium.client.util.task.CancellationToken;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChunkBuilderMeshingTask.class, remap = false)
public class MixinChunkBuilderMeshingTask {
    @Inject(method = "execute(Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lme/jellysquid/mods/sodium/client/util/task/CancellationToken;)Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;", at = @At("TAIL"))
    private void repackageResults(ChunkBuildContext buildContext, CancellationToken cancellationToken, CallbackInfoReturnable<ChunkBuildOutput> cir) {
        if (Meshium.IS_ENABLED) {
            var result = cir.getReturnValue();
            if (result != null) {
                ((IRepackagedResult) result).set(SodiumResultCompatibility.repackage(result));
            }
        }
    }
}
