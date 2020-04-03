package plus.misterplus.plustweaks.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

import static net.minecraftforge.oredict.OreDictionary.getOres;
import static net.minecraftforge.oredict.OreDictionary.itemMatches;

public class SlotEnchantTable extends Slot {

    public static List<ItemStack> ores = new LinkedList<ItemStack>() {
        {
            addAll(getOres("gemLapis"));
        }
    };

    public SlotEnchantTable(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        for (ItemStack ore : ores)
            if (itemMatches(ore, stack, false)) return true;
        return false;
    }
}
