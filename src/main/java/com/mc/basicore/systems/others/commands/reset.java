package com.mc.basicore.systems.others.commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class reset implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender,@Nonnull Command command,@Nonnull String s,@Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.setWalkSpeed(0.2f);
            player.setFlySpeed(0.1f);
            AttributeInstance speed = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            assert speed != null;
            speed.setBaseValue(0.1);
            commandSender.sendMessage("速度已重置");
            AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            AttributeInstance playerSpeed = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            assert playerMaxHealth != null && playerSpeed != null;
            playerMaxHealth.getModifiers().forEach(attributeModifier -> {
                player.sendMessage(attributeModifier.getUniqueId()+" removed");
                playerMaxHealth.removeModifier(attributeModifier);
            });
            playerSpeed.getModifiers().forEach(attributeModifier -> {
                player.sendMessage(attributeModifier.getUniqueId()+" removed");
                playerSpeed.removeModifier(attributeModifier);
            });
            return true;
        }
        return false;
    }
}
