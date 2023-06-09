package com.mc.basicore.systems.others.events;

import com.mc.basicore.Basics;
import com.mc.basicore.itemGroups;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

public class onDispenserShot implements Listener {
    @EventHandler
    public void onShot(BlockDispenseEvent event) {
        DTechMinor(event);
    }
    private void DTechMinor(BlockDispenseEvent event) {
        if (!(event.getBlock().getState() instanceof Dispenser)) return;
        event.setCancelled(true);
        Dispenser dispenser = (Dispenser) event.getBlock().getState();
        if (!itemGroups.tools().contains(event.getItem().getType())) return;
        ItemStack tool = event.getItem();
        BlockFace facing = ((Directional) dispenser.getLocation().getBlock().getBlockData()).getFacing();
        Location target = dispenser.getLocation().add(facing.getDirection());
        if (target.getBlock().getType().equals(Material.AIR)) {
            event.setCancelled(true);
        }
        else {
            Basics.useItem(tool,1);
            target.getBlock().breakNaturally(tool);
            Basics.playSound(target, Sound.BLOCK_STONE_BREAK);
        }
    }
}
