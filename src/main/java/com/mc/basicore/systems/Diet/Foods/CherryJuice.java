package com.mc.basicore.systems.Diet.Foods;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.Diet.Food;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;

import static com.mc.basicore.systems.translate.Translator.translate;

public class CherryJuice extends Food implements Listener {
    public ItemStack item;
    public PotionMeta meta;
    public static ShapelessRecipe recipe;
    public ItemStack generate() {
        CherryJuice temp = new CherryJuice();
        return temp.item;
    }
    public CherryJuice() {
        item = new ItemStack(Material.POTION);
        meta = (PotionMeta) item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.CherryJuice");
        meta.setCustomModelData(1);
        meta.setDisplayName(translate(BasiCore.language,"foods.cherry_juice.name"));
        meta.setColor(Color.fromRGB(235,160,194));
        meta.setLore(Arrays.asList(
                translate(BasiCore.language,"foods.cherry_juice.lore0"),
                translate(BasiCore.language,"foods.cherry_juice.lore1")
        ));
        item.setItemMeta(meta);
    }
    @EventHandler
    public void trigger(PlayerItemConsumeEvent event) {
        if (!event.getItem().hasItemMeta()) return;
        if (!Basics.getID(event.getItem()).equals("BasiCore.CherryJuice")) return;
        event.getPlayer().setMetadata("CherryJuice",new FixedMetadataValue(BasiCore.getPlugin(),true));
        Basics.later(20*3*60,() -> event.getPlayer().removeMetadata("CherryJuice",BasiCore.getPlugin()));
        event.setCancelled(false);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!event.getPlayer().hasMetadata("CherryJuice")) return;
        if (!event.getPlayer().getMetadata("CherryJuice").get(0).asBoolean()) return;
        Basics.SpawnParticle(event.getPlayer(),Particle.CHERRY_LEAVES,30,0.2,0.2,0.2,0.2);
    }
    @EventHandler
    public void onMake(CraftItemEvent event) {
        if (event.getCurrentItem()==null) return;
        ItemStack result = event.getCurrentItem();
        if (!Basics.getID(result).equals("BasiCore.CherryJuice")) return;
        if (event.isShiftClick()) {
            event.setCancelled(true);
            return;
        }
        event.setCurrentItem(generate());
    }
    public void Recipe() {
        recipe = new ShapelessRecipe(new NamespacedKey(BasiCore.getPlugin(),"foods.cherry_juice"),new CherryJuice().item);
        recipe.addIngredient(1,Material.SUGAR);
        recipe.addIngredient(Material.GLASS_BOTTLE);
        recipe.addIngredient(2,Material.CHERRY_LEAVES);
        Bukkit.getServer().addRecipe(recipe);
    }
}
