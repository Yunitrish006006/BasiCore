package com.mc.basicore.systems.WorldManager;

import com.mc.basicore.BasiCore;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class worldManager{
    public static void registerWorld() {
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onWorldChange(),BasiCore.getPlugin());
        WorldCreator creator = new WorldCreator("newWorld");
        creator.environment(World.Environment.NORMAL);
        creator.generateStructures(true);
        creator.type(WorldType.NORMAL);
        World world = creator.createWorld();
    }
}
