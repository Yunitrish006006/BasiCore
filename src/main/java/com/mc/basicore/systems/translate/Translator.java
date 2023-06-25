package com.mc.basicore.systems.translate;

import com.mc.basicore.systems.FileSystem.FileSet;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Locale;

public class Translator {
    public static FileSet set = new FileSet("language.yml");
    public static void initFile() {set = new FileSet("language.yml");}
    public static String transPure(String locale, String id) {
        String lc = locale.toLowerCase(Locale.ROOT);
        String localized = set.data.getString(lc + "." + id);
        String eng = set.data.getString("en_us." + id);
        return localized == null ? (eng == null ? id : eng+" "):localized;
    }
    public static String translate(Player player, String id) {
        return ChatColor.RESET+String.valueOf(ChatColor.WHITE)+transPure(player.getLocale(),id) +ChatColor.RESET;
    }
    public static String translate(String locale, String id) {
        return ChatColor.RESET+String.valueOf(ChatColor.WHITE)+transPure(locale,id)+ChatColor.RESET;
    }
    public static String translate(String locale, String... id) {
        StringBuilder result= new StringBuilder();
        for (String tmp:id) {
            result.append(translate(locale, tmp));
        }
        return result.toString();
    }
    public static String translate(Player player, String... id) {
        StringBuilder result= new StringBuilder();
        for (String tmp:id) {
            result.append(translate(player, tmp));
        }
        return result.toString();
    }
}
