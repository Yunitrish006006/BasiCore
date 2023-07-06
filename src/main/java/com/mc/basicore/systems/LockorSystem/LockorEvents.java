package com.mc.basicore.systems.LockorSystem;

import com.mc.basicore.systems.TribeSystem.Tribe;
import com.mc.basicore.systems.ChatSystem.ChatSet;
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
        Lockor lockor = Lockor.query(target.getLocation(),player);
        if (!lockor.isOwner(player)) return;
        lockor.round();
        player.sendTitle("",translate(player,"generic.space","Lockor."+lockor.purview),1,100,1);
    }
    @EventHandler
    public void LockReset(BlockBreakEvent event) {
        if (Lockor.canNotLock(event.getBlock())) return;
        Lockor lockor = Lockor.query(event.getBlock().getLocation(),event.getPlayer());
        if (lockor.isOwner(event.getPlayer())) {
            lockor.getOwner().sendMessage("you have break ur chest!");
            lockor.reset();
        } else if (event.getPlayer().isOp()) {
            lockor.reset();
            event.getPlayer().sendMessage("you do this as op!");
        } else {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void LockTriggered(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        assert block != null;
        if (Lockor.canNotLock(block)) return;
        Lockor lockor = Lockor.query(block.getLocation(),player);
        switch (lockor.purview){
            case "private":
                if (!lockor.isOwner(player)){
                    player.sendTitle("","此容器屬於"+ ChatSet.query(lockor.owner).getName()+"私人所有",4,6,4);
                    event.setCancelled(true);
                }
                player.sendMessage("你打開了你的私人容器");
                return;
            case "public":
                player.sendTitle("","你打開了"+ ChatSet.query(lockor.owner).getName()+"的容器",4,12,4);
                player.sendMessage("你打開了"+ ChatSet.query(lockor.owner).getName()+"的容器");
                return;
            default:
                Tribe tribe = Tribe.Query(lockor.purview);
                if (!tribe.name.equals("tribe not found!")) {
                    if (tribe.isMember(player.getUniqueId())) player.sendMessage("你打開了"+ ChatSet.query(lockor.owner).getName()+"的容器");
                    else player.sendMessage(translate(player,"你不是該部落成員!"));
                    return;
                }
                Bukkit.broadcastMessage("error for value: "+lockor.purview);
        }
    }
}
