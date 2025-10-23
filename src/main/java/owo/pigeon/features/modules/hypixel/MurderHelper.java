package owo.pigeon.features.modules.hypixel;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.*;
import owo.pigeon.utils.hypixel.HypixelGames;
import owo.pigeon.utils.hypixel.HypixelUtil;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MurderHelper extends Module {
    public MurderHelper() {
        super("MurderHelper", Category.HYPIXEL, -1); // 模块名称和键位绑定
    }

    public EnableSetting hud = setting("hud", true, v -> true);
    public EnableSetting playerEsp = setting("player-esp", true, v -> true);
    public EnableSetting itemEsp = setting("item-esp", true, v -> true);
    public EnableSetting hideSpamCurse = setting("hide-spamcurse", true, v -> true);

    private static List<String> AllPlayer = new ArrayList<>();
    private static List<String> Aliveplayer = new ArrayList<>();

    private static List<String> MurderersName = new ArrayList<>();
    private static List<String> Playerwithbow = new ArrayList<>();
    private static final Set<Integer> Weapons = new HashSet<>(Arrays.asList(
            267, 130, 272, 256, 280, 271, 268,
            32, 338, 273, 369, 277, 406, 400,
            285, 334, 421, 263, 318, 352, 391,
            396, 357, 279, 175, 409, 364, 405,
            366,/*disc,*/294, 351, 283, 276, 293,
            359, 349, 351, 297, 333, 382, 340,
            6, 286, 278, 284
            //Update at 11/30/24
    ));

    static {
        Integer[] musicdisc = {2256, 2257, 2258, 2259, 2260, 2261,
                2262, 2263, 2264, 2265, 2266, 2267};
        Weapons.addAll(Arrays.asList(musicdisc));
    }

    private static final String[] TEAMMATE_MUR = {
            "Your fellow Murderer is: (.*)"
    };
    private static final String[] TEAMMATE_DET = {
            "Your fellow Detective is: (.*)"
    };
    private static final String[] MurdererDideMessage = {
            "One of the Murderers, (.*), was killed.",
            "一名杀手，(.*)，被杀害了。"
    };
    private static final String[] SpamCurse = {
            "YOU HAVE BEEN STRUCK WITH THE CURSE OF SPAM",
            "你在诅咒下被刷屏了"
    };

    @Override
    public void onTick() {
        if (WorldUtil.isNotNull()) {
            if (HypixelUtil.isInGame(HypixelGames.MURDER)) {
                Aliveplayer.clear();
                for (EntityPlayer player : mc.theWorld.playerEntities) {
                    if (!HypixelUtil.isNPC(player)) {
                        String playerName = player.getName();
                        String diedName = "&m" + playerName + "&r";

                        Aliveplayer.add(playerName);
                        AllPlayer.remove(playerName);

                        if (MurderersName.contains(diedName)) {
                            int index = MurderersName.indexOf(diedName);
                            MurderersName.set(index, playerName);
                        }
                        if (Playerwithbow.contains(diedName)) {
                            int index = Playerwithbow.indexOf(diedName);
                            Playerwithbow.set(index, playerName);
                        }

                        ItemStack helditem = player.getHeldItem();
                        if (helditem != null) {
                            int itemId = Item.getIdFromItem(helditem.getItem());

                            if (Weapons.contains(itemId) && helditem.getDisplayName().contains("§a") && !MurderersName.contains(playerName)) {
                                ChatUtil.sendCustomPrefixMessage(this.name, "&c" + playerName + " &ris Murderer!");
                                ChatUtil.sendCustomPrefixMessage(this.name, helditem.getDisplayName());
                                MurderersName.add(playerName);
                                Playerwithbow.remove(playerName);
                            }
                            if (helditem.getItem() == Items.bow && !Playerwithbow.contains(playerName) && !MurderersName.contains(playerName)) {
                                Playerwithbow.add(playerName);
                            }
                        }
                    }
                }


                /*if (!AllPlayer.isEmpty()) {
                    for (String playername : AllPlayer) {
                        if (!Aliveplayer.contains(playername)) {
                            ChatUtil.sendCustomPrefixMessage(this.name,"&7" + playername + " &rhas died.");
                            onPlayerDied(playername);
                        }
                    }
                }*/

                while (!AllPlayer.isEmpty()) {
                    String playername = AllPlayer.get(0);
                    String color;
                    if (MurderersName.contains(playername)) {
                        color = "&c";
                    } else if (Playerwithbow.contains(playername)) {
                        color = "&a";
                    } else {
                        color = "&7";
                    }
                    ChatUtil.sendCustomPrefixMessage(this.name, color + playername + " &rhas died.");
                    playerDied(playername);
                    AllPlayer.remove(playername);
                }

                AllPlayer = new ArrayList<>(Aliveplayer);
                Aliveplayer.clear();

            } /*else {
                ChatUtil.sendCustomPrefixMessage(this.name,"&cYou are Not in Murder Mystery!");
                disable();
            }*/
        }
    }

    @Override
    public void onRender2D() {
        if (WorldUtil.isNotNull()) {
            if (hud.getValue() && HypixelUtil.isInGame(HypixelGames.MURDER)) {
                FontUtil.drawStringWithShadow("Murder Mystery", 5, 5);
                FontUtil.drawStringWithShadow("Murders : &c" + String.join("&r, &c", MurderersName), 5, 5 + h);
                FontUtil.drawStringWithShadow("Who has bow : " + String.join(", ", Playerwithbow), 5, 5 + h * 2);
            }
        }
    }

    @Override
    public void onRender3D() {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (playerEsp.getValue() && HypixelUtil.isInGame(HypixelGames.MURDER)) {
                if (entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP)) {
                    if (!HypixelUtil.isNPC(entity)) {
                        String playername = entity.getName();
                        if (MurderersName.contains(playername)) {
                            RenderUtil.drawSmoothOutlinedBoxEsp(entity, new Color(255, 0, 0));
                        } else if (Playerwithbow.contains(playername)) {
                            RenderUtil.drawSmoothOutlinedBoxEsp(entity, new Color(0, 255, 0));
                        } else {
                            RenderUtil.drawSmoothOutlinedBoxEsp(entity, new Color(255, 255, 255));
                        }
                    }
                }
            }

            if (itemEsp.getValue()) {
                if (entity instanceof EntityItem) {
                    if (entity.getName().equals("item.item.ingotGold")) {
                        RenderUtil.drawSmoothOutlinedBoxEsp(entity, new Color(255, 255, 0));
                    }
                }
            }
        }
    }

    @Override
    public void onWorldLoad() {
        reload();
    }

    @Override
    public void onEnable() {
        reload();
    }

    @SubscribeEvent
    public void onChatReceive(ClientChatReceivedEvent event) {
        String message = OtherUtil.removeColor(event.message.getFormattedText());

        if (OtherUtil.regexGetPart(MurdererDideMessage, message, 1) != null) {
            String playername = OtherUtil.regexGetPart(MurdererDideMessage, message, 1);
            if (!MurderersName.contains(playername) && !MurderersName.contains("&m" + playername + "&r")) {
                MurderersName.add("&m" + playername + "&r");
            }
        }
        if (OtherUtil.regexGetPart(TEAMMATE_MUR, message, 1) != null) {
            MurderersName.add(OtherUtil.regexGetPart(TEAMMATE_MUR, message, 1));
        }
        if (OtherUtil.regexGetPart(TEAMMATE_DET, message, 1) != null) {
            Playerwithbow.add(OtherUtil.regexGetPart(TEAMMATE_DET, message, 1));
        }
        if (hideSpamCurse.getValue() && OtherUtil.isContainsRegex(SpamCurse, message)) {
            event.setCanceled(true);
        }
    }

    private void reload() {
        if (WorldUtil.isNotNull()) {
            MurderersName.clear();
            Playerwithbow.clear();
            getAllPlayer();
        }
    }

    private void getAllPlayer() {
        AllPlayer.clear();
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            String playername = player.getName();
            AllPlayer.add(playername);
        }
    }

    private void playerDied(String playername) {
        if (Playerwithbow.contains(playername)) {
            int index = Playerwithbow.indexOf(playername);
            Playerwithbow.set(index, "&m" + playername + "&r");
        }

        if (MurderersName.contains(playername)) {
            int index = MurderersName.indexOf(playername);
            MurderersName.set(index, "&m" + playername + "&r");
        }
    }
}
