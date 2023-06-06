package com.mc.basicore.systems.translate;

import com.mc.basicore.BasiCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Locale;

public class Translator {
    public static File file;
    public static FileConfiguration data;
    public String result;
    public static final String fileName = "language.yml";
    public static void setFile() {
        file = new File(BasiCore.getRootFolder(), fileName);
        if (!file.exists()) {
            try {
                InputStream inputStream = BasiCore.class.getResourceAsStream("/" + fileName);
                if (inputStream != null) {
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    FileWriter fileWriter = new FileWriter(file);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }

                    bufferedWriter.close();
                    fileWriter.close();
                    bufferedReader.close();
                    reader.close();

                    Bukkit.getServer().getConsoleSender().sendMessage("Creating file: " + file.getAbsolutePath());
                } else {
                    Bukkit.getServer().getConsoleSender().sendMessage("Failed to find the default language.yml in the jar.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        data = YamlConfiguration.loadConfiguration(file);
    }
    public static void saveFile() {
        try {
            data.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void initFile() {
        setFile();
        saveFile();
    }

    public Translator(String locale, String id) {
        String lc = locale.toLowerCase(Locale.ROOT);
        String temp = data.getString(lc + "." + id);
        String eng = data.getString("en_us." + id);
        if (temp != null) result = temp;
        else if (eng != null)  result = eng+" ";
        else result = lc+"."+id;
    }
    public static String translate(Player player, String id) {
        return ChatColor.RESET+String.valueOf(ChatColor.WHITE)+new Translator(player.getLocale(),id).result+ChatColor.RESET;
    }
    public static String translate(String locale, String id) {
        return ChatColor.RESET+String.valueOf(ChatColor.WHITE)+(new Translator(locale,id).result)+ChatColor.RESET;
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
