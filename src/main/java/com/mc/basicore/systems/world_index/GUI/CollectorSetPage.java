package com.mc.basicore.systems.world_index.GUI;

import com.mc.basicore.systems.collector_system.CollectorSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Collections;

import static com.mc.basicore.systems.translate.Translator.translate;
import static com.mc.basicore.systems.world_index.WorldIndex.returnButton;
import static org.bukkit.Material.*;

public class CollectorSetPage implements InventoryHolder {
    private final Inventory inventory;
    public CollectorSet set;
    public Player player;

    public CollectorSetPage(Player call) {
        set = CollectorSet.query(call);
        player = call;
        inventory = Bukkit.createInventory(this,9*3, translate(player,"GUI.collectorSystem","GUI.set"));
        inventory.setItem(10,setPickaxeButton());
        inventory.setItem(12,setAxeButton());
        inventory.setItem(14,setShovelButton());
        inventory.setItem(16,setHoeButton());
        inventory.setItem(26,returnButton(player.getLocale()));
    }
    @Override @Nonnull
    public Inventory getInventory() {
        return inventory;
    }
    private ItemStack setPickaxeButton() {
        ItemStack item = new ItemStack(IRON_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setPickaxe");
        meta.setDisplayName(translate(player,"GUI.set","GUI.pickaxe"));
        meta.setLore(Collections.singletonList(translate(player, "GUI.dot", "GUI." + set.pickaxe)));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack setAxeButton() {
        ItemStack item = new ItemStack(IRON_AXE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setAxe");
        meta.setDisplayName(translate(player,"GUI.set","GUI.axe"));
        meta.setLore(Collections.singletonList(translate(player, "GUI.dot", "GUI." + set.axe)));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack setHoeButton() {
        ItemStack item = new ItemStack(IRON_HOE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setHoe");
        meta.setDisplayName(translate(player, "GUI.set", "GUI.hoe"));
        meta.setLore(Collections.singletonList(translate(player, "GUI.dot", "GUI." + set.hoe)));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack setShovelButton() {
        ItemStack item = new ItemStack(IRON_SHOVEL);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setShovel");
        meta.setDisplayName(translate(player, "GUI.set", "GUI.shovel"));
        meta.setLore(Collections.singletonList(translate(player, "GUI.dot", "GUI." + set.shovel)));
        item.setItemMeta(meta);
        return item;
    }
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        switch (ID) {
            case "setPickaxe": {
                if (press.isLeftClick()) {
                    set.pickaxe = !set.pickaxe;
                    set.save();
                    inventory.setItem(10,setPickaxeButton());
                }
                break;
            }
            case "setAxe": {
                if (press.isLeftClick()) {
                    set.axe = !set.axe;
                    set.save();
                    inventory.setItem(12,setAxeButton());
                }
                break;
            }
            case "setShovel": {
                if (press.isLeftClick()) {
                    set.shovel = !set.shovel;
                    set.save();
                    inventory.setItem(14,setShovelButton());
                }
                break;
            }
            case "setHoe": {
                if (press.isLeftClick()) {
                    set.hoe = !set.hoe;
                    set.save();
                    inventory.setItem(16,setHoeButton());
                }
                break;
            }
            default:
                break;
        }
        event.setCancelled(true);
    }
}
