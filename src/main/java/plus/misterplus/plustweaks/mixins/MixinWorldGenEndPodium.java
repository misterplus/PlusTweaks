package plus.misterplus.plustweaks.mixins;

import net.minecraft.world.gen.feature.WorldGenEndPodium;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static plus.misterplus.plustweaks.config.Configs.portalSettings;

@Mixin(WorldGenEndPodium.class)
public abstract class MixinWorldGenEndPodium {

    @Shadow
    @Final
    @Mutable
    private boolean activePortal;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void injectWorldGenEndPodium(CallbackInfo callbackInfo) {
        this.activePortal = !portalSettings.noEndPortal && this.activePortal;
    }
}
