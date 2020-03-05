package com.misterplus.plustweaks.mixins;

import com.misterplus.plustweaks.config.Configs;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLiquid.class)
public abstract class MixinBlockLiquid {

    @Inject(
            method = "checkForMixing",
            at = @At(
                    value = "FIELD",
                    target = "net/minecraft/init/Blocks.COBBLESTONE:Lnet/minecraft/block/Block;"
            ),
            cancellable = true
    )
    private void injectCheckForMixing(World worldIn, BlockPos pos, IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.genericSettings.noCobbleGen) {
            cir.setReturnValue(false);
        }
    }
}
