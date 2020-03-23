package plus.misterplus.plustweaks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static plus.misterplus.plustweaks.config.Configs.dangerousSettings;
import static plus.misterplus.plustweaks.config.Configs.genericSettings;

@Mod(
        modid = PlusTweaks.MOD_ID,
        name = PlusTweaks.MOD_NAME,
        version = PlusTweaks.VERSION,
        certificateFingerprint = "@FINGERPRINT@",
        dependencies = "after:torcherino"
)
public class PlusTweaks {
    public static final String MOD_ID = "plustweaks";
    public static final String MOD_NAME = "PlusTweaks";
    public static final String VERSION = "1.3.6";

    public static Logger logger = LogManager.getLogger(PlusTweaks.MOD_NAME);

    public static Block blockGen = null;
    public static Block blockCool = null;
    public static Block blockSolid = null;

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        blockGen = findBlock(genericSettings.cobbleGenResult, Blocks.COBBLESTONE, "Setting vanilla cobblegen result to: ", "Vanilla cobblegen was configured to be disabled!");
        blockCool = findBlock(genericSettings.lavaCoolResult, Blocks.OBSIDIAN, "Setting lava cooling result to: ", "Lava cooling was configured to be disabled!");
        blockSolid = findBlock(genericSettings.waterSolidifyResult, Blocks.STONE, "Setting water solidifying result to: ", "Water solidifying was configured to be disabled!");
        if (Loader.isModLoaded("torcherino") && dangerousSettings.disableTorcherino) {
            logger.info("Disabling torcherino functionality due to balancing reasons, if the author of torcherino sees this, nothing is wrong with your mod, don't worry");
        }
    }

    private static Block findBlock(String block, Block original, String success, String disabled) {
        Block result = null;
        if (block.length() > 0) {
            result = Block.REGISTRY.getObject(new ResourceLocation(block));
            if(!result.equals(Blocks.AIR))
                logger.info(success + result.getRegistryName());
            else {
                result = original;
                logger.error(block + " does not exist! Returning to default...");
            }
        }
        else
            logger.info(disabled);
        return result;
    }
}