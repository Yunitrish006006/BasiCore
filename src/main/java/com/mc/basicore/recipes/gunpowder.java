package com.mc.basicore.recipes;

import com.mc.basicore.BasiCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class gunpowder {
    public static void burned_gunPowder() {
        FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(BasiCore.getPlugin(),"coal_to_gunPowder"),new ItemStack(Material.GUNPOWDER),Material.COAL,1.0f,40);
        FurnaceRecipe recipe2 = new FurnaceRecipe(new NamespacedKey(BasiCore.getPlugin(),"charcoal_to_gunPowder"),new ItemStack(Material.GUNPOWDER),Material.CHARCOAL,1.0f,40);
        Bukkit.getServer().addRecipe(recipe);
        Bukkit.getServer().addRecipe(recipe2);
    }
}
