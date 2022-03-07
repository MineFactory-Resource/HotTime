package net.teamuni.hottime.configs;

import static net.teamuni.hottime.HotTime.getInstance;
import net.teamuni.hottime.api.votifier.VotifierData;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class Players extends Frame{
    private static final SimpleDateFormat format = new SimpleDateFormat("yyMMdd");

    public Players() {
        super("players");
    }

    @Override
    protected void load() {
        super.load();

        getData();
    }

    public void save() {
        config.set("saved_date", format.format(new Date()));
        List<String> uuids = new ArrayList<>();
        for (UUID uuid : VotifierData.getUUIDList()) {
            uuids.add(uuid.toString());
        }
        config.set("players", uuids);

        if (configFile != null && config != null) {
            try {
                config.save(configFile);
            } catch (IOException e) {
                getInstance().getLogger().log(Level.SEVERE, "players.yml를 저장할 수 없습니다. " + configFile, e);
            }
        }
    }

    private void getData() {
        String today = format.format(new Date());
        // 마지막 저장일이 오늘 날짜일 경우 데이터 불러오기
        if (today.equals(config.getString("saved_date"))) {
            for (String s : config.getStringList("players")) {
                // 잘못된 uuid 형식일 경우 무시
                String UUID_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";
                if (!Pattern.matches(UUID_PATTERN, s))
                    return;
                VotifierData.addUUID(UUID.fromString(s));
            }
        }
    }
}
