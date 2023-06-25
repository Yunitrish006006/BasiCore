package com.mc.basicore.systems.FileSystem;

import com.mc.basicore.BasiCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class FileSet {
    public File file;
    public FileConfiguration data;
    public String fileName;
    public void set() {
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
                    Bukkit.getServer().getConsoleSender().sendMessage("Failed to find the default file in jar");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        data = YamlConfiguration.loadConfiguration(file);
    }
    public void save() {
        try {
            data.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public FileSet(String file_name) {
        fileName = file_name;
        set();
        save();
    }
}
