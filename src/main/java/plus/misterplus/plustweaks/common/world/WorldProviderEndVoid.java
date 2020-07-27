package plus.misterplus.plustweaks.common.world;

import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.gen.IChunkGenerator;

import static plus.misterplus.plustweaks.config.Configs.worldGenSettings;

public class WorldProviderEndVoid extends WorldProviderEnd
{
	@Override
	public IChunkGenerator createChunkGenerator() {
		if (getDimension() == 1 && worldGenSettings.endVoid)
			return new ChunkGeneratorEndVoid(world, world.getSeed());
		return super.createChunkGenerator();
	}
}
