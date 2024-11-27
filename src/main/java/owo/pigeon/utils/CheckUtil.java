package owo.pigeon.utils;

import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static owo.pigeon.modules.Module.mc;

public class CheckUtil {

    //检测玩家和世界是否为空
    public static boolean NotnullCheck() {
        return (mc.thePlayer != null && mc.theWorld != null);
    }

    public static boolean OnlyWhitespace(String message) {
        return message.trim().isEmpty();
    }

    public static boolean isBreakingBlock(){
        return mc.thePlayer.isSwingInProgress &&
                mc.objectMouseOver != null &&
                mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                Mouse.isButtonDown(0);
    }

    public static boolean isContains(String[] stringarray, String message, boolean isRegex) {
        for (String patternStr : stringarray) {
            if (isRegex) {
                // 如果需要使用正则表达式匹配
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(message);
                if (matcher.find()) {
                    return true;  // 找到并返回
                }
            } else {
                // 如果是普通的子字符串匹配
                if (message.contains(patternStr)) {
                    return true;  // 找到并返回
                }
            }
        }
        return false;  // 如果没有找到任何匹配
    }

    public static boolean isContainsRegex(String[] stringarray, String message) {
        for (String patternStr : stringarray) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                return true;  // 找到并返回
            }
        }
        return false;  // 如果没有找到任何匹配
    }
}
