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

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Material.*;

public class Root implements InventoryHolder {
    private final Inventory inventory;

    public Root(Player player) {
        this.inventory = Bukkit.createInventory(this,9*4, ChatColor.BLUE + "Teleport Hub");
        this.inventory.setItem(27,addSpaceButton());
        List<String> unitNames = SpaceUnit.getUnitList(player);
        for (String unitName : unitNames) {
            this.getInventory().addItem(unitButton(unitName,player));
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public static ItemStack addSpaceButton() {
        ItemStack item = new ItemStack(FEATHER);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.addUnitButton");
        meta.setDisplayName(ChatColor.RESET+"新增傳送點");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack unitButton(String pointName, Player player) {
        SpaceUnit unit = new SpaceUnit(pointName,player);
        ItemStack item = new ItemStack(Material.getMaterial(unit.icon.toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET+""+ChatColor.LIGHT_PURPLE+"【左鍵】"+ChatColor.GOLD+"傳送");
        lore.add(ChatColor.RESET+""+ChatColor.LIGHT_PURPLE+"【右鍵】"+ChatColor.GOLD+"設定");
        lore.add(ChatColor.RESET+""+ChatColor.WHITE+"所有權人: "+ChatColor.GOLD+unit.owner);
        lore.add(ChatColor.RESET+""+ChatColor.WHITE+"倒數時間: "+ChatColor.GOLD+unit.time);
        lore.add(ChatColor.RESET+""+ChatColor.WHITE+"權限設定: "+ChatColor.GOLD+unit.purview);
        lore.add(ChatColor.RESET+""+ChatColor.WHITE+"X: "+ChatColor.GOLD+Math.round(unit.location.getX()*100.0)/100.0);
        lore.add(ChatColor.RESET+""+ChatColor.WHITE+"Y: "+ChatColor.GOLD+Math.round(unit.location.getY()*100.0)/100.0);
        lore.add(ChatColor.RESET+""+ChatColor.WHITE+"Z: "+ChatColor.GOLD+Math.round(unit.location.getZ()*100.0)/100.0);
        meta.setLore(lore);
        meta.setLocalizedName("BasiCore.unitButton");
        meta.setDisplayName(ChatColor.RESET+unit.displayName);
        item.setItemMeta(meta);
        return item;
    }
}
