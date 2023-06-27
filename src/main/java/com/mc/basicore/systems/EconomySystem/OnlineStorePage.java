package com.mc.basicore.systems.EconomySystem;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.ChatSystem.ChatSet;
import com.mc.basicore.systems.FileSystem.FileSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.mc.basicore.systems.translate.Translator.translate;
import static org.bukkit.Material.OAK_HANGING_SIGN;

public class OnlineStorePage implements InventoryHolder {
    public Inventory inventory;
    private final Player player;
    static FileSet fileSet = new FileSet("award.yml");
    public static ItemStack villagerPageButton(String language) {
        ItemStack item = new ItemStack(OAK_HANGING_SIGN);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.OnlineStorePage");
        meta.setDisplayName(translate(language,"GUI.online","GUI.store"));
        item.setItemMeta(meta);
        return item;
    }

    public OnlineStorePage(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this,9*6, translate(player,"GUI.online","GUI.store"));
        Basics.getVillagers(player.getWorld()).forEach(
                villager -> inventory.addItem(VillagerButton(villager))
        );
    }

    public ItemStack VillagerButton(Villager villager) {
        ItemStack item = new ItemStack(OAK_HANGING_SIGN);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLore(Arrays.asList(
                villager.getUniqueId().toString(),
                translate(player,"職業: ","Profession."+villager.getProfession().name()),
                translate(player,"等級: ","Level."+villager.getVillagerLevel())
                ));
        if (villager.hasMetadata("Owner")) {
            if (!ChatSet.query(UUID.fromString(villager.getMetadata("Owner").get(0).asString())).CustomName.equals("Unknown Error!")) {
                ChatSet partner = ChatSet.query(UUID.fromString(villager.getMetadata("Owner").get(0).asString()));
                assert meta.getLore()!=null;
                List<String> old = meta.getLore();
                old.add(translate(player,"合作夥伴: ", partner.getName()));
                meta.setLore(old);
            }
        }
        meta.setLocalizedName("BasiCore.GUI.villager");
        meta.setDisplayName(ChatColor.RESET+"【"+villager.getCustomName()+"】");
        item.setItemMeta(meta);
        return item;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void trigger(InventoryClickEvent event, String ID, ClickType press, Player player) {
        switch (ID) {
            case "villager": {
                if (press.isLeftClick()) {
                    player.closeInventory();
                    Villager villager = (Villager) Bukkit.getServer().getEntity(UUID.fromString(event.getCurrentItem().getItemMeta().getLore().get(0)));
                    if (villager != null) {
                        player.openMerchant(villager, true);
                    }
                }
                break;
            }
        }
    }
}
