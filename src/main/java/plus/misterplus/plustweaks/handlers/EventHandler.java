package plus.misterplus.plustweaks.handlers;

import com.sci.torcherino.blocks.blocks.BlockLanterino;
import com.sci.torcherino.blocks.blocks.BlockTorcherino;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import plus.misterplus.plustweaks.PlusTweaks;
import plus.misterplus.plustweaks.compact.crafttweaker.actions.ActionSetLiquidInfinite;
import plus.misterplus.plustweaks.config.Configs;
import plus.misterplus.plustweaks.utils.EndGatewayJump;

import java.util.Objects;

import static plus.misterplus.plustweaks.config.Configs.dangerousSettings;
import static plus.misterplus.plustweaks.config.Configs.genericSettings;
import static plus.misterplus.plustweaks.utils.UtilityMethods.sendLocalizedMessage;

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
                sendLocalizedMessage(event.getEntityPlayer(), "message.plustweaks.torcherino");
                event.setCanceled(true);
            } else if (block instanceof BlockTorcherino) {
                event.getWorld().setBlockState(event.getPos(), Blocks.TORCH.getStateFromMeta(block.getMetaFromState(state)));
                sendLocalizedMessage(event.getEntityPlayer(), "message.plustweaks.torcherino");
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
        } else if (block instanceof BlockLiquid) {
            name = Objects.requireNonNull(BlockDynamicLiquid.getStaticBlock(state.getMaterial()).getRegistryName()).toString();
        }
        if (ActionSetLiquidInfinite.infiniteLiquids.containsKey(name)) {
            event.setResult(ActionSetLiquidInfinite.infiniteLiquids.get(name) ? Event.Result.DENY : Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.getEntityWorld().isRemote) {
            if (!VALID_JAR) {
                event.player.sendMessage(new TextComponentString(String.format("[%s]Your copy of PlusTweaks is invalid, the author is not responsible for any bugs that might occur with this build.", PlusTweaks.MOD_NAME)));
            }
            if (event.player.getEntityWorld().provider.getDimension() == 1) {
                EntityPlayer player = event.player;
                if (isFirstLogin(player, "plustweaks_gateway_jump")) {
                    if (genericSettings.gatewayJump) {
                        EndGatewayJump.execute(player, player.getEntityWorld());
                    }
                }
            }
        }
    }

    @Mod.EventHandler
    public static void onInvalidCertificate(FMLFingerprintViolationEvent event) {
        if (!DEV_ENV)
            VALID_JAR = false;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(PlusTweaks.MOD_ID)) {
            ConfigManager.sync(PlusTweaks.MOD_ID, Config.Type.INSTANCE);
        }
    }

    //NBT related code from FTBLib
    private static NBTTagCompound getPersistedData(EntityPlayer player, boolean createIfMissing) {
        NBTTagCompound tag = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        if (createIfMissing) {
            player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, tag);
        }
        return tag;
    }

    private static boolean isFirstLogin(EntityPlayer player, String key) {
        if (!getPersistedData(player, false).getBoolean(key)) {
            getPersistedData(player, true).setBoolean(key, true);
            return true;
        }
        return false;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (genericSettings.noBottling && event.getItemStack().getItem() instanceof ItemGlassBottle) {
            RayTraceResult raytraceresult = rayTrace(event.getWorld(), event.getEntityPlayer(), true);
            World worldIn = event.getWorld();
            if (raytraceresult != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos blockpos = raytraceresult.getBlockPos();
                if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER) {
                    event.setCanceled(true);
                }
            }
        }
    }

    private static RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
        float f = playerIn.rotationPitch;
        float f1 = playerIn.rotationYaw;
        double d0 = playerIn.posX;
        double d1 = playerIn.posY + (double) playerIn.getEyeHeight();
        double d2 = playerIn.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d3 = playerIn.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        Vec3d vec3d1 = vec3d.add((double) f6 * d3, (double) f5 * d3, (double) f7 * d3);
        return worldIn.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
    }
}
