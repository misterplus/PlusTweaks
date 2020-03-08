package com.misterplus.plustweaks.compact.crafttweaker.actions;

import com.misterplus.plustweaks.compact.crafttweaker.LiquidProperties;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.util.ResourceLocation;

import static com.misterplus.plustweaks.compact.crafttweaker.LiquidProperties.ctInfinites;
import static com.misterplus.plustweaks.utils.UtilityMethods.getRegisteryName;

public class ActionSetLiquidInfinite implements IAction {

    private final ResourceLocation liquid;
    private final boolean finite;

    public ActionSetLiquidInfinite(ILiquidStack liquidStack, boolean finite) {
        this.liquid = getRegisteryName(liquidStack);
        this.finite = finite;
    }

    @Override
    public void apply() {
        LiquidProperties properties = new LiquidProperties(this.liquid, this.finite);
        ctInfinites.add(properties);
    }

    @Override
    public String describe() {
        return this.finite ? String.format("Setting %s to finite", this.liquid) : String.format("Setting %s to infinite", this.liquid);
    }
}
