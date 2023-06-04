package com.mc.basicore.events;

import com.mc.basicore.systems.Sleep.SleepFunction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class sleepEvent implements Listener {
    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        SleepFunction sleepFunction = new SleepFunction(1);
        sleepFunction.run();
    }
}
