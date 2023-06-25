package com.mc.basicore.systems.ChatSystem;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.FileSystem.FileSet;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class discord extends ListenerAdapter {
    public static FileSet fileSet = new FileSet("config.yml");

    public Map<String,ChatSet> playerChatSets = new HashMap<>();

    public static List<TextChannel> textChannels = new ArrayList<>();

    public static void launchBot() {
        try {
            BasiCore.jda = JDABuilder.createDefault(
                fileSet.data.getString("discord.token"),
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.SCHEDULED_EVENTS
            )
            .addEventListeners(new discord())
            .disableCache(CacheFlag.VOICE_STATE)
            .build();
            Bukkit.broadcastMessage("Discord bot started successfully!");
        } catch (Exception e) {
            Bukkit.broadcastMessage("Failed to start Discord bot!");
            e.printStackTrace();
        }
    }
    public static void shutDownBot() {
        if (BasiCore.jda != null) {
            BasiCore.jda.shutdown();
            Bukkit.broadcastMessage("Discord bot stopped!");
        }
    }
    public static void sendToDiscord(String value) {
        for (TextChannel channel:textChannels) {
            channel.sendMessage(value).queue();
        }
    }

    public void refreshPlayerChatSets () {
        for (Player player:Bukkit.getOnlinePlayers()) {
            if (!ChatSet.query(player.getUniqueId()).discordName.equals("None")) {
                playerChatSets.put(ChatSet.query(player.getUniqueId()).discordName,ChatSet.query(player.getUniqueId()));
            }
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        for(String id: fileSet.data.getStringList("discord.channels")) {
            TextChannel channel = BasiCore.jda.getTextChannelById(id);
            if (channel != null) textChannels.add(channel);
        }
        refreshPlayerChatSets();
        Bukkit.broadcastMessage("[Discord] OuOb");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        if (!message.isFromType(ChannelType.TEXT)) return;
        TextChannel from = (TextChannel) message.getChannel();
        if (!textChannels.contains(from)) return;
        String content = message.getContentDisplay();
        if (!playerChatSets.containsKey(event.getAuthor().getName())) {
            refreshPlayerChatSets();
        }
        Bukkit.broadcastMessage(playerChatSets.getOrDefault(event.getAuthor().getName(),ChatSet.DC(event.getAuthor().getName())).getName()+" | "+ChatColor.WHITE+content);
    }
}
