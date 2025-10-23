package owo.pigeon.features.modules.render;

import org.lwjgl.input.Keyboard;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;

public class Camera extends Module {


    public Camera() {
        super("Camera", Category.RENDER, -1);
    }

    public EnableSetting noFire = setting("no-fire", true, v -> true);
    public EnableSetting noHurtCam = setting("no-hurtcam", true, v -> true);
    public EnableSetting noBlindness = setting("no-blindness", true, v -> true);
    public EnableSetting camNoClip = setting("cam-noclip", true, v -> true);
    public static boolean freelook = true;
    public static int freelookkeybind = Keyboard.KEY_F;
}
