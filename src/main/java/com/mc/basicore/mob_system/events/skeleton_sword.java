package com.mc.basicore.mob_system.events;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Random;

public class skeleton_sword implements Listener {
    @EventHandler
    public void onSkeletonSpawn(EntitySpawnEvent event) {
        if (!event.getEntity().getType().equals(EntityType.SKELETON)) return;
        Random random = new Random();
        if (random.nextInt(10) > 3 ) return;
        Skeleton mob = (Skeleton) event.getEntity();
        mob.setCanPickupItems(true);
        mob.setRemoveWhenFarAway(false);
        if (mob.getEquipment()==null) return;
        mob.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        mob.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
        Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(32);
        Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).getBaseValue()*1.1);
    }
}
