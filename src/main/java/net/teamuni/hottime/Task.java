package net.teamuni.hottime;

import static net.teamuni.hottime.HotTime.getInstance;

import net.teamuni.hottime.api.votifier.VotifierData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.regex.Pattern;

public class Task {
    private final String time;
    private final String one;
    private final String name;
    private final String before;
    private final String after;
    private final List<String> cmds;

    public Task(String name, ConfigurationSection section) {
        this.name = name;
        // 시간 확인
        if (Pattern.matches("^([01]\\d|2[0-3]):[0-5]\\d$", section.getString("time"))) {
            time = section.getString("time");
        } else {
            time = null;
        }

        before = ChatColor.translateAlternateColorCodes('&', section.getString("before"));
        after = ChatColor.translateAlternateColorCodes('&', section.getString("after"));

        cmds = section.getStringList("commands");

        if (time != null) {
            // 1분 전에 알릴 메세지 시간 설정
            if (Pattern.matches("^00:00$", time)) { // 00시 00분

                one = "23:59";
            } else if (Pattern.matches("^[12]0:00$", time)) { // 10시 또는 20시 00분

                one = (Integer.parseInt(time.substring(0, 1))-1) + "9:59";
            } else if (Pattern.matches("^([01][1-9]|2[1-3]):00$", time)) { // 위의 경우의 수를 제외한 00분
                StringBuilder sb = new StringBuilder(time);
                sb.replace(1, 2, String.valueOf(Integer.parseInt(time.substring(1, 2))-1));
                sb.replace(3,5,"59");

                one = sb.toString();
            } else if (Pattern.matches("^([01]\\d|2[0-3]):[1-5]0$", time)) { // 10의 배수 분
                StringBuilder sb = new StringBuilder(time);
                sb.replace(3, 4, String.valueOf(Integer.parseInt(time.substring(3, 4))-1));
                sb.replace(4, 5, "9");

                one = sb.toString();
            } else { // 나머지
                StringBuilder sb = new StringBuilder(time);
                sb.replace(4, 5, String.valueOf(Integer.parseInt(time.substring(4, 5))-1));

                one = sb.toString();
            }
        } else {
            one = null;
        }
    }

    // 이벤트 명령어 목록 실행
    public void run() {
        for (String cmd : cmds) {
            // 명령어에 %player%가 포함되어 있을 경우
            if (cmd.contains("%player%")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replace("%player%", p.getName()));
                }

            // 명령어에 %vote_player%가 포함 되어있을 경우
            } else if (cmd.contains("%vote_player%")) {
                // 서버에 NuVotifier 플러그인을 적용 중일 경우
                if (Bukkit.getPluginManager().getPlugin("Votifier") != null) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (VotifierData.getUUIDList().contains(p.getUniqueId()))
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replace("%vote_player%", p.getName()));
                    }
                }

            // 명령어에 %no_vote_player%가 포함 되어있을 경우
            } else if (cmd.contains("%no_vote_player%")) {
                // 서버에 NuVotifier 플러그인을 적용 중일 경우
                if (Bukkit.getPluginManager().getPlugin("Votifier") != null) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (!VotifierData.getUUIDList().contains(p.getUniqueId()))
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replace("%no_vote_player%", p.getName()));
                    }
                }

            // 명령어에 플레이스홀더가 존재하지 않는 경우
            } else {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
            }
        }
        getInstance().getLogger().info(getName() + " 이벤트가 진행되었습니다.");
    }

    // 이벤트 이름
    public String getName() {
        return name;
    }

    // 이벤트 시간대
    public String getTime() {
        return time;
    }

    // 이벤트 1분 전 시간
    public String getOne() {
        return one;
    }

    // 이벤트 1분 전 메세지
    public String getBefore() {
        return before;
    }

    // 이벤트 진행 후 메세지
    public String getAfter() {
        return after;
    }
}