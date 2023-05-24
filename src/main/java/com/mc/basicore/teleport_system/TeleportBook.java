package com.mc.basicore.teleport_system;

import com.mc.basicore.teleport_system.GUI.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
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
        if (!(event.getInventory().getHolder() instanceof UnitsPage)) return;
        switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
            case "BasiCore.unitButton":
                switch (event.getClick()) {
                    case LEFT:
                        SpaceUnit target = SpaceUnit.query(event.getCurrentItem().getItemMeta().getDisplayName(),player);
                        target.teleportCountDown(player);
                        player.closeInventory();
                        break;
                    case RIGHT:
                        player.openInventory(new UnitSetPage(SpaceUnit.query(event.getCurrentItem().getItemMeta().getDisplayName(),player).displayName).getInventory());
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
                    SpaceUnit unit = SpaceUnit.create(x.toString(),player);
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
    @EventHandler
    public void UnitSetPage(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        if (!(event.getInventory().getHolder() instanceof UnitSetPage)) return;
        UnitSetPage ui = (UnitSetPage) event.getInventory().getHolder();
        switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
            case "BasiCore.GUI.deleteUnit":
                if (event.getClick().isLeftClick()) {
                    SpaceUnit.deleteUnit(player,ui.unitName);
                    player.closeInventory();
                    player.openInventory(new UnitsPage(player).getInventory());
                }
                break;
            case "BasiCore.GUI.setName":
                if (event.getClick().isLeftClick()) {
                    player.openInventory(new NameSet().getInventory());
                }
            case "BasiCore.GUI.setIcon":
                if (event.getClick().isLeftClick()) {
                    player.closeInventory();
                    player.openInventory(new IconsPage(player,SpaceUnit.query(ui.unitName,player)).getInventory());
                }
            case "BasiCore.GUI.return":
                if (event.getClick().isLeftClick()) {
                    player.closeInventory();
                    player.openInventory(new UnitsPage(player).getInventory());
                }
            default:
                break;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void IconsPage(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        if (!(event.getInventory().getHolder() instanceof IconsPage)) return;
        String ID = event.getCurrentItem().getItemMeta().getLocalizedName();

        Bukkit.broadcastMessage("iconPage");
        switch (ID) {
            case "BasiCore.GUI.return":
                if (event.getClick().isLeftClick()) {
                    player.closeInventory();
                    player.openInventory(new UnitsPage(player).getInventory());
                }
                break;
            case "BasiCore.GUI.iconOption":
                IconsPage page = (IconsPage) event.getInventory().getHolder();
                page.setIcon(event.getCurrentItem().getItemMeta().getDisplayName());
                player.openInventory(new UnitSetPage(event.getCurrentItem().getItemMeta().getDisplayName()).getInventory());
                break;
            default:
                break;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void PlayersPage(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        if (!(event.getInventory().getHolder() instanceof PlayersPage)) return;
        if (event.getCurrentItem().getItemMeta().getLocalizedName().equals("BasiCore.GUI.playerButton")) {
            if (event.getClick().isLeftClick()) {
                PlayersPage ui = (PlayersPage) event.getInventory().getHolder();
                Player target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName());
                player.teleport(target.getLocation());
                player.closeInventory();
            }
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void TeleportNameSetEvent(InventoryClickEvent event) {
        if (event.getCurrentItem()==null) return;
        ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
        if (event.getInventory().getType() != InventoryType.ANVIL) return;
        Bukkit.broadcastMessage(itemMeta.getLocalizedName());
        AnvilInventory anvilInventory = (AnvilInventory) event.getClickedInventory();
        if (itemMeta != null && itemMeta.getLocalizedName().equals("BasiCore.confirmNameButton")) {
            Bukkit.broadcastMessage("Rename button clicked!");
            event.setCancelled(true);
        }
    }
}
