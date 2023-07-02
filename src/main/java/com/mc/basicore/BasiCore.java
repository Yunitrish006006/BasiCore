package com.mc.basicore;

import com.mc.basicore.systems.AutoPuppetSystem.PlacerPuppet;
import com.mc.basicore.systems.Diet.DietSystem;
import com.mc.basicore.systems.Diet.ExtraFoodsEvent;
import com.mc.basicore.systems.EconomySystem.VillagerEvents;
import com.mc.basicore.systems.LockorSystem.LockorEvents;
import com.mc.basicore.systems.MobSystem.MobSystem;
import com.mc.basicore.systems.SkillSystem.*;
import com.mc.basicore.systems.TribeSystem.Tribe;
import com.mc.basicore.systems.WorldManager.worldManager;
import com.mc.basicore.systems.ChatSystem.*;
import com.mc.basicore.systems.ResourceSystem.*;
import com.mc.basicore.systems.MobSystem.events.onCreeperExplode;
import com.mc.basicore.systems.others.commands.*;
import com.mc.basicore.systems.enchant_system.EnchantSystem;
import com.mc.basicore.systems.others.events.*;
import com.mc.basicore.systems.others.recipes.furnace_dirt_gravel;
import com.mc.basicore.systems.teleport_system.*;
import com.mc.basicore.systems.teleport_system.events.onPlayerDeathOrReborn;
import com.mc.basicore.systems.translate.Translator;
import com.mc.basicore.systems.world_index.WorldIndex;
import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import static com.mc.basicore.systems.others.recipes.gunpowder.burned_gunPowder;

public final class BasiCore extends JavaPlugin {
    private static BasiCore plugin;
    public static String language = "zh_tw";
    public static JDA jda;
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
        Bukkit.getScheduler().cancelTasks(plugin);
        worldManager.registerNewWorld();
        this.saveDefaultConfig();
        Basics.init();
        ChatSet.chatInit();
        Translator.initFile();
        Tribe.init();
        AllEvents.init();
        /*======================discord bot============================*/
        discord.launchBot();
        /*==================enchant system register====================*/
        EnchantSystem.register();
        getServer().getPluginManager().registerEvents(new EnchantSystem(),this);
        /*==================register====================*/
        getServer().getPluginManager().registerEvents(new swordDash(),this);
//        getServer().getPluginManager().registerEvents(new SkillSet(),this);
        getCommand("puppet").setExecutor(new PlacerPuppet());
        getCommand("reset").setExecutor(new reset());
        getCommand("test").setExecutor(new test());
        getCommand("fly").setExecutor(new fly());
        getCommand("hat").setExecutor(new hat());
        getCommand("laugh").setExecutor(new laugh());
        getCommand("head").setExecutor(new head());
        getCommand("head").setTabCompleter(new head());
        ResourceSystem.init();
        /*============================other little system==============================*/
        getServer().getPluginManager().registerEvents(new VillagerEvents(),this);
        getServer().getPluginManager().registerEvents(new fireBall(),this);
        getServer().getPluginManager().registerEvents(new arrow(),this);
        getServer().getPluginManager().registerEvents(new onCreeperExplode(),this);
        getServer().getPluginManager().registerEvents(new ExtraFoodsEvent(),this);
        getServer().getPluginManager().registerEvents(new onPlayerJoin(),this);
        getServer().getPluginManager().registerEvents(new OnPlayerInteractBeeHive(),this);
        ExtraFoodsEvent.add_recipes();
        furnace_dirt_gravel.dirt_gravel();
        burned_gunPowder();
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("chat").setTabCompleter(new ChatTabComplete());
        getServer().getPluginManager().registerEvents(new ChatEvents(),this);
        getCommand("space").setExecutor(new TeleportCommand());
        getCommand("space").setTabCompleter(new TeleportTabComplete());
        getServer().getPluginManager().registerEvents(new WorldIndex(),this);
        getServer().getPluginManager().registerEvents(new onPlayerDeathOrReborn(),this);
        /*=============================================test=================================================*/
        MobSystem.init();
        DietSystem.initializeDiet();
        getServer().getPluginManager().registerEvents(new LockorEvents(),this);
    }

    @Override
    public void onDisable() {
        discord.shutDownBot();
    }
}
