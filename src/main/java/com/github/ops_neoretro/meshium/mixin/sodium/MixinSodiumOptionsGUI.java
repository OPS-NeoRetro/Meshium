package com.github.ops_neoretro.mixin.sodium;

import com.github.ops_neoretro.MeshiumWorldRenderer;
import com.github.ops_neoretro.config.ConfigGuiBuilder;
import com.github.ops_neoretro.sodiumCompat.IMeshiumWorldRendererGetter;
import com.github.ops_neoretro.sodiumCompat.MeshiumOptionFlags;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import me.jellysquid.mods.sodium.client.render.SodiumWorldRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.*;

@Mixin(value = SodiumOptionsGUI.class, remap = false)
public class MixinSodiumOptionsGUI {
    @Shadow @Final private List<OptionPage> pages;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 3, shift = At.Shift.AFTER))
    private void addMeshiumOptions(Screen prevScreen, CallbackInfo ci) {
        ConfigGuiBuilder.addMeshiumGui(pages);
    }

    @Inject(method = "applyChanges", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void applyShaderReload(CallbackInfo ci, HashSet<OptionStorage<?>> dirtyStorages, EnumSet<OptionFlag> flags, MinecraftClient client) {
        if (client.world != null) {
            SodiumWorldRenderer swr = SodiumWorldRenderer.instanceNullable();
            if (swr != null) {
                MeshiumWorldRenderer pipeline = ((IMeshiumWorldRendererGetter)((SodiumWorldRendererAccessor)swr).getRenderSectionManager()).getRenderer();
                if (pipeline != null && flags.contains(MeshiumOptionFlags.REQUIRES_SHADER_RELOAD)) {
                    pipeline.reloadShaders();
                }
            }
        }
    }
}
