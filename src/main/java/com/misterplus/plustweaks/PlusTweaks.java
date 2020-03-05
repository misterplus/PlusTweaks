package com.misterplus.plustweaks;

import com.misterplus.plustweaks.config.Configs;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.security.krb5.Config;

@Mod(modid = PlusTweaks.MOD_ID, name = PlusTweaks.MOD_NAME, version = PlusTweaks.VERSION)
public class PlusTweaks {
    public static final String MOD_ID = "plustweaks";
    public static final String MOD_NAME = "PlusTweaks";
    public static final String VERSION = "1.1.1";

    public static Logger logger = LogManager.getLogger(PlusTweaks.MOD_ID);

    public static Block blockGen = null;

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        if (Configs.genericSettings.cobbleGenResult.length() > 0) {
            blockGen = Block.REGISTRY.getObject(new ResourceLocation(Configs.genericSettings.cobbleGenResult));
        }
    }
}
