package com.mc.basicore.systems.teleport_system.events;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.teleport_system.SpaceUnit;
import com.mc.basicore.systems.world_index.WorldIndex;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.time.LocalTime;

public class onPlayerDeathOrReborn implements Listener {
    @EventHandler
    public static void addDeathPoint(PlayerDeathEvent event) {
        SpaceUnit spaceUnit = SpaceUnit.create("["+LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond()+"] died",event.getEntity());
        spaceUnit.saveUnit();
        Player player = event.getEntity();
        Bukkit.getScheduler().runTaskLater(BasiCore.getPlugin(), () -> player.getInventory().addItem(WorldIndex.worldIndex(player.getLocale())), 20L);
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item item = event.getEntity();
        ItemStack itemStack = item.getItemStack();
        if (Basics.getID(itemStack).equals(Basics.getID(WorldIndex.worldIndex("en_us")))){
            item.remove();
        }
    }
}
