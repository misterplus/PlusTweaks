package plus.misterplus.plustweaks.compact.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.enchantments.IEnchantment;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.item.IIngredient;
import plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionEnchantmentLock;
import plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionEnchantmentLockByLevel;
import plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionModifyEnchantCatalyst;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Map;

@ZenRegister
@ZenClass("mods.plustweaks.Enchantment")
public class Enchantment {
    @ZenMethod
    public static void lockEnchantment(IEnchantmentDefinition enchant, Map<IBlockState, Integer> blocks) {
        CraftTweakerAPI.apply(new ActionEnchantmentLock(enchant.getRegistryName(), blocks));
    }

    @ZenMethod
    public static void lockEnchantment(IEnchantment enchant, Map<IBlockState, Integer> blocks) {
        CraftTweakerAPI.apply(new ActionEnchantmentLockByLevel(enchant, blocks));
    }

    @ZenMethod
    public static void addCatalyst(IIngredient items) {
        CraftTweakerAPI.apply(new ActionModifyEnchantCatalyst(items, true));
    }

    @ZenMethod
    public static void removeCatalyst(IIngredient items) {
        CraftTweakerAPI.apply(new ActionModifyEnchantCatalyst(items, false));
    }
}
