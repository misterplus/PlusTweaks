package com.misterplus.plustweaks.utils;

import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.util.ResourceLocation;

public class UtilityMethods {
    public static ResourceLocation getRegisteryName(ILiquidStack liquidStack) {
        return CraftTweakerMC.getLiquidStack(liquidStack).getFluid().getBlock().getRegistryName();
    }
}

