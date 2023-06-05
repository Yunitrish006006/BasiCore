package com.mc.basicore.systems.tribeSystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TribeTabComplete implements TabCompleter {
    @Override
    @SuppressWarnings({"NullableProblems"})
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return new ArrayList<>();
        Player player = (Player) commandSender;
        List<String> options = new ArrayList<>();
        switch (strings.length) {
            case 1:
                options.add("list");
                options.add("find");
                options.add("apply");
                options.add("recruit");
                options.add("kick");
                options.add("create");
                return options;
            default:
                return options;
        }
    }
}
