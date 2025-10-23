package owo.pigeon.features.modules;

import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import owo.pigeon.events.clickevent.LeftClickEvent;
import owo.pigeon.events.clickevent.RightClickEndEvent;
import owo.pigeon.events.clickevent.RightClickEvent;
import owo.pigeon.events.renderevent.Render2DEvent;
import owo.pigeon.events.renderevent.Render3DEvent;
import owo.pigeon.features.modules.client.ClickGui;
import owo.pigeon.features.modules.client.ModifyChat;
import owo.pigeon.features.modules.client.test.*;
import owo.pigeon.features.modules.combat.AutoClicker;
import owo.pigeon.features.modules.hypixel.*;
import owo.pigeon.features.modules.hypixel.Skyblock.Dungeon.DungeonBrush;
import owo.pigeon.features.modules.hypixel.Skyblock.Dungeon.F7.PillarsHelper;
import owo.pigeon.features.modules.hypixel.Skyblock.Dungeon.F7.SetClipBlock;
import owo.pigeon.features.modules.hypixel.Skyblock.Dungeon.LividSolver;
import owo.pigeon.features.modules.hypixel.Skyblock.Dungeon.StarMobEsp;
import owo.pigeon.features.modules.hypixel.Skyblock.Fishing.AutoFish;
import owo.pigeon.features.modules.hypixel.Skyblock.Nether.DojoSolver;
import owo.pigeon.features.modules.hypixel.Skyblock.RotateNotice;
import owo.pigeon.features.modules.movement.LegitSpeed;
import owo.pigeon.features.modules.movement.Sprint;
import owo.pigeon.features.modules.player.*;
import owo.pigeon.features.modules.render.*;
import owo.pigeon.features.modules.world.GhostBlock;
import owo.pigeon.features.modules.world.LightningTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
    public static final ArrayList<Module> modules = new ArrayList<>();
    public static final Map<Integer, Boolean> keyStates = new HashMap<>();

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

    public List<Module> getAllModules() {
        return modules;
    }

    public void addClientModules() {
        // addTestModules();

        modules.add(new ModifyChat());
        modules.add(new ClickGui());
    }

    public void addCombatModules() {
        modules.add(new AutoClicker());
    }

    public void addHypixelModules() {
        // addSkyblockModules();

        modules.add(new FarmhuntHelper());
        modules.add(new GTBHelper());
        modules.add(new MurderHelper());
        modules.add(new PartyNotify());
        modules.add(new PixelHelper());
        modules.add(new ProphuntHelper());
        modules.add(new ZombieHelper());
    }

    public void addMovementModules() {
        modules.add(new LegitSpeed());
        // modules.add(new Parkour());
        // modules.add(new SafeWalk());
        modules.add(new Sprint());
    }

    public void addPlayerModules() {
        modules.add(new AutoTool());
        modules.add(new ChestStealer());
        modules.add(new DelayRemover());
        modules.add(new Eagle());
        modules.add(new NoRotateSet());
    }

    public void addRenderModules() {
        modules.add(new BedESP());
        modules.add(new BlockOverlay());
        modules.add(new Camera());
        modules.add(new ESP());
        modules.add(new FullBright());
        modules.add(new OreESP());
        modules.add(new PlayerESP());
        modules.add(new SkullESP());
    }

    public void addworldModules() {
        modules.add(new GhostBlock());
        modules.add(new LightningTracker());
    }

    public void addTestModules() {
        modules.add(new BlockBreaking());
        modules.add(new ContainerClick());
        modules.add(new EntityInformation());
        modules.add(new InstantUseItem());
        modules.add(new MapReceive());
        modules.add(new MessageRetrieval());
        modules.add(new PacketReceive());
        modules.add(new SkullInformation());
        modules.add(new TimeDisplay());
    }

    public void addSkyblockModules() {
        modules.add(new GhostBlock());
        modules.add(new RotateNotice());
        //Dungeon
        modules.add(new DungeonBrush());
        modules.add(new LividSolver());
        modules.add(new StarMobEsp());
        //F7
        modules.add(new PillarsHelper());
        modules.add(new SetClipBlock());
        //Fishing
        modules.add(new AutoFish());
        //Nether
        modules.add(new DojoSolver());
    }

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

    @SubscribeEvent
    public void onOnenGui(GuiOpenEvent event) {
        keyStates.clear();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            modules.stream().filter(Module::isEnable).forEach(Module::onTick);
        }
    }

    @SubscribeEvent
    public void onRender2D(Render2DEvent event) {
        modules.stream().filter(Module::isEnable).forEach(Module::onRender2D);
    }

    @SubscribeEvent
    public void onRender3D(Render3DEvent event) {
        modules.stream().filter(Module::isEnable).forEach(Module::onRender3D);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        modules.stream().filter(Module::isEnable).forEach(Module::onWorldLoad);
    }

    @SubscribeEvent
    public void onLeftClick(LeftClickEvent event) {
        modules.stream().filter(Module::isEnable).forEach(Module::onLeftClick);
    }

    @SubscribeEvent
    public void onRightClick(RightClickEvent event) {
        modules.stream().filter(Module::isEnable).forEach(Module::onRightClick);
    }

    @SubscribeEvent
    public void onRightClickEnd(RightClickEndEvent event) {
        modules.stream().filter(Module::isEnable).forEach(Module::onRightClickEnd);
    }
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