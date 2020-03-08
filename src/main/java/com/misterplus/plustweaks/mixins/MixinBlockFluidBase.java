package com.misterplus.plustweaks.mixins;

import com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

import static com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction.ctInteractions;

@Mixin(BlockFluidBase.class)
public abstract class MixinBlockFluidBase extends Block{

    private MixinBlockFluidBase(){
        super(Material.AIR);
    }

    @Shadow(remap = false) protected int tickRate;
    @Shadow(remap = false) @Final protected Fluid definedFluid;
    @Shadow(remap = false) @Final public static PropertyInteger LEVEL;

    /**
     * @author MisterPlus
     * @reason neighborChanged is deprecated in Block
     */
    @SuppressWarnings("deprecation")
    @Overwrite
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
        if (world.getBlockState(neighbourPos).getMaterial().isLiquid()) {
            for (LiquidInteraction interaction : ctInteractions) {
                boolean flag = false;
                for (EnumFacing enumfacing : EnumFacing.values())
                {
                    if (enumfacing != EnumFacing.DOWN && Objects.equals(this.definedFluid.getBlock().getRegistryName(), interaction.liquid1))
                    {
                        Block liquid = world.getBlockState(neighbourPos).getBlock();
                        if((liquid instanceof BlockFluidBase && Objects.equals(liquid.getRegistryName(), interaction.liquid2)) || (liquid instanceof BlockDynamicLiquid && Objects.equals(BlockLiquid.getStaticBlock(world.getBlockState(neighbourPos).getMaterial()).getRegistryName(), interaction.liquid2))) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag)
                {
                    int integer = state.getValue(LEVEL);
                    if (integer == 0 && interaction.sourceInteraction) {
                        world.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(world, pos, pos, interaction.block.getDefaultState()));
                        break;
                    }
                    else if (integer != 0 && !interaction.sourceInteraction) {
                        world.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(world, pos, pos, interaction.block.getDefaultState()));
                        break;
                    }
                }
            }
        }
        world.scheduleUpdate(pos, this, tickRate);
    }
}
