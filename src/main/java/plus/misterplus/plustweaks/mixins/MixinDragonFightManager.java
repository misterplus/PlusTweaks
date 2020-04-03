package plus.misterplus.plustweaks.mixins;

import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.end.DragonFightManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

import static plus.misterplus.plustweaks.config.Configs.genericSettings;

@Mixin(DragonFightManager.class)
public abstract class MixinDragonFightManager {
    @Inject(
            method = "respawnDragon()V",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "net/minecraft/world/WorldServer.getEntitiesWithinAABB(Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;)Ljava/util/List;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    @SuppressWarnings("rawtypes")
    private void injectRespawnDragon(CallbackInfo ci, BlockPos blockpos, List<EntityEnderCrystal> list1, BlockPos blockpos1, Iterator var4, EnumFacing enumfacing, List<EntityEnderCrystal> list) {
        list.removeIf(crystal -> genericSettings.strictDragon && crystal.getDistanceSqToCenter(blockpos1) != 9.25);
    }
}
