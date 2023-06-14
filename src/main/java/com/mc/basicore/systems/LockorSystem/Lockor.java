package com.mc.basicore.systems.LockorSystem;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.mc.basicore.Basics.*;
import static org.bukkit.Material.*;

public class Lockor {
    Player owner = null;
    String purview = "private";
    Block container;
    public Lockor(Location location, Player player) {
        container = location.getBlock();
        if (!containers().contains(container.getType())) return;
        if (hasLockData(container)) {
            owner = Bukkit.getPlayer(UUID.fromString(getBlockValue(container,"owner")));
            purview = getBlockValue(container,"purview");
        }
        else {
            owner = player;
            purview = "private";
            save();
        }
    }
    public boolean isOwner(Player player) {
        return player.equals(owner);
    }
    public void round() {
        purview = purviews.get((purviews.indexOf(purview)+1)%purviews.size());
        save();
    }
    public void save() {
        setBlockValue(container,"owner",owner.getUniqueId().toString());
        setBlockValue(container,"purview",purview);
    }

    public static List<Material> containers() {
        return Arrays.asList(
                CHEST, TRAPPED_CHEST, BARREL,
                FURNACE, BLAST_FURNACE, SMOKER,ENCHANTING_TABLE,SMITHING_TABLE,
                SHULKER_BOX, BLACK_SHULKER_BOX, BLUE_SHULKER_BOX, BROWN_SHULKER_BOX, CYAN_SHULKER_BOX, GRAY_SHULKER_BOX,
                GREEN_SHULKER_BOX, LIGHT_BLUE_SHULKER_BOX,LIGHT_GRAY_SHULKER_BOX,LIME_SHULKER_BOX,MAGENTA_SHULKER_BOX,
                ORANGE_SHULKER_BOX,PINK_SHULKER_BOX,PURPLE_SHULKER_BOX,RED_SHULKER_BOX,WHITE_SHULKER_BOX,YELLOW_SHULKER_BOX
        );
    }
    public static List<String> purviews = Arrays.asList("private","public","tribe");
    public static boolean canLock(Block block) {
        return containers().contains(block.getType());
    }
    public static boolean isUsingTool(ItemStack tool) {
        return getID(tool).equals("BasiCore.WorldIndex");
    }
    public static boolean hasLockData(Block target) {
        return target.hasMetadata("purview") && target.hasMetadata("owner");
    }
    public static Player getLockOwner(Block target) {
        return Bukkit.getPlayer(UUID.fromString(target.getMetadata("owner").get(0).asString()));
    }
    public static void setLockOwner(Block target,Player player) {
        target.setMetadata("owner",new FixedMetadataValue(BasiCore.getPlugin(), player.getUniqueId().toString()));
    }
    public static String getLockPurview(Block target) {
        return target.getMetadata("purview").get(0).asString();
    }
    public static void setLockPurview(Block target, String value) {
        target.removeMetadata("purview", BasiCore.getPlugin());
        target.setMetadata("purview",new FixedMetadataValue(BasiCore.getPlugin(), value));
    }
    public static void roundLock(Block target) {
        int num = (purviews.indexOf(getLockPurview(target))+1)%purviews.size();
        setLockPurview(target,purviews.get(num));
    }
    public static void initLockBlock(Block target, Player player) {
        setLockOwner(target,player);
        setLockPurview(target,purviews.get(0));
    }
}
