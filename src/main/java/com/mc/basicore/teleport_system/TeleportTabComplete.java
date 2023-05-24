package com.mc.basicore.teleport_system;

import com.mc.basicore.Basics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportTabComplete implements TabCompleter {
    @Override
    @SuppressWarnings({"NullableProblems"})
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return new ArrayList<>();
        Player player = (Player) commandSender;
        List<String> options = new ArrayList<>();
        switch (strings.length) {
            case 1:
                options.add("player");
                options.add("add");
                options.add("delete");
                options.add("own");
                options.add("group");
                options.add("public");
                options.add("GUI");
                options.add("rename");
                options.add("set_icon");
                options.add("query");
                if(player.isOp()) {
                    options.add("allSpaces");
                }
                return options;
            case 2:
                switch (strings[0]) {
                    case "add": {
                        StringBuilder x = new StringBuilder();
                        for(int i=0;i<4;i++) {
                            x.append((char) ('a' + (Math.random() * 26)));
                        }
                        List<String> random_id = new ArrayList<>();
                        random_id.add(x.toString());
                        return random_id;
                    }
                    case "delete": {
                        if (player.isOp()) {
                            return SpaceUnit.getUnitList();
                        }
                        else {
                            return SpaceUnit.getUnitList(player);
                        }
                    }
                    case "query":
                    case "own":
                    case "set_icon":
                    case "rename": {
                        return SpaceUnit.getUnitList(player);
                    }
                    case "player": {
                        return Basics.getPlayerList();
                    }
                    case "group":
                    case "public": {
                        for (SpaceUnit unit: SpaceUnit.getPublicUnits()) {
                            options.add(unit.displayName);
                        }
                        return options;
                    }
                    case "allSpaces": {
                        if(player.isOp()) options = SpaceUnit.getUnitList();
                        return options;
                    }
                }
            case 3:
                switch (strings[0]) {
                    case "rename": {
                        StringBuilder x = new StringBuilder();
                        for(int i=0;i<4;i++) {
                            x.append((char) ('a' + (Math.random() * 26)));
                        }
                        List<String> random_id = new ArrayList<>();
                        random_id.add(x.toString());
                        random_id.add("你要的名字");
                        return random_id;
                    }
                    case "set_icon": {
                        List<String> option = new ArrayList<>();
                        option.add("grass_block");
                        option.add("stone");
                        option.add("red_bed");
                        option.add("iron_ore");
                        option.add("wheat");
                        return option;
                    }
                }
            default:
                return options;
        }
    }
}
