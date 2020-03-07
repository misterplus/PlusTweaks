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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

import static com.misterplus.plustweaks.PlusTweaks.blockCool;
import static com.misterplus.plustweaks.PlusTweaks.blockGen;
import static com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction.ctInteractions;

@Mixin(BlockLiquid.class)
public abstract class MixinBlockLiquid extends Block {

    public MixinBlockLiquid() {
        super(Material.AIR);
    }

    @Redirect(
            method = "checkForMixing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z",
                    ordinal = 0
            )
    )
    private boolean redirect_checkForMixing(World world, BlockPos pos, IBlockState state) {
        return blockCool != null && world.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(world, pos, pos, blockCool.getDefaultState()));
    }

    @Redirect(
            method = "checkForMixing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z",
                    ordinal = 1
            )
    )
    private boolean redirect_checkForMixing$2(World world, BlockPos pos, IBlockState state) {
        return blockGen != null && world.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(world, pos, pos, blockGen.getDefaultState()));
    }

    @Shadow @Final
    public static PropertyInteger LEVEL;

    @Inject(
            method = "checkForMixing",
            at = @At("TAIL"),
            cancellable = true
    )
    private void injectCheckForMixing(World worldIn, BlockPos pos, IBlockState state, CallbackInfoReturnable<Boolean> cir) {
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
                int integer = state.getValue(LEVEL);
                if (integer == 0 && interaction.sourceInteraction) {
                    worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, interaction.block.getDefaultState()));
                    cir.setReturnValue(true);
                }
                else if (integer != 0 && !interaction.sourceInteraction) {
                    worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, interaction.block.getDefaultState()));
                    cir.setReturnValue(true);
                }
            }
        }
    }
}