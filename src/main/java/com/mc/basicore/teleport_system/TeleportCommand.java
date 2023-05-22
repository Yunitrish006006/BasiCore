package com.mc.basicore.teleport_system;

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
                switch (strings[0]) {
                    case "GUI": {
                        if (!player.getInventory().contains(new TeleportBook())) {
                            player.getInventory().addItem(new TeleportBook());
                        }
                        return true;
                    }
                }
            }
            case 2: {
                switch (strings[0]) {
                    case "add": {
                        SpaceUnit unit = new SpaceUnit(strings[1],player);
                        unit.addUnit();
                        return true;
                    }
                    case "delete": {
                        SpaceUnit.deleteUnit(player,strings[1]);
                        return true;
                    }
                    case "own": {
                        SpaceUnit unit = new SpaceUnit(strings[1],player);
                        unit.toUnit(player);
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
                switch (strings[0]) {
                    case "rename": {
                        SpaceUnit unit = new SpaceUnit(strings[1],player);
                        unit.deleteUnit();
                        unit.displayName = strings[2];
                        unit.addUnit();
                        return true;
                    }
                    default: return true;
                }
            }
            default: return true;
        }
    }
}
