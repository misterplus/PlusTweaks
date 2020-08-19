package plus.misterplus.plustweaks.common.world;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;

import static plus.misterplus.plustweaks.config.Configs.worldGenSettings;

public class WorldTypeVoid extends WorldType {

    public WorldTypeVoid() {
        super("void_plustweaks");
    }

    public static void registerWorldProviders() {
        if (worldGenSettings.netherVoid) {
            DimensionManager.unregisterDimension(-1);
            DimensionManager.registerDimension(-1, DimensionType.register("VoidNether", "_nether", -1, WorldProviderNetherVoid.class, true));
        }
        if (worldGenSettings.endVoid) {
            DimensionManager.unregisterDimension(1);
            DimensionManager.registerDimension(1, DimensionType.register("VoidEnd", "_end", 1, WorldProviderEndVoid.class, true));
        }
    }

    public boolean hasInfoNotice() {
        return true;
    }

    @Override
    public net.minecraft.world.gen.IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
        return new ChunkGeneratorOverworldVoid(world, world.getSeed(), !worldGenSettings.overworldVoid, generatorOptions);
    }
}
