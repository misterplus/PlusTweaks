package plus.misterplus.plustweaks.compact.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.liquid.ILiquidStack;
import plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionRegisterLiquidInteraction;
import plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionSetLiquidInfinite;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.plustweaks.Liquid")
public class Liquid {
    @ZenMethod
    public static void registerLiquidInteraction(ILiquidStack liquid1, ILiquidStack liquid2, IBlockState block, int[] levels) {
        CraftTweakerAPI.apply(new ActionRegisterLiquidInteraction(liquid1, liquid2, block, levels));
    }

    @ZenMethod
    public static void registerLiquidInteraction(ILiquidStack liquid1, ILiquidStack liquid2, IBlockState block, @Optional boolean source) {
        CraftTweakerAPI.apply(new ActionRegisterLiquidInteraction(liquid1, liquid2, block, source ? new int[]{0} : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}));
    }

    @ZenMethod
    public static void setInfinite(ILiquidStack liquidStack, @Optional boolean finite) {
        CraftTweakerAPI.apply(new ActionSetLiquidInfinite(liquidStack, finite));
    }
}
