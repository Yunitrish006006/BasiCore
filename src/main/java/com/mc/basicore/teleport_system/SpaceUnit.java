package com.mc.basicore.teleport_system;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
@SuppressWarnings("ConstantConditions")
public class SpaceUnit {
    /*========file system==========*/
    public static FileConfiguration config = Basics.config;
    /*========basic data==========*/
    public Location location = new Location(Bukkit.getWorld("world"),0,0,0);
    public String owner  = "none";
    public UUID playerUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    /*========optional==========*/
    public String displayName = "Previous";
    public boolean gravity = true;
    public String purview = "private";
    public int time = 0;
    public String icon = "stone";

    public SpaceUnit(String name, Player player) {
        playerUUID = player.getUniqueId();
        List<String> uuidList = new ArrayList<>(config.getKeys(false));
        if (
            uuidList.contains(playerUUID.toString()) &&
            new ArrayList<>(config.getConfigurationSection(playerUUID.toString()).getKeys(false)).contains("Units") &&
            getUnitList().contains(name)
            ) {
            ConfigurationSection data = config.getConfigurationSection(playerUUID+".Units."+name);
            location = new Location(
                    Bukkit.getWorld(data.getString(".World")),
                    data.getDouble(".X"),
                    data.getDouble(".Y"),
                    data.getDouble(".Z"),
                    Float.parseFloat(data.getString(".Yaw")),
                    Float.parseFloat(data.getString(".Pitch"))
            );
            owner = data.getString(".owner");
            displayName = name;
            gravity = data.getBoolean(".gravity");
            purview = data.getString(".purview");
            time = data.getInt(".time");
            icon = data.getString(".icon");
        }
        else {
            location = player.getLocation();
            gravity = false;
            purview = "private";
            displayName = name;
            time = 3;
            owner = player.getName();
            icon = "stone";
        }
    }
    public SpaceUnit(String name) {
        List<String> uuidList = SpaceUnit.getUnitList();
        if(getUnitList().contains(name)) {
            for (String s : uuidList) {
                if (config.getConfigurationSection(s + "." + name) != null) {
                    ConfigurationSection data = config.getConfigurationSection(s + "." + name);
                    location = new Location(
                            Bukkit.getWorld(data.getString(".World")),
                            data.getDouble(".X"),
                            data.getDouble(".Y"),
                            data.getDouble(".Z"),
                            Float.parseFloat(data.getString(".Yaw")),
                            Float.parseFloat(data.getString(".Pitch"))
                    );
                    owner = data.getString(".owner");
                    displayName = name;
                    gravity = data.getBoolean(".gravity");
                    purview = data.getString(".purview");
                    time = data.getInt(".time");
                    icon = data.getString(".icon");
                }
            }
        }
        else {
            StringBuilder x = new StringBuilder();
            for(int i=0;i<4;i++) {
                x.append((char) ('a' + (Math.random() * 26)));
            }
            displayName = "not found#" + x;
        }
    }
    public void addUnit() {
        String prefix = playerUUID+".Units."+displayName+".";
        config.set(prefix+"owner",owner);
        config.set(prefix+"purview",purview);
        config.set(prefix+"icon",icon);
        config.set(prefix+"time",time);
        config.set(prefix+"gravity",gravity);

        config.set(prefix+"World",location.getWorld().getName());
        config.set(prefix+"X",location.getX());
        config.set(prefix+"Y",location.getY());
        config.set(prefix+"Z",location.getZ());
        config.set(prefix+"Yaw",location.getYaw());
        config.set(prefix+"Pitch",location.getPitch());

        Basics.saveFile();
    }
    public static void deleteUnit(Player player,String name) {
        List<String> uuidList = new ArrayList<>(config.getKeys(false));
        for (String s : uuidList) {
            if (config.getConfigurationSection(s + ".Units." + name) != null) {
                if (player.isOp() || s.equals(player.getUniqueId().toString())) {
                    config.set(s + ".Units." + name, null);
                    Basics.saveFile();
                }
            }
        }
    }
    public void deleteUnit() {
        config.set(playerUUID+".Units."+displayName,null);
        Basics.saveFile();
    }
    public static List<String> getUnitList(Player player) {
        String prefix = player.getUniqueId()+".Units";
        if (config.getConfigurationSection(prefix) != null) {
            return new ArrayList<>(config.getConfigurationSection(prefix).getKeys(false));
        }
        else {
            return new ArrayList<>();
        }
    }
    public static List<String> getUnitList() {
        List<String> uuidList = new ArrayList<>(config.getKeys(false));
        List<String> units = new ArrayList<>();
        try {
            for (String s : uuidList) {
                String prefix = s + ".Units";
                List<String> names = new ArrayList<>(config.getConfigurationSection(prefix).getKeys(false));
                units.addAll(names);
            }
        }
        catch (Exception exception) {
            Bukkit.getServer().broadcast("Error on get UnitList:",exception.toString());
        }
        return units;
    }
    public void toUnit(Player player) {
        if (playerUUID.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) return;
        switch (purview) {
            case "private": {
                if(player.getUniqueId().equals(playerUUID) || player.isOp()) {
                    String x = String.valueOf(Math.round(location.getX()*100.0)/100.0);
                    String y = String.valueOf(Math.round(location.getY()*100.0)/100.0);
                    String z = String.valueOf(Math.round(location.getZ()*100.0)/100.0);
                    player.sendTitle("",x+","+y+","+z,10,10,10);
                    player.sendMessage(x+","+y+","+z);
                    player.teleport(location);
                }
                else {
                    player.sendMessage(ChatColor.RED + "Private space [ "+displayName+" ] belongs to [ "+owner+" ]");
                }
                return;
            }
            case "group": {
            }
            case "public": {
            }
            default: {
                player.sendMessage(ChatColor.RED + "wrong purview settings[ "+purview+" ]");
            }
        }
    }
    public static void toPlayer(Player player,Player target) {
        if(player.isOp()) {
            player.teleport(player);
        }
        else {
            target.sendMessage(ChatColor.RESET+" ");
        }
    }
    public void teleportCountDown(Player player) {
        Location spaceRecord = player.getLocation();
        for (int i = time;i>0;i--) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            int finalI = i;
            scheduler.runTaskLater(BasiCore.getPlugin(),() -> {
                if (player.getLocation().getX() == spaceRecord.getX() &&
                    player.getLocation().getY() == spaceRecord.getY() &&
                    player.getLocation().getZ() == spaceRecord.getZ()) {
                    player.sendTitle(" "+ (time-finalI+1),"",10,10,10);
                    player.playSound(spaceRecord, Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER, 0.4f, 0.96f);
                }
            },(i-1)*20L);
        }
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskLater(BasiCore.getPlugin(),() -> {
            if (player.getLocation().getX() == spaceRecord.getX() &&
                    player.getLocation().getY() == spaceRecord.getY() &&
                    player.getLocation().getZ() == spaceRecord.getZ()) {
                toUnit(player);
                player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.MASTER, 0.6f, 0.96f);
            }
        },time*20L);
    }
}
