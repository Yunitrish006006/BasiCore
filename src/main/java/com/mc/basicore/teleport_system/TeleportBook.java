package com.mc.basicore.teleport_system;

import com.mc.basicore.teleport_system.GUI.NameSet;
import com.mc.basicore.teleport_system.GUI.PlayersPage;
import com.mc.basicore.teleport_system.GUI.UnitsPage;
import com.mc.basicore.teleport_system.GUI.UnitSet;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@SuppressWarnings("ConstantConditions")
public class TeleportBook extends ItemStack implements Listener {
    public TeleportBook() {
        super(Material.PAPER);
        ItemMeta meta = getItemMeta();
        meta.setLocalizedName("BasiCore.TeleportBook");
        Objects.requireNonNull(meta).setDisplayName(ChatColor.RESET+String.valueOf(ChatColor.GOLD)+ChatColor.BOLD+"傳送之書");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("【右鍵】開啟傳送介面");
        meta.setLore(lore);
        this.setItemMeta(meta);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (this.isSimilar(event.getItem()) && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            player.openInventory(new UnitsPage(player).getInventory());
        }
    }
    @EventHandler
    public void UnitsPage(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getHolder() instanceof UnitsPage) {
            switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
                case "BasiCore.unitButton":
                    switch (event.getClick()) {
                        case LEFT:
                            SpaceUnit unit_1 = new SpaceUnit(event.getCurrentItem().getItemMeta().getDisplayName(),player);
                            unit_1.teleportCountDown(player);
                            player.closeInventory();
                            break;
                        case RIGHT:
                            player.openInventory(new UnitSet(new SpaceUnit(event.getCurrentItem().getItemMeta().getDisplayName(),player).displayName).getInventory());
                            break;
                        default:
                            break;
                    }
                    break;
                case "BasiCore.GUI.addUnit":
                    if (event.getClick().isLeftClick()) {
                        StringBuilder x = new StringBuilder();
                        for(int i=0;i<4;i++) {
                            x.append((char) ('a' + (Math.random() * 26)));
                        }
                        SpaceUnit unit = new SpaceUnit(x.toString(),player);
                        unit.addUnit();
                        player.closeInventory();
                        player.openInventory(new UnitsPage(player).getInventory());
                    }
                    break;
                case "BasiCore.GUI.PlayerPage":
                    if (event.getClick().isLeftClick()) {
                        player.openInventory(new PlayersPage(player).getInventory());
                    }
                default:
                    break;
            }
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void UnitSetUIEvent(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getHolder() instanceof UnitSet) {
            switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
                case "BasiCore.deleteUnitButton":
                    if (event.getClick().isLeftClick()) {
                        UnitSet ui = (UnitSet) event.getInventory().getHolder();
                        SpaceUnit.deleteUnit(player,ui.unitName);
                        player.closeInventory();
                        player.openInventory(new UnitsPage(player).getInventory());
                    }
                    break;
                case "BasiCore.setNameButton":
                    if (event.getClick().isLeftClick()) {
                        player.openInventory(new NameSet().getInventory());
                    }
                default:
                    break;
            }
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void PlayersPage(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getHolder() instanceof PlayersPage) {
            switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
                case "BasiCore.GUI.playerButton":
                    if (event.getClick().isLeftClick()) {
                        PlayersPage ui = (PlayersPage) event.getInventory().getHolder();
                        Player target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName());
                        player.teleport(target.getLocation());
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
    public void TeleportNameSetEvent(InventoryClickEvent event) {
//        ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
//        Bukkit.broadcastMessage(itemMeta.getLocalizedName());
//        if (event.getInventory().getType()==InventoryType.ANVIL) {
//            AnvilInventory anvilInventory = (AnvilInventory) event.getClickedInventory();
//            if (itemMeta != null && itemMeta.getLocalizedName().equals("BasiCore.confirmNameButton")) {
//                Bukkit.broadcastMessage("Rename button clicked!");
//                event.setCancelled(true);
//            }
//        }
    }
}
