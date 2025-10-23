package owo.pigeon.injections;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@IFMLLoadingPlugin.Name("Pigeon")
public class PigeonMixinLoader implements IFMLLoadingPlugin {

    public PigeonMixinLoader() {
        try {
            MixinBootstrap.init();
            Mixins.addConfiguration("mixins.pigeon.json");
            MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
        } catch (Exception | Error e) {
            Logger.getLogger("PigeonMixinLoader").log(Level.OFF, "Mixin cannot load successfully,some function may not available now.");
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> map) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
