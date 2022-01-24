package net.teamuni.hottime;

import static net.teamuni.hottime.HotTime.getInstance;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.HashMap;

public class Config {
    public File configFile;
    public FileConfiguration config;
    public HashMap<String, Task> tasks = new HashMap<>();

    public Config() {
        load();
    }

    // HotTime 폴더에서 config.yml 파일 불러오기
    public void load() {
        // 만약 HotTime 폴더가 존재하지 않을 경우 생성
        if (!getInstance().getDataFolder().exists())
            getInstance().getDataFolder().mkdirs();

        configFile = new File(getInstance().getDataFolder(), "config.yml");
        // config.yml 파일이 존재하지 않을 경우 새로 생성
        if (!configFile.exists())
            getInstance().saveResource("config.yml", false);
        config = YamlConfiguration.loadConfiguration(configFile);

        getTask();
    }

    // config.yml 파일에서 짜여진 이벤트 정보 불러오기
    public void getTask() {
        for(String s: config.getConfigurationSection("events").getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection("events").getConfigurationSection(s);
            tasks.put(s, new Task(s, section));

        }
    }

}