package com.mc.basicore.systems.ResourceSystem;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.FileSystem.FileSet;
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

    public static FileSet fileSet = new FileSet("CollectorSet.yml");
    public static FileConfiguration config = fileSet.data;
    public Map<String,Boolean> stats = new HashMap<String, Boolean>() {{
        put(PICKAXE, false);
        put(SHOVEL, false);
        put(AXE, false);
        put(HOE, false);
    }};
    public UUID ID = Basics.errorID;
    public CollectorSet(){}
    public CollectorSet(Player player) {
        ID = player.getUniqueId();
    }
    public static CollectorSet query(Player player) {
        CollectorSet set = new CollectorSet();
        if (!fileSet.data.contains(player.getUniqueId().toString())) return set;
        set.ID = player.getUniqueId();
        ConfigurationSection section = config.getConfigurationSection(player.getUniqueId().toString());
        if (section.getKeys(false).contains("CollectorSet")) {
            section = section.getConfigurationSection(".CollectorSet.");
            set.stats.put(PICKAXE,section.getBoolean(".pickaxe"));
            set.stats.put(SHOVEL,section.getBoolean(".shovel"));
            set.stats.put(AXE,section.getBoolean(".axe"));
            set.stats.put(HOE,section.getBoolean(".hoe"));
            set.save();
        }
        set.save();
        return set;
    }
    public static CollectorSet query(UUID id) {
        CollectorSet set = new CollectorSet();
        if (!fileSet.data.contains(id.toString())) {set = create(id); return set;}
        ConfigurationSection section = config.getConfigurationSection(id.toString());
        set.ID = id;
        set.stats.put(PICKAXE,section.getBoolean(".pickaxe"));
        set.stats.put(SHOVEL,section.getBoolean(".shovel"));
        set.stats.put(AXE,section.getBoolean(".axe"));
        set.stats.put(HOE,section.getBoolean(".hoe"));
        set.save();
        return set;
    }
    public static CollectorSet create(UUID uuid) {
        CollectorSet set = new CollectorSet();
        set.ID = uuid;
        set.reset();
        set.save();
        return set;
    }

    public void toggle(String value) {
        if (stats.containsKey(value)) stats.put(value,!stats.get(value));
    }
    public void save() {
        String prefix = ID+".";
        config.set(prefix+PICKAXE, stats.get(PICKAXE));
        config.set(prefix+SHOVEL, stats.get(SHOVEL));
        config.set(prefix+AXE, stats.get(AXE));
        config.set(prefix+HOE, stats.get(HOE));
        fileSet.save();
    }
    public void reset() {
        stats = new HashMap<String, Boolean>() {{
            put(PICKAXE, false);
            put(SHOVEL, false);
            put(AXE, false);
            put(HOE, false);
        }};
        save();
    }
}
