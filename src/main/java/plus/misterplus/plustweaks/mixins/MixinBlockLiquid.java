package plus.misterplus.plustweaks.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.misterplus.plustweaks.PlusTweaks;
import plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionRegisterLiquidInteraction;

import java.util.HashMap;
import java.util.Objects;

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
        return PlusTweaks.blockCool != null && world.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(world, pos, pos, PlusTweaks.blockCool.getDefaultState()));
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
        return PlusTweaks.blockGen != null && world.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(world, pos, pos, PlusTweaks.blockGen.getDefaultState()));
    }

    @Shadow @Final
    public static PropertyInteger LEVEL;
    @Shadow public abstract int tickRate(World worldIn);

    @Inject(
            method = "checkForMixing",
            at = @At("TAIL"),
            cancellable = true
    )
    private void injectCheckForMixing(World worldIn, BlockPos pos, IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        for (EnumFacing enumfacing : EnumFacing.values())
        {
            if (!worldIn.getBlockState(pos.offset(enumfacing)).getMaterial().isLiquid() || enumfacing == EnumFacing.UP)
                continue;
            String key = findKey(worldIn, pos, enumfacing);
            BlockPos actualPos = pos;
            if (key != null) {
                if (!key.startsWith(Objects.requireNonNull(BlockDynamicLiquid.getStaticBlock(this.material).getRegistryName()).toString()))
                    actualPos = pos.offset(enumfacing);
                HashMap<Integer, IBlockState> blockList = ActionRegisterLiquidInteraction.interactions.get(key);
                Integer integer = worldIn.getBlockState(actualPos).getValue(LEVEL);
                if (blockList.containsKey(integer)) {
                    worldIn.setBlockState(actualPos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, actualPos, actualPos, blockList.get(integer)));
                    worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
                    cir.setReturnValue(true);
                }
            }
        }
    }

    private String findKey(World worldIn, BlockPos pos, EnumFacing enumfacing) {
        ResourceLocation liquid1 = BlockDynamicLiquid.getStaticBlock(this.material).getRegistryName();
        ResourceLocation liquid2 = worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getRegistryName();
        String key = liquid1 + ":" + liquid2;
        return ActionRegisterLiquidInteraction.interactions.containsKey(key) ? key : (ActionRegisterLiquidInteraction.interactions.containsKey(liquid2 + ":" + liquid1) ? liquid2 + ":" + liquid1 : null);
    }
}