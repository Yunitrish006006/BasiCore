package com.mc.basicore.world_index;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.teleport_system.SpaceUnit;
import com.mc.basicore.world_index.GUI.*;
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

import static org.bukkit.Material.*;
import static org.bukkit.Material.WHITE_BANNER;

@SuppressWarnings("ConstantConditions")
public class WorldIndex extends ItemStack implements Listener {
    public WorldIndex() {
        super(Material.PAPER);
        ItemMeta meta = getItemMeta();
        meta.setLocalizedName("BasiCore.WorldIndex");
        meta.setCustomModelData(1);
        Objects.requireNonNull(meta).setDisplayName(ChatColor.RESET+String.valueOf(ChatColor.GOLD)+ChatColor.BOLD+"世界索引");
        meta.setLore(Arrays.asList("【右鍵】開啟索引","探索世界的方便工具~"));
        this.setItemMeta(meta);
    }
    public static ItemStack returnButton() {
        ItemStack item = new ItemStack(ARROW);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.return");
        meta.setDisplayName(ChatColor.RESET+"返回");
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack playerPageButton() {
        ItemStack item = new ItemStack(PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.playerPage");
        meta.setDisplayName(ChatColor.RESET+"玩家列表");
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack publicPageButton() {
        ItemStack item = new ItemStack(WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.publicPage");
        meta.setDisplayName(ChatColor.RESET+"公用傳送點列表");
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack playerDataButton() {
        ItemStack item = new ItemStack(WHITE_BANNER);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.playerData");
        meta.setDisplayName(ChatColor.RESET+"玩家資料");
        item.setItemMeta(meta);
        return item;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        if (!event.hasItem()) return;
        if (!Basics.getID(event.getItem()).equals("BasiCore.WorldIndex")) return;
        player.openInventory(new UnitsPage(player).getInventory());
    }
    @EventHandler
    public void GUIEvents(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        if (!event.getCurrentItem().getItemMeta().hasLocalizedName()) return;
        String[] ID = event.getCurrentItem().getItemMeta().getLocalizedName().split("\\.");
        if (ID.length < 3) return;
        if (!ID[1].equals("GUI")) return;
        InventoryHolder holder = event.getInventory().getHolder();

        if (Arrays.asList("playerPage","publicPage","playerData","return").contains(ID[2])) {
            ClickType press = event.getClick();
            Player player = (Player) event.getWhoClicked();
            switch (ID[2]) {
                case "playerPage":
                    if (press.isLeftClick()) {
                        player.openInventory(new PlayersPage(player).getInventory());
                    }
                    break;
                case "publicPage":
                    if (press.isLeftClick()) {
                        player.openInventory(new PublicPage(player).getInventory());
                    }
                    break;
                case "playerData":
                    if (press.isLeftClick()) {
                        player.openInventory(new PlayerDataPage(player).getInventory());
                    }
                    break;
                case "return":
                    if (press.isLeftClick()) {
                        player.openInventory(new UnitsPage(player).getInventory());
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
            else if (holder instanceof PublicPage) {
                ((PublicPage)holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof UnitSetPage) {
                ((UnitSetPage)holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof IconsPage) {
                ((IconsPage)holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof PlayersPage) {
                ((PlayersPage)holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
            }
            else if (holder instanceof PlayerDataPage){
                ((PlayerDataPage) holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
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
                output = old+ChatColor.RESET+" to "+playerDataPage.chatSet.getName();
                player.removeMetadata("inputText", BasiCore.getPlugin());
                playerDataPage.chatSet.update();
                break;
            case "playerColor":
                playerDataPage = new PlayerDataPage(player);
                old = playerDataPage.chatSet.getName();
                playerDataPage.chatSet.NameColor = ChatColor.valueOf(input.toUpperCase());
                playerDataPage.chatSet.saveChatSet();
                output = old+ChatColor.RESET+" to "+playerDataPage.chatSet.getName();
                player.removeMetadata("inputText", BasiCore.getPlugin());
                playerDataPage.chatSet.update();
                break;
            case "contentColor":
                playerDataPage = new PlayerDataPage(player);
                old = playerDataPage.chatSet.ContentColor.name();
                playerDataPage.chatSet.ContentColor = ChatColor.valueOf(input.toUpperCase());
                playerDataPage.chatSet.saveChatSet();
                output = old+ChatColor.RESET+" to "+playerDataPage.chatSet.ContentColor.name();
                player.removeMetadata("inputText", BasiCore.getPlugin());
                playerDataPage.chatSet.update();
                break;
            case "unitName":
                unitSetPage = new UnitSetPage((SpaceUnit) player.getMetadata("data").get(0).value());
                unitSetPage.unit.deleteUnit();
                old = unitSetPage.unit.displayName;
                unitSetPage.unit.displayName = input;
                unitSetPage.unit.addUnit();
                output = old+" to "+unitSetPage.unit.displayName;
                player.removeMetadata("data",BasiCore.getPlugin());
                player.removeMetadata("inputText", BasiCore.getPlugin());
                break;
            default:
                output = "error";
                player.sendMessage("wrong in GUI!!");
                break;
        }
        event.setCancelled(true);
        player.sendMessage(output);
    }
}
