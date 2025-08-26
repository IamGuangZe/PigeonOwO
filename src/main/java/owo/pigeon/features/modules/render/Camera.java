package owo.pigeon.features.modules.render;

import org.lwjgl.input.Keyboard;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;

public class Camera extends Module {


    public Camera() {
        super("Camera", Category.RENDER,-1);
    }

    public EnableSetting noFire = setting("No Fire",true,"Remove the flames when on fire.",v->true);
    public EnableSetting noHurtCam = setting("No HurtCam",true,"Remove the camera shake when taking damage.",v->true);
    public EnableSetting noBlindness = setting("No Blindness",true,"Remove the blindness effect.",v->true);
    public EnableSetting camNoClip = setting("Camera noclip",true,"Make the third-person perspective see through walls.",v->true);
    public static boolean freelook = true;
    public static int freelookkeybind = Keyboard.KEY_F;
}
