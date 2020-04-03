package plus.misterplus.plustweaks.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Objects;

import static plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionRegisterLiquidInteraction.interactions;


@Mixin(BlockFluidBase.class)
public abstract class MixinBlockFluidBase extends Block {

    @Shadow(remap = false)
    @Final
    public static PropertyInteger LEVEL;
    @Shadow(remap = false)
    protected int tickRate;
    @Shadow(remap = false)
    @Final
    protected Fluid definedFluid;

    private MixinBlockFluidBase() {
        super(Material.AIR);
    }

    /**
     * @author MisterPlus
     * @reason neighborChanged is deprecated in Block
     */
    @SuppressWarnings("deprecation")
    @Overwrite
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
        world.scheduleUpdate(pos, this, tickRate);
        doInteraction(state, world, pos, neighborBlock, neighbourPos);
    }

    private void doInteraction(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
        if (!world.getBlockState(neighbourPos).getMaterial().isLiquid()) {
            StringBuilder key = new StringBuilder(this.definedFluid.getBlock().getRegistryName() + ":");
            String key$2 = null;
            if (neighborBlock instanceof BlockFluidBase)
                key$2 = Objects.requireNonNull(neighborBlock.getRegistryName()).toString();
            else if (neighborBlock instanceof BlockLiquid) {
                key$2 = Objects.requireNonNull(BlockLiquid.getStaticBlock(neighborBlock.getDefaultState().getMaterial()).getRegistryName()).toString();
            }
            key.append(key$2);
            if (interactions.containsKey(key.toString())) {
                HashMap<Integer, IBlockState> blockList = interactions.get(key.toString());
                Integer integer = state.getValue(LEVEL);
                if (blockList.containsKey(integer)) {
                    world.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(world, pos, pos, blockList.get(integer)));
                }
            }
        }
    }
}
