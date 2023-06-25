package com.mc.basicore.systems.teleport_system;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.ChatSystem.ChatSet;
import com.mc.basicore.systems.FileSystem.FileSet;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
@SuppressWarnings("ConstantConditions")
public class SpaceUnit {
    public static FileSet fileSet = new FileSet("Units.yml");
    public Location location = new Location(Bukkit.getWorld("world"),0,100,0);
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
                if (fileSet.data.getConfigurationSection(s + "." + name) != null) {
                    ConfigurationSection data = fileSet.data.getConfigurationSection(s + "." + name);
                    location = new Location(
                            Bukkit.getWorld(data.getString(".World")),
                            data.getDouble(".X"),
                            data.getDouble(".Y"),
                            data.getDouble(".Z"),
                            Float.parseFloat(data.getString(".Yaw")),
                            Float.parseFloat(data.getString(".Pitch"))
                    );
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
        ConfigurationSection section = fileSet.data.getConfigurationSection(name);
        SpaceUnit unit = new SpaceUnit();
        unit.displayName = name;
        unit.location = player.getLocation();
        unit.playerUUID = player.getUniqueId();
        unit.icon = "grass_block";
        unit.time = 3;
        unit.addUnit();
        return unit;
    }
    public static SpaceUnit query(String name, Player player) {
        SpaceUnit unit = new SpaceUnit();
        if (!fileSet.data.contains(name)) return unit;
        ConfigurationSection section = fileSet.data.getConfigurationSection(name);
        unit.location = new Location(
                Bukkit.getWorld(section.getString(".World")),
                Double.parseDouble(section.getString(".X")),
                Double.parseDouble(section.getString(".Y")),
                Double.parseDouble(section.getString(".Z")),
                Float.parseFloat(section.getString(".Yaw")),
                Float.parseFloat(section.getString(".Pitch"))
        );
        unit.displayName = name;
        unit.playerUUID = UUID.fromString(section.getString(".UUID"));
        unit.gravity = Boolean.parseBoolean(section.getString(".gravity"));
        unit.purview = section.getString(".purview");
        unit.time = Integer.parseInt(section.getString(".time"));
        unit.icon = section.getString(".icon");
        return unit;
    }
    public static SpaceUnit query(String name, String uuid) {
        SpaceUnit unit = new SpaceUnit();
        if (!fileSet.data.getKeys(false).contains(uuid)) {
            unit.displayName = "[E] uuid not found";
            return unit;
        }
        ConfigurationSection section = fileSet.data.getConfigurationSection(uuid);
        if (!section.getKeys(false).contains(name)) {
            unit.displayName = "[E] space name not found";
            return unit;
        }
        section = fileSet.data.getConfigurationSection(uuid+"."+name);
        unit.location = new Location(
                Bukkit.getWorld(section.getString(".World")),
                Double.parseDouble(section.getString(".X")),
                Double.parseDouble(section.getString(".Y")),
                Double.parseDouble(section.getString(".Z")),
                Float.parseFloat(section.getString(".Yaw")),
                Float.parseFloat(section.getString(".Pitch"))
        );
        unit.displayName = name;
        unit.gravity = Boolean.parseBoolean(section.getString(".gravity"));
        unit.purview = section.getString(".purview");
        unit.time = Integer.parseInt(section.getString(".time"));
        unit.icon = section.getString(".icon");
        return unit;
    }
    public static SpaceUnit query(String name) {
        SpaceUnit unit = new SpaceUnit();
        if (!fileSet.data.contains(name)) return unit;
        ConfigurationSection section = fileSet.data.getConfigurationSection(name);
        unit.location = new Location(
                Bukkit.getWorld(section.getString(".World")),
                Double.parseDouble(section.getString(".X")),
                Double.parseDouble(section.getString(".Y")),
                Double.parseDouble(section.getString(".Z")),
                Float.parseFloat(section.getString(".Yaw")),
                Float.parseFloat(section.getString(".Pitch"))
        );
        unit.displayName = name;
        unit.playerUUID = UUID.fromString(section.getString(".UUID"));
        unit.gravity = Boolean.parseBoolean(section.getString(".gravity"));
        unit.purview = section.getString(".purview");
        unit.time = Integer.parseInt(section.getString(".time"));
        unit.icon = section.getString(".icon");
        return unit;
    }
    public void addUnit() {
        String prefix = displayName+".";
        fileSet.data.set(prefix+"purview",purview);
        fileSet.data.set(prefix+"UUID",playerUUID.toString());
        fileSet.data.set(prefix+"icon",icon);
        fileSet.data.set(prefix+"time", String.valueOf(time));
        fileSet.data.set(prefix+"gravity",String.valueOf(gravity));
        fileSet.data.set(prefix+"World",location.getWorld().getName());
        fileSet.data.set(prefix+"X",String.valueOf(location.getX()));
        fileSet.data.set(prefix+"Y",String.valueOf(location.getY()));
        fileSet.data.set(prefix+"Z",String.valueOf(location.getZ()));
        fileSet.data.set(prefix+"Yaw",String.valueOf(location.getYaw()));
        fileSet.data.set(prefix+"Pitch",String.valueOf(location.getPitch()));
        fileSet.save();
    }
    public static void deleteUnit(Player player,String name) {
        if (player.isOp() || fileSet.data.getKeys(false).contains(name)) {
            fileSet.data.set(name, null);
            fileSet.save();
        }
    }
    public void deleteUnit() {
        fileSet.data.set(displayName,null);
        fileSet.save();
    }
    public static List<String> getUnitList(Player player) {
        List<String> list = new ArrayList<>(fileSet.data.getKeys(false));
        list.removeIf(n-> (!query(n).playerUUID.equals(player.getUniqueId())));
        return list;
    }
    public static List<String> getUnitList() {
        return new ArrayList<>(fileSet.data.getKeys(false));
    }
    public static List<SpaceUnit> getPublicUnits() {
        List<SpaceUnit> publicUnits = new ArrayList<>();
        List<String> list = new ArrayList<>(fileSet.data.getKeys(false));
        list.removeIf(n-> (!query(n).purview.equals("public")));
        list.forEach(n -> publicUnits.add(query(n)));
        return publicUnits;
    }
    public static List<SpaceUnit> getTribeUnits() {
        List<SpaceUnit> tribeUnits = new ArrayList<>();
        List<String> list = new ArrayList<>(fileSet.data.getKeys(false));
        list.removeIf(n-> (!query(n).purview.equals("tribe")));
        list.forEach(n -> tribeUnits.add(query(n)));
        return tribeUnits;
    }
    /*=================================teleport================================================*/
    public void toUnit(Player player) {
        if (playerUUID.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) return;
        switch (purview) {
            case "private": {
                if(player.getUniqueId().equals(playerUUID) || player.isOp()) {
                    player.teleport(location);
                }
                else {
                    player.sendMessage(ChatColor.RED + "Private space [ "+displayName+" ] belongs to [ "+ ChatSet.query(playerUUID) +" ]");
                }
                return;
            }
            case "public": {
                player.teleport(location);
                return;
            }
            case "group": {
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
