package com.misterplus.plustweaks.mixins;

import com.misterplus.plustweaks.config.Configs;
import net.minecraft.block.BlockCactus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

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
