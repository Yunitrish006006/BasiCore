package com.mc.basicore.systems.world_index;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.itemGroups;
import com.mc.basicore.systems.teleport_system.SpaceUnit;
import com.mc.basicore.systems.world_index.GUI.*;
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
    public static ItemStack playerPageButton(String language) {
        ItemStack item = new ItemStack(PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.playerPage");
        meta.setDisplayName(translate(language,"GUI.player","GUI.list"));
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack publicPageButton(String language) {
        ItemStack item = new ItemStack(WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.publicPage");
        meta.setDisplayName(translate(language,"GUI.public","GUI.unit","GUI.list"));
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
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!event.hasItem()) return;
        if (!Basics.getID(event.getItem()).equals("BasiCore.WorldIndex")) return;
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            player.openInventory(new UnitsPage(player).getInventory());
        } else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) && itemGroups.lockable().contains(event.getClickedBlock().getType())) {
            Bukkit.broadcastMessage("u locked a block!");
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
        InventoryHolder holder = event.getInventory().getHolder();
        ((Player)event.getWhoClicked()).playSound(event.getWhoClicked(), "basicore.button",0.4f,1.3f);
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
            else if (holder instanceof requestPage){
                ((requestPage) holder).trigger(event,ID[2],event.getClick(), (Player) event.getWhoClicked());
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
                old = unitSetPage.unit.displayName;
                unitSetPage.unit.displayName = input;
                unitSetPage.unit.addUnit();
                output = old+translate(player,"quotes.turn_into")+unitSetPage.unit.displayName;
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
