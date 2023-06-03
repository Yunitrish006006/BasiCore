package com.mc.basicore.teleport_system;

import com.mc.basicore.world_index.WorldIndex;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;


public class TeleportCommand implements CommandExecutor {
    @Override
    @SuppressWarnings({"NullableProblems"})
    public boolean onCommand(CommandSender commandSender,Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        switch (strings.length) {
            case 0: {
                player.sendMessage(ChatColor.GOLD + "help:");
                player.sendMessage(ChatColor.GOLD+"GUI");
            }
            case 1: {
                if (strings[0].equals("GUI")) {
                    if (!player.getInventory().contains(new WorldIndex())) {
                        player.getInventory().addItem(new WorldIndex());
                    }
                    return true;
                }
                return true;
            }
            case 2: {
                switch (strings[0]) {
                    case "add": {
                        SpaceUnit unit = SpaceUnit.create(strings[1],player);
                        unit.addUnit();
                        return true;
                    }
                    case "delete": {
                        SpaceUnit.deleteUnit(player,strings[1]);
                        return true;
                    }
                    case "own": {
                        SpaceUnit unit = SpaceUnit.query(strings[1],player);
                        unit.toUnit(player);
                        return true;
                    }
                    case "query": {
                        SpaceUnit unit = SpaceUnit.query(strings[1],player);
                        unit.sendUnitData();
                        return true;
                    }
                    case "allSpaces": {
                        SpaceUnit unit = new SpaceUnit(strings[1]);
                        unit.toUnit(player);
                        return true;
                    }
                    case "player": {
                        player.teleport(Objects.requireNonNull(Bukkit.getPlayer(strings[1])).getLocation());
                        return true;
                    }
                    case "group":
                    case "public": {
                        player.sendMessage(ChatColor.GOLD + "still developing this function");
                        return true;
                    }
                    default: return true;
                }
            }
            case 3: {
                if (strings[0].equals("rename")) {
                    SpaceUnit unit = SpaceUnit.query(strings[1], player);
                    unit.deleteUnit();
                    unit.displayName = strings[2];
                    unit.addUnit();
                    return true;
                }
                else if (strings[0].equals("set_icon")) {
                    SpaceUnit unit = SpaceUnit.query(strings[1], player);
                    unit.deleteUnit();
                    unit.icon = strings[2];
                    unit.addUnit();
                    return true;
                }
                return true;
            }
            default: return true;
        }
    }
}
