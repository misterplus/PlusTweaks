package com.misterplus.plustweaks.config;

import com.misterplus.plustweaks.PlusTweaks;
import net.minecraftforge.common.config.Config;

import static net.minecraftforge.common.config.Config.*;

@Config(modid = PlusTweaks.MOD_ID, name = PlusTweaks.MOD_NAME)
public class Configs {

    @Comment("Config Settings related to portals")
    public static PortalSettings portalSettings = new PortalSettings();

    @Comment("Config Settings for generic tweaks")
    public static GenericSettings genericSettings = new GenericSettings();

    public static class PortalSettings {
        @RequiresMcRestart
        @Comment("Prevent the End Portal from spawning after the Ender Dragon is dead (default: true)")
        public boolean noEndPortal = true;

        @RequiresMcRestart
        @Comment("Prevent the Nether Portal from igniting (default: true)")
        public boolean noNetherPortal = true;
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

        @RequiresMcRestart
        @Comment("Force ender dragon respawn to check for 4 crystals on the axises (default: true)")
        public boolean strictDragon = true;

        @RequiresMcRestart
        @Comment("Maximum growth height for sugarcane (default: 3)")
        @RangeInt(min = 1, max = 254)
        public int reedHeight = 3;

        @RequiresMcRestart
        @Comment("Maximum growth height for cactus (default: 3)")
        @RangeInt(min = 1, max = 254)
        public int cactusHeight = 3;
    }
}