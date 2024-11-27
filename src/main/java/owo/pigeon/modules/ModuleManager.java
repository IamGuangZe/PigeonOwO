package owo.pigeon.modules;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import owo.pigeon.events.RightClickEvent;
import owo.pigeon.features.client.ModifyChat;
import owo.pigeon.features.combat.AutoClicker;
import owo.pigeon.features.hypixel.ZombieHelper;
import owo.pigeon.features.movement.Eagle;
import owo.pigeon.features.movement.LegitSpeed;
import owo.pigeon.features.movement.Sprint;
import owo.pigeon.features.player.AutoTool;
import owo.pigeon.features.render.FullBright;
import owo.pigeon.features.hypixel.GhostBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList<>();
    private static final Map<Integer, Boolean> keyStates = new HashMap<>();

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);

        addClientModules();
        addCombatModules();
        addHypixelModules();
        addMovementModules();
        addPlayerModules();
        addRenderModules();
        addworldModules();
    }

    public void addClientModules() {
        modules.add(new ModifyChat());
    }
    public void addCombatModules() {
        modules.add(new AutoClicker());
    }
    public void addHypixelModules() {
        modules.add(new GhostBlock());
        modules.add(new ZombieHelper());
    }
    public void addMovementModules() {
        modules.add(new Eagle());
        modules.add(new LegitSpeed());
        // modules.add(new Parkour());
        modules.add(new Sprint());
    }
    public void addPlayerModules() {
        // modules.add(new AutoRespawn());
        modules.add(new AutoTool());
    }
    public void addRenderModules() {
        modules.add(new FullBright());
    }
    public void addworldModules() {

    }

    //自己到处抄的抄在一块 依托 长按会重复执行
    /*    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        for (Module module : modules) {
            if (module.getKey() == Keyboard.getEventKey() && Keyboard.getEventKeyState()) {
                module.toggle();
            }
        }
    }*/

    //Created by 78yun - 指定键按下不松按下任意键仍会执行
    /*    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()){
            modules.stream().filter(it -> Keyboard.isKeyDown(it.Key)).forEach(Module :: toggle);
        }
    }*/

    //created by ChatGPT - 目前没遇到问题
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        int keyCode = Keyboard.getEventKey();  // 获取按下的按键代码
        // 判断按键是否按下，并且之前没有被按下过
        if (Keyboard.getEventKeyState() && (!keyStates.containsKey(keyCode) || !keyStates.get(keyCode))) {
            // 按键从未按下变为按下时，执行相关操作
            modules.stream().filter(it -> it.Key == keyCode)  // 检查模块绑定的按键
                    .forEach(Module::toggle);  // 切换模块的状态
            // 记录按键为已按下状态
            keyStates.put(keyCode, true);
        }
        // 判断按键是否松开
        else if (!Keyboard.getEventKeyState() && keyStates.getOrDefault(keyCode, false)) {
            // 按键从按下变为松开时，更新按键状态
            keyStates.put(keyCode, false);
        }
    }

    //Created by ChatGPT - 不能一个按键开启多个功能
    /*    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        // 遍历模块列表，检查每个模块绑定的按键
        modules.stream().forEach(module -> {
            int key = module.Key;  // 获取模块绑定的按键

            // 检查按键是否被按下，且按键状态发生了变化（从未按下变为按下）
            if (Keyboard.isKeyDown(key) && (!keyStates.containsKey(key) || !keyStates.get(key))) {
                // 切换模块状态
                module.toggle();
                // 更新按键的状态为按下
                keyStates.put(key, true);
            }
            // 如果按键松开，更新状态
            else if (!Keyboard.isKeyDown(key) && keyStates.getOrDefault(key, false)) {
                keyStates.put(key, false);
            }
        });
    }*/

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        modules.stream().filter(Module::isEnable).forEach(Module::onUpdate);
    }

    @SubscribeEvent
    public void onRightClick(RightClickEvent event) {
        modules.stream().filter(Module::isEnable).forEach(Module::onRightClick);
    }

    public List<Module> getAllModules() {
        return modules;
    }
}
