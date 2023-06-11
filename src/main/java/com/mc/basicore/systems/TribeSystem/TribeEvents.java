package com.mc.basicore.systems.TribeSystem;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.chat_system.ChatSet;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

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
    @EventHandler
    public void LockeSet(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
        if (!(event.getItem() != null && event.hasBlock())) return;
        if (!Basics.getID(event.getItem()).equals("BasiCore.WorldIndex")) return;
        Block target = event.getClickedBlock();
        Player player = event.getPlayer();
        assert target != null;
        if (target.hasMetadata("purview") && target.hasMetadata("owner")) {
            Player owner = Bukkit.getPlayer(UUID.fromString(target.getMetadata("owner").get(0).asString()));
            if (player.equals(owner) && target.getMetadata("purview").get(0).asString().equals("private")) {
                target.removeMetadata("purview",BasiCore.getPlugin());
                target.setMetadata("purview",new FixedMetadataValue(BasiCore.getPlugin(), "public"));
            }
        }
        else {
            target.setMetadata("owner",new FixedMetadataValue(BasiCore.getPlugin(), player.getUniqueId().toString()));
            target.setMetadata("purview",new FixedMetadataValue(BasiCore.getPlugin(), "private"));
        }
    }
    @EventHandler
    public void LockReset(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!(block.hasMetadata("purview") && block.hasMetadata("owner"))) return;
        Player breaker = event.getPlayer();
        Player owner = Bukkit.getPlayer(UUID.fromString(block.getMetadata("owner").get(0).asString()));
        if (breaker.equals(owner)) {
            block.removeMetadata("owner",BasiCore.getPlugin());
            block.removeMetadata("purview",BasiCore.getPlugin());
        }
        else {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void LockTriggered(PlayerInteractEvent event) {
        if (!event.hasBlock()) return;
        Block target = event.getClickedBlock();
        Player player = event.getPlayer();
        assert target != null;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!(target.hasMetadata("purview") && target.hasMetadata("owner"))) return;
        Player owner = Bukkit.getPlayer(UUID.fromString(target.getMetadata("owner").get(0).asString()));
        assert owner != null;
        switch (target.getMetadata("purview").get(0).asString()){
            case "private":
                if (!player.equals(owner)) {
                    player.sendMessage("此箱子屬於"+ChatSet.query(owner.getUniqueId()).getName()+"私人所有");
                    event.setCancelled(true);
                }
                return;
            case "tribe":
                boolean open = true;
                for (Tribe t:Tribe.List()) {
                    if (t.members.contains(player) && t.members.contains(owner)) {
                        open = false;
                        break;
                    }
                }
                event.setCancelled(open);
                return;
            case "public":
                return;
            default:
                Bukkit.broadcastMessage("error for value: "+target.getMetadata("purview").get(0).asString());
        }
    }
}
