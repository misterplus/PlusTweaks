package com.misterplus.plustweaks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.misterplus.plustweaks.config.Configs.genericSettings;

@Mod(
        modid = PlusTweaks.MOD_ID,
        name = PlusTweaks.MOD_NAME,
        version = PlusTweaks.VERSION,
        certificateFingerprint = "@FINGERPRINT@"
)
public class PlusTweaks {
    public static final String MOD_ID = "plustweaks";
    public static final String MOD_NAME = "PlusTweaks";
    public static final String VERSION = "1.3.0";

    public static Logger logger = LogManager.getLogger(PlusTweaks.MOD_NAME);

    public static Block blockGen = null;
    public static Block blockCool = null;
    public static Block blockSolid = null;

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        findBlockGen(genericSettings.cobbleGenResult);
        findBlockCool(genericSettings.lavaCoolResult);
        findBlockSolid(genericSettings.waterSolidifyResult);
    }
    
    private static void findBlockGen(String block) {
        if (block.length() > 0) {
            blockGen = Block.REGISTRY.getObject(new ResourceLocation(block));
            if(!blockGen.equals(Blocks.AIR))
                logger.info("Setting vanilla cobblegen result to: " + blockGen.getRegistryName());
            else {
                blockGen = Blocks.COBBLESTONE;
                logger.error(block + " does not exist! Returning to default...");
            }
        }
        else
            logger.info("Vanilla cobblegen was configured to be disabled!");
    }
    
    private static void findBlockCool(String block) {
        if (block.length() > 0) {
            blockCool = Block.REGISTRY.getObject(new ResourceLocation(block));
            if(!blockCool.equals(Blocks.AIR))
                logger.info("Setting lava cooling result to: " + blockCool.getRegistryName());
            else {
                blockCool = Blocks.OBSIDIAN;
                logger.error(block + " does not exist! Returning to default...");
            }
        }
        else
            logger.info("Lava cooling was configured to be disabled!");
    }

    private static void findBlockSolid(String block) {
        if (block.length() > 0) {
            blockSolid = Block.REGISTRY.getObject(new ResourceLocation(block));
            if(!blockSolid.equals(Blocks.AIR))
                logger.info("Setting water solidifying result to: " + blockSolid.getRegistryName());
            else {
                blockSolid = Blocks.STONE;
                logger.error(block + " does not exist! Returning to default...");
            }
        }
        else
            logger.info("Water solidifying was configured to be disabled!");
    }
}