package com.mc.basicore.systems.WorldManager;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.WorldManager.IsleLand.ChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class worldManager{
    public static void registerNewWorld() {
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onWorldChange(),BasiCore.getPlugin());
        createNewWorld();
    }
    public static void createNewWorld() {
        WorldCreator creator = new WorldCreator("newWorld");
        creator.environment(World.Environment.NORMAL);
        creator.generateStructures(true);
        creator.type(WorldType.NORMAL);
        World world = creator.createWorld();
    }
    public static void createIsleLand() {
        WorldCreator creator = new WorldCreator("IsleLand");
        creator.generator(new ChunkGenerator());
        creator.type(WorldType.AMPLIFIED);
        creator.environment(World.Environment.NORMAL);
        creator.generateStructures(true);
        Bukkit.createWorld(creator);
    }
}
