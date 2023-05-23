package com.mc.basicore.events;

import com.mc.basicore.itemGroups;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;


public class onShovelOnGravel implements Listener {
    @EventHandler
    public static void shovelOnGravel(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(event.getClickedBlock().getType().equals(Material.GRAVEL)
        && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
        && event.getItem() != null
        && itemGroups.shovels().contains(event.getItem().getType())
        ) {
            event.getClickedBlock().setType(Material.SAND);
            event.getPlayer().getInventory().addItem(new ItemStack(Material.FLINT));
            event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS,1.0f,2.2f);
            if(event.getItem() instanceof Damageable) {
                ((Damageable) event.getItem()).setDamage(((Damageable) event.getItem()).getDamage()-1);
            }
        }
    }
}
