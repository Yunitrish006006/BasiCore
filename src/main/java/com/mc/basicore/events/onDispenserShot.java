package com.mc.basicore.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class onDispenserShot implements Listener {
    @EventHandler
    public void onShot(BlockDispenseEvent event) {
        DTechMinor(event);
    }
    private void DTechMinor(BlockDispenseEvent event) {
        if (!(event.getBlock().getState() instanceof Dispenser)) {return;}
        Dispenser dispenser = (Dispenser) event.getBlock().getState();
        Location block_to_destroy = dispenser.getLocation().add(((Directional) dispenser.getLocation().getBlock().getBlockData()).getFacing().getDirection());
        Inventory inventory = dispenser.getInventory();
        int space = 4;
        if (inventory.getItem(space)!=null && inventory.getItem(space).getType().getMaxDurability() > 0) {
            ItemStack tool = inventory.getItem(space);
            if (tool != null && tool.getType().getMaxDurability() > 0) {
                if (tool.getItemMeta() instanceof Damageable) {
                    event.setCancelled(true);
                    Damageable tool_meta = (Damageable) tool.getItemMeta();
                    tool_meta.setDamage(tool_meta.getDamage()+1);
                    tool.setItemMeta(tool_meta);
                    inventory.setItem(space,tool);
                    block_to_destroy.getBlock().breakNaturally(tool);
                }
            }
        }
    }
}
