package plus.misterplus.plustweaks.common.world;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorOverworld;

import static plus.misterplus.plustweaks.config.Configs.worldGenSettings;

public class ChunkGeneratorOverworldVoid extends ChunkGeneratorOverworld {

    public ChunkGeneratorOverworldVoid(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions) {
        super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions);
    }

    @Override
    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
        if (!worldGenSettings.overworldVoid)
            super.setBlocksInChunk(x, z, primer);
    }

    @Override
    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
        if (!worldGenSettings.overworldVoid)
            super.replaceBiomeBlocks(x, z, primer, biomesIn);
    }
}
