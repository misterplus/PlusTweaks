package com.misterplus.plustweaks.mixins;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.misterplus.plustweaks.PlusTweaks.blockGen;

@Mixin(BlockLiquid.class)
public abstract class MixinBlockLiquid {

    @Shadow
    protected abstract void triggerMixEffects(World worldIn, BlockPos pos);

    @Inject(
            method = "checkForMixing",
            at = @At(
                    value = "FIELD",
                    target = "net/minecraft/init/Blocks.COBBLESTONE:Lnet/minecraft/block/Block;"
            ),
            cancellable = true
    )
    private void injectCheckForMixing(World worldIn, BlockPos pos, IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (blockGen != null) {
            worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, blockGen.getDefaultState()));
            this.triggerMixEffects(worldIn, pos);
            cir.setReturnValue(true);
        }
        else {
            cir.setReturnValue(false);
        }
    }
}
