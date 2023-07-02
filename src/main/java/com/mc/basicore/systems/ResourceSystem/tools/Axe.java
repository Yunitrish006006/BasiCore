package com.mc.basicore.systems.ResourceSystem.tools;

import com.mc.basicore.Basics;
import com.mc.basicore.itemGroups;
import com.mc.basicore.itemGroups.TreeStructure;
import com.mc.basicore.systems.ResourceSystem.CollectorSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.mc.basicore.Basics.*;
import static com.mc.basicore.itemGroups.*;

public class Axe implements Listener {
    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        CollectorSet set = CollectorSet.query(player);
        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        if (!(axes().contains(tool.getType()) && set.data.get("axe"))) return;
        if(!inBLockTypes(itemGroups.Stems(),block.getType())) return;

        List<ItemStack> drops = new ArrayList<>();
        TreeStructure type = Trees().get(0);
        for (TreeStructure tree: Trees()) {
            if(inBLockTypes(tree.Stems,block.getType())) {
                type = tree;
            }
        }
        cutTree(block,type,block.getLocation(),drops,tool);
        Basics.useItem(tool,type.getDurabilityCost());
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