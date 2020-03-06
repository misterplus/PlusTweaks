package com.misterplus.plustweaks.mixins;

import com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction.ctInteractions;

@Mixin(BlockFluidBase.class)
public abstract class MixinBlockFluidBase {
    @Shadow @Final protected Fluid definedFluid;
    @Shadow @Final public static PropertyInteger LEVEL;

    @Inject(
            method = "neighborChanged",
            at = @At("HEAD")
    )
    private void injectNeighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos, CallbackInfo ci) {
        boolean flag = false;

        for (LiquidInteraction interaction : ctInteractions) {
            if (!world.getBlockState(neighbourPos).getMaterial().isLiquid()) {
                break;
            }
            for (EnumFacing enumfacing : EnumFacing.values())
            {
                if (enumfacing != EnumFacing.DOWN && this.definedFluid.getBlock().getRegistryName().equals(interaction.liquid1) && world.getBlockState(neighbourPos).getBlock().getRegistryName().equals(interaction.liquid2))
                {
                    flag = true;
                    break;
                }
            }
            if (flag)
            {
                Integer integer = state.getValue(LEVEL);
                if (integer != 0) {
                    world.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(world, pos, pos, interaction.block.getDefaultState()));
                    break;
                }
            }
        }
    }
}
