package owo.pigeon.utils;

import net.minecraft.block.Block;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtherUtil {
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
    public static boolean isContainsRegex(String regex, String message) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        return matcher.find();
    }

    public static String regexGetPart(String[] regex, String message, int part) {
        for (String r : regex) {
            Pattern pattern = Pattern.compile(r + "( \\[C])?");
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                if (matcher.groupCount() >= part) {
                    return matcher.group(part);
                }
            }
        }
        return null;
    }
    public static String regexGetPart(String regex, String message, int part) {
        Pattern pattern = Pattern.compile(regex + "( \\[C])?");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            if (matcher.groupCount() >= part) {
                return matcher.group(part);
            }
        }
        return null;
    }

    public static boolean OnlyWhitespace(String message) {
        return message.trim().isEmpty();
    }

    public static String parseColor(String msg) {
        return msg.replaceAll("&","§")  //& -> §
                .replaceAll("§§","&");  //&& -> §§ -> &
    }
    public static String removeColor(String msg) {
        return msg.replaceAll("§.", "");
    }
    public static String removeColorA(String msg) {
        return msg.replaceAll("§", "&");
    }

    public static String removeEmoji(String s) {
        return s.replaceAll("[🎂🎉🎁👹🏀⚽🍭🌠👾🐍🔮👽💣🍫🔫]","");
    }

    public static int countChar(String s, char c) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

}
