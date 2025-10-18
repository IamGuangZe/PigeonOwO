package owo.pigeon.utils;

public class CommandUtil {

    public enum errorReason {

        ExpectedFloat,  // 不为浮点
        ExpectedInteger,    // 不为整数
        UnknownOrIncompleteCommand, // 未知指令/参数不完整
        InvalidBoolean, // 不为布尔
        IncorrectArgument,  // 参数错误
        UnknownBlock,   // 未知方块
        UnknownItem,    // 未知物品
        UnknownMoudle,
        UnknownSetting,
    }

    public static void sendCommandError(errorReason reason, String correctPart, String errorPart) {
        sendCommandError(getReason(reason), correctPart, errorPart);
    }

    public static void sendCommandError(errorReason reason, String command, String[] args, int errorIndex) {
        StringBuilder correctPartBuilder = new StringBuilder(command);
        StringBuilder errorPartBuilder = new StringBuilder();

        if (errorIndex >= 0 && errorIndex < args.length) {
            for (int i = 0; i < errorIndex; i++) {
                correctPartBuilder.append(" ").append(args[i]);
            }
            for (int i = errorIndex; i < args.length; i++) {
                if (errorPartBuilder.length() > 0) errorPartBuilder.append(" ");
                errorPartBuilder.append(args[i]);
            }
        } else {
            for (String arg : args) {
                correctPartBuilder.append(" ").append(arg);
            }
        }

        String correctPart = correctPartBuilder.toString();
        String errorPart = errorPartBuilder.toString();

        sendCommandError(getReason(reason), correctPart, errorPart);
    }


    public static void sendCommandError(String reason, String correctPart, String errorPart) {
        ChatUtil.sendMessage("&c" + reason);

        if (!errorPart.isEmpty() && !correctPart.isEmpty()) {
            correctPart += " ";
        }

        if (correctPart.length() > 10) {
            correctPart = "..." + correctPart.substring(correctPart.length() - 10);
        }

        ChatUtil.sendMessage("&7" + correctPart + "&c&n" + errorPart + "&c&o<--[HERE]");
    }

    public static String getReason(errorReason reason) {
        switch (reason) {
            case ExpectedFloat: return "Expected float";
            case ExpectedInteger: return "Expected integer";
            case InvalidBoolean: return "Invalid boolean: expected 'true' or 'false'";
            case IncorrectArgument: return "Incorrect argument for command";
            case UnknownOrIncompleteCommand: return "Unknown or incomplete command. See below for error";
            case UnknownBlock: return "Unknown block type";
            case UnknownItem: return "Unknown item";
            case UnknownMoudle: return "Unknown Moudle";
            case UnknownSetting: return "Unknown Setting";
            default: return "Unknown error";
        }
    }
}
