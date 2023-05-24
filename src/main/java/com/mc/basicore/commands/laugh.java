package com.mc.basicore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class laugh implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender,@Nonnull Command command,@Nonnull String s,@Nonnull String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            for (Player p: Bukkit.getOnlinePlayers()) {
                p.playSound(player.getLocation(), "basicore.devil_laugh",0.8f,2.0f);
            }
            return true;
        }
        return false;
    }
}
