package com.misterplus.plustweaks.compact.crafttweaker.actions;

import com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import static com.misterplus.plustweaks.compact.crafttweaker.LiquidInteraction.ctInteractions;

public class ActionRegisterLiquidInteraction implements IAction {

    private final ResourceLocation liquid1, liquid2;
    private final IItemStack block;

    public ActionRegisterLiquidInteraction(ILiquidStack liquid1, ILiquidStack liquid2, IItemStack block) {
        this.liquid1 = getRegisteryName(liquid1);
        this.liquid2 = getRegisteryName(liquid2);
        this.block = block;
    }

    private static ResourceLocation getRegisteryName(ILiquidStack liquidStack) {
        return CraftTweakerMC.getLiquidStack(liquidStack).getFluid().getBlock().getRegistryName();
    }

    @Override
    public void apply() {
        LiquidInteraction interaction = new LiquidInteraction(this.liquid1, this.liquid2, CraftTweakerMC.getBlock(this.block));
        ctInteractions.add(interaction);
    }

    @Override
    public String describe() {
        return String.format("Adding a new interaction between %s and %s that produces %s", liquid1, liquid2, CraftTweakerMC.getBlock(this.block).getRegistryName());
    }

    @Override
    public boolean validate() {
        if (liquid1.equals(liquid2))
            return false;
        else if (CraftTweakerMC.getBlock(this.block).getRegistryName() == Blocks.AIR.getRegistryName())
            return false;
        return true;
    }

    @Override
    public String describeInvalid() {
        if (liquid1.equals(liquid2))
            return String.format("%s cannot interact with itself, skipping...", liquid1);
        else if (CraftTweakerMC.getBlock(this.block).getRegistryName() == Blocks.AIR.getRegistryName())
            return String.format("%s is not a block, skipping...", CraftTweakerMC.getItemStack(block).getItem().getRegistryName());
        return null;
    }
}