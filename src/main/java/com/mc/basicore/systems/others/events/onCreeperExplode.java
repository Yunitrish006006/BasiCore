package com.mc.basicore.systems.others.events;

import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class onCreeperExplode implements Listener {
    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent event) {
        if(event.getEntity() instanceof Creeper) {
            Creeper creeper = (Creeper) event.getEntity();
            Location c_location = creeper.getLocation();
            double range = 4.0;
            List<Entity> nearbyEntities = new ArrayList<>(Objects.requireNonNull(c_location.getWorld()).getNearbyEntities(c_location, range, range, range));
            for (Entity nearbyEntity : nearbyEntities) {
                Location ori = nearbyEntity.getLocation();
                Vector vector = new Vector();
                vector.setX(range - (ori.getX() - c_location.getX()) );
                vector.setY(range - (ori.getY() - c_location.getY()) );
                vector.setZ(range - (ori.getZ() - c_location.getZ()) );
                nearbyEntity.setVelocity(vector.multiply(0.1));
            }
            event.setCancelled(true);
        }
    }
}