package com.mc.basicore.systems.EconomySystem;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.ChatSystem.ChatSet;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class VillagerEvents implements Listener {
    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (!event.getEntity().getType().equals(EntityType.VILLAGER)) return;
        Villager villager = (Villager) event.getEntity();
        villager.setCustomName(Basics.getRandomName(5));
        villager.setCustomNameVisible(true);
    }
    @EventHandler
    public void onClaim(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER)) return;
        if (!event.getEntity().getType().equals(EntityType.VILLAGER)) return;
        Player player = (Player) event.getDamager();
        if (!Basics.getID(player.getInventory().getItemInMainHand()).equals("BasiCore.WorldIndex")) return;
        Villager villager = (Villager) event.getEntity();
        villager.setMetadata("Owner",new FixedMetadataValue(BasiCore.getPlugin(),player.getUniqueId().toString()));
        ChatSet name = ChatSet.query(UUID.fromString(villager.getMetadata("Owner").get(0).asString()));
        player.sendMessage(villager.getCustomName()+"的合作夥伴是"+name.getName());
        OnlineStorePage.villagerFileSet.data.set(villager.getUniqueId().toString(),player.getUniqueId().toString());
        OnlineStorePage.villagerFileSet.save();
        event.setCancelled(true);
    }
}
