package com.mc.basicore.systems.Diet.Foods;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.Diet.Food;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

import static com.mc.basicore.Basics.*;
import static com.mc.basicore.systems.translate.Translator.translate;

public class Cocoa extends Food implements Listener {
    public ItemStack item;
    public PotionMeta meta;
    public static ShapelessRecipe recipe;
    String ID = "BasiCore.Cocoa";
    public ItemStack generate() {
        Cocoa temp = new Cocoa();
        temp.meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, getRandomInt(600),0,false,false,false) ,true);
        temp.meta.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION,PotionEffect.INFINITE_DURATION,getRandomInt(4),false,false,false) ,true);
        temp.item.setItemMeta(temp.meta);
        return temp.item;
    }
    public Cocoa() {
        item = new ItemStack(Material.POTION);
        meta = (PotionMeta) item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName(ID);
        meta.setCustomModelData(1);
        meta.setDisplayName(translate(BasiCore.language,"foods.cocoa.name"));
        meta.setColor(Color.MAROON);
        meta.setLore(Arrays.asList(
                translate(BasiCore.language,"foods.cocoa.lore0"),
                translate(BasiCore.language,"foods.cocoa.lore1")
        ));
        item.setItemMeta(meta);
    }
    @EventHandler
    public void trigger(PlayerItemConsumeEvent event) {
        if (!event.getItem().hasItemMeta()) return;
        if (!getID(event.getItem()).equals(ID)) return;
        event.setCancelled(false);
    }
    @EventHandler
    public void onMake(CraftItemEvent event) {
        if (event.getCurrentItem()==null) return;
        ItemStack result = event.getCurrentItem();
        if (!getID(result).equals(ID)) return;
        if (event.isShiftClick()) {
            event.setCancelled(true);
            return;
        }
        event.setCurrentItem(generate());
    }
    public void Recipe() {
        recipe = new ShapelessRecipe(new NamespacedKey(BasiCore.getPlugin(),"foods.cocoa"),new Cocoa().item);
        recipe.addIngredient(Material.SUGAR);
        recipe.addIngredient(Material.GLASS_BOTTLE);
        recipe.addIngredient(2,Material.COCOA_BEANS);
        Bukkit.getServer().addRecipe(recipe);
    }
}
