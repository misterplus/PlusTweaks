package com.misterplus.plustweaks.mixins;

import com.misterplus.plustweaks.config.Configs;
import net.minecraft.block.BlockReed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockReed.class)
public class MixinBlockReed {
    @ModifyConstant(
            method = "updateTick",
            constant = @Constant(intValue = 3)
    )
    private int modifyConstant_BlockReed(int maxHeight) {
        return Configs.genericSettings.reedHeight;
    }
}
