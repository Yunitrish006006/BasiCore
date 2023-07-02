package com.mc.basicore.systems.SkillSystem;

import com.mc.basicore.Basics;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class arrow implements Listener {
    @EventHandler
    public void onCalled(PlayerInteractEvent event) {
        assert event.getItem() != null;
        if (!event.hasItem()) return;
        if (!Basics.getID(event.getItem()).equals("minecraft.arrow")) return;
        if (event.hasBlock()) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        Arrow arrow = event.getPlayer().launchProjectile(Arrow.class);
        arrow.setVelocity(event.getPlayer().getLocation().getDirection().multiply(4));
        arrow.setShooter(event.getPlayer());
        Basics.playSound(event.getPlayer().getLocation(), Sound.ENTITY_ARROW_SHOOT);
        if (event.getItem().getAmount() == 1) event.getItem().setAmount(0);
        else event.getItem().setAmount(event.getItem().getAmount()-1);
    }
//    @EventHandler
//    public void onProjectileHit(ProjectileHitEvent event) {
//        if (!(event.getEntity() instanceof Fireball)) return;
//        Fireball fireball = (Fireball) event.getEntity();
//        if (!(fireball.getShooter() instanceof Player))  return;
//        Player player = (Player) fireball.getShooter();
//        fireball.getWorld().createExplosion(fireball.getLocation(), 0.2f);
//    }
}
