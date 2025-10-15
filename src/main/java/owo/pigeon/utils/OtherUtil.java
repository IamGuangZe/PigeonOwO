package owo.pigeon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtherUtil {

    // 正则表达匹配
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
    public static int regexGetPartInteger(String regex, String message, int part) {
        String result = regexGetPart(regex, message, part);
        if (result != null) {
            try {
                return Integer.parseInt(result);
            } catch (NumberFormatException ignored) {
            }
        }
        return -1;
    }

    // 空白检测
    public static boolean OnlyWhitespace(String message) {
        return message.trim().isEmpty();
    }

    // 颜色替换
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

    // 我也忘了我为什么写这个了
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

    // 双开区间随机数
    public static int intRandom(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }

        Random random = new Random();
        return min + random.nextInt(max - min + 1);
    }
    public static double doubleRandom(double min, double max) {
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }

        Random random = new Random();
        return min + (random.nextDouble() * (max - min + Double.MIN_VALUE));
    }

    // json
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static void updateJsonKey(File file, String key, Object value) {
        Map<String, Object> map = null;

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                map = gson.fromJson(reader, Map.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (map == null) {
            map = new HashMap<>();
        }

        map.put(key, value);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(map, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
