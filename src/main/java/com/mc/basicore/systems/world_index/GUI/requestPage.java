package com.mc.basicore.systems.world_index.GUI;

import com.mc.basicore.systems.TribeSystem.Tribe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import static com.mc.basicore.systems.translate.Translator.translate;
import static org.bukkit.Material.*;

public class requestPage implements InventoryHolder {
    private final Inventory inventory;
    String[] Reason;
    Tribe tribe = new Tribe();
    Player from;
    Player target;
    public requestPage(Player from,Player target,String[] reason) {
        this.inventory = Bukkit.createInventory(this,9, translate(from,"GUI.confirm","GUI.menu"));
        this.from = from;
        this.target = target;
        this.Reason = reason;
        if (reason.length < 2) return;
        if (Reason[0].equals("recruit")) tribe = Tribe.Query(Reason[1]);
        else if (Reason[0].equals("apply")) tribe = Tribe.Query(Reason[1]);
        this.getInventory().setItem(2,acceptButton());
        this.getInventory().setItem(4,questionButton());
        this.getInventory().setItem(6,denyButton());
    }

    private ItemStack acceptButton() {
        ItemStack item = new ItemStack(EMERALD);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.accept");
        meta.setDisplayName(ChatColor.RESET+"【"+ChatColor.GREEN+"接受"+ChatColor.RESET+"】");
        meta.setLore(Collections.singletonList(ChatColor.RESET + "• " + ChatColor.LIGHT_PURPLE + "【左鍵】" + ChatColor.GOLD + "選定"));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack denyButton() {
        ItemStack item = new ItemStack(BARRIER);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.deny");
        meta.setDisplayName(ChatColor.RESET+"【"+ChatColor.RED+"拒絕"+ChatColor.RESET+"】");
        meta.setLore(Collections.singletonList(ChatColor.RESET + "• " + ChatColor.LIGHT_PURPLE + "【左鍵】" + ChatColor.GOLD + "選定"));
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack questionButton() {
        ItemStack item = new ItemStack(LIGHT);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.question");
        if (Reason[0].equals("recruit")) meta.setDisplayName(translate(from,"GUI.from")+tribe.name+translate(from,"GUI.s","GUI.tribe","GUI.recruit"));
        else if (Reason[0].equals("apply")) meta.setDisplayName(translate(from,"GUI.from")+tribe.name+translate(from,"GUI.s","GUI.tribe","GUI.apply"));
        else meta.setDisplayName(translate(from,"quotes.unknown_request"));
        item.setItemMeta(meta);
        return item;
    }
    @SuppressWarnings("ConstantConditions")
    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        switch (ID) {
            case "accept":
                if (!press.isLeftClick()) break;
                if (Reason[0].equals("recruit") && !tribe.ID.toString().equals(Tribe.errorID)) {
                    if (!tribe.isMember(player)) {
                        player.sendMessage(ChatColor.GREEN+"你接受了來自"+tribe.name+ChatColor.GREEN+"的部落邀請!");
                        tribe.members.add(player.getUniqueId());
                        tribe.save();
                    }
                    else {
                        player.sendMessage("你已經在該部落中!");
                    }
                } else if (Reason[0].equals("apply") && !tribe.ID.toString().equals(Tribe.errorID)) {
                    if (!tribe.isMember(from)) {
                        tribe.getOwner().sendMessage(ChatColor.GREEN+"你批准了來自"+tribe.name+ChatColor.GREEN+"的部落加入申請!");
                        tribe.members.add(from.getUniqueId());
                        tribe.save();
                    }
                    else {
                        player.sendMessage("該成員已經在部落中!");
                    }
                } else player.sendMessage("Unknown Request!");
                break;
            case "deny":
                if (press.isLeftClick()) {
                        Bukkit.broadcastMessage(Reason[0]+" denied!");
                }
                break;
            default:
                break;
        }
        player.closeInventory();
        event.setCancelled(true);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
