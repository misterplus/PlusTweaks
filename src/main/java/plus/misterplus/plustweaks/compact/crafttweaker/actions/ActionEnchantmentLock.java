package plus.misterplus.plustweaks.compact.crafttweaker.actions;

import crafttweaker.IAction;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.minecraft.CraftTweakerMC;

import java.util.HashMap;
import java.util.Map;

public class ActionEnchantmentLock implements IAction {

    public static HashMap<String, Map<net.minecraft.block.state.IBlockState, Integer>> lockedEnchants = new HashMap<>();

    private final String enchantName;
    private final Map<net.minecraft.block.state.IBlockState, Integer> blocks;

    public ActionEnchantmentLock(String enchantName, Map<IBlockState, Integer> blocks) {
        this.enchantName = enchantName;
        Map<net.minecraft.block.state.IBlockState, Integer> blockMap = new HashMap<>();
        for (IBlockState blockState : blocks.keySet()) {
            blockMap.put(CraftTweakerMC.getBlockState(blockState), blocks.get(blockState));
        }
        this.blocks = blockMap;
    }

    @Override
    public void apply() {
        lockedEnchants.put(this.enchantName, this.blocks);
    }

    @Override
    public String describe() {
        return String.format("Locking enchantment %s behind %s", this.enchantName, this.blocks.toString());
    }
}
