package com.mc.basicore.systems.collector_system.tools;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.collector_system.CollectorSet;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.mc.basicore.Basics.*;
import static com.mc.basicore.itemGroups.*;
import static org.bukkit.Material.*;
import static org.bukkit.block.BlockFace.*;
import static org.bukkit.block.BlockFace.WEST;

public class Shovel implements Listener {
    public static abstract class SoilData {
        public List<Material> blocks;
        public Float Range;
        public Float Height;
        public String Shape = "circle";
        public int Count = 0;
        public SoilData(List<Material> target, Float range, Float height) {
            blocks= target;
            Range = range;
            Height = height;
        }
    }
    public static List<Material> SoilTargets() {
        List<Material> temp = new ArrayList<>();
        for (SoilData mine: soilDataList) {
            temp.addAll(mine.blocks);
        }
        return temp;
    }
    public static List<SoilData> soilDataList = Arrays.asList(dirt(),sand(),gravel());
    public static SoilData dirt() {
        return new SoilData(Arrays.asList(
                DIRT,GRASS_BLOCK
        ), 5f, 5f) {};
    }
    public static SoilData sand() {
        return new SoilData(Collections.singletonList(
                SAND
        ), 5f, 5f) {};
    }
    public static SoilData gravel() {
        return new SoilData(Collections.singletonList(
                GRAVEL
        ), 5f, 5f) {};
    }
    @EventHandler
    public void onShoveling(BlockBreakEvent event) {
        Player player = event.getPlayer();
        CollectorSet set = CollectorSet.query(player);
        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        if (!(shovels().contains(tool.getType()) && set.data.get("shovel"))) return;
        if(!inBLockTypes(SoilTargets(),block.getType())) return;
        if (willBreak(tool)) {
            event.setCancelled(true);
            return;
        }

        if(!inBLockTypes(SoilTargets(),block.getType())) return;
        List<ItemStack> drops = new ArrayList<>();
        SoilData type = soilDataList.get(0);
        for (SoilData soil: soilDataList) {
            if(inBLockTypes(soil.blocks,block.getType())) {
                type = soil;
            }
        }
        vein(block,type,block.getLocation(),drops,event.getPlayer().getInventory().getItemInMainHand());
        Basics.useItem(event.getPlayer().getInventory().getItemInMainHand(),type.Count);
        for (ItemStack item:drops) {
            block.getWorld().dropItem(block.getLocation(),item);
        }
    }
    private void vein(Block block, SoilData targets, Location original_space, List<ItemStack> drops, ItemStack tool) {
        drops.addAll(block.getDrops(tool));
        block.setType(Material.AIR);
        for (Block relativeBlock : getAdjacentBlocks(block, original_space)) {
            if (getCubeDistance(relativeBlock.getLocation(),original_space) > targets.Height) continue;
            if (getFlatDistance(relativeBlock.getLocation(),original_space) > targets.Range) continue;
//            if (abs(relativeBlock.getLocation().getX()-original_space.getX()) > targets.Range) continue;
//            if (abs(relativeBlock.getLocation().getY()-original_space.getY()) > targets.Height) continue;
//            if (abs(relativeBlock.getLocation().getZ()-original_space.getZ()) > targets.Range) continue;
            if (!Basics.inBLockTypes(targets.blocks,relativeBlock.getType())) continue;
            targets.Count+=1;
            vein(relativeBlock,targets,original_space, drops, tool);
        }
    }
    private Block[] getAdjacentBlocks(Block block, Location original_space) {
        List<Block> adjacentBlocks = new ArrayList<>();
        List<BlockFace> faces = Arrays.asList(UP, DOWN, NORTH, SOUTH, EAST, WEST);
        for (BlockFace face:faces) {
            Block temp = block.getRelative(face);
            if (!temp.getType().equals(Material.AIR)) {
                adjacentBlocks.add(temp);
            }
        }
        return adjacentBlocks.toArray(new Block[adjacentBlocks.size()]);
    }
}
