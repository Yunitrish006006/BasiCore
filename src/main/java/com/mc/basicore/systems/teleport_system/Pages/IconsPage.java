package com.mc.basicore.systems.teleport_system.Pages;

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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mc.basicore.systems.world_index.WorldIndex.returnButton;
import static org.bukkit.Material.*;

public class IconsPage implements InventoryHolder {
    private final Inventory inventory;

    public SpaceUnit spaceUnit;

    public IconsPage(Player player,SpaceUnit unit) {
        spaceUnit = unit;
        inventory = Bukkit.createInventory(this,9*4, ChatColor.GOLD + "圖像設定");
        inventory.setItem(35,returnButton(player.getLocale()));
        List<Material> materials = Arrays.asList(
                GRASS_BLOCK, STONE, RED_BED, IRON_ORE, WHEAT, SPAWNER,
                ACACIA_SAPLING, BAMBOO_SAPLING, BIRCH_SAPLING, CHERRY_SAPLING, DARK_OAK_SAPLING, JUNGLE_SAPLING, OAK_SAPLING
        );
        for (Material m : materials) {
            this.getInventory().addItem(icon(m));
        }
    }

    public void setIcon(String material) {
        spaceUnit.deleteUnit();
        spaceUnit.unitIcon = material;
        spaceUnit.saveUnit();
    }

    @Override @Nonnull
    public Inventory getInventory() {
        return inventory;
    }
    public static ItemStack icon(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET+"• "+ChatColor.LIGHT_PURPLE+"【左鍵】"+ChatColor.GOLD+"選定");
        meta.setLore(lore);
        meta.setLocalizedName("BasiCore.GUI.iconOption");
        meta.setDisplayName(ChatColor.RESET+material.toString().toLowerCase());
        item.setItemMeta(meta);
        return item;
    }
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        SpaceUnit unit = ((IconsPage)event.getInventory().getHolder()).spaceUnit;
        switch (ID) {
            case "return":
                if (press.isLeftClick()) {
                    player.closeInventory();
                    player.openInventory(new UnitsPage(player,"private").getInventory());
                }
                break;
            case "iconOption":
                if (press.isLeftClick()) {
                    setIcon(event.getCurrentItem().getItemMeta().getDisplayName());
                    player.openInventory(new UnitSetPage(unit).getInventory());
                }
                break;
            default:
                break;
        }
        event.setCancelled(true);
    }
}
