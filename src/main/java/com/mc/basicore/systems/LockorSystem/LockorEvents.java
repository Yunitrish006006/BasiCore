package com.mc.basicore.systems.LockorSystem;

import com.mc.basicore.systems.TribeSystem.Tribe;
import com.mc.basicore.systems.chat_system.ChatSet;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.mc.basicore.systems.translate.Translator.translate;

public class LockorEvents implements Listener {
    @EventHandler
    public void LockeSet(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
        if (!(event.getItem() != null && event.hasBlock())) return;
        if (!Lockor.isUsingTool(event.getItem())) return;
        Block target = event.getClickedBlock();
        Player player = event.getPlayer();
        assert target != null;
        if (Lockor.canNotLock(target)) return;
        Lockor lockor = new Lockor(target.getLocation(),player);
        if (!lockor.isOwner(player)) return;
        lockor.round();
        player.sendTitle("",translate(player,"generic.space","Lockor."+lockor.purview),1,100,1);
    }
    @EventHandler
    public void LockReset(BlockBreakEvent event) {
        if (Lockor.canNotLock(event.getBlock())) return;
        Lockor lockor = new Lockor(event.getBlock().getLocation(),event.getPlayer());
        if (lockor.isOwner(event.getPlayer())) {
            lockor.reset();
        }
        else {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void LockTriggered(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        assert block != null;
        if (Lockor.canNotLock(block)) return;
        Lockor lockor = new Lockor(block.getLocation(),event.getPlayer());
        if (lockor.owner == null) {
            player.sendMessage("玩家未上線!");
            event.setCancelled(true);
            return;
        }
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        switch (lockor.purview){
            case "private":
                if (!lockor.isOwner(player)){
                    player.sendTitle("","此箱子屬於"+ ChatSet.query(lockor.owner.getUniqueId()).getName()+"私人所有",4,6,4);
                    event.setCancelled(true);
                }
                return;
            case "tribe":
                boolean open = true;
                for (Tribe t:Tribe.List()) {
                    if (t.members.contains(player) && t.members.contains(lockor.owner)) {
                        open = false;
                        break;
                    }
                }
                event.setCancelled(open);
                return;
            case "public":
                return;
            default:
                Bukkit.broadcastMessage("error for value: "+lockor.purview);
        }
    }
}
