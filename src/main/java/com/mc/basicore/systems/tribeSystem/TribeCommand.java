package com.mc.basicore.systems.tribeSystem;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TribeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        switch (strings.length) {
            case 1:
                if (strings[0].equals("create")) {
                    Tribe tribe = new Tribe(player);
                    tribe.save();
                }
                break;
            case 2:
                switch (strings[0]) {
                    case "find":
                        Tribe.Find(strings[1]).tribeData(player);
                        break;
                    case "apply":
                        Tribe.Find(strings[1]).apply(player);
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (strings[0]) {
                    case "recruit":
                        Tribe.Find(strings[1]).recruit(player, Bukkit.getPlayer(strings[2]));
                        break;
                    case "kick":
                        Tribe.Find(strings[1]).members.remove(Bukkit.getPlayer(strings[2]));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }
}