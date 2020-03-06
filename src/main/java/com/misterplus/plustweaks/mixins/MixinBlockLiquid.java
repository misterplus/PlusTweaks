package com.misterplus.plustweaks.mixins;

import com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

import static com.misterplus.plustweaks.PlusTweaks.blockGen;
import static com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction.ctInteractions;

@Mixin(BlockLiquid.class)
public abstract class MixinBlockLiquid extends Block{

    private MixinBlockLiquid() {
        super(Material.AIR);
    }

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

    @Shadow @Final
    public static PropertyInteger LEVEL;

    @Inject(
            method = "checkForMixing",
            at = @At(
                    value = "RETURN",
                    ordinal = 2
            ),
            cancellable = true
    )
    private void injectCheckForMixing$2(World worldIn, BlockPos pos, IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        boolean flagPlus = false;
        for (LiquidInteraction interaction : ctInteractions) {

            for (EnumFacing enumfacing : EnumFacing.values())
            {
                if (!worldIn.getBlockState(pos.offset(enumfacing)).getMaterial().isLiquid())
                    continue;
                if (enumfacing != EnumFacing.DOWN && Objects.equals(this.getRegistryName(), interaction.liquid1) && Objects.equals(worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getRegistryName(), interaction.liquid2))
                {
                    flagPlus = true;
                    break;
                }
            }
            if (flagPlus)
            {
                Integer integer = state.getValue(LEVEL);
                if (integer != 0) {
                    worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, interaction.block.getDefaultState()));
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
