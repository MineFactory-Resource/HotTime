package net.teamuni.hottime.configs;

import net.teamuni.hottime.Task;
import org.bukkit.configuration.ConfigurationSection;
import java.util.HashMap;

public class Config extends Frame{
    public HashMap<String, Task> tasks;
    public String votifierName;

    public Config() {
        super("config");
        load();
    }

    protected void load() {
        super.load();

        getTask();
    }

    // config.yml 파일에서 짜여진 이벤트 정보 불러오기
    private void getTask() {
        tasks = new HashMap<>();

        for(String s: config.getConfigurationSection("events").getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection("events").getConfigurationSection(s);
            tasks.put(s, new Task(s, section));

        }

        votifierName = config.getString("votifier.service");
    }

}