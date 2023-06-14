package com.mc.basicore.systems.TribeSystem;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TribeEvents implements Listener {
    @EventHandler
    public void teamDamage(EntityDamageByEntityEvent event) {
        if (!((event.getDamager() instanceof Player || event.getDamager() instanceof Arrow) && event.getEntity() instanceof Player)) return;
        Player target = (Player) event.getEntity();
        if (event.getDamager() instanceof Player) {
            Player source = (Player) event.getDamager();
            for (Tribe t:Tribe.List()) {
                if (t.members.contains(source) && t.members.contains(target)) {
                    double damage = event.getFinalDamage()/4;
                    if (target.getHealth() > damage) event.setDamage(damage);
                    else event.setCancelled(true);
                }
            }
        }
        else{
            Arrow source = (Arrow) event.getDamager();
            if (!(source.getShooter() instanceof Player)) return;
            Player arrow_owner = (Player) source.getShooter();
            for (Tribe t:Tribe.List()) {
                if (t.members.contains(arrow_owner) && t.members.contains(target)) {
                    double damage = event.getFinalDamage()/4;
                    if (target.getHealth() > damage) event.setDamage(damage);
                    else event.setCancelled(true);
                }
            }
        }
    }
}
