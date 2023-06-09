//package com.mc.basicore.systems.WorldManager;
//
//import org.bukkit.World;
//import org.bukkit.WorldCreator;
//import org.bukkit.WorldType;
//import org.bukkit.generator.BlockPopulator;
//import org.bukkit.generator.ChunkGenerator;
//import org.bukkit.plugin.java.JavaPlugin;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//public class FloatIsland extends ChunkGenerator {
//
//    private JavaPlugin plugin;
//
//    public FloatIsland(JavaPlugin plugin) {
//        this.plugin = plugin;
//    }
//
//    @Override
//    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
//        return createChunkData(world);
//    }
//
//    @Override
//    public boolean shouldGenerateStructures() {
//        return false;
//    }
//
//    @Override
//    public List<BlockPopulator> getDefaultPopulators(World world) {
//        return Arrays.asList(new CustomBlockPopulator());
//    }
//
//    public static void registerWorld(JavaPlugin plugin) {
//        WorldCreator creator = new WorldCreator("skyblockWorld");
//        creator.environment(World.Environment.NORMAL);
//        creator.generateStructures(false);
//        creator.type(WorldType.NORMAL);
//        creator.generator(new FloatIsland(plugin));
//        World world = creator.createWorld();
//    }
//}
