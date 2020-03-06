package com.misterplus.plustweaks.compact.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.LinkedList;
import java.util.List;

@ZenRegister
@ZenClass("mods.plustweaks.LiquidInteraction")
public class LiquidInteraction {

    public static List<LiquidInteraction> ctInteractions = new LinkedList<>();
    public final ResourceLocation liquid1, liquid2;
    public final Block block;

    public LiquidInteraction(ILiquidStack liquid1, ILiquidStack liquid2, IItemStack block) {
        this.liquid1 = getRegisteryName(liquid1);
        this.liquid2 = getRegisteryName(liquid2);
        this.block = CraftTweakerMC.getBlock(block);
    }

    private static ResourceLocation getRegisteryName(ILiquidStack liquidStack) {
        return CraftTweakerMC.getLiquidStack(liquidStack).getFluid().getBlock().getRegistryName();
    }
    
    @ZenMethod
    public static void registerLiquidInteraction(ILiquidStack liquid1, ILiquidStack liquid2, IItemStack block) {
        LiquidInteraction interaction = new LiquidInteraction(liquid1, liquid2, block);
        ctInteractions.add(interaction);
    }
}
