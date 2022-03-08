package net.teamuni.hottime;

import net.teamuni.hottime.api.votifier.VoteEvent;
import net.teamuni.hottime.api.votifier.VotifierData;
import net.teamuni.hottime.configs.Config;
import net.teamuni.hottime.configs.Players;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class HotTime extends JavaPlugin {

    private static HotTime instance;

    public static Config config;
    public static Players players;

    public static SimpleDateFormat format;

    @Override
    public void onEnable() {
        instance = this;
        format = new SimpleDateFormat("HH:mm:ss");

        // config.yml 설정
        config = new Config();

        // votifier 설정
        if (Bukkit.getPluginManager().getPlugin("Votifier") != null) {
            // players.yml 설정
            players = new Players();

            // 이벤트 설정
            getServer().getPluginManager().registerEvents(new VoteEvent(), this);

            // 00시 플레이어 데이터 초기화
            Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), () -> {
                String time = format.format(new Date());

                if (time.equals("00:00:00")) {
                    // 00시에 투표한 플레이어 목록 초기화
                    VotifierData.resetUUIDList();
                }
            }, 20, 20);
        }

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
        if (Bukkit.getPluginManager().getPlugin("Votifier") != null) {
            players.save();
        }
    }

    public static HotTime getInstance() {
        return instance;
    }
}
