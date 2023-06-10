package com.mc.basicore.systems.TribeSystem;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.chat_system.ChatSet;
import com.mc.basicore.systems.world_index.GUI.requestPage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.*;

public class Tribe {
    public static FileConfiguration config = Basics.config;
    public static final String errorID = "00000000-0000-0000-0000-000000000000";
    public UUID ID = UUID.fromString(errorID);
    public String name = "Error Unknown";
    public int level = 1;
    public List<Player> members = new ArrayList<>();
    public Player owner;
    public String icon = "GREEN_BANNER";
    public static Tribe create(Player creator) {
        Tribe tribe = new Tribe();
        tribe.ID = UUID.randomUUID();
        tribe.name = ChatSet.query(creator.getUniqueId()).CustomName+"的部落";
        tribe.owner = creator;
        tribe.level = 1;
        tribe.icon = "GREEN_BANNER";
        tribe.members.add(tribe.owner);
        tribe.save();
        return tribe;
    }
    public Tribe() {}
    @SuppressWarnings("ConstantConditions")
    public void sendTribeData(Player player) {
        player.sendMessage(ChatColor.GOLD+"TribeName: "+ChatColor.WHITE+name);
        player.sendMessage(ChatColor.GOLD+"ID: "+ChatColor.WHITE+ID);
        player.sendMessage(ChatColor.GOLD+"owner: "+ChatColor.WHITE+new ChatSet(owner).getName());
        player.sendMessage(ChatColor.GOLD+"level: "+ChatColor.WHITE+level);
        player.sendMessage(ChatColor.GOLD+"members: ");
        for (Player p:members) {
            player.sendMessage("- "+ChatSet.query(p.getUniqueId()).getName());
        }
    }
    public void sendTribeData() {
        Bukkit.broadcastMessage(ChatColor.GOLD+"TribeName: "+ChatColor.WHITE+name);
        Bukkit.broadcastMessage(ChatColor.GOLD+"ID: "+ChatColor.WHITE+ID);
        Bukkit.broadcastMessage(ChatColor.GOLD+"owner: "+ChatColor.WHITE+new ChatSet(owner).getName());
        Bukkit.broadcastMessage(ChatColor.GOLD+"level: "+ChatColor.WHITE+level);
        Bukkit.broadcastMessage(ChatColor.GOLD+"members: ");
        for (Player p:members) {
            Bukkit.broadcastMessage("- "+ChatSet.query(p.getUniqueId()).getName());
        }
    }
    public List<String> tribeData() {
        List<String> data = new ArrayList<>();
        data.addAll(Arrays.asList(
                ChatColor.GOLD+"ID: "+ChatColor.WHITE+ID,
                ChatColor.GOLD+"owner: "+ChatColor.WHITE+new ChatSet(owner).getName(),
                ChatColor.GOLD+"level: "+ChatColor.WHITE+level,
                ChatColor.GOLD+"members: "
        ));
        for (Player p:members) {
            data.add("- "+ChatSet.query(p.getUniqueId()).getName());
        }
        return data;
    }
    public static Tribe Query(String tribeName) {
        Tribe tribe = new Tribe();
        tribe.ID = Basics.errorID();
        if (config.getKeys(false).isEmpty()) return tribe;
        for(String t:Basics.UUIDS()) {
            if (Objects.equals(config.getString(t + ".type"), "tribe") && tribeName.equalsIgnoreCase(config.getString(t + ".name"))) {
                tribe.ID = UUID.fromString(t);
                break;
            }
        }
        if (tribe.ID == Basics.errorID()) {
            tribe.name = "tribe not found!";
            return tribe;
        }
        tribe.name = config.getString(tribe.ID+".name");
        tribe.level = config.getInt(tribe.ID+".level");
        tribe.icon = config.getString(tribe.ID+".icon");
        if (Bukkit.getPlayer(UUID.fromString(config.getString(tribe.ID+".owner",errorID))) == null) {
            tribe.name = "owner not found!";
            return tribe;
        }
        tribe.owner = Bukkit.getPlayer(UUID.fromString(config.getString(tribe.ID+".owner",errorID)));
        List<Player> players = new ArrayList<>();
        tribe.members = new ArrayList<>();
        for (String id:config.getStringList(tribe.ID+".members")) {
            Player player = Bukkit.getPlayer(UUID.fromString(id));
            if (player==null) player = Bukkit.getPlayerExact(Objects.requireNonNull(Bukkit.getOfflinePlayer(UUID.fromString(id)).getName()));
            tribe.members.add(player);

        }
        return tribe;
    }
    public static List<Tribe> List() {
        List<Tribe> tribes = new ArrayList<>();
        int c = 0;
        for(String t:Basics.UUIDS()) {
            if (!Objects.equals(config.getString(t + ".type"), "tribe")) continue;
            Tribe temp = Tribe.Query(config.getString(t+".name"));
            tribes.add(temp);
        }
        return tribes;
    }
    public void apply(Player candidate) {
        String[] reason = {"apply",name};
        owner.openInventory(new requestPage(candidate,owner,reason).getInventory());
    }
    public void recruit(Player from, Player target) {
        if (owner == null) {
            from.sendMessage("Wrong tribe name");
            return;
        }
        if (!owner.equals(from)) return;
        String[] reason = {"recruit",name};
        target.openInventory(new requestPage(from,target,reason).getInventory());
    }
    public void save() {
        String prefix = ID.toString();
        String type = "tribe";
        config.set(prefix+".type", type);
        config.set(prefix+".name",name);
        config.set(prefix+".icon",icon);
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
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new TribeEvents(),BasiCore.getPlugin());
    }
}
