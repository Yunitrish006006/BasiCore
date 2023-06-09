package com.mc.basicore.systems.ResourceSystem.tools;
import static com.mc.basicore.Basics.*;
import static com.mc.basicore.itemGroups.*;
import static java.lang.Math.abs;
import static org.bukkit.block.BlockFace.*;

import com.mc.basicore.Basics;
import com.mc.basicore.itemGroups;
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
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pickaxe implements Listener {
    @EventHandler
    public void Minor(BlockBreakEvent event) {
        Player player = event.getPlayer();
        CollectorSet set = CollectorSet.query(player.getUniqueId());
        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        if (!(pickaxes().contains(tool.getType()) && set.stats.get("pickaxe"))) return;
        if(!inBLockTypes(itemGroups.MineTargets(),block.getType())) return;

        if (!(tool.getItemMeta() instanceof Damageable)) return;
        if (((Damageable) tool.getItemMeta()).getDamage() > tool.getType().getMaxDurability()-2)  {
            event.setCancelled(true);
            return;
        }


        if(!inBLockTypes(itemGroups.MineTargets(),block.getType())) return;
        List<ItemStack> drops = new ArrayList<>();
        MineData type = Mines().get(0);
        for (MineData mine: Mines()) {
            if(inBLockTypes(mine.blocks,block.getType())) {
                type = mine;
            }
        }
        vein(block,type,block.getLocation(),drops,event.getPlayer().getInventory().getItemInMainHand());
        Basics.useItem(event.getPlayer().getInventory().getItemInMainHand(),type.getDurabilityCost());
        for (ItemStack item:drops) {
            block.getWorld().dropItem(block.getLocation(),item);
        }
    }
    private void vein(Block block, MineData targets, Location original_space, List<ItemStack> drops, ItemStack tool) {
        drops.addAll(block.getDrops(tool));
        block.setType(Material.AIR);
        for (Block relativeBlock : getAdjacentBlocks(block, original_space)) {
//            if (getCubeDistance(relativeBlock.getLocation(),original_space) > targets.Height) continue;
//            if (getFlatDistance(relativeBlock.getLocation(),original_space) > targets.Range) continue;
            if (abs(relativeBlock.getLocation().getX()-original_space.getX()) > targets.Range) continue;
            if (abs(relativeBlock.getLocation().getY()-original_space.getY()) > targets.Height) continue;
            if (abs(relativeBlock.getLocation().getZ()-original_space.getZ()) > targets.Range) continue;
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
