package plus.misterplus.plustweaks.compact.crafttweaker.actions;

import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.util.ResourceLocation;
import plus.misterplus.plustweaks.utils.UtilityMethods;

import java.util.HashMap;

public class ActionSetLiquidInfinite implements IAction {

    public static HashMap<String, Boolean> infiniteLiquids = new HashMap<>();

    private final ResourceLocation liquid;
    private final boolean finite;

    public ActionSetLiquidInfinite(ILiquidStack liquidStack, boolean finite) {
        this.liquid = UtilityMethods.getRegisteryName(liquidStack);
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
