package com.mc.basicore.systems.others.events;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.mob_system.events.skeleton_sword;

public class AllEvents {
    public void init() {
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new sleepEvent(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new skeleton_sword(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onPlayerFished(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onPlayerRide(),BasiCore.getPlugin());
    }
}
