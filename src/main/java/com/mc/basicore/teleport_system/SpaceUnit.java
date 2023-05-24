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
    public static FileConfiguration config = Basics.config;
    public Location location = new Location(Bukkit.getWorld("world"),0,100,0);
    public String owner  = "none";
    public UUID playerUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public String displayName = "Error Unknown";
    public boolean gravity = true;
    public String purview = "private";
    public int time = 3;
    public String icon = "stone";

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
    public SpaceUnit() {}
    public static SpaceUnit create(String name, Player player) {
        ConfigurationSection section = config.getConfigurationSection(player.getUniqueId()+".Units."+name);
        SpaceUnit unit = new SpaceUnit();
        unit.displayName = name;
        unit.owner = player.getName();
        unit.location = player.getLocation();
        unit.playerUUID = player.getUniqueId();
        unit.icon = "grass_block";
        unit.time = 3;
        unit.addUnit();
        return unit;
    }
    public static SpaceUnit query(String name, Player player) {
        SpaceUnit unit = new SpaceUnit();
        unit.location = player.getLocation();
        if (!config.getKeys(false).contains(player.getUniqueId().toString())) {
            unit.displayName = "[E] uuid not found";
            return unit;
        }
        unit.playerUUID = player.getUniqueId();
        ConfigurationSection section = config.getConfigurationSection(player.getUniqueId().toString());
        if (!section.getKeys(false).contains("Units")) {
            unit.displayName = "[E] Unit section not found";
            config.set(player.getUniqueId()+".Units",null);
            Basics.saveFile();
            return unit;
        }
        section = config.getConfigurationSection(player.getUniqueId()+".Units.");
        if (!section.getKeys(false).contains(name)) {
            unit.displayName = "[E] space name not found";
            return unit;
        }
        section = config.getConfigurationSection(player.getUniqueId()+".Units."+name);
        unit.location = new Location(
                Bukkit.getWorld(section.getString(".World")),
                Double.parseDouble(section.getString(".X")),
                Double.parseDouble(section.getString(".Y")),
                Double.parseDouble(section.getString(".Z")),
                Float.parseFloat(section.getString(".Yaw")),
                Float.parseFloat(section.getString(".Pitch"))
        );
        unit.owner = section.getString(".owner");
        unit.displayName = name;
        unit.gravity = Boolean.parseBoolean(section.getString(".gravity"));
        unit.purview = section.getString(".purview");
        unit.time = Integer.parseInt(section.getString(".time"));
        unit.icon = section.getString(".icon");
        return unit;
    }
    public void addUnit() {
        String prefix = playerUUID+".Units."+displayName+".";
        config.set(prefix+"owner",owner);
        config.set(prefix+"purview",purview);
        config.set(prefix+"icon",icon);
        config.set(prefix+"time", String.valueOf(time));
        config.set(prefix+"gravity",String.valueOf(gravity));

        config.set(prefix+"World",location.getWorld().getName());
        config.set(prefix+"X",String.valueOf(location.getX()));
        config.set(prefix+"Y",String.valueOf(location.getY()));
        config.set(prefix+"Z",String.valueOf(location.getZ()));
        config.set(prefix+"Yaw",String.valueOf(location.getYaw()));
        config.set(prefix+"Pitch",String.valueOf(location.getPitch()));

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
    public void sendUnitData() {
        Bukkit.broadcastMessage("==========================================================");
        Bukkit.broadcastMessage("name:"+displayName);
        Bukkit.broadcastMessage("owner:"+owner);
        Bukkit.broadcastMessage("World:"+location.getWorld().getName());
        Bukkit.broadcastMessage("Location:"+Basics.getStandardPosition(location));
        Bukkit.broadcastMessage("Yaw & Pitch : "+Basics.standard(location.getYaw())+", "+Basics.standard(location.getPitch()));
        Bukkit.broadcastMessage("gravity:"+gravity);
        Bukkit.broadcastMessage("purview:"+purview);
        Bukkit.broadcastMessage("time:"+time);
        Bukkit.broadcastMessage("icon:"+icon);
        Bukkit.broadcastMessage("playerUUID:"+playerUUID);
        Bukkit.broadcastMessage("==========================================================");
    }
}
