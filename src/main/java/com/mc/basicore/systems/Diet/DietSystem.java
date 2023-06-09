package com.mc.basicore.systems.Diet;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.Diet.Foods.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DietSystem{
    public static Food[] foods = {new BerryJuice(),new Cocoa()};
    public static ItemStack[] diet_foods() {
        List<ItemStack> temp = new ArrayList<>();
        for (Food i:foods) {
            temp.add(i.generate());
        }
        return temp.toArray(new ItemStack[temp.size()]);
    }
    public static void initializeDiet() {
        for (Food i:foods) {
            BasiCore.getPlugin().getServer().getPluginManager().registerEvents((Listener) i,BasiCore.getPlugin());
            i.Recipe();
        }
    }
}
