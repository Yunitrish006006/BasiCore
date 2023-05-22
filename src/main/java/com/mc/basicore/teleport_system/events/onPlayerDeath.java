package com.mc.basicore.teleport_system.events;

import com.mc.basicore.teleport_system.SpaceUnit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onPlayerDeath implements Listener {
    @EventHandler
    public static void addDeathPoint(PlayerDeathEvent event) {
        SpaceUnit spaceUnit = new SpaceUnit("DeathPoint of "+event.getDeathMessage(),event.getEntity());
        spaceUnit.addUnit();
    }
}
