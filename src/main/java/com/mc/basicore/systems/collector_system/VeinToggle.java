package com.mc.basicore.systems.collector_system;

import com.mc.basicore.BasiCore;
import com.mc.basicore.itemGroups;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class VeinToggle implements Listener {
    @EventHandler
    public void onToggle(PlayerInteractEvent event) {
        if (!(itemGroups.tools().contains(event.getMaterial()))) return;
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getPlayer().isSneaking())) return;
        Player player = event.getPlayer();
        boolean toggle = player.hasMetadata("VeinToggle");
        if (player.getMetadata("VeinToggle").isEmpty()) toggle = false;
        if (!toggle) {
            player.setMetadata("VeinToggle", new FixedMetadataValue(BasiCore.getPlugin(), true));
            player.sendTitle(ChatColor.GOLD+"連鎖資源",ChatColor.GREEN+"開啟",0,10,0);
        }
        else {
            if(player.getMetadata("VeinToggle").get(0).asBoolean()) {
                player.setMetadata("VeinToggle", new FixedMetadataValue(BasiCore.getPlugin(), false));
                player.sendTitle(ChatColor.GOLD+"連鎖資源",ChatColor.RED+"關閉",0,10,0);
            }
            else {
                player.setMetadata("VeinToggle", new FixedMetadataValue(BasiCore.getPlugin(), true));
                player.sendTitle(ChatColor.GOLD+"連鎖資源",ChatColor.GREEN+"開啟",0,10,0);
            }
        }
    }
}
