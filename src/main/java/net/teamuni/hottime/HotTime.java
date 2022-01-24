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
            // 시간 설정이 잘못된 경우 해당 핫타임을 제외
            if (task.getTime()==null || task.getOne()==null)
                continue;

            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                String time = format.format(new Date());

                if (time.equals(task.getOne()+":00")) {
                    if (!task.getBefore().isEmpty())
                        Bukkit.broadcastMessage(task.getBefore());

                } else if (time.equals(task.getTime()+":00")) {
                    task.run();
                    if (!task.getAfter().isEmpty())
                        Bukkit.broadcastMessage(task.getAfter());
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
