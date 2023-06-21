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

import static com.mc.basicore.systems.translate.Translator.translate;
import static com.mc.basicore.systems.world_index.WorldIndex.returnButton;
import static org.bukkit.Material.*;

public class PlayerDataPage implements InventoryHolder {
    public Inventory inventory;
    public ChatSet chatSet;
    private final Player player;

    public PlayerDataPage(Player from) {
        player = from;
        chatSet = new ChatSet(player);
        this.inventory = Bukkit.createInventory(this,9*4, translate(player,"GUI.player","GUI.data"));
        this.inventory.setItem(10, customNameSetButton());
        this.inventory.setItem(12, setDiscordIDButton());
        this.inventory.setItem(14, nameColorSetButton());
        this.inventory.setItem(16, contentColorSetButton());
        this.inventory.setItem(27, returnButton(player.getLocale()));
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
        meta.setDisplayName(translate(player,"GUI.set","GUI.player","GUI.name"));
        meta.setLore(Arrays.asList(
                translate(player,"GUI.player","GUI.name")+ChatColor.WHITE+": " +chatSet.getName(),
                translate(player,"GUI.left_click","GUI.set"),
                translate(player,"GUI.right_click","GUI.reset")
        ));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack setDiscordIDButton() {
        ItemStack item = new ItemStack(NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.playerDiscordID");
        meta.setDisplayName(translate(player,"GUI.set","GUI.player","GUI.discordID"));
        meta.setLore(Arrays.asList(
                translate(player,"GUI.player","GUI.discordID")+ChatColor.WHITE+": " +chatSet.discordName,
                translate(player,"GUI.left_click","GUI.set"),
                translate(player,"GUI.right_click","GUI.reset")
        ));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack nameColorSetButton() {
        ItemStack item = new ItemStack(BLUE_DYE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.playerColor");
        meta.setDisplayName(translate(player,"GUI.set","GUI.player","GUI.color"));
        meta.setLore(Arrays.asList(
                translate(player,"GUI.player","GUI.color")+ChatColor.WHITE+": "+chatSet.getName(),
                translate(player,"GUI.left_click","GUI.round","GUI.set"),
                translate(player,"GUI.right_click","GUI.type","GUI.set")
        ));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack contentColorSetButton() {
        ItemStack item = new ItemStack(RED_DYE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.contentColor");
        meta.setDisplayName(translate(player,"GUI.set","GUI.content","GUI.color"));
        meta.setLore(Arrays.asList(
                chatSet.ContentColor+"■",
                translate(player,"GUI.left_click","GUI.round","GUI.set"),
                translate(player,"GUI.right_click","GUI.type","GUI.set")
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
                        player.sendMessage(translate(player,"GUI.input","GUI.player","GUI.name")+": ");
                        chatSet.CustomName = "Editing";
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
            case "playerDiscordID":
                switch (press) {
                    case LEFT:
                        event.setCancelled(true);
                        player.closeInventory();
                        player.sendMessage(translate(player,"GUI.input","GUI.player","GUI.discordID")+": ");
                        chatSet.CustomName = "Editing";
                        player.setMetadata("inputText", new FixedMetadataValue(BasiCore.getPlugin(), "discordID"));
                        break;
                    case RIGHT:
                        chatSet.resetChat();
                        chatSet.saveChatSet();
                        inventory.setItem(12, setDiscordIDButton());
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
                        inventory.setItem(14,nameColorSetButton());
                        break;
                    case RIGHT:
                        event.setCancelled(true);
                        player.closeInventory();
                        player.sendMessage(translate(player,"GUI.input","GUI.player","GUI.color")+": ");
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
                        inventory.setItem(16,contentColorSetButton());
                        break;
                    case RIGHT:
                        event.setCancelled(true);
                        player.closeInventory();
                        player.sendMessage(translate(player,"GUI.input","GUI.content","GUI.color")+": ");
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
