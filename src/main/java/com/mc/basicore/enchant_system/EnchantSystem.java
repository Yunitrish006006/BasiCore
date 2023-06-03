package com.mc.basicore.enchant_system;

import com.mc.basicore.BasiCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.Math.random;

public class EnchantSystem implements Listener {
    public static final Enchantment HEAD_CEASE = new HeadCeaseWrapper(new NamespacedKey(BasiCore.getPlugin(),"HeadCease"));
    public static void register() {
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(HEAD_CEASE);

        if (!registered) {
            registerEnchantment(HEAD_CEASE);
        }
    }
    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null,true);
            Enchantment.registerEnchantment(enchantment);
        }
        catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
        if (registered) {
            Bukkit.broadcastMessage("registered enchants successfully!");
        }
    }
    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) return;
        Player player = (Player) event.getEntity();
        if (player.getInventory().getHelmet() == null) return;
        if (!player.getInventory().getHelmet().hasItemMeta()) return;
        if (!player.getInventory().getHelmet().getItemMeta().hasEnchant(EnchantSystem.HEAD_CEASE)) return;
        player.playSound(player.getLocation(), "basicore.devil_laugh",0.3f,2.0f);
        player.setVelocity(new Vector(0, 0, 0));
        player.setSprinting(false);
        int durationTicks = 20 * 5;
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setVelocity(player.getVelocity());
                player.setSprinting(true);
            }
        }.runTaskLater(BasiCore.getPlugin(), durationTicks);
    }
    @EventHandler
    public void onPrepareEnchant(PrepareItemEnchantEvent event) {
//        Enchantment enchantment = HEAD_CEASE;
//
////            e.setCancelled(false);
//        event.getOffers()[0] = new EnchantmentOffer(HEAD_CEASE, 1, 1);
//
//
//        System.out.println("Listing all registered enchantments");
//        for(int i = 0; i < Enchantment.values().length; i++) {
//            System.out.println(Enchantment.values()[i].getName());
//        }
//        System.out.println("Listing all offered enchantments");
//        for(int i = 0; i < 3; i++){
//            System.out.println("#"+i+": "+event.getOffers()[i].getEnchantment().getName());
//        }
//        if (!enchantment.canEnchantItem(event.getItem())) return;
//        List<EnchantmentOffer> table = Arrays.asList(event.getOffers());
//        table.add(new EnchantmentOffer(enchantment,1,2));
//        for (EnchantmentOffer offer:table) {
//            Bukkit.broadcastMessage(offer.toString());
//        }
    }
}
