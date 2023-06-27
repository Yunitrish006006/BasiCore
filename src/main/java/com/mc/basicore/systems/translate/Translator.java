package com.mc.basicore.systems.translate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mc.basicore.systems.FileSystem.FileSet;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    public String trans(String locale, String id) {
        URL resourceUrl = getClass().getClassLoader().getResource(locale+".json");
        String translationJson = "";
        try {
            assert resourceUrl != null;
            try (InputStream inputStream = resourceUrl.openStream()) {

                StringBuilder stringBuilder = new StringBuilder();

                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                translationJson = stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonObject translations = gson.fromJson(translationJson, JsonObject.class);
        return translations.get(id).getAsString();
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
