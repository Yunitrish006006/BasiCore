package com.mc.basicore.systems.others.events;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.ChatSystem.ChatSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoin implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ChatSet.chatInit();
        if (player.hasMetadata("inputText")) {
            player.removeMetadata("inputText",BasiCore.getPlugin());
        }
        Bukkit.getScheduler().runTaskLater(BasiCore.getPlugin(), () -> {
            player.playSound(player, "basicore.entergame",1.0f,1.0f);
        },6*20L);
    }
}
