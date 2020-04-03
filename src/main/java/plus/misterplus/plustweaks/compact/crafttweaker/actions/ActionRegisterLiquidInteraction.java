package plus.misterplus.plustweaks.compact.crafttweaker.actions;

import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import plus.misterplus.plustweaks.utils.UtilityMethods;

import java.util.Arrays;
import java.util.HashMap;

public class ActionRegisterLiquidInteraction implements IAction {

    public static HashMap<String, HashMap<Integer, IBlockState>> interactions = new HashMap<>();

    private final ResourceLocation liquid1, liquid2;
    private final crafttweaker.api.block.IBlockState block;
    private final int[] levels;

    public ActionRegisterLiquidInteraction(ILiquidStack liquid1, ILiquidStack liquid2, crafttweaker.api.block.IBlockState block, int[] levels) {
        this.liquid1 = UtilityMethods.getRegisteryName(liquid1);
        this.liquid2 = UtilityMethods.getRegisteryName(liquid2);
        this.block = block;
        this.levels = levels;
    }

    @Override
    public void apply() {
        HashMap<Integer, IBlockState> blockList = new HashMap<>();
        for (Integer i : this.levels) {
            blockList.put(i, CraftTweakerMC.getBlockState(this.block));
        }
        interactions.put(this.liquid1 + ":" + this.liquid2, blockList);
    }

    @Override
    public String describe() {
        return String.format("Adding a interaction between %s and %s that produces %s, levels : %s", this.liquid1, this.liquid2, CraftTweakerMC.getBlockState(this.block).getBlock().getRegistryName(), Arrays.toString(this.levels));
    }

    @Override
    public boolean validate() {
        return !this.liquid1.equals(this.liquid2);
    }

    @Override
    public String describeInvalid() {
        if (liquid1.equals(liquid2))
            return String.format("%s cannot interact with itself, skipping...", liquid1);
        return null;
    }
}