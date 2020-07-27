package plus.misterplus.plustweaks.common.world;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.gen.IChunkGenerator;

import static plus.misterplus.plustweaks.config.Configs.worldGenSettings;

public class WorldProviderNetherVoid extends WorldProviderHell {

	@Override
	public IChunkGenerator createChunkGenerator() {
		if (getDimension() == -1 && worldGenSettings.netherVoid)
			return new ChunkGeneratorNetherVoid(world, world.getSeed());
		return super.createChunkGenerator();
	}
}
