package com.mc.basicore.systems.Diet;

import com.mc.basicore.itemGroups;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class ExtraFoodsEvent implements Listener {
    @EventHandler
    public void onEatingExtraFoods(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(Objects.requireNonNull(item.getItemMeta()).hasLocalizedName()) {
            String ID = item.getItemMeta().getLocalizedName();
            switch (ID) {
                case "item.minecraft.apple_pie":
                    int random = (int)(Math.random()*120);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,random,0,true));
                    break;
                case "enchanted_golden_apple":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,20*60*3,0,true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*60*3,0,true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,20*60*3,3,true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*18,2,true));
                    break;
                default:
                    break;
            }
        }
    }
    public static void add_recipes() {
        apple_pie_recipe();
    }
    private static void apple_pie_recipe() {
        ShapelessRecipe apple_pie_recipe = new ShapelessRecipe(NamespacedKey.minecraft("apple_pie"), itemGroups.apple_pie());
        apple_pie_recipe.addIngredient(Material.EGG);
        apple_pie_recipe.addIngredient(Material.SUGAR);
        apple_pie_recipe.addIngredient(Material.WHEAT);
        apple_pie_recipe.addIngredient(Material.APPLE);
        Bukkit.getServer().addRecipe(apple_pie_recipe);
    }
}
