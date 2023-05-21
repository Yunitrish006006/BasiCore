package com.mc.basicore.collector_system;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class TreeCutter implements Listener {

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material blockType = block.getType();

        if (blockType == Material.OAK_LOG || blockType == Material.OAK_LEAVES) {
            cutTree(block);
        }
    }

    private void cutTree(Block block) {
        block.breakNaturally();
        for (Block relativeBlock : getAdjacentBlocks(block)) {
            if (relativeBlock.getType() == Material.OAK_LOG || relativeBlock.getType() == Material.OAK_LEAVES) {
                cutTree(relativeBlock);
            }
        }
    }

    private List<Block> getAdjacentBlocks(Block block) {
        List<Block> blocks = new ArrayList<>();
        blocks.add(block.getRelative(BlockFace.EAST));
        blocks.add(block.getRelative(BlockFace.WEST));
        blocks.add(block.getRelative(BlockFace.SOUTH));
        blocks.add(block.getRelative(BlockFace.NORTH));
        return null;
    }
}