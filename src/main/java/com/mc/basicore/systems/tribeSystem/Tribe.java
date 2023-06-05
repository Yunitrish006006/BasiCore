package com.mc.basicore.systems.tribeSystem;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.chat_system.ChatSet;
import com.mc.basicore.systems.world_index.GUI.requestPage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Tribe {
    public static FileConfiguration config = Basics.config;
    public static final String errorID = "00000000-0000-0000-0000-000000000000";
    public UUID ID;
    public String name;
    public int level;
    public List<Player> members;
    public Player owner;
    public Tribe(Player creator) {
        ID = UUID.randomUUID();
        name = creator.getDisplayName()+"的部落";
        owner = creator;
        level = 1;
        members = Collections.singletonList(creator);
    }
    public Tribe() {
        ID = UUID.fromString(errorID);
        name = "Error Unknown";
        level = 1;
        members = null;
        owner = null;
    }
    @SuppressWarnings("ConstantConditions")
    public void tribeData(Player player) {
        player.sendMessage(ChatColor.GOLD+"TribeName: "+ChatColor.WHITE+name);
        player.sendMessage(ChatColor.GOLD+"ID: "+ChatColor.WHITE+ID);
        player.sendMessage(ChatColor.GOLD+"owner: "+ChatColor.WHITE+new ChatSet(owner).getName());
        player.sendMessage(ChatColor.GOLD+"level: "+ChatColor.WHITE+level);
        player.sendMessage(ChatColor.GOLD+"members: ");
        for (Player p:members) {
            player.sendMessage(new ChatSet(p).getName());
        }

    }
    public static Tribe Find(String tribeName) {
        List<String> uuids = new ArrayList<>(config.getKeys(false));
        Tribe tribe = new Tribe();
        if (config.getKeys(false).isEmpty()) return tribe;
        String targetUUID = errorID;
        for(String t:uuids) {
            if (!config.getKeys(false).contains(t)) continue;
            ConfigurationSection section = config.getConfigurationSection(t);
            assert section != null;
            if (section.getKeys(false).contains("name")) {
                targetUUID = t;
                break;
            }
            else {
                tribe.name = "tribe not found!";
            }
        }
        if (targetUUID.equals(errorID)) return tribe;
        tribe.ID = UUID.fromString(targetUUID);
        tribe.name = config.getString(targetUUID+".name");
        tribe.level = config.getInt(targetUUID+".level");
        if (Bukkit.getPlayer(UUID.fromString(config.getString(targetUUID+".owner",errorID))) == null) {
            tribe.name = "owner not found!";
            return tribe;
        }
        tribe.owner = Bukkit.getPlayer(UUID.fromString(config.getString(targetUUID+".owner",errorID)));
        List<Player> players = new ArrayList<>();
        tribe.members = new ArrayList<>();
        for (String id:config.getStringList(targetUUID+".members")) {
            tribe.members.add(Bukkit.getPlayer(UUID.fromString(id)));
        }
        return tribe;
    }
    public void apply(Player candidate) {
        String[] reason = {"apply",name};
        owner.openInventory(new requestPage(candidate,owner,reason).getInventory());
    }
    public void recruit(Player from, Player target) {
        if (!owner.equals(from)) return;
        String[] reason = {"recruit",name};
        target.openInventory(new requestPage(from,target,reason).getInventory());
    }
    public void save() {
        String prefix = ID.toString();
        config.set(prefix+".name",name);
        config.set(prefix+".level",level);
        config.set(prefix+".owner",owner.getUniqueId().toString());
        List<String> member_IDs = new ArrayList<>();
        for (Player player:members) {
            member_IDs.add(player.getUniqueId().toString());
        }
        config.set(prefix+".members",member_IDs);
        Basics.saveFile();
    }
}