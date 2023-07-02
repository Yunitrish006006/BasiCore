package com.mc.basicore.systems.SkillSystem;

import org.bukkit.Bukkit;

public class Skill {
    public String Name = "none";
    public String Type = "normal";
    public int Level = 1;
    public int CoolDown = 20;
    public Skill(){}
    public Skill(String name, String type, int level, int coolDown) {
        Name = name;
        Type = type;
        Level = level;
        CoolDown = coolDown;
    }
    public void debug() {
        Bukkit.broadcastMessage("Name: "+Name);
        Bukkit.broadcastMessage("Type: "+Type);
        Bukkit.broadcastMessage("Level: "+Level);
        Bukkit.broadcastMessage("CoolDown: "+CoolDown);
    }
}
