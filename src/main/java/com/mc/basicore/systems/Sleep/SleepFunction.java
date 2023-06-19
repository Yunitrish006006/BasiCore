package com.mc.basicore.systems.Sleep;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.scheduler.BukkitRunnable;

public class SleepFunction extends BukkitRunnable {
    private final int minPlayers;

    public SleepFunction(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    private void refreshVillagerTrades() {
        Bukkit.getServer().getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
            if (entity.getType() == EntityType.VILLAGER) {
                Merchant merchant = (Merchant) entity;
                for (MerchantRecipe recipe:merchant.getRecipes()) {
                    recipe.setUses(0);
                }
            }
        }));
    }

    @Override
    public void run() {
        int sleepingPlayers = 0;
        World world = Bukkit.getWorld("world");

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
                refreshVillagerTrades();
                Bukkit.broadcastMessage("早安!");
                Bukkit.broadcastMessage("所有的村民商店已經刷新!");
            }
        } else {
            Bukkit.broadcastMessage("Not enough players sleeping to skip the night.");
        }
    }
}
