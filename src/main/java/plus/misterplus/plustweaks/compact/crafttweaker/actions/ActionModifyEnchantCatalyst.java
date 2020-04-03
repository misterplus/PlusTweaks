package plus.misterplus.plustweaks.compact.crafttweaker.actions;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static net.minecraftforge.oredict.OreDictionary.getOres;
import static plus.misterplus.plustweaks.utils.SlotEnchantTable.ores;

public class ActionModifyEnchantCatalyst implements IAction {
    private final List<ItemStack> items;
    private final boolean add;
    private List<String> ghostItems = new LinkedList<>();

    public ActionModifyEnchantCatalyst(IIngredient items, boolean add) {
        if (items instanceof IOreDictEntry) {
            this.items = getOres(((IOreDictEntry) items).getName());
        } else if (items instanceof IItemStack) {
            this.items = Collections.singletonList(CraftTweakerMC.getItemStack(items));
        } else {
            //ILiquidStack, which doesn't work
            this.items = new LinkedList<>();
        }
        this.add = add;
    }

    @Override
    public void apply() {
        if (this.add) {
            for (ItemStack item : this.items) {
                if (!ores.contains(item)) {
                    ores.add(item);
                }
            }
        } else {
            ores.removeAll(this.items);
        }
    }

    @Override
    public String describe() {
        List<String> itemNames = new LinkedList<>();
        for (ItemStack item : this.items) {
            itemNames.add(item.getItem().getRegistryName().toString());
        }
        return String.format("%s %s as enchantment catalyst(s)", this.add ? "Adding" : "Removing", itemNames);
    }

    @Override
    public boolean validate() {
        if (this.items.isEmpty())
            return false;
        else if (!this.add) {
            for (ItemStack item : this.items) {
                if (!ores.contains(item))
                    this.ghostItems.add(item.getItem().getRegistryName() + ":" + item.getMetadata());
            }
        }
        return this.ghostItems.isEmpty();
    }

    @Override
    public String describeInvalid() {
        if (this.items.isEmpty())
            return "A ILiquidStack is not eligible for enchantment catalyst!";
        return String.format("These items are not enchantment catalysts: %s", this.ghostItems);
    }
}
