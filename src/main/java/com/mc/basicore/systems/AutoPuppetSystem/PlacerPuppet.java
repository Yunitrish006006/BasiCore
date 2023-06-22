package com.mc.basicore.systems.AutoPuppetSystem;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.others.commands.head;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class PlacerPuppet implements CommandExecutor {
    public void summonPlacer(Location location,Player player) {
        assert location.getWorld() != null;
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location.add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
        armorStand.setBasePlate(false);
        armorStand.setArms(true);
        armorStand.setGravity(true);
        armorStand.setVisible(true);
        armorStand.setInvulnerable(true);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        assert  armorStand.getEquipment()!=null;
        armorStand.getEquipment().setHelmet(head.playerHead(player));
        armorStand.setMetadata("count",new FixedMetadataValue(BasiCore.getPlugin(),40));
        dig(armorStand);
    }
    public void dig(ArmorStand center) {
        if (center.getMetadata("count").get(0).asInt() < 1) {
            center.remove();
            return;
        }
        center.setMetadata("count",new FixedMetadataValue(BasiCore.getPlugin(),center.getMetadata("count").get(0).asInt()-1));
        Basics.later(2,()-> dig(center));
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        summonPlacer(player.getLocation(),player);
        return false;
    }
}
