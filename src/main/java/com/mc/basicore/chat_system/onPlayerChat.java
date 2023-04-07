package com.mc.basicore.chat_system;

import com.mc.basicore.BasiCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onPlayerChat implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ChatSet chatSet = new ChatSet(player);
        String originalName = player.getDisplayName();
        player.setDisplayName(chatSet.NameColor + chatSet.CustomName);
        event.setFormat("%s"+ChatColor.WHITE+" | "+chatSet.ContentColor+"%s");
        Bukkit.getScheduler().runTaskLater(BasiCore.getPlugin(), () -> player.setDisplayName(originalName), 20L);
    }
}
