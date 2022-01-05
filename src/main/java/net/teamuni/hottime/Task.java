package net.teamuni.hottime;

import static net.teamuni.hottime.HotTime.getInstance;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import java.util.List;

public class Task {
    private final String time;
    private final String name;
    private final List<String> cmds;

    public Task(String name, ConfigurationSection section) {
        this.name = name;
        time = section.getString("time");
        cmds = section.getStringList("commands");
    }

    // 이벤트 이름
    public String getName() {
        return name;
    }

    // 이벤트 시간대
    public String getTime() {
        return time;
    }

    // 이벤트 명령어 목록 실행
    public void run() {
        for (String cmd : cmds) {
            if (cmd.contains("%p")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replace("%p", p.getName()));
                }
            } else {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
            }
        }
        getInstance().getLogger().info(getName() + " 이벤트 명령어가 실행되었습니다.");
    }
}