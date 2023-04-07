package com.mc.basicore.discord;

import com.mc.basicore.BasiCore;
import com.mc.basicore.chat_system.ChatSet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onPlayerChatDiscord implements Listener {
    private String webhook_url = "https://discord.com/api/webhooks/1093507373815050270/YAS9bYfQUNThC3igxGF30-YyVFJebL8j5_JdfV0E4oA1RqAyT0-NAH7FZQghyWtbUD-2";
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();
        ChatSet set = new ChatSet(player);
        DiscordWebhook webhook = new DiscordWebhook(webhook_url);
//        message = message.replaceAll("@everyone","`@everyone`").replaceAll("@here","`@here`");
        webhook.setContent("```"+set.CustomName+" >>> "+message+"```");
        try {
            webhook.execute();
        }
        catch (java.io.IOException e){
            BasiCore.getPlugin().getLogger().severe(e.getStackTrace().toString());
        }
    }
}
