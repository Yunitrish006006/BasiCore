package com.mc.basicore.commands;

import com.mc.basicore.systems.enchant_system.EnchantSystem;
import com.mc.basicore.systems.translate.Translator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Collections;

public class test implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender,@Nonnull Command command,@Nonnull String s,@Nonnull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if (!player.isOp()) return false;
        String error_message="";
        switch (strings.length) {
            case 1: {
                switch (strings[0]) {
                    case "hat": {
                        ItemStack itemStack = new ItemStack(Material.CHAINMAIL_HELMET);
                        itemStack.addEnchantment(EnchantSystem.HEAD_CEASE,1);
                        ItemMeta meta = itemStack.getItemMeta();
                        assert meta != null;
                        meta.setLore(Collections.singletonList(ChatColor.RESET + " " + ChatColor.GRAY + "腦袋有洞 I"));
                        itemStack.setItemMeta(meta);
                        player.getInventory().addItem(itemStack);
                        break;
                    }
                    case "body": {
                        Player body = Bukkit.getPlayerExact(player.getName());
                        player.getWorld().spawn(player.getLocation(), player.getClass());
                        break;
                    }
                }
                break;
            }
            case 2: {
                switch (strings[0]) {
                    case "translate":
                    case "trans":
                    case "ts": {
                        player.sendMessage(player.getLocale()+"."+"Basicore."+strings[1]);
                        player.sendMessage(Translator.translate(player,strings[1]));
                        break;
                    }
                }
                break;
            }
            default: player.sendMessage("command error");
        }

        return true;
    }
}
