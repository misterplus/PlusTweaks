package plus.misterplus.plustweaks.mixins;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static plus.misterplus.plustweaks.config.Configs.genericSettings;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Inject(
            method = "isOnSameTeam",
            at = @At("HEAD"),
            cancellable = true
    )
    private void injectIsOnSameTeam(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if (genericSettings.pigmenBugFix)
            cir.setReturnValue(false);
    }
}
