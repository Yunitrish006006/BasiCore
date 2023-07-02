package com.mc.basicore.systems.ResourceSystem;

import com.mc.basicore.BasiCore;
import com.mc.basicore.systems.ResourceSystem.tools.Axe;
import com.mc.basicore.systems.ResourceSystem.tools.Pickaxe;
import com.mc.basicore.systems.ResourceSystem.tools.Shovel;

public class ResourceSystem {
    public static void init() {
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new VeinToggle(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new Axe(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new Shovel(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new Pickaxe(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onMachineBlockWork(),BasiCore.getPlugin());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new onShovelOnGravel(),BasiCore.getPlugin());
    }
}
