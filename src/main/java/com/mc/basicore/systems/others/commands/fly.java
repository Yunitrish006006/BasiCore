package com.mc.basicore.systems.others.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class fly implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender,@Nonnull Command command,@Nonnull String s,@Nonnull String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
//            if(player.isOp()) {
                player.setAllowFlight(!player.getAllowFlight());
//            }
            return true;
        }
        return false;
    }
}
