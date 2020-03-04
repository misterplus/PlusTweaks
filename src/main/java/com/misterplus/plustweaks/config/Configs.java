package com.misterplus.plustweaks.config;

import com.misterplus.plustweaks.PlusTweaks;
import net.minecraftforge.common.config.Config;

@Config(modid = PlusTweaks.MOD_ID)
public class Configs {

    @Config.Comment("Config Settings related to portals")
    public static PortalSettings portalSettings = new PortalSettings();

    @Config.Comment("Config Settings for generic tweaks")
    public static GenericSettings genericSettings = new GenericSettings();

    public static class PortalSettings {
        @Config.Comment("Prevent the End Portal from spawning after the Ender Dragon is dead")
        public boolean noEndPortal = true;
        @Config.Comment("Prevent the Nether Portal from igniting")
        public boolean noNetherPortal = true;
    }

    public static class GenericSettings {
        @Config.Comment("Disable vanilla cobblestone generator")
        public boolean noCobbleGen = true;
    }
}