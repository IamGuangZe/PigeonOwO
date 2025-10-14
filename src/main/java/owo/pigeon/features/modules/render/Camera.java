package owo.pigeon.features.modules.render;

import org.lwjgl.input.Keyboard;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;

public class Camera extends Module {


    public Camera() {
        super("Camera", Category.RENDER,-1);
    }

    public EnableSetting noFire = setting("nofire",true,"Remove the flames when on fire.",v->true);
    public EnableSetting noHurtCam = setting("nohurtcam",true,"Remove the camera shake when taking damage.",v->true);
    public EnableSetting noBlindness = setting("noblindness",true,"Remove the blindness effect.",v->true);
    public EnableSetting camNoClip = setting("camnoclip",true,"Make the third-person perspective see through walls.",v->true);
    public static boolean freelook = true;
    public static int freelookkeybind = Keyboard.KEY_F;
}
