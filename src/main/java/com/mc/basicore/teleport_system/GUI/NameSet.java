package com.mc.basicore.teleport_system.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

import static org.bukkit.Material.NAME_TAG;

public class NameSet implements InventoryHolder {
    private final Inventory inventory;
    public String value;
    public NameSet() {
        this.inventory = Bukkit.createInventory(this , InventoryType.ANVIL, "Enter Name");
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
    @Override @Nonnull
    public Inventory getInventory() {
        return this.inventory;
    }
}
