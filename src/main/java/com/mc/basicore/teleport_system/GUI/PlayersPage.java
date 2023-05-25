package com.mc.basicore.teleport_system.GUI;

import com.mc.basicore.Basics;
import com.mc.basicore.chat_system.ChatSet;
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

import static org.bukkit.ChatColor.*;
import static org.bukkit.Material.FEATHER;

public class PlayersPage implements InventoryHolder {
    private final Inventory inventory;

    public PlayersPage(Player player) {
        this.inventory = Bukkit.createInventory(this,9*4, BLUE + "玩家列表");
        List<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        players.remove(player);
        for (Player p : players) {
            this.getInventory().addItem(playerButton(p));
        }
    }

    @Override @Nonnull
    public Inventory getInventory() {
        return inventory;
    }

    public static ItemStack playerPageButton() {
        ItemStack item = new ItemStack(FEATHER);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.PlayerPage");
        meta.setDisplayName(RESET+"玩家列表");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack playerButton(Player player) {
        ItemStack item = Basics.getPlayerSkull(player.getName());
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = new ArrayList<>();
        lore.add(RESET+player.getName());
        lore.add(RESET+"• "+ LIGHT_PURPLE+"【左鍵】"+ GOLD+"傳送");
        meta.setLore(lore);
        meta.setLocalizedName("BasiCore.GUI.playerButton");
        ChatSet chatSet = new ChatSet(player);
        String name = player.getName();
        if (chatSet.CustomName != null) name = chatSet.NameColor+chatSet.CustomName;
        meta.setDisplayName(RESET+name);
        item.setItemMeta(meta);
        return item;
    }
}
