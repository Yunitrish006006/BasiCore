package com.mc.basicore.teleport_system;

import com.mc.basicore.BasiCore;
import com.mc.basicore.teleport_system.GUI.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

@SuppressWarnings("ConstantConditions")
public class TeleportBook extends ItemStack implements Listener {
    public TeleportBook() {
        super(Material.PAPER);
        ItemMeta meta = getItemMeta();
        meta.setLocalizedName("BasiCore.TeleportBook");
        meta.setCustomModelData(1);
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
        if (!(event.getInventory().getHolder() instanceof UnitsPage)) return;
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
            case "BasiCore.GUI.unit":
                switch (event.getClick()) {
                    case LEFT:
                        SpaceUnit target = SpaceUnit.query(event.getCurrentItem().getItemMeta().getDisplayName(),player);
                        target.teleportCountDown(player);
                        player.closeInventory();
                        break;
                    case RIGHT:
                        player.openInventory(new UnitSetPage(SpaceUnit.query(event.getCurrentItem().getItemMeta().getDisplayName(),player)).getInventory());
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
            case "BasiCore.GUI.playerPage":
                if (event.getClick().isLeftClick()) {
                    player.openInventory(new PlayersPage(player).getInventory());
                }
                break;
            case "BasiCore.GUI.publicPage":
                if (event.getClick().isLeftClick()) {
                    player.openInventory(new PublicPage(player).getInventory());
                }
                break;
            default:
                break;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void PublicPage(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof PublicPage)) return;
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
            case "BasiCore.GUI.publicUnit":
                if (event.getClick() == ClickType.LEFT) {
                    List<SpaceUnit> units = SpaceUnit.getPublicUnits();
                    for (SpaceUnit u: units) {
                        if (u.displayName.equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                            u.teleportCountDown(player);
                            break;
                        }
                    }
                    player.closeInventory();
                }
                break;
            case "BasiCore.GUI.return":
                if (event.getClick().isLeftClick()) {
                    player.closeInventory();
                    player.openInventory(new UnitsPage(player).getInventory());
                }
                break;
            default:
                break;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void UnitSetPage(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof UnitSetPage)) return;
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        UnitSetPage ui = (UnitSetPage) event.getInventory().getHolder();
        switch (event.getCurrentItem().getItemMeta().getLocalizedName()) {
            case "BasiCore.GUI.deleteUnit":
                if (event.getClick().isLeftClick()) {
                    SpaceUnit.deleteUnit(player,ui.unit.displayName);
                    player.closeInventory();
                    player.openInventory(new UnitsPage(player).getInventory());
                }
                break;
            case "BasiCore.GUI.setName":
                if (event.getClick().isLeftClick()) {
                    event.setCancelled(true);
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "Enter the player name:");
                    ui.setPlayerNameInput("");
                    player.setMetadata("teleport_name_input", new FixedMetadataValue(BasiCore.getPlugin(), ui));
                    player.sendTitle("", ChatColor.GREEN + "Enter Player Name", 0, 40, 20);
                }
                break;
            case "BasiCore.GUI.setPurview":
                if (event.getClick().isLeftClick()) {
                    SpaceUnit unit = SpaceUnit.query(ui.unit.displayName,player);
                    unit.deleteUnit();
                    if (unit.purview.equals("private")) unit.purview = "public";
                    else unit.purview = "private";
                    unit.addUnit();
                    player.closeInventory();
                    player.openInventory(new UnitSetPage(unit).getInventory());
                }
                break;
            case "BasiCore.GUI.setIcon":
                if (event.getClick().isLeftClick()) {
                    player.closeInventory();
                    player.openInventory(new IconsPage(player,SpaceUnit.query(ui.unit.displayName,player)).getInventory());
                }
                break;
            case "BasiCore.GUI.return":
                if (event.getClick().isLeftClick()) {
                    player.closeInventory();
                    player.openInventory(new UnitsPage(player).getInventory());
                }
                break;
            default:
                break;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void IconsPage(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof IconsPage)) return;
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        SpaceUnit unit = ((IconsPage)event.getInventory().getHolder()).spaceUnit;
        String ID = event.getCurrentItem().getItemMeta().getLocalizedName();
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
                player.openInventory(new UnitSetPage(unit).getInventory());
                break;
            default:
                break;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void PlayersPage(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof PlayersPage)) return;
        if (event.getCurrentItem()==null) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem().getItemMeta().getLocalizedName().equals("BasiCore.GUI.playerButton")) {
            if (event.getClick().isLeftClick()) {
                PlayersPage ui = (PlayersPage) event.getInventory().getHolder();
                Player target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getLore().get(0));
                player.teleport(target.getLocation());
                player.closeInventory();
            }
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void NameSetPage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!player.hasMetadata("teleport_name_input")) return;
        if (!player.getMetadata("teleport_name_input").equals(null)) return;
        event.setCancelled(true);
        String input = event.getMessage();
        UnitSetPage ui = (UnitSetPage) player.getMetadata("teleport_name_input").get(0).value();
        ui.setPlayerNameInput(input);
        String old = ui.unit.displayName;
        ui.unit.deleteUnit();
        ui.unit.displayName = ui.getPlayerNameInput();
        ui.unit.addUnit();
        player.sendTitle("",ChatColor.WHITE + old + ChatColor.GREEN + "=>"+ChatColor.WHITE+ui.getPlayerNameInput(), 0, 40, 20);
        player.removeMetadata("teleport_name_input", BasiCore.getPlugin());
        player.openInventory(new UnitSetPage(ui.unit).getInventory());
    }
}
