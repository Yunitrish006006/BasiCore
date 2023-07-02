package com.mc.basicore.systems.MobSystem.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ZombieVariety  implements Listener {
    @EventHandler
    public void onSkeletonSpawn(EntitySpawnEvent event) {
        if (!event.getEntity().getType().equals(EntityType.ZOMBIE)) return;
        Random random = new Random();
        if (random.nextInt(10) > 1 ) return;
        Location location = event.getLocation();
        assert location.getWorld() != null;
        ZombieHorse horse = (ZombieHorse) location.getWorld().spawnEntity(event.getLocation(),EntityType.ZOMBIE_HORSE,true);
        Zombie mob = (Zombie) event.getEntity();
        AttributeModifier maxHealthModifier = new AttributeModifier("double_health",1.6, AttributeModifier.Operation.MULTIPLY_SCALAR_1);
        AttributeInstance maxHealth = horse.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        assert maxHealth != null;
        maxHealth.addModifier(maxHealthModifier);
        horse.addPassenger(mob);
        assert mob.getEquipment() != null;
        mob.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
    }
}
