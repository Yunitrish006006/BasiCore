package com.mc.basicore.systems.world_index.GUI;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.chat_system.ChatSet;
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
import java.util.Objects;

import static com.mc.basicore.systems.translate.Translator.translate;
import static com.mc.basicore.systems.world_index.WorldIndex.*;
import static org.bukkit.Material.*;

public class UnitsPage implements InventoryHolder {
    private final Inventory inventory;
    Player player;
    public UnitsPage(Player from) {
        this.player = from;
        this.inventory = Bukkit.createInventory(this,9*4,translate(player,"GUI.unit","GUI.list"));
        this.inventory.setItem(27,addSpaceButton());
        this.inventory.setItem(29,playerPageButton(player.getLocale()));
        this.inventory.setItem(31,publicPageButton(player.getLocale()));
        this.inventory.setItem(33,playerDataButton(player.getLocale()));
        this.inventory.setItem(35,tribeListButton(player.getLocale()));
        List<String> unitNames = SpaceUnit.getUnitList(player);
        for (String unitName : unitNames) {
            this.getInventory().addItem(unitButton(unitName));
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
    public ItemStack unitButton(String pointName) {
        SpaceUnit unit = SpaceUnit.query(pointName,player);
        ItemStack item = new ItemStack(Basics.getMaterialFromName(unit.icon.toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        ChatSet chatSet = new ChatSet(Objects.requireNonNull(Bukkit.getServer().getPlayer(unit.owner)));
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
                    player.closeInventory();
                    player.openInventory(new UnitsPage(player).getInventory());
                }
                break;
            default:
                break;
        }
        event.setCancelled(true);
    }
}
