package com.mc.basicore;

import com.mc.basicore.chat_system.*;
import com.mc.basicore.commands.fly;
import com.mc.basicore.discord.onPlayerChatDiscord;
import com.mc.basicore.events.ExtraFoodsEvent;
import com.mc.basicore.events.onCreeperExplode;
import com.mc.basicore.events.onPlayerFished;
import com.mc.basicore.events.onDispenserShot;
import com.mc.basicore.teleport_system.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class BasiCore extends JavaPlugin {
    private static BasiCore plugin;
    public static BasiCore getPlugin() {
        return plugin;
    }
    public static String getRootFolder() {
        return plugin.getDataFolder().getPath();
    }
    @Override
    @SuppressWarnings("ConstantConditions")
    public void onEnable() {
        /*=====================initialize===================*/
        BasiCore.plugin = this;
        this.saveDefaultConfig();
        CoordinateUnit.initFile();
        ChatSet.initFile();
        /*==================register====================*/
        getCommand("fly").setExecutor(new fly());
        getServer().getPluginManager().registerEvents(new onPlayerFished(),this);
        getServer().getPluginManager().registerEvents(new onCreeperExplode(),this);
        getServer().getPluginManager().registerEvents(new onDispenserShot(),this);
        getServer().getPluginManager().registerEvents(new ExtraFoodsEvent(),this);
        ExtraFoodsEvent.apple_pie_recipe();

        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("chat").setTabCompleter(new ChatTabComplete());
        getServer().getPluginManager().registerEvents(new onPlayerChat(),this);
        getServer().getPluginManager().registerEvents(new onPlayerChatDiscord(),this);

        getCommand("space").setExecutor(new TeleportCommand());
        getCommand("space").setTabCompleter(new TeleportTabComplete());
        getServer().getPluginManager().registerEvents(new TeleportBook(),this);
    }

    @Override
    public void onDisable() {
    }
}
