package com.mc.basicore.systems.LockorSystem;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.TribeSystem.Tribe;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.mc.basicore.Basics.*;
import static org.bukkit.Material.*;

public class Lockor {
    UUID owner = Basics.errorID;
    String purview = "undefined";
    Block container = null;
    public Lockor() {}
    public static Lockor create(Location location,Player player) {
        Lockor lockor = new Lockor();
        lockor.container = location.getBlock();
        lockor.owner = player.getUniqueId();
        lockor.purview = "private";
        lockor.save();
        return lockor;
    }
    public static Lockor query(Location location,Player player) {
        if (!hasLockData(location.getBlock())) return Lockor.create(location,player);
        Lockor lockor = new Lockor();
        lockor.container = location.getBlock();
        lockor.owner = UUID.fromString(getBlockValue(lockor.container,"owner"));
        lockor.purview = getBlockValue(lockor.container,"purview");
        lockor.save();
        return lockor;
    }
    public Player getOwner() {
        return Bukkit.getPlayer(owner);
    }
    public boolean isOwner(Player player) {
        return player.getUniqueId().equals(owner);
    }
    public void round() {
        List<String> purviews = new ArrayList<>();
        purviews.addAll(Arrays.asList("private","public"));
        purviews.addAll(Tribe.getTribeListNames(owner));
        purview = purviews.get((purviews.indexOf(purview)+1)%purviews.size());
        save();
    }
    public void save() {
        container.setMetadata("owner",new FixedMetadataValue(BasiCore.getPlugin(),owner.toString()));
        container.setMetadata("purview",new FixedMetadataValue(BasiCore.getPlugin(),purview));
    }
    public void reset() {
        resetBlockValue(container,"owner");
        resetBlockValue(container,"purview");
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
    public static boolean canNotLock(Block block) {
        boolean value = true;
        if (block != null) {
            value = !containers().contains(block.getType());
        }
        return value;
    }
    public static boolean isUsingTool(ItemStack tool) {
        return getID(tool).equals("BasiCore.WorldIndex");
    }
    public static boolean hasLockData(Block target) {
        return target.hasMetadata("purview") && target.hasMetadata("owner");
    }
}
