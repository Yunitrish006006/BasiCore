package com.mc.basicore.chat_system;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender,@Nonnull Command command,@Nonnull String s,@Nonnull String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        ChatSet chatSet = new ChatSet(player);
        switch (strings.length) {
            case 1 : {
                if (strings[0].equalsIgnoreCase("get")) {
                    player.sendMessage("Name Color: " + chatSet.NameColor.name().toLowerCase());
                    player.sendMessage("Content Color: " + chatSet.ContentColor.name().toLowerCase());
                    player.sendMessage("Custom Name: " + chatSet.CustomName);
                    return true;
                }
                else if(strings[0].equalsIgnoreCase("reset")){
                    chatSet.reset();
                    chatSet.setChatSet();
                    return true;
                }
            }
            case 2 :{
                if (Objects.equals(strings[0], "name_color")) {
                    chatSet.setNameColor(strings[1]);
                }
                else if (Objects.equals(strings[0], "content_color")) {
                    chatSet.setContentColor(strings[1]);
                }
                else if (Objects.equals(strings[0], "custom_name")) {
                    chatSet.setCustomName(strings[1]);
                }
                else {
                    player.sendMessage(ChatColor.DARK_RED + "only <name_color/content_color/custom_name> is available");
                }
                chatSet.setChatSet();
                return true;
            }
        }
        return false;
    }
}