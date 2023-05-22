package com.mc.basicore.collector_system;

import com.mc.basicore.Basics;
import com.mc.basicore.itemGroups;
import com.sun.tools.javac.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class TreeCutter implements Listener {

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material blockType = block.getType();
        for (List<Material> tree: itemGroups.Trees()) {
            if(Basics.inBLockTypes(tree,blockType)) {
                cutTree(block,tree,block.getLocation());
            }
        }
    }

    private void cutTree(Block block, List<Material> treeBlocks, Location original_space) {
        block.breakNaturally();
        for (Block relativeBlock : getAdjacentBlocks(block)) {
            double RangeX = Math.abs(block.getLocation().getX() - original_space.getX());
            double RangeZ = Math.abs(block.getLocation().getZ() - original_space.getZ());
            if (Basics.inBLockTypes(treeBlocks,relativeBlock.getType())
            && RangeX < 2
            && RangeZ < 2
            ) {
//                Bukkit.broadcastMessage("Range: " + "("+RangeX + "," + RangeZ+"),Type:"+relativeBlock.getType());
                cutTree(relativeBlock,treeBlocks,original_space);
            }
        }
    }

    private Block[] getAdjacentBlocks(Block block) {
        Block[] adjacentBlocks = new Block[6];
        adjacentBlocks[0] = block.getRelative(BlockFace.UP);
        adjacentBlocks[1] = block.getRelative(BlockFace.DOWN);
        adjacentBlocks[2] = block.getRelative(BlockFace.NORTH);
        adjacentBlocks[3] = block.getRelative(BlockFace.SOUTH);
        adjacentBlocks[4] = block.getRelative(BlockFace.EAST);
        adjacentBlocks[5] = block.getRelative(BlockFace.WEST);
        return adjacentBlocks;
    }

}