package com.mc.basicore.systems.CreatureSystem.events;

import com.mc.basicore.systems.CreatureSystem.CreatureSystem;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.stream.Collectors;

public class onLiftRide implements Listener {

    @EventHandler
    public static void onLiftOrRide(PlayerInteractAtEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        if (CreatureSystem.mobSet.data.getStringList("canLift").stream().map(entity -> EntityType.valueOf(entity.toUpperCase())).collect(Collectors.toList()).contains(event.getRightClicked().getType())) {
            if (!event.getPlayer().isSneaking()) return;
            if (event.getPlayer().getPassengers().isEmpty()) {
                event.getPlayer().addPassenger(event.getRightClicked());
            }
            else {
                event.getPlayer().removePassenger(event.getRightClicked());
            }
            event.setCancelled(true);
        }
        else if (CreatureSystem.mobSet.data.getStringList("canRide").stream().map(entity -> EntityType.valueOf(entity.toUpperCase())).collect(Collectors.toList()).contains(event.getRightClicked().getType())) {
            event.getRightClicked().addPassenger(event.getPlayer());
            event.setCancelled(true);
        }
        if (event.getPlayer().isOp() && event.getRightClicked().getType().equals(EntityType.PLAYER)) {
            if (event.getPlayer().isSneaking()) {
                event.getPlayer().addPassenger(event.getRightClicked());
                event.setCancelled(true);
            }
            else {
                event.getRightClicked().addPassenger(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }
}
