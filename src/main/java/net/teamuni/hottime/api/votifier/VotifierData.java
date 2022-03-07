package net.teamuni.hottime.api.votifier;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VotifierData {
    private static List<UUID> uuids = new ArrayList<>();

    public static void addUUID(String name) {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                if (!uuids.contains(p.getUniqueId()))
                    uuids.add(p.getUniqueId());
                return;
            }
        }
    }

    public static void addUUID(UUID uuid) {
        if (!uuids.contains(uuid))
            uuids.add(uuid);
    }

    public static void resetUUIDList() {
        uuids = new ArrayList<>();
    }

    public static List<UUID> getUUIDList() {
        return uuids;
    }
}
