package com.misterplus.plustweaks.handlers;

import com.misterplus.plustweaks.PlusTweaks;
import com.misterplus.plustweaks.config.Configs;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static com.misterplus.plustweaks.PlusTweaks.MOD_NAME;

@Mod.EventBusSubscriber(modid = PlusTweaks.MOD_ID)
public class EventHandler {
    public static boolean DEV_ENV = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    public static boolean VALID_JAR = true;

    @SubscribeEvent
    public static void onPortalSpawnEvent(BlockEvent.PortalSpawnEvent event) {
        if (Configs.portalSettings.noNetherPortal)
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!VALID_JAR)
            event.player.sendMessage(new TextComponentString(String.format("[%s]Your copy of PlusTweaks is invalid, the author is not responsible for any bugs that might occur with this build.",MOD_NAME)));
    }

    @Mod.EventHandler
    public static void onInvalidCertificate(FMLFingerprintViolationEvent event) {
        if (!DEV_ENV)
            VALID_JAR = false;
    }
}
