package io.github.lix3nn53.guardiansofadelia.minigames;

import io.github.lix3nn53.guardiansofadelia.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public interface Minigame {

    int getLevelReq();

    List<Party> getTeams();

    BukkitRunnable getCountDown();

    int getTimeLimitInMinutes();

    void startGame();

    void endGame();

    void forceTeamParties();

    int getQueueTimeLimitInMinutes();

    BukkitRunnable getQueueCountDown();

    boolean joinQueue(Player player);

    int getTeamSize();

    int getTeamAmount();
}
