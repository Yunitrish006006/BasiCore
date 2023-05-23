package com.mc.basicore.chat_system;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onPlayerChat implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ChatSet chatSet = new ChatSet(player);
        player.setDisplayName(chatSet.NameColor + chatSet.CustomName);
        event.setFormat("%s"+ChatColor.WHITE+" | "+chatSet.ContentColor+"%s");
    }
}
