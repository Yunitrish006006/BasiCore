package com.mc.basicore.systems.collector_system;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.others.events.onDispenserShot;

public class collector {
    public static void init() {
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new VeinToggle(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new Axe(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new Pickaxe(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onDispenserShot(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onShovelOnGravel(),BasiCore.getPlugin());
    }
}
