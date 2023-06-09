package com.mc.basicore.systems.WorldManager;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import static com.mc.basicore.systems.translate.Translator.translate;

public class onWorldChange implements Listener {
    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World fromWorld = event.getFrom();
        World toWorld = player.getWorld();
        player.sendTitle("",translate(player,"WorldType.space","WorldType."+toWorld.getName()),1,100,1);
    }
}
