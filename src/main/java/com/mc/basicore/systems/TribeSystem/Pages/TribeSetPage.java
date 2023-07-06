package com.mc.basicore.systems.TribeSystem.Pages;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.TribeSystem.Tribe;
import com.mc.basicore.systems.teleport_system.Pages.UnitsPage;
import com.mc.basicore.systems.teleport_system.SpaceUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

import static com.mc.basicore.systems.translate.Translator.translate;
import static com.mc.basicore.systems.world_index.WorldIndex.returnButton;
import static org.bukkit.Material.*;

public class TribeSetPage implements InventoryHolder {
    private final Inventory inventory;
    public Tribe tribe;
    private final Player owner;
    public static List<Material> recommendIcons() {
        return Arrays.asList(GRASS_BLOCK, STONE, RED_BED, IRON_ORE, WHEAT, SPAWNER);
    }

    public TribeSetPage(Tribe t) {
        tribe = t;
        owner = Bukkit.getPlayer(tribe.owner);
        this.inventory = Bukkit.createInventory(this,9*3, translate(owner,"GUI.unit","GUI.set"));
        this.inventory.setItem(10,setNameButton());
        this.inventory.setItem(12,setIconButton());
        this.inventory.setItem(16,deleteButton());
        assert owner != null;
        this.inventory.setItem(26,returnButton(owner.getLocale()));
    }
    @Override @Nonnull
    public Inventory getInventory() {
        return inventory;
    }
    private ItemStack setIconButton() {
        ItemStack item = new ItemStack(PAINTING);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setIcon");
        meta.setDisplayName(translate(owner,"GUI.set","GUI.icon"));
        meta.setLore(Arrays.asList(
                translate(owner,"GUI.dot")+ChatColor.GOLD+ tribe.icon,
                translate(owner,"GUI.left_click","GUI.round","GUI.icon"),
                translate(owner,"GUI.right_click","GUI.enter","GUI.icon","GUI.interface")
                ));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack deleteButton() {
        ItemStack item = new ItemStack(MUSIC_DISC_11);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.deleteUnit");
        meta.setDisplayName(ChatColor.GRAY+translate(owner,"GUI.remove","GUI.tribe"));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack setNameButton() {
        ItemStack item = new ItemStack(NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setName");
        meta.setDisplayName(translate(owner,"GUI.set","GUI.name"));
        item.setItemMeta(meta);
        return item;
    }
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        switch (ID) {
            case "deleteUnit": {
                if (press.isLeftClick()) {
                    SpaceUnit.deleteUnit(player, tribe.ID.toString());
                    player.closeInventory();
                    player.openInventory(new UnitsPage(player,"private").getInventory());
                }
                break;
            }
            case "setName": {
                if (press.isLeftClick()) {
                    event.setCancelled(true);
                    player.closeInventory();
                    player.sendMessage(translate(owner,"GUI.enter","GUI.unit","GUI.name"));
                    player.setMetadata("inputText", new FixedMetadataValue(BasiCore.getPlugin(), "unitName"));
                    player.setMetadata("data", new FixedMetadataValue(BasiCore.getPlugin(), tribe));
                }
                player.closeInventory();
                break;
            }
            default:
                break;
        }
        event.setCancelled(true);
    }
}
