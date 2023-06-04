package com.mc.basicore.systems.world_index.GUI;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.chat_system.ChatSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.mc.basicore.systems.world_index.WorldIndex.returnButton;
import static org.bukkit.ChatColor.*;

public class PlayersPage implements InventoryHolder {
    private final Inventory inventory;

    public PlayersPage(Player player) {
        this.inventory = Bukkit.createInventory(this,9*4, BLUE + "玩家列表");
        List<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        players.remove(player);
        for (Player p : players) {
            this.getInventory().addItem(playerButton(p));
        }
        inventory.setItem(27,returnButton());
    }

    @Override @Nonnull
    public Inventory getInventory() {
        return inventory;
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
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        if (ID.equals("playerButton")) {
            if (press.isLeftClick()) {
                PlayersPage ui = (PlayersPage) event.getInventory().getHolder();
                Player target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getLore().get(0));
                player.teleport(target.getLocation());
                player.closeInventory();
            }
        }
        event.setCancelled(true);
    }
}
