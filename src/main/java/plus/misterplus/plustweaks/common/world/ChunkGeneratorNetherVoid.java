package plus.misterplus.plustweaks.common.world;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorHell;

import static plus.misterplus.plustweaks.config.Configs.worldGenSettings;

public class ChunkGeneratorNetherVoid extends ChunkGeneratorHell {

    public ChunkGeneratorNetherVoid(World worldIn, long seed) {
        super(worldIn, false, seed);
    }

    @Override
    public void prepareHeights(int p_185936_1_, int p_185936_2_, ChunkPrimer primer) {
        if (!worldGenSettings.netherVoid)
            super.prepareHeights(p_185936_1_, p_185936_2_, primer);
    }

    @Override
    public void buildSurfaces(int p_185937_1_, int p_185937_2_, ChunkPrimer primer) {
        if (!worldGenSettings.netherVoid)
            super.buildSurfaces(p_185937_1_, p_185937_2_, primer);
    }
}
