package com.mc.basicore.systems.teleport_system;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.ChatSystem.ChatSet;
import com.mc.basicore.systems.FileSystem.FileSet;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public class SpaceUnit {
    public static FileSet fileSet = new FileSet("Unit.yml");
    public Location location = new Location(Bukkit.getWorld("world"),0,100,0);
    public UUID ownerUUID = Basics.errorID;
    public UUID unitUUID = Basics.errorID;
    public String unitName = "Unknown Error";
    public boolean gravity = false;
    public String purview = "private";
    public int time = 3;
    public String unitIcon = "barrier";

    public static SpaceUnit create(String name, Player player) {
        SpaceUnit unit = new SpaceUnit();
        unit.unitUUID = UUID.randomUUID();
        unit.unitName = name;
        unit.location = player.getLocation();
        unit.ownerUUID = player.getUniqueId();
        unit.unitIcon = "grass_block";
        unit.purview = "private";
        unit.saveUnit();
        return unit;
    }
    public static SpaceUnit queryFromName(String name, Player player) {
        List<SpaceUnit> units = getPrivateUnits(player);
        units.removeIf(u -> !u.unitName.equals(name));
        if (units.isEmpty()) return new SpaceUnit();
        else return units.get(0);
    }
    public static SpaceUnit queryIDAsPlayer(String id, Player player) {
        SpaceUnit unit = new SpaceUnit();
        if (!fileSet.data.getKeys(false).contains(id)) {
            unit.unitName = "unit ID not found!";
            return unit;
        }
        ConfigurationSection section = fileSet.data.getConfigurationSection(id);
        if (!UUID.fromString(section.getString(".ownerUUID")).equals(player.getUniqueId())) return unit;
        unit.unitUUID = UUID.fromString(id);
        unit.unitName = section.getString(".unitName");
        unit.purview = section.getString(".purview");
        unit.ownerUUID = UUID.fromString(section.getString(".ownerUUID"));
        unit.unitIcon = section.getString(".unitIcon");
        unit.time = Integer.parseInt(section.getString(".time"));
        unit.gravity = Boolean.parseBoolean(section.getString(".gravity"));
        unit.location = new Location(
                Bukkit.getWorld(section.getString(".World")),
                Double.parseDouble(section.getString(".X")),
                Double.parseDouble(section.getString(".Y")),
                Double.parseDouble(section.getString(".Z")),
                Float.parseFloat(section.getString(".Yaw")),
                Float.parseFloat(section.getString(".Pitch"))
        );
        return unit;
    }
    public static SpaceUnit queryAsUUID(String name, String uuid) {
        SpaceUnit unit = new SpaceUnit();
        if (!fileSet.data.getKeys(false).contains(uuid)) {
            unit.unitName = "[E] uuid not found";
            return unit;
        }
        ConfigurationSection section = fileSet.data.getConfigurationSection(uuid);
        if (!section.getKeys(false).contains(name)) {
            unit.unitName = "[E] space name not found";
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
        unit.unitName = name;
        unit.gravity = Boolean.parseBoolean(section.getString(".gravity"));
        unit.purview = section.getString(".purview");
        unit.time = Integer.parseInt(section.getString(".time"));
        unit.unitIcon = section.getString(".icon");
        return unit;
    }
    public static SpaceUnit queryIDAsSystem(String id) {
        SpaceUnit unit = new SpaceUnit();
        if (!fileSet.data.contains(id)) return unit;
        ConfigurationSection section = fileSet.data.getConfigurationSection(id);
        unit.unitUUID = UUID.fromString(id);
        unit.unitName = section.getString(".unitName");
        unit.purview = section.getString(".purview");
        unit.ownerUUID = UUID.fromString(section.getString(".ownerUUID"));
        unit.unitIcon = section.getString(".unitIcon");
        unit.time = Integer.parseInt(section.getString(".time"));
        unit.gravity = Boolean.parseBoolean(section.getString(".gravity"));
        unit.location = new Location(
                Bukkit.getWorld(section.getString(".World")),
                Double.parseDouble(section.getString(".X")),
                Double.parseDouble(section.getString(".Y")),
                Double.parseDouble(section.getString(".Z")),
                Float.parseFloat(section.getString(".Yaw")),
                Float.parseFloat(section.getString(".Pitch"))
        );
        return unit;
    }
    public void saveUnit() {
        String prefix = unitUUID+".";
        fileSet.data.set(prefix+"unitName", unitName);
        fileSet.data.set(prefix+"purview",purview);
        fileSet.data.set(prefix+"ownerUUID", ownerUUID.toString());
        fileSet.data.set(prefix+"unitIcon", unitIcon);
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
    public static void deleteUnit(Player player,String id) {
        if (fileSet.data.getKeys(false).contains(id)) {
            fileSet.data.set(id, null);
            fileSet.save();
        }
    }
    public void deleteUnit() {
        fileSet.data.set(unitName,null);
        fileSet.save();
    }
    public static List<SpaceUnit> getPrivateUnits(Player player) {
        List<SpaceUnit> unitList = new ArrayList<>();
        fileSet.data.getKeys(false).forEach(id -> {
            if (!fileSet.data.getConfigurationSection(id).getKeys(false).contains("ownerUUID")) return;
            if (!UUID.fromString(fileSet.data.getString(id+".ownerUUID")).equals(player.getUniqueId())) return;
            unitList.add(queryIDAsPlayer(id,player));
        });
        return unitList;
    }
    public static List<String> getPrivateUnitsNames(Player player) {
        return getPrivateUnits(player).stream().map(unit -> unit.unitName).collect(Collectors.toList());
    }
    public static List<String> getUnitListNames() {
        return getPrivateUnits().stream().map(unit -> unit.unitName).collect(Collectors.toList());
    }
    public static List<SpaceUnit> getPrivateUnits() {
        List<SpaceUnit> result = new ArrayList<>();
        fileSet.data.getKeys(false).forEach(id -> result.add(queryIDAsSystem(fileSet.data.getString(id))));
        return result;
    }
    public static List<SpaceUnit> getPublicUnits() {
        List<SpaceUnit> result = new ArrayList<>();
        fileSet.data.getKeys(false).forEach(id -> {
            if (queryIDAsSystem(id).purview.equals("public")) result.add(queryIDAsSystem(id));
        });
        return result;
    }
    /*=================================teleport================================================*/
    public void toUnit(Player player) {
        if (ownerUUID.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) return;
        switch (purview) {
            case "private": {
                if(player.getUniqueId().equals(ownerUUID) || player.isOp()) {
                    player.teleport(location);
                }
                else {
                    player.sendMessage(ChatColor.RED + "Private space [ "+ unitName +" ] belongs to [ "+ ChatSet.query(ownerUUID) +" ]");
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
