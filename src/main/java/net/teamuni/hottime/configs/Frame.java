package net.teamuni.hottime.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static net.teamuni.hottime.HotTime.getInstance;

abstract class Frame {
    protected File configFile;
    protected FileConfiguration config;
    private final String name;

    public Frame(String name) {
        this.name = name;
        load();
    }

    protected void load() {
        // 만약 HotTime 폴더가 존재하지 않을 경우 생성
        if (!getInstance().getDataFolder().exists())
            getInstance().getDataFolder().mkdirs();

        configFile = new File(getInstance().getDataFolder(), name + ".yml");
        // 파일이 존재하지 않을 경우 새로 생성
        if (!configFile.exists())
            getInstance().saveResource(name + ".yml", false);
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}
