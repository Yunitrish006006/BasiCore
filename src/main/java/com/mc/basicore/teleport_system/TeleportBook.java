package com.mc.basicore.teleport_system;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

@SuppressWarnings("ConstantConditions")
public class TeleportBook extends ItemStack implements Listener {
    public TeleportBook() {
        super(Material.BOOK);
        ItemMeta meta = getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(ChatColor.RESET+""+ChatColor.GOLD+""+ChatColor.BOLD+"傳送之書");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("【右鍵】開啟傳送介面");
        meta.setLore(lore);
        this.setItemMeta(meta);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (this.isSimilar(event.getItem()) && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            player.openInventory(new TeleportRootGUI(player).getInventory());
        }
    }
    @EventHandler
    public void TeleportRootUIEvent(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getHolder() instanceof TeleportRootGUI) {
            switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
                case "BasiCore.unitButton":
                    switch (event.getClick()) {
                        case LEFT:
                            CoordinateUnit unit_1 = new CoordinateUnit(event.getCurrentItem().getItemMeta().getDisplayName(),player);
                            unit_1.teleportCountDown(player);
                            player.closeInventory();
                            break;
                        case RIGHT:
                            player.openInventory(new TeleportUnitSetGUI(new CoordinateUnit(event.getCurrentItem().getItemMeta().getDisplayName(),player).displayName).getInventory());
                            break;
                        default:
                            break;
                    }
                    break;
                case "BasiCore.addUnitButton":
                    if (event.getClick().isLeftClick()) {
                        StringBuilder x = new StringBuilder();
                        for(int i=0;i<4;i++) {
                            x.append((char) ('a' + (Math.random() * 26)));
                        }
                        CoordinateUnit unit = new CoordinateUnit(x.toString(),player);
                        unit.addUnit();
                        player.closeInventory();
                    }
                    break;
                default:
                    break;
            }
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void TeleportSetUIEvent(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getHolder() instanceof TeleportUnitSetGUI) {
            switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
                case "BasiCore.deleteUnitButton":
                    if (event.getClick().isLeftClick()) {
                        TeleportUnitSetGUI ui = (TeleportUnitSetGUI) event.getInventory().getHolder();
                        CoordinateUnit.deleteUnit(player,ui.unitName);
                        player.closeInventory();
                    }
                    break;
                case "BasiCore.setNameButton":
                    if (event.getClick().isLeftClick()) {
                        player.openInventory(new TeleportNameSetGUI().getInventory());
                    }
                default:
                    break;
            }
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        if (event.getInventory() instanceof TeleportNameSetGUI) {
            Bukkit.broadcastMessage("anvil activated!");
        }
    }
}
