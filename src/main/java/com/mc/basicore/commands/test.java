package com.mc.basicore.commands;

import com.mc.basicore.enchant_system.EnchantSystem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class test implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if (!player.isOp()) return false;
        ItemStack itemStack = new ItemStack(Material.CHAINMAIL_HELMET);
        itemStack.addEnchantment(EnchantSystem.HEAD_CEASE,1);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET+""+ChatColor.GRAY+"腦袋有洞 I");
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        player.getInventory().addItem(itemStack);
        return false;
    }
}
