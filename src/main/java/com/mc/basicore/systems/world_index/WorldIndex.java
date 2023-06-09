package com.mc.basicore.systems.world_index;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.EconomySystem.AwardPage;
import com.mc.basicore.systems.ChatSystem.Pages.PlayerDataPage;
import com.mc.basicore.systems.EconomySystem.OnlineStorePage;
import com.mc.basicore.systems.TribeSystem.Pages.TribesListPage;
import com.mc.basicore.systems.ResourceSystem.CollectorSetPage;
import com.mc.basicore.systems.teleport_system.Pages.*;
import com.mc.basicore.systems.teleport_system.SpaceUnit;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static com.mc.basicore.systems.translate.Translator.*;
import static org.bukkit.Material.*;
import static org.bukkit.Material.WHITE_BANNER;

@SuppressWarnings("ConstantConditions")
public class WorldIndex implements Listener {
    public Player player;
    public static ItemStack worldIndex(String language) {
        ItemStack itemStack = new ItemStack(PAPER);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLocalizedName("BasiCore.WorldIndex");
        meta.setCustomModelData(1);
        Objects.requireNonNull(meta).setDisplayName(translate(language,"GUI.world_index"));
        meta.setLore(Arrays.asList(
                translate(language, "GUI.right_click", "quotes.open_index"),
                translate(language, "quotes.best_tool_to_explore")
        ));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public static ItemStack returnButton(String language) {
        ItemStack item = new ItemStack(ARROW);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.return");
        meta.setDisplayName(translate(language,"GUI.return"));
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack playerDataButton(String language) {
        ItemStack item = new ItemStack(WHITE_BANNER);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.playerData");
        meta.setDisplayName(translate(language,"GUI.player","GUI.data"));
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack tribeListButton(String language) {
        ItemStack item = new ItemStack(BLACK_BANNER);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.tribeList");
        meta.setDisplayName(translate(language,"GUI.tribe","GUI.list"));
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack collectorSetButton(String language) {
        ItemStack item = new ItemStack(IRON_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.collectorSet");
        meta.setDisplayName(translate(language,"GUI.collectorSystem"));
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack AwardPageButton(String language) {
        ItemStack item = new ItemStack(BELL);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.AwardPage");
        meta.setDisplayName(translate(language,"GUI.AwardPage"));
        item.setItemMeta(meta);
        return item;
    }
    public static int getPlace(int row, int col) {
        return 9*(row-1)+(col-1);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        player = event.getPlayer();
        if (!event.hasItem()) return;
        if (!Basics.getID(event.getItem()).equals("BasiCore.WorldIndex")) return;
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            player.openInventory(new UnitsPage(player,"private").getInventory());
        }
    }
    @EventHandler
    public void GUIEvents(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (!event.getCurrentItem().getItemMeta().hasLocalizedName()) return;
        String[] ID = event.getCurrentItem().getItemMeta().getLocalizedName().split("\\.");
        if (ID.length < 3) return;
        if (!ID[1].equals("GUI")) return;
        event.setCancelled(true);
        InventoryHolder holder = event.getInventory().getHolder();
        ((Player)event.getWhoClicked()).playSound(event.getWhoClicked(), "basicore.button",0.4f,1.3f);
        if (Arrays.asList("publicPage","playerData","tribeList","collectorSet","AwardPage","return").contains(ID[2])) {
            ClickType press = event.getClick();
            Player player = (Player) event.getWhoClicked();
            switch (ID[2]) {
                case "return":
                    if (press.isLeftClick()) {
                        player.openInventory(new UnitsPage(player,"private").getInventory());
                    }
                    break;
                case "playerData":
                    if (press.isLeftClick()) {
                        player.openInventory(new PlayerDataPage(player).getInventory());
                    }
                    break;
                case "tribeList":
                    if (press.isLeftClick()) {
                        player.openInventory(new TribesListPage(player).getInventory());
                    }
                    break;
                case "collectorSet":
                    if (press.isLeftClick()) {
                        player.openInventory(new CollectorSetPage(player).getInventory());
                    }
                    break;
                case "AwardPage":
                    if (press.isLeftClick()) {
                        player.openInventory(new AwardPage(player).getInventory());
                    }
                    break;
                default:
                    break;
            }
        }
        else {
            if (holder instanceof UnitsPage) {
                ((UnitsPage)holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof UnitSetPage) {
                ((UnitSetPage)holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof IconsPage) {
                ((IconsPage)holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof PlayerDataPage){
                ((PlayerDataPage) holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof requestPage){
                ((requestPage) holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof TribesListPage){
                ((TribesListPage) holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof CollectorSetPage){
                ((CollectorSetPage) holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof AwardPage) {
                ((AwardPage) holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof OnlineStorePage) {
                ((OnlineStorePage) holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
        }
    }
    @EventHandler
    public void textInput(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!player.hasMetadata("inputText")) return;
        if (player.getMetadata("inputText").equals(null)) return;
        String input = event.getMessage();
        String output;
        String old;
        PlayerDataPage playerDataPage;
        UnitSetPage unitSetPage;
        switch (player.getMetadata("inputText").get(0).value().toString()) {
            case "playerName":
                playerDataPage = new PlayerDataPage(player);
                old = playerDataPage.chatSet.getName();
                playerDataPage.chatSet.CustomName = input;
                playerDataPage.chatSet.saveChatSet();
                output = old+ChatColor.RESET+translate(player,"quotes.turn_into")+playerDataPage.chatSet.getName();
                player.removeMetadata("inputText", BasiCore.getPlugin());
                playerDataPage.chatSet.update();
                break;
            case "discordID":
                playerDataPage = new PlayerDataPage(player);
                old = playerDataPage.chatSet.discordName;
                playerDataPage.chatSet.discordName = input;
                playerDataPage.chatSet.saveChatSet();
                output = old+ChatColor.RESET+translate(player,"quotes.turn_into")+playerDataPage.chatSet.discordName;
                player.removeMetadata("inputText", BasiCore.getPlugin());
                playerDataPage.chatSet.update();
                break;
            case "playerColor":
                playerDataPage = new PlayerDataPage(player);
                old = playerDataPage.chatSet.getName();
                playerDataPage.chatSet.NameColor = ChatColor.valueOf(input.toUpperCase());
                playerDataPage.chatSet.saveChatSet();
                output = old+ChatColor.RESET+ translate(player,"quotes.turn_into") +playerDataPage.chatSet.getName();
                player.removeMetadata("inputText", BasiCore.getPlugin());
                playerDataPage.chatSet.update();
                break;
            case "contentColor":
                playerDataPage = new PlayerDataPage(player);
                old = playerDataPage.chatSet.ContentColor.name();
                playerDataPage.chatSet.ContentColor = ChatColor.valueOf(input.toUpperCase());
                playerDataPage.chatSet.saveChatSet();
                output = old+ChatColor.RESET+translate(player,"quotes.turn_into")+playerDataPage.chatSet.ContentColor.name();
                player.removeMetadata("inputText", BasiCore.getPlugin());
                playerDataPage.chatSet.update();
                break;
            case "unitName":
                unitSetPage = new UnitSetPage((SpaceUnit) player.getMetadata("data").get(0).value());
                unitSetPage.unit.deleteUnit();
                old = unitSetPage.unit.unitName;
                unitSetPage.unit.unitName = input;
                unitSetPage.unit.saveUnit();
                output = old+translate(player,"quotes.turn_into")+unitSetPage.unit.unitName;
                player.removeMetadata("data",BasiCore.getPlugin());
                player.removeMetadata("inputText", BasiCore.getPlugin());
                break;
            default:
                output = translate(player,"quotes.turn_into");
                player.sendMessage("wrong in GUI!!");
                break;
        }
        event.setCancelled(true);
        player.sendMessage(output);
    }
}
