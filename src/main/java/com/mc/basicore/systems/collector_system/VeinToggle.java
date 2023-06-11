package com.mc.basicore.systems.collector_system;

import com.mc.basicore.itemGroups;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.mc.basicore.systems.translate.Translator.translate;

public class VeinToggle implements Listener {
    @EventHandler
    public void onToggle(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getPlayer().isSneaking())) return;
        assert event.getItem() != null;
        Player player = event.getPlayer();
        CollectorSet set = CollectorSet.query(player);
        if (itemGroups.pickaxes().contains(event.getItem().getType())) {
            set.pickaxe = !set.pickaxe;
            set.save();
            player.sendTitle("",translate(player,"WorldType.space","GUI.collectorSystem","GUI.pickaxe","GUI."+set.pickaxe),1,60,1);
        }
        else if (itemGroups.shovels().contains(event.getItem().getType())) {
            set.shovel = !set.shovel;
            set.save();
            player.sendTitle("",translate(player,"WorldType.space","GUI.collectorSystem","GUI.shovel","GUI."+set.shovel),1,60,1);
        }
        else if (itemGroups.axes().contains(event.getItem().getType())) {
            set.axe = !set.axe;
            set.save();
            player.sendTitle("",translate(player,"WorldType.space","GUI.collectorSystem","GUI.axe","GUI."+set.axe),1,60,1);
        }
    }
}
