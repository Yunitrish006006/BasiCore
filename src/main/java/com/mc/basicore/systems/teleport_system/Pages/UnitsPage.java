package com.mc.basicore.systems.teleport_system.Pages;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.ChatSystem.ChatSet;
import com.mc.basicore.systems.TribeSystem.Tribe;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.mc.basicore.systems.translate.Translator.translate;
import static com.mc.basicore.systems.world_index.WorldIndex.*;
import static org.bukkit.Material.*;

public class UnitsPage implements InventoryHolder {
    List<String> filters = new ArrayList<>();
    private final Inventory inventory;
    Player player;
    String filter;
    public UnitsPage(Player from,String filter) {
        this.player = from;
        this.filter = filter;
        filters.addAll(Arrays.asList("private","public"));
        Tribe.getTribeList(player).forEach(tribe -> filters.add(tribe.name));
        if (from.isOp()) filters.add("all");
        this.inventory = Bukkit.createInventory(this,getPlace(7,1),translate(player,"GUI.unit","GUI.list"));
        setInventory(this.filter);
    }
    public void setInventory(String filter) {
        this.inventory.setItem(getPlace(1,9),playerPageButton(player.getLocale()));
        this.inventory.setItem(getPlace(2,9), AwardPageButton(player.getLocale()));
        this.inventory.setItem(getPlace(3,9),tribeListButton(player.getLocale()));
        this.inventory.setItem(getPlace(4,9),playerDataButton(player.getLocale()));
        this.inventory.setItem(getPlace(5,9),collectorSetButton(player.getLocale()));
        this.inventory.setItem(getPlace(6,7), filterChangeButton());
        this.inventory.setItem(getPlace(6,9),addSpaceButton());
        switch (filter) {
            case "all": {
                SpaceUnit.getPrivateUnits().forEach(unit -> inventory.addItem(UnitButton(unit)));
                break;
            }
            case "private": {
                SpaceUnit.getPrivateUnits(player).forEach(unit -> inventory.addItem(UnitButton(unit)));
                break;
            }
            case "public": {
                SpaceUnit.getPublicUnits().forEach(e -> inventory.addItem(UnitButton(e)));
                break;
            }
            default:
                if (filters.contains(filter)) {
                    Tribe.Query(filter);
                }
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
        meta.setLore(Collections.singletonList(
                "current filter: " + filter
        ));
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack UnitButton(SpaceUnit unit) {
        ItemStack item = new ItemStack(Basics.getMaterialFromName(unit.unitIcon.toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        ChatSet chatSet = ChatSet.query(unit.ownerUUID);
        String p = unit.purview;
        if (Arrays.asList("private","all","public").contains(unit.purview)) p = "GUI." + unit.purview;
        meta.setLore(Arrays.asList(
                translate(player,"GUI.dot","GUI.left_click","GUI.teleport"),
                translate(player,"GUI.dot","GUI.shift_right_click","GUI.delete"),
                translate(player, "GUI.dot","GUI.right_click","GUI.set"),
                translate(player,"GUI.dot","GUI.owner")+chatSet.getName(),
                translate(player,"GUI.dot","GUI.time")+unit.time,
                translate(player,"GUI.dot","GUI.purview",p),
                Basics.getStandardPosition(unit.location)
        ));
        meta.setLocalizedName("BasiCore.GUI.unit");
        meta.setDisplayName(ChatColor.RESET+unit.unitName);
        item.setItemMeta(meta);
        return item;
    }
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        switch (ID) {
            case "unit":
                if (press.equals(ClickType.LEFT)) {
                    SpaceUnit target = SpaceUnit.queryFromName(event.getCurrentItem().getItemMeta().getDisplayName(),player);
                    target.teleportCountDown(player);
                    player.closeInventory();
                } else if (press.equals(ClickType.SHIFT_RIGHT)) {
                    SpaceUnit.queryFromName(event.getCurrentItem().getItemMeta().getDisplayName(),player).deleteUnit();
                    event.getInventory().remove(event.getCurrentItem());
                    break;
                } else if (press.equals(ClickType.RIGHT)) {
                    player.openInventory(new UnitSetPage(SpaceUnit.queryFromName(event.getCurrentItem().getItemMeta().getDisplayName(),player)).getInventory());
                }
                break;
            case "addUnit":
                if (press.isLeftClick()) {
                    SpaceUnit unit = SpaceUnit.create(Basics.getRandomName(8),player);
                    event.getInventory().addItem(UnitButton(unit));
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
    }
}
