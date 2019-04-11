package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.minigames.MinigameType;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Dungeon extends Minigame {

    private final DungeonTheme theme;
    private final String bossMobName;


    public Dungeon(int levelReq, int timeLimitInMinutes, DungeonTheme theme, int roomNo, List<Location> startLocation, String bossMobName) {
        super(MinigameType.DUNGEON, roomNo, levelReq, 4, 1, startLocation, timeLimitInMinutes, 1, theme.getDefaultTown());
        this.theme = theme;
        this.bossMobName = bossMobName;
    }

    @Override
    public void endGame() {
        super.endGame();
        int winnerTeam = getWinnerTeam();
        if (winnerTeam >= 0) {
            PrizeChest prizeChest = theme.getPrizeChest();
            ItemStack prizeItem = prizeChest.getChest();

            Party party = getTeams().get(winnerTeam);
            for (Player member : party.getMembers()) {
                member.sendMessage(ChatColor.GOLD + "Dungeon prize: " + prizeItem.getItemMeta().getDisplayName());
                InventoryUtils.giveItemToPlayer(member, prizeItem);
            }
        }
    }

    @Override
    public String getMinigameName() {
        return this.theme.getName() + " #" + getRoomNo();
    }

    public void onBossKill(String mobName) {
        if (isInGame() && this.bossMobName.equals(mobName)) {
            addScore(0, 1);
            endGame();
        }
    }

    @Override
    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Time remaining: " + getTimeLimitInMinutes() * 60);
        topLines.add("Boss: " + getBossMobName());
        return topLines;
    }

    public String getBossMobName() {
        return bossMobName;
    }
}
