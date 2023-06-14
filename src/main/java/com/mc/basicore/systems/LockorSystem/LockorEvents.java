package com.mc.basicore.systems.LockorSystem;

import com.mc.basicore.BasiCore;
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

import java.util.UUID;

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
        Lockor lockor = new Lockor(target.getLocation(),player);
        if (!lockor.isOwner(player)) return;
        lockor.round();
        player.sendTitle("",translate(player,"generic.space","Lockor."+lockor.purview),1,100,1);
    }
    @EventHandler
    public void LockReset(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!(block.hasMetadata("purview") && block.hasMetadata("owner"))) return;
        Player breaker = event.getPlayer();
        Player owner = Bukkit.getPlayer(UUID.fromString(block.getMetadata("owner").get(0).asString()));
        if (breaker.equals(owner)) {
            block.removeMetadata("owner",BasiCore.getPlugin());
            block.removeMetadata("purview",BasiCore.getPlugin());
        }
        else {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void LockTriggered(PlayerInteractEvent event) {
        if (!event.hasBlock()) return;
        Block target = event.getClickedBlock();
        Player player = event.getPlayer();
        assert target != null;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!(target.hasMetadata("purview") && target.hasMetadata("owner"))) return;
        Player owner = Bukkit.getPlayer(UUID.fromString(target.getMetadata("owner").get(0).asString()));
        assert owner != null;
        switch (target.getMetadata("purview").get(0).asString()){
            case "private":
                if (!player.equals(owner)) {
                    player.sendMessage("此箱子屬於"+ ChatSet.query(owner.getUniqueId()).getName()+"私人所有");
                    event.setCancelled(true);
                }
                return;
            case "tribe":
                boolean open = true;
                for (Tribe t:Tribe.List()) {
                    if (t.members.contains(player) && t.members.contains(owner)) {
                        open = false;
                        break;
                    }
                }
                event.setCancelled(open);
                return;
            case "public":
                return;
            default:
                Bukkit.broadcastMessage("error for value: "+target.getMetadata("purview").get(0).asString());
        }
    }
}
