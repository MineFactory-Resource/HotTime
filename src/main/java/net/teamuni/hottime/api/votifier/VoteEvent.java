package net.teamuni.hottime.api.votifier;

import static net.teamuni.hottime.HotTime.config;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class VoteEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onVoteEvent(VotifierEvent event) {
        Vote vote = event.getVote();
        if (vote.getServiceName().equalsIgnoreCase(config.votifierName)) {
            VotifierData.addUUID(vote.getUsername());
        }
    }
}
