package com.mc.basicore.teleport_system.GUI;

import com.mc.basicore.Basics;
import com.mc.basicore.teleport_system.SpaceUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.*;
import static org.bukkit.Material.ARROW;

public class PublicPage implements InventoryHolder {
    private final Inventory inventory;

    public PublicPage(Player player) {
        this.inventory = Bukkit.createInventory(this,9*4, BLUE + "公用傳送點列表");
        for (SpaceUnit unit : SpaceUnit.getPublicUnits()) {
            this.getInventory().addItem(publicUnit(unit));
        }
        this.inventory.setItem(35,returnButton());
    }

    @Override @Nonnull
    public Inventory getInventory() {
        return inventory;
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
    private static ItemStack publicUnit(SpaceUnit unit) {
        ItemStack item = new ItemStack(Basics.getMaterialFromName(unit.icon));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = new ArrayList<>();
        lore.add(RESET+"• "+ LIGHT_PURPLE+"【左鍵】"+ GOLD+"傳送");
        lore.add(ChatColor.RESET+"• "+ChatColor.WHITE+"所有權人: "+ChatColor.GOLD+unit.owner);
        lore.add(ChatColor.RESET+"• "+ChatColor.WHITE+"倒數時間: "+ChatColor.GOLD+unit.time);
        lore.add(ChatColor.RESET+"• "+ChatColor.WHITE+"權限設定: "+ChatColor.GOLD+unit.purview);
        lore.add(ChatColor.RESET+"• "+Basics.getStandardPosition(unit.location));
        meta.setLore(lore);
        meta.setLocalizedName("BasiCore.GUI.publicUnit");
        meta.setDisplayName(RESET+unit.displayName);
        item.setItemMeta(meta);
        return item;
    }
}
