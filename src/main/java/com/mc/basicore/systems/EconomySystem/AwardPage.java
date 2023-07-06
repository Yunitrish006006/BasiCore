package com.mc.basicore.systems.EconomySystem;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.ChatSystem.ChatSet;
import com.mc.basicore.systems.FileSystem.FileSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Arrays;

import static com.mc.basicore.systems.EconomySystem.OnlineStorePage.villagerPageButton;
import static com.mc.basicore.systems.translate.Translator.translate;
import static org.bukkit.Material.BELL;
import static org.bukkit.Material.EMERALD;

public class AwardPage implements InventoryHolder {
    public Inventory inventory;
    public ChatSet chatSet;
    private final Player player;
    static FileSet fileSet = new FileSet("award.yml");

    public AwardPage(Player player) {
        this.player = player;
        chatSet = new ChatSet(player);
        this.inventory = Bukkit.createInventory(this,9*4, translate(player,"GUI.player","GUI.Awards"));
        inventory.setItem(10,getDailyRewardButton());
        inventory.setItem(12,villagerPageButton("zh_tw"));
    }

    public ItemStack getDailyRewardButton() {
        ItemStack item = new ItemStack(BELL);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLocalizedName("BasiCore.GUI.getDailyReward");
        meta.setDisplayName(translate(player,"GUI.set","GUI.player","GUI.name"));
        meta.setLore(Arrays.asList(
                translate(player,"GUI.left_click","AwardSystem.getDailyReward")
                ,LocalDate.now().toString()
        ));
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
            case "getDailyReward":
                if (press.isLeftClick()) {
                    event.setCancelled(true);
                    String today = LocalDate.now().toString();
                    String lastTime = fileSet.data.getString(player.getUniqueId()+".daily");
                    if (lastTime == null || !lastTime.equals(today)) {
                        ItemStack itemStack = new ItemStack(EMERALD);
                        itemStack.setAmount(5+ Basics.getRandomInt(5));
                        player.getInventory().addItem(itemStack);
                        player.sendMessage("簽到成功!("+today+")");
                        fileSet.data.set(player.getUniqueId()+".daily",today);
                        fileSet.save();
                    }
                    else {
                        player.sendMessage("今日已簽到!");
                    }
                    player.closeInventory();
                }
                break;
            case "OnlineStorePage": {
                if (press.isLeftClick()) {
                    player.openInventory(new OnlineStorePage(player).inventory);
                    break;
                }
            }
        }
    }
}
