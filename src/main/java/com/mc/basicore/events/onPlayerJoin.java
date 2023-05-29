package com.mc.basicore.events;

import com.mc.basicore.BasiCore;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class onPlayerJoin implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("teleport_name_input")) {
            player.setMetadata("teleport_name_input", new FixedMetadataValue(BasiCore.getPlugin(), null));
        }
        Bukkit.getScheduler().runTaskLater(BasiCore.getPlugin(), () -> {
            player.playSound(player, Sound.BLOCK_DEEPSLATE_BRICKS_BREAK,1.0f,1.0f);
            player.playSound(player, "basicore.welcome_voice",1.0f,1.0f);
        },3*20L);
    }
}
