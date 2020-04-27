package plus.misterplus.plustweaks.compact.crafttweaker.actions;

import crafttweaker.IAction;
import crafttweaker.api.enchantments.IEnchantment;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.state.IBlockState;

import java.util.HashMap;
import java.util.Map;

public class ActionEnchantmentLockByLevel implements IAction {

    public static HashMap<String, Map<IBlockState, Integer>> lockedLeveledEnchants = new HashMap<>();
    private final Map<net.minecraft.block.state.IBlockState, Integer> blocks;
    private final String enchantment;

    public ActionEnchantmentLockByLevel(IEnchantment enchantment, Map<crafttweaker.api.block.IBlockState, Integer> blocks) {
        this.enchantment = enchantment.getDefinition().getRegistryName() + ":" + enchantment.getLevel();
        Map<net.minecraft.block.state.IBlockState, Integer> blockMap = new HashMap<>();
        for (crafttweaker.api.block.IBlockState blockState : blocks.keySet()) {
            blockMap.put(CraftTweakerMC.getBlockState(blockState), blocks.get(blockState));
        }
        this.blocks = blockMap;
    }

    @Override
    public void apply() {
        lockedLeveledEnchants.put(this.enchantment, this.blocks);
    }

    @Override
    public String describe() {
        return String.format("Locking enchantment %s behind %s", this.enchantment, this.blocks.toString());
    }
}
