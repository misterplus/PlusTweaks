package com.misterplus.plustweaks.mixins;

import com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

import static com.misterplus.plustweaks.PlusTweaks.blockGen;
import static com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction.ctInteractions;

@Mixin(BlockLiquid.class)
public abstract class MixinBlockLiquid extends Block {

    private MixinBlockLiquid() {
        super(Material.AIR);
    }

    @Shadow @Final
    public static PropertyInteger LEVEL;

    @Shadow
    protected abstract void triggerMixEffects(World worldIn, BlockPos pos);

    /**
     * @author MisterPlus
     * @reason Inject won't generate refmap
     */
    @Overwrite
    public boolean checkForMixing(World worldIn, BlockPos pos, IBlockState state)
    {
        boolean flag = false;
        for (LiquidInteraction interaction : ctInteractions) {
            for (EnumFacing enumfacing : EnumFacing.values())
            {
                if (!worldIn.getBlockState(pos.offset(enumfacing)).getMaterial().isLiquid())
                    continue;
                if (enumfacing != EnumFacing.DOWN && Objects.equals(this.getRegistryName(), interaction.liquid1) && Objects.equals(worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getRegistryName(), interaction.liquid2))
                {
                    flag = true;
                    break;
                }
            }
            if (flag)
            {
                Integer integer = state.getValue(LEVEL);
                if (integer != 0) {
                    worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, interaction.block.getDefaultState()));
                    return true;
                }
            }
        }
        flag = false;
        if (this.material == Material.LAVA)
        {
            for (EnumFacing enumfacing : EnumFacing.values())
            {
                if (enumfacing != EnumFacing.DOWN && worldIn.getBlockState(pos.offset(enumfacing)).getMaterial() == Material.WATER)
                {
                    flag = true;
                    break;
                }
            }
            if (flag)
            {
                int integer = state.getValue(LEVEL);

                if (integer == 0)
                {
                    worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, Blocks.OBSIDIAN.getDefaultState()));
                    this.triggerMixEffects(worldIn, pos);
                    return true;
                }
                if (integer <= 4)
                {
                    if (blockGen != null) {
                        worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, blockGen.getDefaultState()));
                        this.triggerMixEffects(worldIn, pos);
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    //Figure out how to inject the same method two times?
    /*private MixinBlockLiquid() {
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
        boolean flag = false;
        for (LiquidInteraction interaction : ctInteractions) {

            for (EnumFacing enumfacing : EnumFacing.values())
            {
                if (!worldIn.getBlockState(pos.offset(enumfacing)).getMaterial().isLiquid())
                    continue;
                if (enumfacing != EnumFacing.DOWN && this.getRegistryName().equals(interaction.liquid1) && worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getRegistryName().equals(interaction.liquid2))
                {
                    flag = true;
                    break;
                }
            }
            if (flag)
            {
                Integer integer = state.getValue(LEVEL);
                if (integer != 0) {
                    worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, interaction.block.getDefaultState()));
                    cir.setReturnValue(true);
                }
            }
        }
    }*/
}
