package com.mc.basicore.systems.SkillSystem;

import com.mc.basicore.Basics;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class fireBall implements Listener {
    @EventHandler
    public void onCalled(PlayerInteractEvent event) {
        assert event.getItem() != null;
        if (!event.hasItem()) return;
        if (!Basics.getID(event.getItem()).equals("minecraft.fire_charge")) return;
        if (event.hasBlock()) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        Fireball fireball = event.getPlayer().launchProjectile(Fireball.class);
        fireball.setVelocity(event.getPlayer().getLocation().getDirection().multiply(4));
        fireball.setShooter(event.getPlayer());
        Basics.playSound(event.getPlayer().getLocation(), Sound.ENTITY_BLAZE_SHOOT);
        if (event.getItem().getAmount() == 1) event.getItem().setAmount(0);
        else event.getItem().setAmount(event.getItem().getAmount()-1);
    }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Fireball)) return;
        Fireball fireball = (Fireball) event.getEntity();
        if (!(fireball.getShooter() instanceof Player || fireball.getShooter() instanceof Arrow))  return;
        event.setCancelled(true);
        Player player = (Player) fireball.getShooter();
        fireball.getNearbyEntities(1.6,1,1.6).forEach(entity -> ((Damageable) entity).damage(2.0));
        fireball.getWorld().createExplosion(fireball.getLocation(), 0.01f);
        fireball.remove();
    }
}
