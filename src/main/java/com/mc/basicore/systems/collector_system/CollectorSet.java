package com.mc.basicore.systems.collector_system;

import com.mc.basicore.Basics;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

@SuppressWarnings("ConstantConditions")
public class CollectorSet{
    public static String PICKAXE = "pickaxe";
    public static String SHOVEL = "shovel";
    public static String AXE = "axe";
    public static String HOE = "hoe";

    public static FileConfiguration config = Basics.config;
    public Map<String,Boolean> data = new HashMap<String, Boolean>() {{
        put(PICKAXE, false);
        put(SHOVEL, false);
        put(AXE, false);
        put(HOE, false);
    }};
    public Player self = null;
    public UUID ID = Basics.errorID();
    public CollectorSet(){}
    public CollectorSet(Player player) {
        self = player;
        ID = self.getUniqueId();
    }
    public static CollectorSet query(Player player) {
        CollectorSet set = new CollectorSet();
        if (!Basics.UUIDS().contains(player.getUniqueId().toString())) return set;
        set.self = player;
        set.ID = player.getUniqueId();
        ConfigurationSection section = config.getConfigurationSection(player.getUniqueId().toString());
        if (section.getKeys(false).contains("CollectorSet")) {
            section = section.getConfigurationSection(".CollectorSet.");
            set.data.put(PICKAXE,section.getBoolean(".pickaxe"));
            set.data.put(SHOVEL,section.getBoolean(".shovel"));
            set.data.put(AXE,section.getBoolean(".axe"));
            set.data.put(HOE,section.getBoolean(".hoe"));
            set.save();
        }
        set.save();
        return set;
    }
    public void toggle(String value) {
        if (data.containsKey(value)) data.put(value,!data.get(value));
    }
    public void save() {
        String prefix = self.getUniqueId()+".CollectorSet.";
        config.set(prefix+PICKAXE,data.get(PICKAXE));
        config.set(prefix+SHOVEL,data.get(SHOVEL));
        config.set(prefix+AXE,data.get(AXE));
        config.set(prefix+HOE,data.get(HOE));
        Basics.saveFile();
    }
    public void reset() {
        data = new HashMap<String, Boolean>() {{
            put(PICKAXE, false);
            put(SHOVEL, false);
            put(AXE, false);
            put(HOE, false);
        }};
        save();
    }
}
