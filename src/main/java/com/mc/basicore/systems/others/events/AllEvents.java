package com.mc.basicore.systems.others.events;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.Sleep.sleepEvent;
import com.mc.basicore.systems.CreatureSystem.events.skeleton_sword;

public class AllEvents {
    public static void init() {
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new sleepEvent(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new skeleton_sword(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onPlayerFished(),BasiCore.getPlugin());
    }
}
