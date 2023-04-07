package com.mc.basicore.teleport_system;

import com.mc.basicore.BasiCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TeleportCommand implements CommandExecutor {
    @Override
    @SuppressWarnings({"NullableProblems"})
    public boolean onCommand(CommandSender commandSender,Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        switch (strings.length) {
            case 0: {
                player.sendMessage(ChatColor.GOLD + "help:");
                player.sendMessage(ChatColor.GOLD + "\t" + BasiCore.getPlugin().getName());
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
                        CoordinateUnit unit = new CoordinateUnit(strings[1],player);
                        unit.addUnit();
                        return true;
                    }
                    case "delete": {
                        CoordinateUnit.deleteUnit(player,strings[1]);
                        return true;
                    }
                    case "own": {
                        CoordinateUnit unit = new CoordinateUnit(strings[1],player);
                        unit.toUnit(player);
                        return true;
                    }
                    case "allSpaces": {
                        CoordinateUnit unit = new CoordinateUnit(strings[1]);
                        unit.toUnit(player);
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
            default: return true;
        }
    }
}
