package com.mc.basicore.systems.others.events;

import com.mc.basicore.Basics;
import com.mc.basicore.itemGroups;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

public class onDispenserShot implements Listener {
    @EventHandler
    public void onShot(BlockDispenseEvent event) {
        DTechMinor(event);
    }
    private void DTechMinor(BlockDispenseEvent event) {
        if (!(event.getBlock().getState() instanceof Dispenser)) return;
        Dispenser dispenser = (Dispenser) event.getBlock().getState();
        if (!itemGroups.tools().contains(event.getItem().getType())) return;
        event.setCancelled(true);
        ItemStack tool = event.getItem();
        BlockFace facing = ((Directional) dispenser.getLocation().getBlock().getBlockData()).getFacing();
        Location target = dispenser.getLocation().add(facing.getDirection());
        if (target.getBlock().getType().equals(Material.AIR)) {
            event.setCancelled(true);
        }
        else {
            Basics.useItem(tool,1);
            target.getBlock().breakNaturally(tool);
            Basics.playSound(target, Sound.BLOCK_STONE_BREAK);
        }
    }
//    @EventHandler
//    public void onInventoryClick(InventoryClickEvent event) {
//        int clickedSlot = event.getRawSlot();
//        Player player = (Player) event.getWhoClicked();
//        if (clickedSlot <= player.getInventory().getSize()) {
//            sortInventory(player.getInventory());
//        }
//    }
//
//    private void sortInventory(Inventory inventory) {
//        ItemStack[] contents = inventory.getContents();
//        Arrays.sort(contents, new ItemStackComparator());
//        inventory.setContents(contents);
//    }
//
//    private static class ItemStackComparator implements Comparator<ItemStack> {
//        @Override
//        public int compare(ItemStack item1, ItemStack item2) {
//            if (item1 == null && item2 == null) {
//                return 9;
//            }
//            if (item1 == null) {
//                return 1;
//            }
//            if (item2 == null) {
//                return -1;
//            }
//            return item1.getType().compareTo(item2.getType());
//        }
//    }
}
