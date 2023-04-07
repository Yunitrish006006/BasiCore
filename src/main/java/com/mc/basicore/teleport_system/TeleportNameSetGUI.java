package com.mc.basicore.teleport_system;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Material.NAME_TAG;

public class TeleportNameSetGUI implements InventoryHolder {
    private final Inventory inventory;
    public String value;
    public TeleportNameSetGUI () {
        this.inventory = Bukkit.createInventory(null , InventoryType.ANVIL, "Enter Name");
        this.inventory.setItem(0,confirmNameButton());
    }
    private ItemStack confirmNameButton() {
        org.bukkit.inventory.ItemStack item = new ItemStack(NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.confirmNameButton");
        meta.setDisplayName(ChatColor.RESET+"請輸入名稱");
        item.setItemMeta(meta);
        return item;
    }
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
