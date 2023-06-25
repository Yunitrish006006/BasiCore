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
import java.util.Arrays;
import java.util.List;

import static com.mc.basicore.systems.translate.Translator.translate;
import static com.mc.basicore.systems.world_index.WorldIndex.*;
import static org.bukkit.Material.*;

public class UnitsPage implements InventoryHolder {
    List<String> filters = Arrays.asList("private","public","tribe","all");
    private final Inventory inventory;
    Player player;
    String filter;
    public UnitsPage(Player from,String filter) {
        this.player = from;
        this.filter = filter;
        this.inventory = Bukkit.createInventory(this,getPlace(7,1),translate(player,"GUI.unit","GUI.list"));
        setInventory(this.filter);
    }
    public void setInventory(String filter) {
        this.inventory.setItem(getPlace(1,9),playerPageButton(player.getLocale()));
        this.inventory.setItem(getPlace(2,9),publicPageButton(player.getLocale()));
        this.inventory.setItem(getPlace(3,9),tribeListButton(player.getLocale()));
        this.inventory.setItem(getPlace(4,9),playerDataButton(player.getLocale()));
        this.inventory.setItem(getPlace(5,9),collectorSetButton(player.getLocale()));
        this.inventory.setItem(getPlace(6,8), AwardPageButton(player.getLocale()));
        this.inventory.setItem(getPlace(6,7), filterChangeButton());
        this.inventory.setItem(getPlace(6,9),addSpaceButton());
        switch (filter) {
            case "all": {
                SpaceUnit.getUnitList().forEach(e -> inventory.addItem(unitButton(e)));
                break;
            }
            case "private": {
                SpaceUnit.getUnitList(player).forEach(e -> inventory.addItem(unitButton(e)));
                break;
            }
            case "public": {
                SpaceUnit.getPublicUnits().forEach(e -> inventory.addItem(UnitButton(e)));
                break;
            }
            case "tribe": {
                SpaceUnit.getTribeUnits().forEach(e -> inventory.addItem(UnitButton(e)));
                break;
            }
            default:
                break;
        }
    }

    @Override
    @Nonnull
    public Inventory getInventory() {
        return inventory;
    }
    public ItemStack addSpaceButton() {
        ItemStack item = new ItemStack(FEATHER);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.addUnit");
        meta.setDisplayName(translate(player,"GUI.add","GUI.unit"));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack filterChangeButton() {
        ItemStack item = new ItemStack(ENDER_EYE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.switchUnitFilter");
        meta.setDisplayName(translate(player,"GUI.change","GUI.filter"));
        meta.setLore(Arrays.asList(
                "current filter: "+filter
        ));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack unitButton(String pointName) {
        SpaceUnit unit = SpaceUnit.query(pointName,player);
        ItemStack item = new ItemStack(Basics.getMaterialFromName(unit.icon.toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        ChatSet chatSet = ChatSet.query(unit.playerUUID);
        meta.setLore(Arrays.asList(
                translate(player,"GUI.dot","GUI.left_click","GUI.teleport"),
                translate(player, "GUI.dot","GUI.right_click","GUI.set"),
                translate(player,"GUI.dot","GUI.owner")+chatSet.getName(),
                translate(player,"GUI.dot","GUI.time")+unit.time,
                translate(player,"GUI.dot","GUI.purview","GUI."+unit.purview),
                Basics.getStandardPosition(unit.location)
                ));
        meta.setLocalizedName("BasiCore.GUI.unit");
        meta.setDisplayName(ChatColor.RESET+unit.displayName);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack UnitButton(SpaceUnit unit) {
        ItemStack item = new ItemStack(Basics.getMaterialFromName(unit.icon.toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        ChatSet chatSet = ChatSet.query(unit.playerUUID);
        meta.setLore(Arrays.asList(
                translate(player,"GUI.dot","GUI.left_click","GUI.teleport"),
                translate(player, "GUI.dot","GUI.right_click","GUI.set"),
                translate(player,"GUI.dot","GUI.owner")+chatSet.getName(),
                translate(player,"GUI.dot","GUI.time")+unit.time,
                translate(player,"GUI.dot","GUI.purview","GUI."+unit.purview),
                Basics.getStandardPosition(unit.location)
        ));
        meta.setLocalizedName("BasiCore.GUI.unit");
        meta.setDisplayName(ChatColor.RESET+unit.displayName);
        item.setItemMeta(meta);
        return item;
    }
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        switch (ID) {
            case "unit":
                switch (press) {
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
            case "addUnit":
                if (press.isLeftClick()) {
                    SpaceUnit unit = SpaceUnit.create(Basics.getRandomName(8),player);
                    event.getInventory().addItem(unitButton(unit.displayName));
                }
                break;
            case "switchUnitFilter": {
                if (press.isLeftClick()) {
                    inventory.clear();
                    if (player.isOp()) filter = filters.get((filters.indexOf(filter)+1)%filters.size());
                    else filter = filters.get((filters.indexOf(filter)+1)%(filters.size()-1));
                    setInventory(filter);
                }
                break;
            }
            default:
                break;
        }
        event.setCancelled(true);
    }
}
