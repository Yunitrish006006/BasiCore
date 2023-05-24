package com.mc.basicore.teleport_system.GUI;

import com.mc.basicore.teleport_system.SpaceUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Material.*;

public class UnitSetPage implements InventoryHolder {
    private final Inventory inventory;
    public SpaceUnit unit;
    public UnitSetPage(SpaceUnit u) {
        this.inventory = Bukkit.createInventory(this,9*3, ChatColor.GOLD + "傳送點設定");
        unit = u;
        this.inventory.setItem(10,setNameButton());
        this.inventory.setItem(12,setIconButton());
        this.inventory.setItem(14,setPurviewButton());
        this.inventory.setItem(16,deleteButton());
        this.inventory.setItem(26,returnButton());
    }

    @Override @Nonnull
    public Inventory getInventory() {
        return inventory;
    }
    private ItemStack setPurviewButton() {
        ItemStack item = new ItemStack(DARK_OAK_DOOR);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setPurview");
        meta.setDisplayName(ChatColor.RESET+"設定可見範圍");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET+"• "+ChatColor.GOLD+unit.purview);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack returnButton() {
        ItemStack item = new ItemStack(ARROW);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.return");
        meta.setDisplayName(ChatColor.RESET+"回到上一頁");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack setIconButton() {
        ItemStack item = new ItemStack(PAINTING);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setIcon");
        meta.setDisplayName(ChatColor.RESET+"設定圖案");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack deleteButton() {
        ItemStack item = new ItemStack(MUSIC_DISC_11);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.deleteUnit");
        meta.setDisplayName(ChatColor.RESET+"移除傳送點");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack setNameButton() {
        ItemStack item = new ItemStack(NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setName");
        meta.setDisplayName(ChatColor.RESET+"設定名稱");
        item.setItemMeta(meta);
        return item;
    }
}
