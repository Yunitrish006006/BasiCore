package com.mc.basicore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Math.round;

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
    public static String getID(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta != null && meta.hasLocalizedName()) {
                return meta.getLocalizedName();
            }
            else {
                return "minecraft."+itemStack.getType();
            }
        }
        else {
            return "minecraft."+itemStack.getType();
        }
    }
    public static double getDistance(Location p1,Location p2) {
        double rx = Math.abs(p1.getBlockX() - p2.getBlockX());
        double ry = Math.abs(p1.getBlockY() - p2.getBlockY());
        double rz = Math.abs(p1.getBlockZ() - p2.getBlockZ());
        return Math.sqrt((rx*rx)+(ry*ry)+(rz*rz));
    }
    public static double getFlatDistance(Location p1,Location p2) {
        double rx = Math.abs(p1.getBlockX() - p2.getBlockX());
        double rz = Math.abs(p1.getBlockZ() - p2.getBlockZ());
        return Math.sqrt((rx*rx)+(rz*rz));
    }
    public static void useItem(ItemStack itemStack,int times) {
        if (itemStack.getItemMeta() instanceof Damageable) {
            Damageable meta = (Damageable) itemStack.getItemMeta();
            int decrease = (int) Math.pow(2,meta.getEnchantLevel(Enchantment.DURABILITY));
            int cost = (int)(times/Math.pow(2,meta.getEnchantLevel(Enchantment.DURABILITY)));
            if (cost<decrease) cost=times/meta.getEnchantLevel(Enchantment.DURABILITY);
            meta.setDamage(meta.getDamage()+cost);
            itemStack.setItemMeta(meta);
        }
    }
    public static Material getMaterialFromName(String id) {
        if(id == null) return Material.STONE;
        if (Material.matchMaterial(id)!=null) return Material.matchMaterial(id);
        else return Material.STONE;
    }
    public static double standard(double number) {
        return round(number*100.0)/100.0;
    }
    public static String getStandardPosition(Location location) {
        return ChatColor.RESET+" "+ChatColor.BOLD+" "
                + ChatColor.RED + standard(location.getX())+" "
                + ChatColor.GREEN + standard(location.getY())+" "
                + ChatColor.BLUE + standard(location.getZ())
                +ChatColor.RESET;
    }
    public static ItemStack getPlayerSkull(String playerName) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        assert skullMeta != null;
        skullMeta.setOwningPlayer(Bukkit.getPlayer(playerName));
        skull.setItemMeta(skullMeta);
        return skull;
    }
}
