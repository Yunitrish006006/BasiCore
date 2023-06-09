package com.mc.basicore;

import org.bukkit.*;
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
import java.util.UUID;

import static java.lang.Math.round;

public class Basics {
    public static File file;
    public static final String fileName = "PlayerData.yml";
    public static FileConfiguration config;
    public static UUID errorID() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
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
    public static void initFile() {
        setFile();
        saveFile();
    }
    public static List<String> UUIDS() {
        return new ArrayList<>(config.getKeys(false));
    }
    public static List<String> getPlayerList() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        List<String> names = new ArrayList<>();
        for (Player p : players) {
            names.add(p.getName());
        }
        return names;
    }
    public static void playSound(Location location, Sound sound) {
        for (Player player:Bukkit.getOnlinePlayers()) {
            player.playSound(location,sound,SoundCategory.BLOCKS,1.0f,1.0f);
        }
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
    public static double getCubeDistance(Location from, Location to) {
        double rx = Math.abs(from.getBlockX() - to.getBlockX());
        double ry = Math.abs(from.getBlockY() - to.getBlockY());
        double rz = Math.abs(from.getBlockZ() - to.getBlockZ());
        return Math.sqrt((rx*rx)+(ry*ry)+(rz*rz));
    }
    public static void useItem(ItemStack itemStack,int times) {
        if (itemStack.getItemMeta() instanceof Damageable) {
            Damageable meta = (Damageable) itemStack.getItemMeta();
            int d_level = meta.getEnchantLevel(Enchantment.DURABILITY)+1;
            int decrease = (int) Math.pow(2,d_level);
            int cost = (int)(times/Math.pow(2,d_level));
            if (cost<decrease) cost=times/d_level;
            meta.setDamage(meta.getDamage()+cost);
            if (meta.getDamage() > itemStack.getType().getMaxDurability()) meta.setDamage(itemStack.getType().getMaxDurability()-1);
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
    public static String getRandomName(int length) {
        StringBuilder temp = new StringBuilder();
        for(int i=0;i<length;i++) {
            temp.append((char) ('a' + (Math.random() * 26)));
        }
        return temp.toString();
    }
    public static int getRandomInt(int range) {
        return (int) (Math.random() * range);
    }
    public static boolean getRandomBool() {
        if (Math.random() < 0.5) return false;
        else return true;
    }
}
