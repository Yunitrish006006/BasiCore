package com.mc.basicore.systems.SkillSystem;

import com.mc.basicore.Basics;
import com.mc.basicore.systems.FileSystem.FileSet;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public class SkillSet implements Listener {
    static FileSet fileSet = new FileSet("skill.yml");
    public List<Skill> learned = new ArrayList<>();
    public UUID playerID = Basics.errorID;
    public static SkillSet create(Player player) {
        SkillSet skillSystem = new SkillSet();
        skillSystem.playerID = player.getUniqueId();
        skillSystem.learned.add(new Skill("run","basic",0,-1));
        skillSystem.learned.add(new Skill("health","basic",0,-1));
        skillSystem.save();
        return skillSystem;
    }
    public static SkillSet query(Player player) {
        SkillSet skillSystem = new SkillSet();
        skillSystem.playerID = player.getUniqueId();
        ConfigurationSection section = fileSet.data.getConfigurationSection(player.getUniqueId().toString());
        if (section == null) {
            player.sendMessage("null section");
            return SkillSet.create(player);
        }
        section.getKeys(false).forEach(name -> {
            Skill skill = new Skill(
                    name,
                    section.getString(name+".type"),
                    section.getInt(name+".level"),
                    section.getInt(name+".coolDown")
            );
            switch (skill.Name) {
                case "run": {
                    skill.Level = player.getStatistic(Statistic.SPRINT_ONE_CM)/(256*100*100*(skill.Level*skill.Level+1));
                    break;
                }
                case "health": {
                    skill.Level = Math.min(player.getStatistic(Statistic.DAMAGE_ABSORBED)/8*(skill.Level+1),
                            player.getStatistic(Statistic.DAMAGE_TAKEN)/(256*(skill.Level+1)));
                    AttributeModifier modifier = new AttributeModifier(UUID.fromString("00000000-0010-0000-0000-000000000000"),"skill.health",(0.01* skill.Level),AttributeModifier.Operation.ADD_NUMBER);
                    if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers().stream().map(AttributeModifier::getName).collect(Collectors.toList()).contains("skill.health")) {
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(modifier);
                    }
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(modifier);
                    break;
                }
            }
            skillSystem.learned.add(skill);
        });
        skillSystem.save();
        return skillSystem;
    }
    public void save() {
        learned.forEach(skill -> {
            fileSet.data.set(playerID+"."+skill.Name+".type",skill.Type);
            fileSet.data.set(playerID+"."+skill.Name+".level",skill.Level);
            fileSet.data.set(playerID+"."+skill.Name+".coolDown",skill.CoolDown);
        });
        fileSet.save();
    }

    public Skill getSkill(String name) {
        List<Skill> skills = learned;
        skills.removeIf(skill -> !skill.Name.equals(name));
        if (skills.isEmpty()) return new Skill();
        return skills.get(0);
    }
    @EventHandler
    public void onRun(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();
        SkillSet skillSet = SkillSet.query(player);
        Skill skill = skillSet.getSkill("run");
        AttributeModifier modifier = new AttributeModifier(UUID.fromString("00000000-0001-0000-0000-000000000000"),"skill.run",(0.005* skill.Level),AttributeModifier.Operation.ADD_NUMBER);
        if (event.isSprinting()) {
            if (player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers().contains(modifier)) {
                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(modifier);
            }
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(modifier);
        }
        else {
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(modifier);
        }
    }
}
