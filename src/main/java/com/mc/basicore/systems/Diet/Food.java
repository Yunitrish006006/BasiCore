package com.mc.basicore.systems.Diet;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;

public class Food {
    public ItemStack item;
    public PotionMeta meta;
    public static ShapelessRecipe recipe;
    public void Recipe() {}
    public ItemStack generate() {return item;}
}
