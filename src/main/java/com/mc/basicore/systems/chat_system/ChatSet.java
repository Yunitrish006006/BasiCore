package com.mc.basicore.systems.chat_system;

import com.mc.basicore.Basics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
@SuppressWarnings("ConstantConditions")
public class ChatSet {
    public static FileConfiguration config = Basics.config;
    public ChatColor NameColor = ChatColor.WHITE;
    public ChatColor ContentColor = ChatColor.WHITE;
    public String CustomName = "None";
    public String discordName = "None";
    public UUID playerUUID = Basics.errorID();
    public Player self = null;

    public ChatSet(Player player) {
        playerUUID = player.getUniqueId();
        self = player;
        if (!config.getKeys(true).contains(player.getUniqueId().toString())){
            config.set(player.getUniqueId()+".type","player");
            CustomName = player.getName();
            saveChatSet();
        }
        ConfigurationSection section = config.getConfigurationSection(player.getUniqueId().toString());
        if (section.getKeys(true).contains("ChatSets")) {
            CustomName = section.getString(".ChatSets.CustomName");
            NameColor = ChatColor.valueOf(section.getString(".ChatSets.NameColor"));
            ContentColor = ChatColor.valueOf(section.getString(".ChatSets.ContentColor"));
            discordName = section.getString(".ChatSets.DiscordName");
        }
    }
    public ChatSet() {}
    public static ChatSet DC(String name) {
        ChatSet chatSet = new ChatSet();
        chatSet.NameColor = ChatColor.BLUE;
        chatSet.CustomName = "[Discord] "+ChatColor.GOLD+name+ChatColor.WHITE;
        return chatSet;
    }
    public static ChatSet query(UUID uuid){
        ChatSet chatSet = new ChatSet();
        chatSet.CustomName = "Unknown Error!";
        if (!Basics.UUIDS().contains(uuid.toString())) return chatSet;
        ConfigurationSection section = config.getConfigurationSection(uuid.toString());
        chatSet.CustomName = section.getString(".ChatSets.CustomName");
        chatSet.discordName = section.getString(".ChatSets.DiscordName")==null ? "None":section.getString(".ChatSets.DiscordName");
        chatSet.NameColor = ChatColor.valueOf(section.getString(".ChatSets.NameColor"));
        chatSet.ContentColor = ChatColor.valueOf(section.getString(".ChatSets.ContentColor"));
        return chatSet;
    }
    public String getName() {
        return NameColor+CustomName+ChatColor.WHITE;
    }
    public void update() {
        self.setDisplayName(getName());
        self.setCustomName(getName());
        self.setPlayerListName(getName());
    }
    public static void chatInit() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player:players) {
            ChatSet chatSet = new ChatSet(player);
            player.setDisplayName(chatSet.NameColor+chatSet.CustomName);
            player.setCustomName(chatSet.NameColor+chatSet.CustomName);
            player.setPlayerListName(chatSet.NameColor+chatSet.CustomName);
            player.setCustomNameVisible(false);
        }
    }
    public void saveChatSet() {
        String prefix = playerUUID+".ChatSets.";
        config.set(prefix+"CustomName",CustomName);
        config.set(prefix+"ContentColor",ContentColor.name());
        config.set(prefix+"NameColor",NameColor.name());
        config.set(prefix+"DiscordName",discordName);
        Basics.saveFile();
    }
    public void resetChat() {
        CustomName = Bukkit.getPlayer(playerUUID).getName();
        ContentColor = ChatColor.WHITE;
        NameColor = ChatColor.WHITE;
        discordName = "None";
        saveChatSet();
    }
    public void setContentColor(ChatColor color) {
        this.ContentColor = color;
    }
    public void setContentColor(String color){
        switch (color.toLowerCase(Locale.ROOT)){
            case "aqua":
                this.setContentColor(ChatColor.AQUA);
                break;
            case "black":
                this.setContentColor(ChatColor.BLACK);
                break;
            case "blue":
                this.setContentColor(ChatColor.BLUE);
                break;
            case "bold":
                this.setContentColor(ChatColor.BOLD);
                break;
            case "dark_aqua":
                this.setContentColor(ChatColor.DARK_AQUA);
                break;
            case "dark_blue":
                this.setContentColor(ChatColor.DARK_BLUE);
                break;
            case "dark_gray":
                this.setContentColor(ChatColor.DARK_GRAY);
                break;
            case "dark_green":
                this.setContentColor(ChatColor.DARK_GREEN);
                break;
            case "dark_purple":
                this.setContentColor(ChatColor.DARK_PURPLE);
                break;
            case "dark_red":
                this.setContentColor(ChatColor.DARK_RED);
                break;
            case "gold":
                this.setContentColor(ChatColor.GOLD);
                break;
            case "gray":
                this.setContentColor(ChatColor.GRAY);
                break;
            case "green":
                this.setContentColor(ChatColor.GREEN);
                break;
            case "italic":
                this.setContentColor(ChatColor.ITALIC);
                break;
            case "light_purple":
                this.setContentColor(ChatColor.LIGHT_PURPLE);
                break;
            case "magic":
                this.setContentColor(ChatColor.MAGIC);
                break;
            case "red":
                this.setContentColor(ChatColor.RED);
                break;
            case "reset":
                this.setContentColor(ChatColor.RESET);
                break;
            case "strikethrough":
                this.setContentColor(ChatColor.STRIKETHROUGH);
                break;
            case "underline":
                this.setContentColor(ChatColor.UNDERLINE);
                break;
            case "white":
                this.setContentColor(ChatColor.WHITE);
                break;
            case "yellow":
                this.setContentColor(ChatColor.YELLOW);
                break;
            default:
                break;
        }
    }
    public void setNameColor(ChatColor color) {
        this.NameColor = color;
    }
    public void setNameColor(String color){
        switch (color.toLowerCase(Locale.ROOT)){
            case "aqua":
                this.setNameColor(ChatColor.AQUA);
                break;
            case "black":
                this.setNameColor(ChatColor.BLACK);
                break;
            case "blue":
                this.setNameColor(ChatColor.BLUE);
                break;
            case "bold":
                this.setNameColor(ChatColor.BOLD);
                break;
            case "dark_aqua":
                this.setNameColor(ChatColor.DARK_AQUA);
                break;
            case "dark_blue":
                this.setNameColor(ChatColor.DARK_BLUE);
                break;
            case "dark_gray":
                this.setNameColor(ChatColor.DARK_GRAY);
                break;
            case "dark_green":
                this.setNameColor(ChatColor.DARK_GREEN);
                break;
            case "dark_purple":
                this.setNameColor(ChatColor.DARK_PURPLE);
                break;
            case "dark_red":
                this.setNameColor(ChatColor.DARK_RED);
                break;
            case "gold":
                this.setNameColor(ChatColor.GOLD);
                break;
            case "gray":
                this.setNameColor(ChatColor.GRAY);
                break;
            case "green":
                this.setNameColor(ChatColor.GREEN);
                break;
            case "italic":
                this.setNameColor(ChatColor.ITALIC);
                break;
            case "light_purple":
                this.setNameColor(ChatColor.LIGHT_PURPLE);
                break;
            case "magic":
                this.setNameColor(ChatColor.MAGIC);
                break;
            case "red":
                this.setNameColor(ChatColor.RED);
                break;
            case "reset":
                this.setNameColor(ChatColor.RESET);
                break;
            case "strikethrough":
                this.setNameColor(ChatColor.STRIKETHROUGH);
                break;
            case "underline":
                this.setNameColor(ChatColor.UNDERLINE);
                break;
            case "white":
                this.setNameColor(ChatColor.WHITE);
                break;
            case "yellow":
                this.setNameColor(ChatColor.YELLOW);
                break;
            default:
                break;
        }
    }
    public void setCustomName(String name) {
        this.CustomName = name;
    }
}
