package com.mc.basicore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Basics {
    public static File file;
    public static final String fileName = "PlayerData.yml";
    public static FileConfiguration config;
    public static void setFile() {
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
    public static void saveFile() {
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
    public static List<String> getPlayerList() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        List<String> names = new ArrayList<>();
        for (Player p : players) {
            names.add(p.getName());
        }
        return names;
    }
    public static boolean inBLockTypes(List<Material> blocks, Material target) {
        return blocks.contains(target);
    }
}
