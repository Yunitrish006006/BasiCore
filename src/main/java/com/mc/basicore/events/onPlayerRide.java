package com.mc.basicore.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class onPlayerRide implements Listener {
    @EventHandler
    public static void rideBee(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.BEE) {
            event.getRightClicked().addPassenger(event.getPlayer());
        }
    }
    @EventHandler
    public static void axolotlRide(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.AXOLOTL && event.getHand() == EquipmentSlot.HAND) {
            if (event.getPlayer().getPassengers().size() > 0) {
                event.getPlayer().removePassenger(event.getPlayer().getPassengers().get(0));
            }
            else {
                event.getPlayer().addPassenger(event.getRightClicked());
            }
            event.setCancelled(true);
        }
    }
}
