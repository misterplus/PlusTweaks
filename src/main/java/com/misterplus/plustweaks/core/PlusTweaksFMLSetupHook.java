package com.misterplus.plustweaks.core;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

public class PlusTweaksFMLSetupHook implements IFMLCallHook {

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public Void call() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.plustweaks.json");
        return null;
    }
}
