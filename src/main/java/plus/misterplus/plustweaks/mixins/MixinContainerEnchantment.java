package plus.misterplus.plustweaks.mixins;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import plus.misterplus.plustweaks.utils.SlotEnchantTable;

import java.util.List;

import static plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionEnchantmentLock.lockedEnchants;

@Mixin(ContainerEnchantment.class)
public abstract class MixinContainerEnchantment extends Container {
    @Shadow
    public IInventory tableInventory;
    @Final
    @Shadow
    private World world;
    @Final
    @Shadow
    private BlockPos position;

    @Inject(
            method = "getEnchantmentList",
            at = @At("TAIL"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void injectGetEnchantmentList(ItemStack stack, int enchantSlot, int level, CallbackInfoReturnable<List<EnchantmentData>> cir, List<EnchantmentData> list) {
        for (EnchantmentData enchantmentData : list) {
            if (lockedEnchants.containsKey(enchantmentData.enchantment.getRegistryName().toString())) {
                boolean flag = true;
                for (IBlockState iBlockState : lockedEnchants.get(enchantmentData.enchantment.getRegistryName().toString()).keySet()) {
                    int i = 0;
                    for (BlockPos blockPos : BlockPos.getAllInBox(position.offset(EnumFacing.NORTH, 3).offset(EnumFacing.WEST, 3), position.offset(EnumFacing.SOUTH, 3).offset(EnumFacing.EAST, 3).offset(EnumFacing.UP, 1))) {
                        if (world.getBlockState(blockPos).equals(iBlockState))
                            i++;
                    }
                    if (i < lockedEnchants.get(enchantmentData.enchantment.getRegistryName().toString()).get(iBlockState)) {
                        flag = false;
                        break;
                    }
                }
                if (!flag)
                    list.remove(enchantmentData);
            }
        }
    }

    @Redirect(
            method = "<init>(Lnet/minecraft/entity/player/InventoryPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/inventory/ContainerEnchantment;addSlotToContainer(Lnet/minecraft/inventory/Slot;)Lnet/minecraft/inventory/Slot;",
                    ordinal = 1
            )
    )
    private Slot redirectAddSlotToContainer(ContainerEnchantment containerEnchantment, Slot slotIn) {
        slotIn = new SlotEnchantTable(this.tableInventory, 1, 35, 47);
        slotIn.slotNumber = containerEnchantment.inventorySlots.size();
        containerEnchantment.inventorySlots.add(slotIn);
        containerEnchantment.inventoryItemStacks.add(ItemStack.EMPTY);
        return slotIn;
    }
}
