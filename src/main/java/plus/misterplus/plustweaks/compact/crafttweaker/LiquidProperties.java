package plus.misterplus.plustweaks.compact.crafttweaker;

import plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionSetLiquidInfinite;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.plustweaks.LiquidProperties")
public class LiquidProperties {

    @ZenMethod
    public static void setInfinite(ILiquidStack liquidStack, @Optional boolean finite) {
        CraftTweakerAPI.apply(new ActionSetLiquidInfinite(liquidStack, finite));
    }
}
