package com.mc.basicore.systems.ResourceSystem;

import com.mc.basicore.itemGroups;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import static com.mc.basicore.systems.translate.Translator.translate;

public class VeinToggle implements Listener {
    @EventHandler
    public void onToggle(PlayerSwapHandItemsEvent event) {
        if (!event.getPlayer().isSneaking()) return;
        if (event.getOffHandItem() == null) return;
        ItemStack tool = event.getOffHandItem();
        Player player = event.getPlayer();
        CollectorSet set = CollectorSet.query(player);
        if (itemGroups.pickaxes().contains(tool.getType())) {
            set.toggle(CollectorSet.PICKAXE);
            set.save();
            player.sendTitle("",translate(player,"WorldType.space","GUI.collectorSystem","GUI.pickaxe","GUI."+set.data.get("pickaxe")),1,60,1);
            event.setCancelled(true);
        }
        else if (itemGroups.shovels().contains(tool.getType())) {
            set.toggle(CollectorSet.SHOVEL);
            set.save();
            player.sendTitle("",translate(player,"WorldType.space","GUI.collectorSystem","GUI.shovel","GUI."+set.data.get("shovel")),1,60,1);
            event.setCancelled(true);
        }
        else if (itemGroups.axes().contains(tool.getType())) {
            set.toggle(CollectorSet.AXE);
            set.save();
            player.sendTitle("",translate(player,"WorldType.space","GUI.collectorSystem","GUI.axe","GUI."+set.data.get("axe")),1,60,1);
            event.setCancelled(true);
        }
    }
}
