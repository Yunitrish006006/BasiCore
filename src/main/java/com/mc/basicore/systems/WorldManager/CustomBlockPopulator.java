//package com.mc.basicore.systems.WorldManager;
//
//import org.bukkit.Chunk;
//import org.bukkit.Material;
//import org.bukkit.World;
//import org.bukkit.generator.BlockPopulator;
//
//import java.util.Random;
//
//public class CustomBlockPopulator extends BlockPopulator {
//
//    @Override
//    public void populate(World world, Random random, Chunk chunk) {
//        int chunkX = chunk.getX() << 4; // 将区块坐标转换为方块坐标
//        int chunkZ = chunk.getZ() << 4;
//
//        // 填充生态系统的代码
//        for (int x = chunkX; x < chunkX + 16; x++) {
//            for (int z = chunkZ; z < chunkZ + 16; z++) {
//                int y = getHighestBlockY(world, x, z); // 获取最高方块的Y坐标
//
//                // 在最高方块上放置特定的方块
//                world.getBlockAt(x, y, z).setType(Material.GRASS_BLOCK);
//                world.getBlockAt(x, y + 1, z).setType(Material.OAK_SAPLING);
//                // 添加其他的方块设置...
//            }
//        }
//    }
//
//    private int getHighestBlockY(World world, int x, int z) {
//        int y = world.getHighestBlockYAt(x, z);
//        while (world.getBlockAt(x, y - 1, z).getType() == Material.AIR) {
//            y--;
//        }
//        return y;
//    }
//}
