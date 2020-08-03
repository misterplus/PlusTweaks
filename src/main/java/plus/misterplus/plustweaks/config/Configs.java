package plus.misterplus.plustweaks.config;

import net.minecraftforge.common.config.Config;
import plus.misterplus.plustweaks.PlusTweaks;

import static net.minecraftforge.common.config.Config.*;

@Config(modid = PlusTweaks.MOD_ID, name = PlusTweaks.MOD_NAME)
public class Configs {

    @Comment("Config Settings related to portals")
    public static PortalSettings portalSettings = new PortalSettings();

    @Comment("Config Settings for generic tweaks")
    public static GenericSettings genericSettings = new GenericSettings();

    @Comment("Config Settings that I question why somebody would ever touch")
    public static DangerousSettings dangerousSettings = new DangerousSettings();

    @Comment("Config Settings related to worldgen")
    public static WorldGenSettings worldGenSettings = new WorldGenSettings();

    public static class PortalSettings {
        @Comment("Prevent the End Portal from spawning after the Ender Dragon is dead (default: false)")
        public boolean noEndPortal = false;

        @Comment("Prevent the Nether Portal from igniting (default: false)")
        public boolean noNetherPortal = false;
    }

    public static class GenericSettings {
        @RequiresMcRestart
        @Comment("The block that vanilla cobblegen produces, leave empty to disable cobblegen completely (default: minecraft:cobblestone)")
        public String cobbleGenResult = "minecraft:cobblestone";

        @RequiresMcRestart
        @Comment("The block that lava source block gets replaced with when touching water, leave empty to disable lava cooling (default: minecraft:obsidian)")
        public String lavaCoolResult = "minecraft:obsidian";

        @RequiresMcRestart
        @Comment("The block that water source block gets replaced with when touching lava, leave empty to disable water solidifying (default: minecraft:stone)")
        public String waterSolidifyResult = "minecraft:stone";

        @Comment("Force ender dragon respawn to check for 4 crystals on the axises (default: true)")
        public boolean strictDragon = true;

        @Comment("Maximum growth height for sugarcane (default: 3)")
        @RangeInt(min = 1, max = 254)
        public int reedHeight = 3;

        @Comment("Maximum growth height for cactus (default: 3)")
        @RangeInt(min = 1, max = 254)
        public int cactusHeight = 3;
    }

    public static class WorldGenSettings {
        @Comment("Set the default world type to plustweaks' void world (default: false)")
        @RequiresMcRestart
        public boolean defaultToVoidWorldType = false;

        @Comment("Void World will have a void Overworld (default: false)")
        @RequiresMcRestart
        public boolean overworldVoid = false;

        @Comment("Void World will have a void Nether (default: false)")
        @RequiresMcRestart
        public boolean netherVoid = false;

        @Comment("Void World will have a void End (default: false)")
        @RequiresMcRestart
        public boolean endVoid = false;
    }

    public static class DangerousSettings {
        @Comment("HEY GUYS IT'S NOT OP BECAUSE I CRAFTED IT")
        public boolean disableTorcherino = true;
    }
}