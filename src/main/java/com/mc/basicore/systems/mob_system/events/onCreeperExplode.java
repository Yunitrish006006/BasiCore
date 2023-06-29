package com.mc.basicore.systems.mob_system.events;

import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class onCreeperExplode implements Listener {
    @EventHandler
    public void explode(EntityExplodeEvent event) {
        if (!event.getEntity().getType().equals(EntityType.CREEPER)) return;
        event.setCancelled(true);
    }
    void explodeOld(Location location,double range) {
        List<Entity> nearbyEntities = new ArrayList<>(Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, range, range, range));
        for (Entity nearbyEntity : nearbyEntities) {
            Location ori = nearbyEntity.getLocation();
            Vector vector = new Vector();
            vector.setX(range - (ori.getX() - location.getX()) );
            vector.setY(range - (ori.getY() - location.getY()) );
            vector.setZ(range - (ori.getZ() - location.getZ()) );
            nearbyEntity.setVelocity(vector.multiply(0.1));
        }
    }
    @EventHandler
    public void reduceC(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Creeper && event.getEntity() instanceof Item) event.setCancelled(true);
    }
}