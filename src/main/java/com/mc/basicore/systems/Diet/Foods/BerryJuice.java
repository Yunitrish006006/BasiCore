package com.mc.basicore.systems.Diet.Foods;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.Diet.Food;
import org.bukkit.*;
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

import static com.mc.basicore.systems.translate.Translator.translate;

public class BerryJuice extends Food implements Listener {
    public ItemStack item;
    public PotionMeta meta;
    public static ShapelessRecipe recipe;
    public ItemStack generate() {
        BerryJuice temp = new BerryJuice();
        temp.meta.addCustomEffect(new PotionEffect(PotionEffectType.SATURATION,1,Basics.getRandomInt(6),true,true,true) ,true);
        temp.meta.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION,Basics.getRandomInt(200),1,true,true,false) ,true);
        if (Basics.getRandomBool()) temp.meta.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING,Basics.getRandomInt(200),1,true,true,false) ,true);
        temp.item.setItemMeta(temp.meta);
        return temp.item;
    }
    public BerryJuice() {
        item = new ItemStack(Material.POTION);
        meta = (PotionMeta) item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.BerryJuice");
        meta.setCustomModelData(1);
        meta.setDisplayName(translate(BasiCore.language,"foods.berry_juice.name"));
        meta.setColor(Color.fromARGB(100,200,200,3));
        meta.setLore(Arrays.asList(
                translate(BasiCore.language,"foods.berry_juice.lore0"),
                translate(BasiCore.language,"foods.berry_juice.lore1")
        ));
        item.setItemMeta(meta);
    }
    @EventHandler
    public void trigger(PlayerItemConsumeEvent event) {
        if (!event.getItem().hasItemMeta()) return;
        if (!Basics.getID(event.getItem()).equals("BasiCore.BerryJuice")) return;
        event.setCancelled(false);
    }
    @EventHandler
    public void onMake(CraftItemEvent event) {
        if (event.getCurrentItem()==null) return;
        ItemStack result = event.getCurrentItem();
        if (!Basics.getID(result).equals("BasiCore.BerryJuice")) return;
        if (event.isShiftClick()) {
            event.setCancelled(true);
            return;
        }
        event.setCurrentItem(generate());
    }
    public void Recipe() {
        recipe = new ShapelessRecipe(new NamespacedKey(BasiCore.getPlugin(),"foods.berry_juice"),new BerryJuice().item);
        recipe.addIngredient(1,Material.SUGAR);
        recipe.addIngredient(Material.GLASS_BOTTLE);
        recipe.addIngredient(2,Material.GLOW_BERRIES);
        Bukkit.getServer().addRecipe(recipe);
    }
}