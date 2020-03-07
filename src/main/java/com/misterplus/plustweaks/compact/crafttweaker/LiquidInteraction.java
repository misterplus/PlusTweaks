package com.misterplus.plustweaks.compact.crafttweaker;

import com.misterplus.plustweaks.compact.crafttweaker.actions.ActionRegisterLiquidInteraction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
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

    public ResourceLocation liquid1, liquid2;
    public Block block;

    public LiquidInteraction(ResourceLocation liquid1, ResourceLocation liquid2, Block block) {
        this.liquid1 = liquid1;
        this.liquid2 = liquid2;
        this.block = block;
    }

    @ZenMethod
    public static void registerLiquidInteraction(ILiquidStack liquid1, ILiquidStack liquid2, IItemStack block) {
        CraftTweakerAPI.apply(new ActionRegisterLiquidInteraction(liquid1, liquid2, block));
    }
}
