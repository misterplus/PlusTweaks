package plus.misterplus.plustweaks.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import net.minecraft.world.gen.feature.WorldGenEndIsland;

import java.util.Random;

import static net.minecraft.init.Blocks.END_GATEWAY;

public class EndGatewayJump {

    private static Chunk getChunk(World worldIn, Vec3d vec3) {
        return worldIn.getChunk(MathHelper.floor(vec3.x / 16.0D), MathHelper.floor(vec3.z / 16.0D));
    }

    private static BlockPos findHighestBlock(World p_184308_0_, BlockPos p_184308_1_, int p_184308_2_, boolean p_184308_3_) {
        BlockPos blockpos = null;
        for (int i = -p_184308_2_; i <= p_184308_2_; ++i) {
            for (int j = -p_184308_2_; j <= p_184308_2_; ++j) {
                if (i != 0 || j != 0 || p_184308_3_) {
                    for (int k = 255; k > (blockpos == null ? 0 : blockpos.getY()); --k) {
                        BlockPos blockpos1 = new BlockPos(p_184308_1_.getX() + i, k, p_184308_1_.getZ() + j);
                        IBlockState iblockstate = p_184308_0_.getBlockState(blockpos1);

                        if (iblockstate.isBlockNormalCube() && (p_184308_3_ || iblockstate.getBlock() != Blocks.BEDROCK)) {
                            blockpos = blockpos1;
                            break;
                        }
                    }
                }
            }
        }
        return blockpos == null ? p_184308_1_ : blockpos;
    }

    private static BlockPos findSpawnpointInChunk(Chunk chunkIn) {
        BlockPos blockpos = new BlockPos(chunkIn.x * 16, 30, chunkIn.z * 16);
        int i = chunkIn.getTopFilledSegment() + 16 - 1;
        BlockPos blockpos1 = new BlockPos(chunkIn.x * 16 + 16 - 1, i, chunkIn.z * 16 + 16 - 1);
        BlockPos blockpos2 = null;
        double d0 = 0.0D;
        for (BlockPos blockpos3 : BlockPos.getAllInBox(blockpos, blockpos1)) {
            IBlockState iblockstate = chunkIn.getBlockState(blockpos3);
            if (iblockstate.getBlock() == Blocks.END_STONE && !chunkIn.getBlockState(blockpos3.up(1)).isBlockNormalCube() && !chunkIn.getBlockState(blockpos3.up(2)).isBlockNormalCube()) {
                double d1 = blockpos3.distanceSqToCenter(0.0D, 0.0D, 0.0D);
                if (blockpos2 == null || d1 < d0) {
                    blockpos2 = blockpos3;
                    d0 = d1;
                }
            }
        }
        return blockpos2;
    }

    public static void execute(EntityPlayer player, World world) {
        BlockPos entrance = spawnGateway();
        BlockPos exit = findExitPortal(entrance, world);
        BlockPos blockpos = findExitPosition(world, exit);
        player.setPositionAndUpdate((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D, (double) blockpos.getZ() + 0.5D);
        player.setSpawnPoint(blockpos, true);
    }

    private static BlockPos spawnGateway() {
        int j = (int) (96.0D * Math.cos(2.0D * (-Math.PI + 0.15707963267948966D * (double) 19)));
        int k = (int) (96.0D * Math.sin(2.0D * (-Math.PI + 0.15707963267948966D * (double) 19)));
        return new BlockPos(j, 75, k);
    }

    private static BlockPos findExitPosition(World world, BlockPos exit) {
        BlockPos blockpos = findHighestBlock(world, exit, 5, false);
        return blockpos.up();
    }

    private static BlockPos findExitPortal(BlockPos entrance, World world) {
        Vec3d vec3d = (new Vec3d(entrance.getX(), 0.0D, entrance.getZ())).normalize();
        Vec3d vec3d1 = vec3d.scale(1024.0D);
        for (int i = 16; getChunk(world, vec3d1).getTopFilledSegment() > 0 && i-- > 0; vec3d1 = vec3d1.add(vec3d.scale(-16.0D)))
            ;
        for (int j = 16; getChunk(world, vec3d1).getTopFilledSegment() == 0 && j-- > 0; vec3d1 = vec3d1.add(vec3d.scale(16.0D)))
            ;
        Chunk chunk = getChunk(world, vec3d1);
        BlockPos exit = findSpawnpointInChunk(chunk);
        if (exit == null) {
            exit = new BlockPos(vec3d1.x + 0.5D, 75.0D, vec3d1.z + 0.5D);
            (new WorldGenEndIsland()).generate(world, new Random(exit.toLong()), exit);
        }
        exit = findHighestBlock(world, exit, 16, true);
        exit = exit.up(10);
        if (!world.getBlockState(exit.down()).equals(END_GATEWAY.getDefaultState())) {
            createExitPortal(exit, world, entrance);
        }
        return exit;
    }

    private static void createExitPortal(BlockPos posIn, World world, BlockPos entrance) {
        (new WorldGenEndGateway()).generate(world, new Random(), posIn);
        TileEntity tileentity = world.getTileEntity(posIn);
        if (tileentity instanceof TileEntityEndGateway) {
            TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway) tileentity;
            tileentityendgateway.exitPortal = new BlockPos(entrance);
            tileentityendgateway.markDirty();
        }
    }
}
