package plus.misterplus.plustweaks.mixins;

import net.minecraft.block.BlockReed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import plus.misterplus.plustweaks.config.Configs;

@Mixin(BlockReed.class)
public abstract class MixinBlockReed {
    @ModifyConstant(
            method = "updateTick",
            constant = @Constant(intValue = 3)
    )
    private int modifyConstant_BlockReed(int maxHeight) {
        return Configs.genericSettings.reedHeight;
    }
}
