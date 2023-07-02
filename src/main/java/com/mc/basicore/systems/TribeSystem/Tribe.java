package com.mc.basicore.systems.TribeSystem;

import com.mc.basicore.BasiCore;
import com.mc.basicore.Basics;
import com.mc.basicore.systems.ChatSystem.ChatSet;
import com.mc.basicore.systems.FileSystem.FileSet;
import com.mc.basicore.systems.world_index.requestPage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.*;

public class Tribe {
    public static FileSet fileSet = new FileSet("tribes.yml");
    public static FileConfiguration config = fileSet.data;
    public UUID ID = Basics.errorID;
    public String name = "Error Unknown";
    public int level = 1;
    public List<UUID> members = new ArrayList<>();
    public UUID owner = Basics.errorID;
    public String icon = "GREEN_BANNER";
    public static Tribe create(Player creator) {
        Tribe tribe = new Tribe();
        tribe.ID = UUID.randomUUID();
        tribe.name = ChatSet.query(creator.getUniqueId()).CustomName+"的部落";
        tribe.owner = creator.getUniqueId();
        tribe.level = 1;
        tribe.icon = "GREEN_BANNER";
        tribe.members.add(tribe.owner);
        tribe.save();
        return tribe;
    }
    public Tribe() {}
    @SuppressWarnings("ConstantConditions")
    public Player getOwner() {
        return Bukkit.getPlayer(owner);
    }
    public void sendTribeData(Player player) {
        player.sendMessage(ChatColor.GOLD+"TribeName: "+ChatColor.WHITE+name);
        player.sendMessage(ChatColor.GOLD+"ID: "+ChatColor.WHITE+ID);
        player.sendMessage(ChatColor.GOLD+"owner: "+ChatColor.WHITE+ChatSet.query(owner));
        player.sendMessage(ChatColor.GOLD+"level: "+ChatColor.WHITE+level);
        player.sendMessage(ChatColor.GOLD+"members: ");
        for (UUID p:members) {
            player.sendMessage("- "+ChatSet.query(p).getName());
        }
    }
    public void sendTribeData() {
        Bukkit.broadcastMessage(ChatColor.GOLD+"TribeName: "+ChatColor.WHITE+name);
        Bukkit.broadcastMessage(ChatColor.GOLD+"ID: "+ChatColor.WHITE+ID);
        Bukkit.broadcastMessage(ChatColor.GOLD+"owner: "+ChatColor.WHITE+ChatSet.query(owner).getName());
        Bukkit.broadcastMessage(ChatColor.GOLD+"level: "+ChatColor.WHITE+level);
        Bukkit.broadcastMessage(ChatColor.GOLD+"members: ");
        for (UUID p:members) {
            Bukkit.broadcastMessage("- "+ChatSet.query(p).getName());
        }
    }
    public List<String> tribeData() {
        if (owner == null) return Arrays.asList("無法預覽",ChatColor.RED + "部落主目前不在線!");
        List<String> data = new ArrayList<>(Arrays.asList(
                ChatColor.GOLD + "ID: " + ChatColor.WHITE + ID,
                ChatColor.GOLD + "owner: " + ChatColor.WHITE + ChatSet.query(owner).getName(),
                ChatColor.GOLD + "level: " + ChatColor.WHITE + level,
                ChatColor.GOLD + "members: "
        ));
        for (UUID p:members) {
            data.add("- "+ChatSet.query(p).getName());
        }
        return data;
    }
    public static Tribe Query(String tribeName) {
        Tribe tribe = new Tribe();
        if (config.getKeys(false).isEmpty()) return tribe;
        for(String t:config.getKeys(false)) {
            if (Objects.equals(config.getString(t + ".type"), "tribe") && tribeName.equalsIgnoreCase(config.getString(t + ".name"))) {
                tribe.ID = UUID.fromString(t);
                break;
            }
        }
        if (tribe.ID == Basics.errorID) {
            tribe.name = "tribe not found!";
            return tribe;
        }
        tribe.name = config.getString(tribe.ID+".name");
        tribe.level = config.getInt(tribe.ID+".level");
        tribe.icon = config.getString(tribe.ID+".icon");
        tribe.owner = UUID.fromString(config.getString(tribe.ID+".owner",Basics.errorID.toString()));
        tribe.members = new ArrayList<>();
        for (String id:config.getStringList(tribe.ID+".members")) {
            tribe.members.add(UUID.fromString(id));
        }
        return tribe;
    }
    public static Tribe QueryID(String id) {
        Tribe tribe = new Tribe();
        if (!config.getKeys(false).contains(id)) return tribe;
        tribe.name = config.getString(id+".name");
        tribe.level = config.getInt(id+".level");
        tribe.icon = config.getString(id+".icon");
        tribe.owner = UUID.fromString(config.getString(tribe.ID+".owner",Basics.errorID.toString()));
        config.getStringList(id+".members").forEach(member -> tribe.members.add(UUID.fromString(member)));
        return tribe;
    }
    public static List<Tribe> List() {
        List<Tribe> tribes = new ArrayList<>();
        int c = 0;
        for(String t:config.getKeys(false)) {
            Tribe temp = Tribe.Query(config.getString(t+".name"));
            tribes.add(temp);
        }
        return tribes;
    }
    public void apply(Player candidate) {
        String[] reason = {"apply",name};
        Player ownerEntity = Bukkit.getPlayer(owner);
        if (ownerEntity == null) {
            candidate.closeInventory();
            candidate.sendTitle("","族長未上線，請等族長上線後再次申請",4,10,4);
            return;
        }
        ownerEntity.openInventory(new requestPage(candidate,ownerEntity,reason).getInventory());
    }
    public void recruit(Player from, Player target) {
        if (owner == null) {
            from.sendMessage("Wrong tribe name");
            return;
        }
        if (!owner.equals(from.getUniqueId())) return;
        String[] reason = {"recruit",name};
        target.openInventory(new requestPage(from,target,reason).getInventory());
    }
    public void quit(Player player) {
        UUID id = player.getUniqueId();
        if (owner.equals(id)) return;
        if (!isMember(id)) return;
        members.remove(id);
        save();
    }
    public void save() {
        String prefix = ID.toString();
        String type = "tribe";
        config.set(prefix+".type", type);
        config.set(prefix+".name",name);
        config.set(prefix+".icon",icon);
        config.set(prefix+".level",level);
        config.set(prefix+".owner",owner.toString());
        List<String> member_IDs = new ArrayList<>();
        for (UUID p:members) {
            member_IDs.add(p.toString());
        }
        config.set(prefix+".members",member_IDs);
        fileSet.save();
    }
    public static List<Tribe> getTribeList(Player player) {
        List<Tribe> tribes = new ArrayList<>();
        config.getKeys(false).forEach(id -> {
            Tribe temp = QueryID(id);
            if (temp.members.contains(player.getUniqueId())) tribes.add(QueryID(id));
        });
        return tribes;
    }
    public boolean isMember(Player player) {
        return members.contains(player.getUniqueId());
    }
    public boolean isMember(UUID id) {
        return members.contains(id);
    }
    public static void init() {
        Objects.requireNonNull(BasiCore.getPlugin().getCommand("tribe")).setExecutor(new TribeCommand());
        Objects.requireNonNull(BasiCore.getPlugin().getCommand("tribe")).setTabCompleter(new TribeTabComplete());
        BasiCore.getPlugin().getServer().getPluginManager().registerEvents(new TribeEvents(),BasiCore.getPlugin());
    }
}
