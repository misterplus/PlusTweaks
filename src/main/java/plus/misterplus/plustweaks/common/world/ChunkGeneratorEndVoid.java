package plus.misterplus.plustweaks.common.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorEnd;

public class ChunkGeneratorEndVoid extends ChunkGeneratorEnd {

    public ChunkGeneratorEndVoid(World p_i47241_1_, long p_i47241_3_) {
        super(p_i47241_1_, false, p_i47241_3_, new BlockPos(0, 63, 0));
    }

    @Override
    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
        if (x == 0 && z == 0)
            primer.setBlockState(0, 63, 0, Blocks.BEDROCK.getDefaultState());
    }

    @Override
    public void buildSurfaces(ChunkPrimer primer) {
    }
}
