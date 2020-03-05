package com.misterplus.plustweaks.core;

import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Map;

public class PlusTweaksFMLSetupHook implements IFMLCallHook {

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public Void call() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.plustweaks.json");
        CodeSource codeSource = this.getClass().getProtectionDomain().getCodeSource();
        if (codeSource != null) {
            URL location = codeSource.getLocation();
            try {
                File file = new File(location.toURI());
                if (file.isFile()) {
                    CoreModManager.getReparseableCoremods().remove(file.getName());
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            LogManager.getLogger().warn("No CodeSource, if this is not a development environment we might run into problems!");
            LogManager.getLogger().warn(this.getClass().getProtectionDomain());
        }
        return null;
    }
}
