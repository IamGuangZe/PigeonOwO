package owo.pigeon.utils;

import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.features.modules.hypixel.Skyblock.Fishing.AutoFish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static owo.pigeon.features.modules.ModuleManager.modules;

public class ModuleUtil {

    /**
     * <h5>filter 过滤</h5>
     * @param clazz lambda表达式(匿名函数) it表示参数名称 匹配与clazz相同的类
     * <p>collect() 收集为List格式</p>
     * <p>获取第一个元素(下标0)</p>
     */
    public static Module getModuleByClass(Class<? extends Module> clazz) {
        return modules.stream() //切换至 Steam 流处理
                .filter(it -> it.getClass() == clazz)
                .map(clazz::cast)
                .collect(Collectors.toList())
                .get(0);
    }
    public static Module getModule(Class<? extends Module> clazz) {
        for (Module m : modules) {
            if (m.getClass() == clazz) {
                return m;
            }
        }
        throw new RuntimeException();
    }
    public static Module getModule(String module) {
        for (Module m : modules) {
            if (m.name.equalsIgnoreCase(module)) {
                return m;
            }
        }
        throw new RuntimeException();
    }

    public static boolean isEnable(String module) {
        for (Module m : modules) {
            if (m.name.equalsIgnoreCase(module)) {
                return m.isEnable();
            }
        }
        return false;
    }
    public static boolean isEnable(Class<? extends Module> clazz) {
        for (Module m : modules) {
            if (m.getClass() == clazz) {
                return m.isEnable();
            }
        }
        return false;
    }

    public static boolean isModuleExist(String module) {
        for (Module m : modules) {
            if (m.name.equalsIgnoreCase(module)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isModuleExist(Class<? extends Module> clazz) {
        for (Module m : modules) {
            if (m.getClass() == clazz) {
                return true;
            }
        }
        return false;
    }

    public static String getregisteredname(String module) {
        for (Module m : modules) {
            if (m.name.equalsIgnoreCase(module)) {
                return m.name;
            }
        }
        return null;
    }
    
    public static void moduleEnable(String module) {
        for (Module m : modules) {
            if (m.name.equalsIgnoreCase(module)) {
                m.enable();
            }
        }
    }

    public static void moduleDisable(String module) {
        for (Module m : modules) {
            if (m.name.equalsIgnoreCase(module)) {
                m.disable();
            }
        }
    }
    public static void moduleDisable(Class<? extends Module> clazz) {
        for (Module m : modules) {
            if (m.getClass() == clazz) {
                m.disable();
            }
        }
    }
    
    public static void moduleToggle(String module) {
        for (Module m : modules) {
            if (m.name.equalsIgnoreCase(module)) {
                m.toggle();
            }
        }
    }
    public static void moduleToggle(Class<? extends Module> clazz) {
        for (Module m : modules) {
            if (m.getClass() == clazz) {
                m.toggle();
            }
        }
    }

    public static void moduleSetKey(String module, int key) {
        for (Module m : modules) {
            if (m.name.equalsIgnoreCase(module)) {
                m.setKey(key);
            }
        }
    }

    public static List<Module> getAllModules() {
        return modules;
    }

    public static List<Module> getAllModulesFromCategory(Category category) {
        ArrayList<Module> modulesC = new ArrayList<>();
        for (Module m : modules) {
            if (m.category == category) {
                modulesC.add(m);
            }
        }
        return modulesC;
    }

    public static void macroDisable() {
        List<Class<? extends Module>> macros = Arrays.asList(
                AutoFish.class
        );

        for (Class<? extends Module> clazz : macros) {
            if (isEnable(clazz)) {
                moduleDisable(clazz);
            }
        }
    }
}
