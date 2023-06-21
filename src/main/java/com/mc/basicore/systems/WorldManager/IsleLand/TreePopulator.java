package com.mc.basicore.systems.WorldManager.IsleLand;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TreePopulator extends BlockPopulator {
    private final HashMap<Biome, List<TreeType>> biomeTrees = new HashMap<Biome, List<TreeType>>() {{
        put(Biome.PLAINS, Arrays.asList());
        put(Biome.FOREST, Arrays.asList(TreeType.BIRCH));
        put(Biome.DARK_FOREST, Arrays.asList(TreeType.DARK_OAK));
    }};

    @Override
    public void populate(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, LimitedRegion limitedRegion) {
        int x = random.nextInt(16) + chunkX * 16;
        int z = random.nextInt(16) + chunkZ * 16;
        int y = 319;
        while(limitedRegion.getType(x, y, z).isAir() && y > -64) y--;

        Location location = new Location(Bukkit.getWorld(worldInfo.getUID()), x, y, z);
        List<TreeType> trees = biomeTrees.getOrDefault(limitedRegion.getBiome(location), Arrays.asList(TreeType.TREE, TreeType.BIRCH));

        if (trees.size() > 0 && limitedRegion.getType(x, y - 1, z).isSolid()) {
            limitedRegion.generateTree(location, random, trees.get(random.nextInt(trees.size())));
        }
    }
}
