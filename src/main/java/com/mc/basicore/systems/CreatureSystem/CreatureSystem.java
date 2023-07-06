package com.mc.basicore.systems.CreatureSystem;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.CreatureSystem.events.ZombieHorseEvent;
import com.mc.basicore.systems.CreatureSystem.events.onLiftRide;
import com.mc.basicore.systems.CreatureSystem.events.onVillagerLeashed;
import com.mc.basicore.systems.FileSystem.FileSet;
import com.mc.basicore.systems.CreatureSystem.events.ZombieVariety;
import org.bukkit.entity.EntityType;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreatureSystem {
    public static FileSet mobSet = new FileSet("MobSet.yml");
    public static void init() {
        mobSet.data.set("canRide", Stream.of(
                EntityType.BEE,EntityType.PHANTOM,EntityType.DOLPHIN,EntityType.ZOMBIE_HORSE,EntityType.SKELETON_HORSE
        ).map(entityType -> entityType.getTranslationKey().split("\\.")[2]).collect(Collectors.toList()));
        mobSet.data.set("canLift", Stream.of(
                EntityType.AXOLOTL,EntityType.ARMOR_STAND,
                EntityType.LLAMA,EntityType.PANDA,EntityType.COW,EntityType.SHEEP,EntityType.ALLAY,EntityType.CAT,EntityType.WOLF,EntityType.FOX,EntityType.FROG
        ).map(entityType -> entityType.getTranslationKey().split("\\.")[2]).collect(Collectors.toList()));
        mobSet.save();
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new ZombieVariety(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onLiftRide(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onVillagerLeashed(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new ZombieHorseEvent(),BasiCore.getPlugin());
    }
}
