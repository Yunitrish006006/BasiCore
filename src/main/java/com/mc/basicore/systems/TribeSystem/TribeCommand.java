package com.mc.basicore.systems.TribeSystem;

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
            case 1: {
                switch (strings[0]) {
                    case "list":
                        for (Tribe t:Tribe.List()) {
                            player.sendMessage("===================================================");
                            t.sendTribeData(player);
                        }
                        break;
                    case "create":
                        Tribe.create(player).sendTribeData(player);
                        break;
                    default:
                        break;
                }
                break;
            }
            case 2:
                switch (strings[0]) {
                    case "find":
                        player.sendMessage("===================================================");
                        Tribe.Query(strings[1]).sendTribeData(player);
                        break;
                    case "apply":
                        Tribe.Query(strings[1]).apply(player);
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (strings[0]) {
                    case "recruit":
                        Tribe.Query(strings[1]).recruit(player, Bukkit.getPlayer(strings[2]));
                        break;
                    case "kick":
                        Tribe.Query(strings[1]).members.remove(Bukkit.getPlayer(strings[2]));
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
