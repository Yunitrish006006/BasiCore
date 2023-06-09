package com.mc.basicore.systems.collector_system;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.others.events.onDispenserShot;

public class collector {
    public static void init() {
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new VeinToggle(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new TreeCutter(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new ChainMiner(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onDispenserShot(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onShovelOnGravel(),BasiCore.getPlugin());
    }
}
