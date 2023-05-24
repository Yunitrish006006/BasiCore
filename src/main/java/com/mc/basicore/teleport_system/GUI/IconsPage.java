package com.mc.basicore.teleport_system.GUI;

import com.mc.basicore.teleport_system.SpaceUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Material.ARROW;

public class IconsPage implements InventoryHolder {
    private final Inventory inventory;

    public SpaceUnit spaceUnit;

    public IconsPage(Player player,SpaceUnit unit) {
        Bukkit.broadcastMessage("Icon Page Initializing!");
        spaceUnit = unit;
        this.inventory = Bukkit.createInventory(this,9*4, ChatColor.GOLD + "圖像設定");
        List<Material> materials = new ArrayList<>();
        materials.add(Material.GRASS_BLOCK);
        materials.add(Material.STONE);
        materials.add(Material.RED_BED);
        materials.add(Material.IRON_ORE);
        materials.add(Material.WHEAT);
        for (Material m : materials) {
            this.getInventory().addItem(icon(m));
        }
        this.getInventory().setItem(35,returnButton());
    }

    public void setIcon(String material) {
        Bukkit.broadcastMessage("Material setting");
        spaceUnit.deleteUnit();
        spaceUnit.icon = material;
        spaceUnit.addUnit();
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
    public static ItemStack icon(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET+"• "+ChatColor.LIGHT_PURPLE+"【左鍵】"+ChatColor.GOLD+"選定");
        meta.setLore(lore);
        meta.setLocalizedName("BasiCore.GUI.iconOption");
        meta.setDisplayName(ChatColor.RESET+material.toString().toLowerCase());
        item.setItemMeta(meta);
        return item;
    }
}