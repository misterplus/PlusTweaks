package com.misterplus.plustweaks.handlers;

import com.misterplus.plustweaks.PlusTweaks;
import com.misterplus.plustweaks.config.Configs;
import com.sci.torcherino.blocks.blocks.BlockLanterino;
import com.sci.torcherino.blocks.blocks.BlockTorcherino;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static com.misterplus.plustweaks.PlusTweaks.MOD_NAME;
import static com.misterplus.plustweaks.config.Configs.dangerousSettings;

@Mod.EventBusSubscriber(modid = PlusTweaks.MOD_ID)
public class EventHandler {
    public static boolean DEV_ENV = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    public static boolean VALID_JAR = true;

    @SubscribeEvent
    public static void onPortalSpawnEvent(BlockEvent.PortalSpawnEvent event) {
        if (Configs.portalSettings.noNetherPortal)
            event.setCanceled(true);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (Loader.isModLoaded("torcherino") && dangerousSettings.disableTorcherino) {
            IBlockState state = event.getWorld().getBlockState(event.getPos());
            Block block = state.getBlock();
            if (block instanceof BlockLanterino) {
                event.getWorld().setBlockState(event.getPos(), Blocks.LIT_PUMPKIN.getStateFromMeta(block.getMetaFromState(state)));
                event.setCanceled(true);
            }
            else if (block instanceof BlockTorcherino) {
                event.getWorld().setBlockState(event.getPos(), Blocks.TORCH.getStateFromMeta(block.getMetaFromState(state)));
                event.setCanceled(true);
            }
        }
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
