package com.mc.basicore;

import com.mc.basicore.systems.AutoPuppetSystem.PlacerPuppet;
import com.mc.basicore.systems.Diet.DietSystem;
import com.mc.basicore.systems.Diet.ExtraFoodsEvent;
import com.mc.basicore.systems.LockorSystem.LockorEvents;
import com.mc.basicore.systems.SkillSystem.fireBall;
import com.mc.basicore.systems.TribeSystem.Tribe;
import com.mc.basicore.systems.WorldManager.worldManager;
import com.mc.basicore.systems.chat_system.*;
import com.mc.basicore.systems.collector_system.*;
import com.mc.basicore.systems.mob_system.events.onCreeperExplode;
import com.mc.basicore.systems.others.commands.*;
import com.mc.basicore.systems.enchant_system.EnchantSystem;
import com.mc.basicore.systems.others.events.*;
import com.mc.basicore.systems.others.recipes.furnace_dirt_gravel;
import com.mc.basicore.systems.teleport_system.*;
import com.mc.basicore.systems.teleport_system.events.onPlayerDeathOrReborn;
import com.mc.basicore.systems.translate.Translator;
import com.mc.basicore.systems.world_index.WorldIndex;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
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
        try {
            jda = JDABuilder.createDefault(discord.discordToken,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.SCHEDULED_EVENTS
            )
            .addEventListeners(new discord())
            .disableCache(CacheFlag.VOICE_STATE)
            .build();
            BasiCore.getPlugin().getLogger().info("Discord bot started successfully!");
        } catch (Exception e) {
            BasiCore.getPlugin().getLogger().severe("Failed to start Discord bot!");
            e.printStackTrace();
        }
        /*==================enchant system register====================*/
        EnchantSystem.register();
        getServer().getPluginManager().registerEvents(new EnchantSystem(),this);
        /*==================register====================*/
        getServer().getPluginManager().registerEvents(new skill_system(),this);
        getCommand("puppet").setExecutor(new PlacerPuppet());
        getCommand("reset").setExecutor(new reset());
        getCommand("test").setExecutor(new test());
        getCommand("fly").setExecutor(new fly());
        getCommand("hat").setExecutor(new hat());
        getCommand("laugh").setExecutor(new laugh());
        getCommand("head").setExecutor(new head());
        getCommand("head").setTabCompleter(new head());
        collector.init();
        /*============================other little system==============================*/
        getServer().getPluginManager().registerEvents(new fireBall(),this);
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
        getCommand("space").setExecutor(new TeleportCommand());
        getCommand("space").setTabCompleter(new TeleportTabComplete());
        getServer().getPluginManager().registerEvents(new WorldIndex(),this);
        getServer().getPluginManager().registerEvents(new onPlayerDeathOrReborn(),this);
        /*=============================================test=================================================*/
        DietSystem.initializeDiet();
        getServer().getPluginManager().registerEvents(new LockorEvents(),this);
    }

    @Override
    public void onDisable() {
        if (jda != null) {
            jda.shutdown();
            BasiCore.getPlugin().getLogger().info("Discord bot stopped!");
        }
    }
}
