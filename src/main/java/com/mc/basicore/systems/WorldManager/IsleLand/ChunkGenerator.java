package com.mc.basicore.systems.WorldManager.IsleLand;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ChunkGenerator extends org.bukkit.generator.ChunkGenerator {
    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        int bottom = chunkData.getMinHeight()+65;
        for(int y = bottom;y < chunkData.getMaxHeight(); y++) {
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    int value = random.nextInt(100);
                    if (chunkData.getType(x,y-1,z).equals(Material.AIR) && y>bottom+3) value = 1;
                    else if (chunkData.getType(x,y-1,z).equals(Material.STONE) || chunkData.getType(x,y-1,z).equals(Material.DIRT)) value=6;
                    if (value < 2) {
                        chunkData.setBlock(x, y, z, Material.AIR);
                    }
                    else if (value<5) {
                        chunkData.setBlock(x, y, z, Material.STONE);
                    }
                    else if (value<8) {
                        chunkData.setBlock(x, y, z, Material.DIRT);
                    }
                }
            }
        }
    }
}
