package com.misterplus.plustweaks.compact.crafttweaker.actions;

import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

import static com.misterplus.plustweaks.utils.UtilityMethods.getRegisteryName;

public class ActionSetLiquidInfinite implements IAction {

    public static HashMap<String, Boolean> infiniteLiquids = new HashMap<>();

    private final ResourceLocation liquid;
    private final boolean finite;

    public ActionSetLiquidInfinite(ILiquidStack liquidStack, boolean finite) {
        this.liquid = getRegisteryName(liquidStack);
        this.finite = finite;
    }

    @Override
    public void apply() {
        infiniteLiquids.put(this.liquid.toString(), this.finite);
    }

    @Override
    public String describe() {
        return this.finite ? String.format("Setting %s to finite", this.liquid) : String.format("Setting %s to infinite", this.liquid);
    }
}
