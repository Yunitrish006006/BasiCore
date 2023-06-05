package com.mc.basicore.systems.teleport_system.events;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.teleport_system.SpaceUnit;
import com.mc.basicore.systems.world_index.WorldIndex;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.time.LocalTime;
import java.util.Objects;

public class onPlayerDeathOrReborn implements Listener {
    @EventHandler
    public static void addDeathPoint(PlayerDeathEvent event) {
        SpaceUnit spaceUnit = SpaceUnit.create("["+LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond()+"] died",event.getEntity());
        spaceUnit.addUnit();
        Bukkit.getScheduler().runTaskLater(BasiCore.getPlugin(), () -> Objects.requireNonNull(event.getEntity().getPlayer()).getInventory().addItem(new WorldIndex()), 20L);
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item item = event.getEntity();
        ItemStack itemStack = item.getItemStack();
        if (Basics.getID(itemStack).equals(Basics.getID(new WorldIndex()))) {
            item.remove();
        }
    }
}