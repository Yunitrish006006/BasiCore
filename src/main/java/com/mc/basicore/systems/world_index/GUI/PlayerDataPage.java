package com.mc.basicore.systems.world_index.GUI;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.chat_system.ChatSet;
import com.mc.basicore.itemGroups;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import javax.annotation.Nonnull;

import java.util.Arrays;

import static com.mc.basicore.systems.world_index.WorldIndex.returnButton;
import static org.bukkit.Material.*;

public class PlayerDataPage implements InventoryHolder {
    public Inventory inventory;
    
    public ChatSet chatSet;
    
    public PlayerDataPage(Player player) {
        chatSet = new ChatSet(player);
        this.inventory = Bukkit.createInventory(this,9*4, ChatColor.GOLD + "玩家個人資料");
        this.inventory.setItem(10, customNameSetButton());
        this.inventory.setItem(12, nameColorSetButton());
        this.inventory.setItem(14, contentColorSetButton());
        this.inventory.setItem(27, returnButton());
    }

    @Override
    @Nonnull
    public Inventory getInventory() {
        return inventory;
    }


    public ItemStack customNameSetButton() {
        ItemStack item = new ItemStack(NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.playerName");
        meta.setDisplayName(ChatColor.RESET+"設定玩家名稱");
        meta.setLore(Arrays.asList(
                ChatColor.RESET+"玩家名稱: " +chatSet.getName(),
                ChatColor.RESET+"【左鍵】設定",
                ChatColor.RESET+"【右鍵】重設"
        ));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack nameColorSetButton() {
        ItemStack item = new ItemStack(BLUE_DYE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.playerColor");
        meta.setDisplayName(ChatColor.RESET+"設定玩家顏色");
        meta.setLore(Arrays.asList(
                ChatColor.RESET+"玩家名稱: " +chatSet.getName(),
                ChatColor.RESET+"【左鍵】輪轉設定",
                ChatColor.RESET+"【右鍵】打字選定"
        ));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack contentColorSetButton() {
        ItemStack item = new ItemStack(RED_DYE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.contentColor");
        meta.setDisplayName(ChatColor.RESET+"設定內容顏色");
        meta.setLore(Arrays.asList(
                chatSet.ContentColor+"內容顏色預覽",
                ChatColor.RESET+"【左鍵】輪轉設定",
                ChatColor.RESET+"【右鍵】打字選定"
        ));
        item.setItemMeta(meta);
        return item;
    }
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        switch (ID) {
            case "playerName":
                switch (press) {
                    case LEFT:
                        event.setCancelled(true);
                        player.closeInventory();
                        player.sendMessage(ChatColor.YELLOW + "輸入玩家名稱:");
                        chatSet.CustomName = "";
                        player.setMetadata("inputText", new FixedMetadataValue(BasiCore.getPlugin(), "playerName"));
                        break;
                    case RIGHT:
                        chatSet.resetChat();
                        chatSet.saveChatSet();
                        inventory.setItem(10, customNameSetButton());
                        break;
                    default:
                        break;
                }
                break;
            case "playerColor":
                switch (press) {
                    case LEFT:
                        event.setCancelled(true);
                        int index = (itemGroups.colors().indexOf(chatSet.NameColor)+1)%itemGroups.colors().size();
                        chatSet.NameColor = itemGroups.colors().get(index);
                        chatSet.saveChatSet();
                        chatSet.update();
                        inventory.setItem(12,nameColorSetButton());
                        break;
                    case RIGHT:
                        event.setCancelled(true);
                        player.closeInventory();
                        player.sendMessage(ChatColor.YELLOW + "輸入玩家顏色:");
                        player.setMetadata("inputText", new FixedMetadataValue(BasiCore.getPlugin(), "playerColor"));
                        break;
                    default:
                        break;
                }
                break;
            case "contentColor":
                switch (press) {
                    case LEFT:
                        event.setCancelled(true);
                        int index = (itemGroups.colors().indexOf(chatSet.ContentColor)+1)%itemGroups.colors().size();
                        chatSet.ContentColor = itemGroups.colors().get(index);
                        chatSet.saveChatSet();
                        chatSet.update();
                        inventory.setItem(14,contentColorSetButton());
                        break;
                    case RIGHT:
                        event.setCancelled(true);
                        player.closeInventory();
                        player.sendMessage(ChatColor.YELLOW + "輸入內容顏色:");
                        player.setMetadata("inputText", new FixedMetadataValue(BasiCore.getPlugin(), "contentColor"));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        event.setCancelled(true);
    }
}
