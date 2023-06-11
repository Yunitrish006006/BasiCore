package com.mc.basicore.systems.collector_system;

import com.mc.basicore.Basics;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class CollectorSet{
    public static FileConfiguration config = Basics.config;
    public boolean pickaxe = false;
    public boolean shovel = false;
    public boolean axe = false;
    public boolean hoe = false;
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
            set.pickaxe = section.getBoolean(".CollectorSet.pickaxe");
            set.shovel = section.getBoolean(".CollectorSet.shovel");
            set.axe = section.getBoolean(".CollectorSet.axe");
            set.hoe = section.getBoolean(".CollectorSet.hoe");
            set.save();
        }
        else {
            set.save();
        }
        return set;
    }
    public void save() {
        String prefix = self.getUniqueId()+".CollectorSet.";
        config.set(prefix+"pickaxe",pickaxe);
        config.set(prefix+"shovel",shovel);
        config.set(prefix+"axe",axe);
        config.set(prefix+"hoe",hoe);
        Basics.saveFile();
    }
    public void reset() {
        pickaxe = false;
        shovel = false;
        axe = false;
        hoe = false;
        save();
    }
}
