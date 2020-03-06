package com.misterplus.plustweaks.mixins;

import com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction;
import net.minecraft.block.Block;
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

    private MixinBlockFluidBase() {
        super(Material.AIR);
    }

    @Shadow @Final protected Fluid definedFluid;
    @Shadow @Final public static PropertyInteger LEVEL;
    @Shadow protected int tickRate;

    /**
     * @author MisterPlus
     * @reason Inject won't generate refmap
     */
    @Overwrite
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
        boolean flag = false;

        for (LiquidInteraction interaction : ctInteractions) {
            if (!world.getBlockState(neighbourPos).getMaterial().isLiquid()) {
                break;
            }
            for (EnumFacing enumfacing : EnumFacing.values())
            {
                if (enumfacing != EnumFacing.DOWN && Objects.equals(this.definedFluid.getBlock().getRegistryName(), interaction.liquid1) && Objects.equals(world.getBlockState(neighbourPos).getBlock().getRegistryName(), interaction.liquid2))
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
        world.scheduleUpdate(pos, this, tickRate);
    }
}
