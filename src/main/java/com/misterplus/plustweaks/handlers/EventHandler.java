package com.misterplus.plustweaks.handlers;

import com.misterplus.plustweaks.PlusTweaks;
import com.misterplus.plustweaks.config.Configs;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = PlusTweaks.MOD_ID)
public class EventHandler {
    @SubscribeEvent
    public static void onPortalSpawnEvent(BlockEvent.PortalSpawnEvent event) {
        if (Configs.portalSettings.noNetherPortal)
            event.setCanceled(true);
    }
}
