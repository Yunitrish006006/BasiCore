package com.mc.basicore.collector_system;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.itemGroups;
import com.mc.basicore.itemGroups.TreeStructure;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.mc.basicore.Basics.*;
import static com.mc.basicore.itemGroups.*;

public class TreeCutter implements Listener {
    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        if (!axes().contains(event.getPlayer().getInventory().getItemInMainHand().getType())) return;
        Block block = event.getBlock();
        Material blockType = block.getType();
        if(!inBLockTypes(itemGroups.Stems(),blockType)) return;
        List<ItemStack> drops = new ArrayList<>();
        TreeStructure type = Trees().get(0);
        for (TreeStructure tree: Trees()) {
            if(inBLockTypes(tree.Stems,blockType)) {
                type = tree;
            }
        }
        cutTree(block,type,block.getLocation(),drops,event.getPlayer().getInventory().getItemInMainHand());
        Basics.useItem(event.getPlayer().getInventory().getItemInMainHand(),type.getDurabilityCost());
        for (ItemStack item:drops) {
            block.getWorld().dropItem(block.getLocation(),item);
        }
    }
    private void cutTree(Block block, TreeStructure treeBlocks, Location original_space, List<ItemStack> drops, ItemStack tool) {
        drops.addAll(block.getDrops(tool));
        block.setType(Material.AIR);
        for (Block relativeBlock : getAdjacentBlocks(block, treeBlocks,original_space)) {
            if(getFlatDistance(relativeBlock.getLocation(),original_space) < treeBlocks.Range) {
                if(inBLockTypes(treeBlocks.Stems,relativeBlock.getType())){
                    treeBlocks.stemCount+=1;
                    cutTree(relativeBlock,treeBlocks,original_space, drops, tool);
                }
                else if(inBLockTypes(treeBlocks.Bushes,relativeBlock.getType())) {
                    treeBlocks.bushCount+=1;
                    cutTree(relativeBlock,treeBlocks,original_space, drops, tool);
                }
            }
        }
    }
    private Block[] getAdjacentBlocks(Block block, TreeStructure treeBlocks, Location original_space) {
        List<Block> adjacentBlocks = new ArrayList<>();
        adjacentBlocks.add(block.getRelative(BlockFace.UP));
        adjacentBlocks.add(block.getRelative(BlockFace.DOWN));
        adjacentBlocks.add(block.getRelative(BlockFace.NORTH));
        adjacentBlocks.add(block.getRelative(BlockFace.SOUTH));
        adjacentBlocks.add(block.getRelative(BlockFace.EAST));
        adjacentBlocks.add(block.getRelative(BlockFace.WEST));
        return adjacentBlocks.toArray(new Block[adjacentBlocks.size()]);
    }
}