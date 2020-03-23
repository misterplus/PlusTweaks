package plus.misterplus.plustweaks.mixins;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

import static plus.misterplus.plustweaks.config.Configs.genericSettings;
import static plus.misterplus.plustweaks.config.Configs.portalSettings;

public class MixinConfigPlugin implements IMixinConfigPlugin {

    private static final String PREFIX = "com.misterplus.plustweaks.mixins.";

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals(PREFIX + "MixinWorldGenEndPodium"))
            return portalSettings.noEndPortal;
        if (mixinClassName.equals(PREFIX + "MixinDragonFightManager"))
            return genericSettings.strictDragon;
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
