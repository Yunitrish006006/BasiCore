package com.mc.basicore.systems.tribeSystem;

import com.mc.basicore.BasiCore;
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
            player.sendMessage(ChatSet.query(p.getUniqueId()).getName());
        }

    }
    public static Tribe Find(String tribeName) {
        Tribe tribe = new Tribe();
        if (config.getKeys(false).isEmpty()) return tribe;
        String targetUUID = Basics.errorID().toString();
        for(String t:Basics.UUIDS()) {
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
            Player player = Bukkit.getPlayer(UUID.fromString(id));
            if (player==null) player = Bukkit.getPlayerExact(Objects.requireNonNull(Bukkit.getOfflinePlayer(UUID.fromString(id)).getName()));
            tribe.members.add(player);

        }
        return tribe;
    }
    public static List<Tribe> List() {
        List<Tribe> tribes = new ArrayList<>();
        for(String t:Basics.UUIDS()) {
            ConfigurationSection section = config.getConfigurationSection(t);
            assert section != null;
            if (Objects.equals(section.getString(".type"), "tribe")) {
                Tribe temp = new Tribe();
                temp.owner = Bukkit.getPlayer(UUID.fromString(config.getString(t+".owner",errorID)));
                temp.ID = UUID.fromString(t);
                temp.name = config.getString(t+".name");
                temp.level = config.getInt(t+".level");
                temp.members = new ArrayList<>();
                for (String id:config.getStringList(temp+".members")) {
                    temp.members.add(Bukkit.getPlayer(UUID.fromString(id)));
                }
                tribes.add(temp);
            }
        }
        return tribes;
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
        String type = "tribe";
        config.set(prefix+".type", type);
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
    public boolean isMember(Player player) {
        return members.contains(player);
    }
    public static void init() {
        Objects.requireNonNull(BasiCore.getPlugin().getCommand("tribe")).setExecutor(new TribeCommand());
        Objects.requireNonNull(BasiCore.getPlugin().getCommand("tribe")).setTabCompleter(new TribeTabComplete());
    }
}
