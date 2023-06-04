package com.mc.basicore.systems.collector_system;

import com.mc.basicore.Basics;
import com.mc.basicore.itemGroups;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class onShovelOnGravel implements Listener {
    @EventHandler
    public static void shovelOnGravel(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(!event.getClickedBlock().getType().equals(Material.GRAVEL)) return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(event.getItem() != null && itemGroups.shovels().contains(event.getItem().getType())) {
            event.getClickedBlock().setType(Material.SAND);
            event.getPlayer().getInventory().addItem(new ItemStack(Material.FLINT));
            event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS,1.0f,2.2f);
            Basics.useItem(event.getItem(),1);
        }
    }
}
