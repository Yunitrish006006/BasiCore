package com.mc.basicore.systems.MobSystem;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.stream.Collectors;

public class MobSystemEvents implements Listener {

    @EventHandler
    public static void onLiftOrRide(PlayerInteractAtEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (MobSystem.mobSet.data.getStringList("canLift").stream().map(entity -> EntityType.valueOf(entity.toUpperCase())).collect(Collectors.toList()).contains(event.getRightClicked().getType())) {
            if (!event.getPlayer().isSneaking()) return;
            if (event.getPlayer().getPassengers().size() > 0) {
                event.getPlayer().removePassenger(event.getPlayer().getPassengers().get(0));
            } else {
                event.getPlayer().addPassenger(event.getRightClicked());
            }
            event.setCancelled(true);
        }
        else if (MobSystem.mobSet.data.getStringList("canRide").stream().map(entity -> EntityType.valueOf(entity.toUpperCase())).collect(Collectors.toList()).contains(event.getRightClicked().getType())) {
            event.getRightClicked().addPassenger(event.getPlayer());
            event.setCancelled(true);
        }
    }
}
