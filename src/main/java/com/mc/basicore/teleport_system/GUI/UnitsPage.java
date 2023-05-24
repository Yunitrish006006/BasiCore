package com.mc.basicore.teleport_system.GUI;

import com.mc.basicore.Basics;
import com.mc.basicore.chat_system.ChatSet;
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
import java.util.Objects;

import static org.bukkit.Material.*;

public class UnitsPage implements InventoryHolder {
    private final Inventory inventory;

    public UnitsPage(Player player) {
        this.inventory = Bukkit.createInventory(this,9*4, ChatColor.BLUE + "Teleport Hub");
        this.inventory.setItem(27,addSpaceButton());
        this.inventory.setItem(29,playerPageButton());
        this.inventory.setItem(31,publicPageButton());
        List<String> unitNames = SpaceUnit.getUnitList(player);
        for (String unitName : unitNames) {
            this.getInventory().addItem(unitButton(unitName,player));
        }
    }

    @Override
    @Nonnull
    public Inventory getInventory() {
        return inventory;
    }

    public static ItemStack addSpaceButton() {
        ItemStack item = new ItemStack(FEATHER);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.addUnit");
        meta.setDisplayName(ChatColor.RESET+"新增傳送點");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack playerPageButton() {
        ItemStack item = new ItemStack(PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.playerPage");
        meta.setDisplayName(ChatColor.RESET+"玩家列表");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack publicPageButton() {
        ItemStack item = new ItemStack(WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.publicPage");
        meta.setDisplayName(ChatColor.RESET+"公用傳送點列表");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack unitButton(String pointName, Player player) {
        SpaceUnit unit = SpaceUnit.query(pointName,player);
        ItemStack item = new ItemStack(Basics.getMaterialFromName(unit.icon.toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        ChatSet chatSet = new ChatSet(Objects.requireNonNull(Bukkit.getServer().getPlayer(unit.owner)));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET+"• "+ChatColor.LIGHT_PURPLE+"【左鍵】"+ChatColor.GOLD+"傳送");
        lore.add(ChatColor.RESET+"• "+ChatColor.LIGHT_PURPLE+"【右鍵】"+ChatColor.GOLD+"設定");
        lore.add(ChatColor.RESET+"• "+ChatColor.WHITE+"所有權人: "+ chatSet.NameColor + chatSet.CustomName);
        lore.add(ChatColor.RESET+"• "+ChatColor.WHITE+"倒數時間: "+ChatColor.GOLD+unit.time);
        lore.add(ChatColor.RESET+"• "+ChatColor.WHITE+"權限設定: "+ChatColor.GOLD+unit.purview);
        lore.add(ChatColor.RESET+"• "+Basics.getStandardPosition(unit.location));
        meta.setLore(lore);
        meta.setLocalizedName("BasiCore.GUI.unit");
        meta.setDisplayName(ChatColor.RESET+unit.displayName);
        item.setItemMeta(meta);
        return item;
    }
}
