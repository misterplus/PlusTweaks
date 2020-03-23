package plus.misterplus.plustweaks.handlers;

import plus.misterplus.plustweaks.PlusTweaks;
import plus.misterplus.plustweaks.config.Configs;
import com.sci.torcherino.blocks.blocks.BlockLanterino;
import com.sci.torcherino.blocks.blocks.BlockTorcherino;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionSetLiquidInfinite;

import java.util.Objects;

import static plus.misterplus.plustweaks.config.Configs.dangerousSettings;

@Mod.EventBusSubscriber(modid = PlusTweaks.MOD_ID)
public class EventHandler {
    public static boolean DEV_ENV = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    public static boolean VALID_JAR = true;

    @SubscribeEvent
    public static void onPortalSpawn(BlockEvent.PortalSpawnEvent event) {
        if (Configs.portalSettings.noNetherPortal)
            event.setCanceled(true);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (Loader.isModLoaded("torcherino") && dangerousSettings.disableTorcherino && !event.getWorld().isRemote) {
            IBlockState state = event.getWorld().getBlockState(event.getPos());
            Block block = state.getBlock();
            if (block instanceof BlockLanterino) {
                event.getWorld().setBlockState(event.getPos(), Blocks.LIT_PUMPKIN.getStateFromMeta(block.getMetaFromState(state)));
                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.plustweaks.torcherino"));
                event.setCanceled(true);
            }
            else if (block instanceof BlockTorcherino) {
                event.getWorld().setBlockState(event.getPos(), Blocks.TORCH.getStateFromMeta(block.getMetaFromState(state)));
                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.plustweaks.torcherino"));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onCreateFluidSource(BlockEvent.CreateFluidSourceEvent event) {
        IBlockState state = event.getState();
        Block block = state.getBlock();
        String name = null;
        if (block instanceof BlockFluidBase) {
            name = Objects.requireNonNull(block.getRegistryName()).toString();
        }
        else if (block instanceof BlockLiquid) {
            name = Objects.requireNonNull(BlockDynamicLiquid.getStaticBlock(state.getMaterial()).getRegistryName()).toString();
        }
        if (ActionSetLiquidInfinite.infiniteLiquids.containsKey(name)) {
            event.setResult(ActionSetLiquidInfinite.infiniteLiquids.get(name) ? Event.Result.DENY : Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!VALID_JAR && !event.player.getEntityWorld().isRemote)
            event.player.sendMessage(new TextComponentString(String.format("[%s]Your copy of PlusTweaks is invalid, the author is not responsible for any bugs that might occur with this build.", PlusTweaks.MOD_NAME)));
    }

    @Mod.EventHandler
    public static void onInvalidCertificate(FMLFingerprintViolationEvent event) {
        if (!DEV_ENV)
            VALID_JAR = false;
    }
}
