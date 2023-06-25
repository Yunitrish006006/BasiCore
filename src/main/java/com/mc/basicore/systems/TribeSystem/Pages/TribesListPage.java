package com.mc.basicore.systems.TribeSystem.Pages;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.TribeSystem.Tribe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.mc.basicore.systems.translate.Translator.translate;
import static com.mc.basicore.systems.world_index.WorldIndex.returnButton;
import static org.bukkit.Material.RED_BANNER;

public class TribesListPage implements InventoryHolder {
    private final Inventory inventory;
    Player player;
    public TribesListPage (Player call) {
        player = call;
        inventory = Bukkit.createInventory(this,9*4,translate(player));
        inventory.setItem(28,addTribeButton());
        inventory.setItem(27,returnButton(player.getLocale()));
        for (Tribe t:Tribe.List()) {
            inventory.addItem(TribeButton(t));
        }
    }
    public ItemStack TribeButton(Tribe tribe) {
        ItemStack item = new ItemStack(Basics.getMaterialFromName(tribe.icon.toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.tribe."+tribe.name);
        meta.setDisplayName(ChatColor.RESET+tribe.name);
        List<String> lore = new ArrayList<>();
        lore.add(translate(player,"GUI.dot","GUI.left_click","GUI.apply"));
        lore.add(translate(player, "GUI.dot","GUI.right_click","GUI.quit"));
        lore.addAll(tribe.tribeData());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack addTribeButton() {
        ItemStack item = new ItemStack(RED_BANNER);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.addTribe");
        meta.setDisplayName(translate(player,"GUI.add","GUI.tribe"));
        item.setItemMeta(meta);
        return item;
    }
    @NotNull @Override
    public Inventory getInventory() {
        return inventory;
    }
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        switch (ID) {
            case "tribe": {
                switch (press) {
                    case LEFT:
                        Tribe.Query(event.getCurrentItem().getItemMeta().getLocalizedName().split("\\.")[3]).apply(player);
                        break;
                    case RIGHT:
                        Tribe.Query(event.getCurrentItem().getItemMeta().getLocalizedName().split("\\.")[3]).quit(player);
                        break;
                    default:
                        break;
                }
                break;
            }
            case "addTribe": {
                switch (press) {
                    case LEFT:
                        List<Tribe> tribes = Tribe.List();
                        int count = 0;
                        for (Tribe tribe:tribes) {
                            if (tribe.owner == null) continue;
                            if (tribe.owner.equals(player.getUniqueId())) {
                                count+=1;
                            }
                        }
                        if (count == 0) event.getInventory().addItem(TribeButton(Tribe.create(player)));
                        break;
                    default:
                        break;
                }
                break;
            }
            default:
                break;
        }
        event.setCancelled(true);
    }
}
