package net.teamuni.hottime;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class HotTime extends JavaPlugin {

    private static HotTime instance;

    public static Config config;

    @Override
    public void onEnable() {
        instance = this;

        // config.yml 셋업
        config = new Config();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        // 이벤트 목록을 불러와서 전부 실행
        for (Task task : config.tasks.values()) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                String time = format.format(new Date());

                if (time.equals(task.getTime()+":00")) {
                    task.run();
                }
            }, 20, 20);
        }
    }

    @Override
    public void onDisable() {
        // code
    }

    public static HotTime getInstance() {
        return instance;
    }
}
