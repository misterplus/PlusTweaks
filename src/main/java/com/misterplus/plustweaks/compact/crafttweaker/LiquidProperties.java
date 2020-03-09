package com.misterplus.plustweaks.compact.crafttweaker;

import com.misterplus.plustweaks.compact.crafttweaker.actions.ActionSetLiquidInfinite;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.LinkedList;
import java.util.List;

@ZenRegister
@ZenClass("mods.plustweaks.LiquidProperties")
public class LiquidProperties {

    public static List<LiquidProperties> ctInfinites = new LinkedList<>();

    @ZenMethod
    public static void setInfinite(ILiquidStack liquidStack, @Optional boolean finite) {
        CraftTweakerAPI.apply(new ActionSetLiquidInfinite(liquidStack, finite));
    }
}
