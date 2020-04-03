package plus.misterplus.plustweaks.utils;

import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class UtilityMethods {
    public static ResourceLocation getRegisteryName(ILiquidStack liquidStack) {
        return CraftTweakerMC.getLiquidStack(liquidStack).getFluid().getBlock().getRegistryName();
    }

    public static void sendLocalizedMessage(EntityPlayer player, String key) {
        player.sendMessage(new TextComponentTranslation(key));
    }
}



