package com.mc.basicore.teleport_system;

import com.mc.basicore.BasiCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Material.*;

public class TeleportUnitSetGUI implements InventoryHolder {
    private final Inventory inventory;
    public String unitName;
    public TeleportUnitSetGUI(String name) {
        this.inventory = Bukkit.createInventory(this,9*3, ChatColor.GOLD + "Unit Settings");
        this.inventory.setItem(10,setNameButton());
        this.inventory.setItem(16,deleteButton());
        this.unitName = name;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    private ItemStack deleteButton() {
        ItemStack item = new ItemStack(MUSIC_DISC_11);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.deleteUnitButton");
        meta.setDisplayName(ChatColor.RESET+"移除傳送點");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack setNameButton() {
        ItemStack item = new ItemStack(NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.setNameButton");
        meta.setDisplayName(ChatColor.RESET+"設定名稱");
        item.setItemMeta(meta);
        return item;
    }
}
