package com.mc.basicore;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class skill_system implements Listener {
    @EventHandler
    public void swordDash(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (!event.getItem().getType().equals(Material.IRON_SWORD)) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        if (event.getPlayer().getPortalCooldown()>0) return;
        Vector vector = event.getPlayer().getFacing().getDirection();
        vector.setX(vector.getX()*-1.2);
        vector.setY(0.3);
        vector.setZ(vector.getZ()*-1.2);
        event.getPlayer().setVelocity(vector);
        event.getPlayer().setPortalCooldown(30);
    }
}
