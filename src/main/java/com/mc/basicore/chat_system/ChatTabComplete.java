package com.mc.basicore.chat_system;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ChatTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> options = new ArrayList<>();
        switch (strings.length) {
            case 1:
                options.add("name_color");
                options.add("content_color");
                options.add("custom_name");
                options.add("reset");
                options.add("get");
                return options;
            case 2:
                if (strings[0].split("_")[1].equals("color")) {
                    options.add("aqua");
                    options.add("black");
                    options.add("blue");
                    options.add("bold");
                    options.add("dark_aqua");
                    options.add("dark_blue");
                    options.add("dark_gray");
                    options.add("dark_green");
                    options.add("dark_purple");
                    options.add("dark_red");
                    options.add("gold");
                    options.add("gray");
                    options.add("green");
                    options.add("italic");
                    options.add("light_purple");
                    options.add("magic");
                    options.add("red");
                    options.add("reset");
                    options.add("strikethrough");
                    options.add("underline");
                    options.add("white");
                    options.add("yellow");
                    return options;
                }
            default:
                return options;
        }
    }
}
