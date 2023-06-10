package com.mc.basicore.systems.world_index.GUI;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.TribeSystem.Tribe;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import static com.mc.basicore.systems.translate.Translator.translate;
import static com.mc.basicore.systems.world_index.WorldIndex.returnButton;

public class TribesListPage implements InventoryHolder {
    private final Inventory inventory;
    Player player;
    public TribesListPage (Player call) {
        player = call;
        inventory = Bukkit.createInventory(this,9*4,translate(player));
        inventory.setItem(27,returnButton(player.getLocale()));
        for (Tribe t:Tribe.List()) {
            inventory.addItem(TribeButton(t));
        }
    }

    public ItemStack TribeButton(Tribe tribe) {
        ItemStack item = new ItemStack(Basics.getMaterialFromName(tribe.icon.toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.tribe");
        meta.setDisplayName(tribe.name);
        meta.setLore(tribe.tribeData());
        item.setItemMeta(meta);
        return item;
    }

    @NotNull @Override
    public Inventory getInventory() {
        return inventory;
    }
}
