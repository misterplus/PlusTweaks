package com.misterplus.plustweaks;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = PlusTweaks.MOD_ID, name = PlusTweaks.MOD_NAME, version = PlusTweaks.VERSION)
public class PlusTweaks {
    public static final String MOD_ID = "plustweaks";
    public static final String MOD_NAME = "PlusTweaks";
    public static final String VERSION = "1.1.0";

    public static Logger logger = LogManager.getLogger(PlusTweaks.MOD_ID);
}
