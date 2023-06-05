package com.mc.basicore;

import com.mc.basicore.systems.chat_system.*;
import com.mc.basicore.systems.collector_system.ChainMiner;
import com.mc.basicore.systems.collector_system.TreeCutter;
import com.mc.basicore.systems.collector_system.VeinToggle;
import com.mc.basicore.commands.fly;
import com.mc.basicore.commands.hat;
import com.mc.basicore.commands.laugh;
import com.mc.basicore.commands.test;
import com.mc.basicore.discord.onPlayerChatDiscord;
import com.mc.basicore.systems.collector_system.onShovelOnGravel;
import com.mc.basicore.systems.enchant_system.EnchantSystem;
import com.mc.basicore.events.*;
import com.mc.basicore.systems.mob_system.events.skeleton_sword;
import com.mc.basicore.recipes.furnace_dirt_gravel;
import com.mc.basicore.systems.teleport_system.*;
import com.mc.basicore.systems.teleport_system.events.onPlayerDeathOrReborn;
import com.mc.basicore.systems.tribeSystem.*;
import com.mc.basicore.systems.world_index.WorldIndex;
import org.bukkit.plugin.java.JavaPlugin;

import static com.mc.basicore.recipes.gunpowder.burned_gunPowder;

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
        /*============================vein collector system==============================*/
        getServer().getPluginManager().registerEvents(new VeinToggle(),this);
        getServer().getPluginManager().registerEvents(new TreeCutter(),this);
        getServer().getPluginManager().registerEvents(new ChainMiner(),this);
        getServer().getPluginManager().registerEvents(new onDispenserShot(),this);
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
        getCommand("tribe").setExecutor(new TribeCommand());
        getCommand("tribe").setTabCompleter(new TribeTabComplete());
    }

    @Override
    public void onDisable() {
    }
}
