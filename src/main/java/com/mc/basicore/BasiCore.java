package com.mc.basicore;

import com.mc.basicore.chat_system.*;
import com.mc.basicore.collector_system.TreeCutter;
import com.mc.basicore.commands.fly;
import com.mc.basicore.commands.hat;
import com.mc.basicore.commands.laugh;
import com.mc.basicore.commands.test;
import com.mc.basicore.discord.onPlayerChatDiscord;
import com.mc.basicore.enchant_system.EnchantSystem;
import com.mc.basicore.events.*;
import com.mc.basicore.mob_system.events.skeleton_sword;
import com.mc.basicore.recipes.furnace_dirt_gravel;
import com.mc.basicore.teleport_system.*;
import com.mc.basicore.teleport_system.events.onPlayerDeathOrReborn;
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
        Basics.initFile();
        ChatSet.chatInit();
        /*==================enchant system register====================*/
        EnchantSystem.register();
        getServer().getPluginManager().registerEvents(new EnchantSystem(),this);
        /*==================register====================*/
        getServer().getPluginManager().registerEvents(new skill_system(),this);

        getCommand("test").setExecutor(new test());
        getCommand("fly").setExecutor(new fly());
        getCommand("hat").setExecutor(new hat());
        getCommand("laugh").setExecutor(new laugh());
        getServer().getPluginManager().registerEvents(new sleepEvent(),this);
        getServer().getPluginManager().registerEvents(new skeleton_sword(),this);
        getServer().getPluginManager().registerEvents(new onPlayerFished(),this);
        getServer().getPluginManager().registerEvents(new onShovelOnGravel(),this);
        getServer().getPluginManager().registerEvents(new onPlayerRide(),this);

        getServer().getPluginManager().registerEvents(new TreeCutter(),this);
        getServer().getPluginManager().registerEvents(new onCreeperExplode(),this);
        getServer().getPluginManager().registerEvents(new onDispenserShot(),this);
        getServer().getPluginManager().registerEvents(new ExtraFoodsEvent(),this);
        getServer().getPluginManager().registerEvents(new onPlayerJoin(),this);
        ExtraFoodsEvent.add_recipes();
        furnace_dirt_gravel.dirt_gravel();

        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("chat").setTabCompleter(new ChatTabComplete());
        getServer().getPluginManager().registerEvents(new onPlayerChat(),this);
        getServer().getPluginManager().registerEvents(new onPlayerChatDiscord(),this);

        getCommand("space").setExecutor(new TeleportCommand());
        getCommand("space").setTabCompleter(new TeleportTabComplete());
        getServer().getPluginManager().registerEvents(new TeleportBook(),this);
        getServer().getPluginManager().registerEvents(new onPlayerDeathOrReborn(),this);
    }

    @Override
    public void onDisable() {
    }
}
