package com.mc.basicore;

import com.mc.basicore.systems.Diet.ExtraFoodsEvent;
import com.mc.basicore.systems.SystemTable;
import com.mc.basicore.systems.WorldManager.worldManager;
import com.mc.basicore.systems.chat_system.*;
import com.mc.basicore.systems.collector_system.*;
import com.mc.basicore.systems.others.commands.fly;
import com.mc.basicore.systems.others.commands.hat;
import com.mc.basicore.systems.others.commands.laugh;
import com.mc.basicore.systems.others.commands.test;
import com.mc.basicore.systems.others.discord.onPlayerChatDiscord;
import com.mc.basicore.systems.enchant_system.EnchantSystem;
import com.mc.basicore.systems.others.events.*;
import com.mc.basicore.systems.others.recipes.furnace_dirt_gravel;
import com.mc.basicore.systems.teleport_system.*;
import com.mc.basicore.systems.teleport_system.events.onPlayerDeathOrReborn;
import com.mc.basicore.systems.translate.Translator;
import com.mc.basicore.systems.tribeSystem.Tribe;
import com.mc.basicore.systems.world_index.WorldIndex;
import org.bukkit.plugin.java.JavaPlugin;

import static com.mc.basicore.systems.others.recipes.gunpowder.burned_gunPowder;

public final class BasiCore extends JavaPlugin {
    private static BasiCore plugin;
    public static String language = "zh_tw";
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
        worldManager.registerWorld();
        this.saveDefaultConfig();
        Basics.initFile();
        ChatSet.chatInit();
        Translator.initFile();
        SystemTable.initialize();
        /*==================enchant system register====================*/
        EnchantSystem.register();
        getServer().getPluginManager().registerEvents(new EnchantSystem(),this);
        /*==================register====================*/
        getServer().getPluginManager().registerEvents(new skill_system(),this);
        getCommand("test").setExecutor(new test());
        getCommand("fly").setExecutor(new fly());
        getCommand("hat").setExecutor(new hat());
        getCommand("laugh").setExecutor(new laugh());
        collector.init();
        /*============================other little system==============================*/
        getServer().getPluginManager().registerEvents(new onCreeperExplode(),this);
        getServer().getPluginManager().registerEvents(new ExtraFoodsEvent(),this);
        getServer().getPluginManager().registerEvents(new onPlayerJoin(),this);
        getServer().getPluginManager().registerEvents(new OnPlayerInteractBeeHive(),this);
        ExtraFoodsEvent.add_recipes();
        furnace_dirt_gravel.dirt_gravel();
        burned_gunPowder();
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("chat").setTabCompleter(new ChatTabComplete());
        getServer().getPluginManager().registerEvents(new onPlayerChat(),this);
        getServer().getPluginManager().registerEvents(new onPlayerChatDiscord(),this);
        getCommand("space").setExecutor(new TeleportCommand());
        getCommand("space").setTabCompleter(new TeleportTabComplete());
        getServer().getPluginManager().registerEvents(new WorldIndex(),this);
        getServer().getPluginManager().registerEvents(new onPlayerDeathOrReborn(),this);
        /*=============================================test=================================================*/
        Tribe.init();
    }

    @Override
    public void onDisable() {
    }
}
