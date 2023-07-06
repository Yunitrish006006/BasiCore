package com.mc.basicore.systems.ResourceSystem.tools;

import com.mc.basicore.BasiCore;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class debugStick implements Listener {
    @EventHandler
    public void handleBlockBreak(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) return;
        assert event.getHand() != null;
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        if (!event.hasItem()) return;
        assert event.getItem() != null;
        if (!event.getItem().getType().equals(Material.STICK)) return;
        Block target = event.getClickedBlock();
        assert target != null;
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            player.sendMessage("取得方塊資料: ");
            target.getMetadata("owner").forEach(owner -> player.sendMessage("Owner:"+owner.asString()));
            target.getMetadata("purview").forEach(owner -> player.sendMessage("Purview:"+owner.asString()));
        } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            player.sendMessage("重設方塊資料...");
            target.removeMetadata("owner", BasiCore.getPlugin());
            target.removeMetadata("purview", BasiCore.getPlugin());
        }
        event.setCancelled(true);
    }
}
