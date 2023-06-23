package com.mc.basicore.systems.chat_system;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.translate.Translator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ChatEvents implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ChatSet chatSet = new ChatSet(player);
        player.setDisplayName(chatSet.NameColor + chatSet.CustomName);
        event.setFormat("%s"+ChatColor.WHITE+" | "+chatSet.ContentColor+"%s");
        if (!event.isCancelled()) discord.sendToDiscord("【"+chatSet.CustomName+"】"+event.getMessage());
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        assert event.getDeathMessage() != null;
        discord.sendToDiscord(
            "【"+ChatSet.query(event.getEntity().getUniqueId()).CustomName+"】"+
            Translator.transPure(event.getEntity().getLocale(),"DeathMessage."+ event.getDeathMessage().replaceFirst(event.getEntity().getName()+" ",""))
        );
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        event.setJoinMessage(null);
        Basics.later(20,() -> {
            Player player = Bukkit.getPlayer(id) == null ? event.getPlayer() : Bukkit.getPlayer(id);
            assert player != null;
            Bukkit.broadcastMessage(ChatSet.query(id).CustomName+Translator.translate(player.getLocale(),"JoinMessage"));
            discord.sendToDiscord("【"+ChatSet.query(id).CustomName+"】"+ Translator.transPure(player.getLocale(),"JoinMessage"));
        });
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        event.setQuitMessage(ChatSet.query(id).CustomName+Translator.translate(event.getPlayer().getLocale(),"QuitMessage"));
        discord.sendToDiscord("【"+ChatSet.query(id).CustomName+"】"+ Translator.transPure(event.getPlayer().getLocale(),"QuitMessage"));
    }
}
