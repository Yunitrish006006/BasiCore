package com.mc.basicore.Sleep;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SleepFunction extends BukkitRunnable {
    private int minPlayers;

    public SleepFunction(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    @Override
    public void run() {
        int sleepingPlayers = 0;
        World world = Bukkit.getWorld("world");  // Replace "world" with your desired world name

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld() == world && player.isSleeping()) {
                sleepingPlayers++;
            }
        }

        if (sleepingPlayers >= minPlayers) {
            assert world != null;
            long time = world.getTime();
            if (time > 12541 && time < 23458) {
                world.setTime(23458);
                Bukkit.broadcastMessage("Good morning!");
            }
        } else {
            // Not enough players sleeping
            Bukkit.broadcastMessage("Not enough players sleeping to skip the night.");
        }
    }
}
