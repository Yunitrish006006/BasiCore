package com.mc.basicore.systems.teleport_system.Pages;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.ChatSystem.ChatSet;
import com.mc.basicore.systems.teleport_system.SpaceUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import java.util.Objects;

import static com.mc.basicore.systems.translate.Translator.translate;
import static com.mc.basicore.systems.world_index.WorldIndex.returnButton;
import static org.bukkit.ChatColor.*;

public class PublicPage implements InventoryHolder {
    private final Inventory inventory;

    public PublicPage(Player player) {
        this.inventory = Bukkit.createInventory(this,9*4, translate(player,"GUI.public","GUI.unit","GUI.list"));
        for (SpaceUnit unit : SpaceUnit.getPublicUnits()) {
            this.getInventory().addItem(publicUnit(unit));
        }
        this.inventory.setItem(27, returnButton(player.getLocale()));
    }

    @Override @Nonnull
    public Inventory getInventory() {
        return inventory;
    }
    private static ItemStack publicUnit(SpaceUnit unit) {
        ItemStack item = new ItemStack(Basics.getMaterialFromName(unit.icon));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = new ArrayList<>();
        String ownerName;
        if (Bukkit.getServer().getPlayer(unit.playerUUID)!=null) {
            ChatSet chatSet = new ChatSet(Objects.requireNonNull(Bukkit.getServer().getPlayer(unit.playerUUID)));
            ownerName = chatSet.NameColor+chatSet.CustomName;
        }
        else {
            ownerName = GOLD + ChatSet.query(unit.playerUUID).getName();
        }
        lore.add(RESET+"• "+ LIGHT_PURPLE+"【左鍵】"+ GOLD+"傳送");
        lore.add(ChatColor.RESET+"• "+ChatColor.WHITE+"所有權人: "+ownerName);
        lore.add(ChatColor.RESET+"• "+ChatColor.WHITE+"倒數時間: "+ChatColor.GOLD+unit.time);
        lore.add(ChatColor.RESET+"• "+ChatColor.WHITE+"權限設定: "+ChatColor.GOLD+unit.purview);
        lore.add(ChatColor.RESET+"• "+Basics.getStandardPosition(unit.location));
        meta.setLore(lore);
        meta.setLocalizedName("BasiCore.GUI.publicUnit");
        meta.setDisplayName(RESET+unit.displayName);
        item.setItemMeta(meta);
        return item;
    }
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        if (ID.equals("publicUnit")) {
            if (press.isLeftClick()) {
                List<SpaceUnit> units = SpaceUnit.getPublicUnits();
                for (SpaceUnit u : units) {
                    if (u.displayName.equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                        u.teleportCountDown(player);
                        break;
                    }
                }
                player.closeInventory();
            }
        }
        event.setCancelled(true);
    }
}
