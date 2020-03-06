package com.misterplus.plustweaks.config;

import com.misterplus.plustweaks.PlusTweaks;
import net.minecraftforge.common.config.Config;

@Config(modid = PlusTweaks.MOD_ID, name = PlusTweaks.MOD_NAME)
public class Configs {

    @Config.Comment("Config Settings related to portals")
    public static PortalSettings portalSettings = new PortalSettings();

    @Config.Comment("Config Settings for generic tweaks")
    public static GenericSettings genericSettings = new GenericSettings();

    public static class PortalSettings {
        @Config.RequiresMcRestart
        @Config.Comment("Prevent the End Portal from spawning after the Ender Dragon is dead (default: true)")
        public boolean noEndPortal = true;
        @Config.Comment("Prevent the Nether Portal from igniting (default: true)")
        public boolean noNetherPortal = true;
    }

    public static class GenericSettings {
        @Config.RequiresMcRestart
        @Config.Comment("The block that vanilla cobblegen produces, leave empty to disable cobblegen completely (default: minecraft:cobblestone)")
        public String cobbleGenResult = "minecraft:cobblestone";
    }
}