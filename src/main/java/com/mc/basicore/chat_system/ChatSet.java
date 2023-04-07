package com.mc.basicore.chat_system;

import com.mc.basicore.BasiCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;
@SuppressWarnings("ConstantConditions")
public class ChatSet implements Serializable {

    private static File file;
    private static final String fileName = "PlayerData.yml";
    private static FileConfiguration config;
    private static void setFile() {
        file = new File(BasiCore.getRootFolder(),fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Bukkit.getServer().getConsoleSender().sendMessage("creating files: ");
                    Bukkit.getServer().getConsoleSender().sendMessage(BasiCore.getRootFolder(),fileName);
                }
                else {
                    Bukkit.getServer().getConsoleSender().sendMessage("initializing files");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }
    private static void saveFile() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void reloadFile() {
        config = YamlConfiguration.loadConfiguration(file);
    }
    public static void initFile() {
        setFile();
        saveFile();
    }

    public ChatColor NameColor = ChatColor.WHITE;
    public ChatColor ContentColor = ChatColor.WHITE;
    public String CustomName = "None";
    public UUID playerUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public ChatSet(Player player) {
        playerUUID = player.getUniqueId();
        if (!config.getKeys(true).contains(player.getUniqueId().toString())){
            config.set(player.getUniqueId()+".type","player");
            CustomName = player.getDisplayName();
            saveFile();
        }
        ConfigurationSection section = config.getConfigurationSection(player.getUniqueId().toString());
        if (section.getKeys(true).contains("ChatSets")) {
            CustomName = section.getString(".ChatSets.CustomName");
            NameColor = ChatColor.valueOf(section.getString(".ChatSets.NameColor"));
            ContentColor = ChatColor.valueOf(section.getString(".ChatSets.ContentColor"));
        }
    }
    public void setChatSet () {
        String prefix = playerUUID+".ChatSets.";
        config.set(prefix+"CustomName",CustomName);
        config.set(prefix+"ContentColor",ContentColor.name());
        config.set(prefix+"NameColor",NameColor.name());
        saveFile();
    }
    public void reset() {
        CustomName = Bukkit.getPlayer(playerUUID).getName();
        ContentColor = ChatColor.WHITE;
        NameColor = ChatColor.WHITE;
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
