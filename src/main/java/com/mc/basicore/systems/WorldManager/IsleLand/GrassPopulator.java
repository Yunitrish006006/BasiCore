package com.mc.basicore.systems.WorldManager.IsleLand;

import org.bukkit.Material;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class GrassPopulator extends BlockPopulator {
    @Override
    public void populate(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, LimitedRegion limitedRegion) {
        for(int iteration = 0; iteration < 10; iteration++) {
            int x = random.nextInt(16) + chunkX * 16;
            int z = random.nextInt(16) + chunkZ * 16;
            int y = 319;
            while(limitedRegion.getType(x, y, z).isAir() && y > -64) y--;

            if(limitedRegion.getType(x, y, z).isSolid()) {
                limitedRegion.setType(x, y + 1, z, Material.GRASS);
            }
        }
    }
}
