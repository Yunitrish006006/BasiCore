package com.mc.basicore.world_index.GUI;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.teleport_system.SpaceUnit;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mc.basicore.world_index.WorldIndex.returnButton;
import static org.bukkit.Material.*;

public class UnitSetPage implements InventoryHolder {
    private final Inventory inventory;
    public SpaceUnit unit;

    public static List<Material> recommendIcons() {
        return Arrays.asList(GRASS_BLOCK, STONE, RED_BED, IRON_ORE, WHEAT, SPAWNER);
    }

    public UnitSetPage(SpaceUnit u) {
        this.inventory = Bukkit.createInventory(this,9*3, ChatColor.GOLD + "傳送點設定");
        unit = u;
        this.inventory.setItem(10,setNameButton());
        this.inventory.setItem(12,setIconButton());
        this.inventory.setItem(14,setPurviewButton());
        this.inventory.setItem(16,deleteButton());
        this.inventory.setItem(26,returnButton());
    }
    void setIcon(String material) {
        unit.deleteUnit();
        unit.icon = material;
        unit.addUnit();
    }
    @Override @Nonnull
    public Inventory getInventory() {
        return inventory;
    }
    private ItemStack setPurviewButton() {
        ItemStack item = new ItemStack(DARK_OAK_DOOR);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setPurview");
        meta.setDisplayName(ChatColor.RESET+"設定可見範圍");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET+"• "+ChatColor.GOLD+unit.purview);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack setIconButton() {
        ItemStack item = new ItemStack(PAINTING);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setIcon");
        meta.setDisplayName(ChatColor.RESET+"設定圖案");
        meta.setLore(Arrays.asList(
                ChatColor.RESET+"• "+ChatColor.GOLD+unit.icon,
                ChatColor.RESET+"【左鍵】輪替圖案",
                ChatColor.RESET+"【右鍵】進入圖案介面"
                ));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack deleteButton() {
        ItemStack item = new ItemStack(MUSIC_DISC_11);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.deleteUnit");
        meta.setDisplayName(ChatColor.RESET+"移除傳送點");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack setNameButton() {
        ItemStack item = new ItemStack(NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.setName");
        meta.setDisplayName(ChatColor.RESET+"設定名稱");
        item.setItemMeta(meta);
        return item;
    }
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        switch (ID) {
            case "deleteUnit":
                if (press.isLeftClick()) {
                    SpaceUnit.deleteUnit(player,unit.displayName);
                    player.closeInventory();
                    player.openInventory(new UnitsPage(player).getInventory());
                }
                break;
            case "setName":
                if (press.isLeftClick()) {
                    event.setCancelled(true);
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "輸入節點名稱:");
                    player.setMetadata("inputText", new FixedMetadataValue(BasiCore.getPlugin(), "unitName"));
                    player.setMetadata("data", new FixedMetadataValue(BasiCore.getPlugin(), unit));
                }
                player.closeInventory();
                break;
            case "setPurview":
                if (press.isLeftClick()) {
                    unit.deleteUnit();
                    if (unit.purview.equals("private")) unit.purview = "public";
                    else unit.purview = "private";
                    unit.addUnit();
                    inventory.setItem(14,setPurviewButton());
                }
                break;
            case "setIcon":
                if (press.isLeftClick()) {
                    unit.deleteUnit();
                    int next = (recommendIcons().indexOf(Basics.getMaterialFromName(unit.icon))+1)%6;
                    unit.icon = recommendIcons().get(next).toString();
                    unit.addUnit();
                    inventory.setItem(12,setIconButton());
                }
                else if (press.isRightClick()) {
                    player.openInventory(new IconsPage(player,SpaceUnit.query(unit.displayName,player)).getInventory());
                }
                break;
            case "return":
                if (press.isLeftClick()) {
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
