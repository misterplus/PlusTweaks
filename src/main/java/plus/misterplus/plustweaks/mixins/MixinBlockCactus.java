package plus.misterplus.plustweaks.mixins;

import net.minecraft.block.BlockCactus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import plus.misterplus.plustweaks.config.Configs;

@Mixin(BlockCactus.class)
public abstract class MixinBlockCactus {
    @ModifyConstant(
            method = "updateTick",
            constant = @Constant(intValue = 3)
    )
    private int modifyConstant_BlockCactus(int maxHeight) {
        return Configs.genericSettings.cactusHeight;
    }
}
