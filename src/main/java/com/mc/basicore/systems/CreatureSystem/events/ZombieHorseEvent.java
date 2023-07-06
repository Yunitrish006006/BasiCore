package com.mc.basicore.systems.CreatureSystem.events;

import com.mc.basicore.Basics;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ZombieHorseEvent implements Listener {
    @EventHandler
    public void regenerate(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.ROTTEN_FLESH)) return;
        if (!(event.getRightClicked() instanceof org.bukkit.entity.ZombieHorse)) return;
        ZombieHorse zombieHorse = (ZombieHorse) event.getRightClicked();
        player.sendMessage("u feed zombie horse!");
        event.setCancelled(true);
        AttributeInstance ZombieHorseMaxHealth = zombieHorse.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        assert ZombieHorseMaxHealth != null;
        if (ZombieHorseMaxHealth.getValue() == zombieHorse.getHealth()) return;
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
        Basics.SpawnParticle(zombieHorse.getLocation(),Particle.HEART,10,0.2,0.2,0.2,0.1);
        zombieHorse.setHealth(Math.min(zombieHorse.getHealth() + 4, ZombieHorseMaxHealth.getValue()));
    }
    @EventHandler
    public void onZombieHorse(HorseJumpEvent event) {
        if (!event.getEntityType().equals(EntityType.ZOMBIE_HORSE)) return;
        ZombieHorse horse = (ZombieHorse) event.getEntity();
        if (horse.getPortalCooldown() > 0) return;
        horse.getNearbyEntities(5,5,5).forEach(entity -> entity.setVelocity(entity.getVelocity().rotateAroundY(180).multiply(3).setY(1)));
        horse.setPortalCooldown(100);
    }
    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (!(event.getEntity() instanceof ZombieHorse)) return;
        ZombieHorse zombieHorse = (ZombieHorse) event.getEntity();
        if (zombieHorse.isTamed()) return;
        zombieHorse.setFireTicks(20 * 60);
    }
}
