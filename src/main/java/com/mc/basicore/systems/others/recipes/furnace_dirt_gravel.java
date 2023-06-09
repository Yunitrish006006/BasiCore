package com.mc.basicore.systems.others.recipes;

import com.mc.basicore.BasiCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class furnace_dirt_gravel {
    public static void dirt_gravel() {
        FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(BasiCore.getPlugin(),"dirt_gravel"),new ItemStack(Material.GRAVEL),Material.DIRT,1.0f,60);
        Bukkit.getServer().addRecipe(recipe);
    }
}
