package com.mc.basicore.systems.CreatureSystem.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class onVillagerLeashed implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.LEAD)) return;
        if (!(event.getRightClicked() instanceof Villager)) return;
        event.setCancelled(true);
        Villager villager = (Villager) event.getRightClicked();
        villager.setLeashHolder(player);
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
    }
}
