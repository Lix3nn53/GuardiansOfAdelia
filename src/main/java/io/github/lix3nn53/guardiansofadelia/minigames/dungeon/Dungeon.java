package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Dungeon implements Minigame {

    private final Location startLocation;

    private final DungeonTheme theme;
    private final int roomNo;
    private final int levelReq;

    private final int queueTimeLimitInMinutes;
    private final int timeLimitInMinutes;

    private final List<Party> teams = new ArrayList<>();
    private final int teamSize;

    private final int teamAmount;
    private final List<Player> playersInQueue = new ArrayList<>();
    private BukkitRunnable countDown;
    private BukkitRunnable queueCountDown;
    private boolean isInGame = false;
    private boolean bossKill = false;

    public Dungeon(int levelReq, int queueTimeLimitInMinutes, int timeLimitInMinutes, int teamSize, int teamAmount, DungeonTheme theme,
                   int roomNo, Location startLocation) {
        this.levelReq = levelReq;
        this.queueTimeLimitInMinutes = queueTimeLimitInMinutes;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.teamSize = teamSize;
        this.teamAmount = teamAmount;
        this.theme = theme;
        this.roomNo = roomNo;
        this.startLocation = startLocation;
    }

    @Override
    public int getLevelReq() {
        return levelReq;
    }

    @Override
    public List<Party> getTeams() {
        return teams;
    }

    @Override
    public BukkitRunnable getCountDown() {
        return countDown;
    }

    @Override
    public int getTimeLimitInMinutes() {
        return timeLimitInMinutes;
    }

    @Override
    public void startGame() {
        if (!playersInQueue.isEmpty()) {
            isInGame = true;
            forceTeamParties();
            for (Party team : teams) {
                for (Player member : team.getMembers()) {
                    if (member.isOnline()) {
                        member.teleport(startLocation);
                        member.sendMessage(ChatColor.AQUA + "Dungeon Start! " + getDungeonName());
                    }
                }
            }

            this.countDown = new BukkitRunnable() {

                int minutesPass = 0;

                @Override
                public void run() {
                    if (minutesPass == timeLimitInMinutes) {
                        //end dungeon
                        cancel();
                        endGame();
                    } else {
                        for (Party team : teams) {
                            for (Player member : team.getMembers()) {
                                if (member.isOnline()) {
                                    member.sendMessage(ChatColor.AQUA.toString() + (timeLimitInMinutes - (minutesPass)) + " minute(s) remaining..");
                                }
                            }
                        }
                        minutesPass++;
                    }
                }
            };
            countDown.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 20 * 60L);
        }
    }

    @Override
    public void endGame() {
        Town backTown = theme.getDefaultTown();
        PrizeChest prizeChest = theme.getPrizeChest();
        ItemStack prizeItem = prizeChest.getChest();
        for (Party team : teams) {
            for (Player member : team.getMembers()) {
                if (member.isOnline()) {
                    member.sendTitle(ChatColor.GREEN + "Congratulations!", ChatColor.YELLOW + "", 30, 80, 30);
                    member.sendMessage("You have successfully completed the " + ChatColor.GREEN + getDungeonName() + " !");
                    member.setGameMode(GameMode.SPECTATOR);
                }
            }
        }
        new BukkitRunnable() {

            int count = 0;

            @Override
            public void run() {
                if (count == 5) {
                    cancel();
                    for (Party team : teams) {
                        for (Player member : team.getMembers()) {
                            if (member.isOnline()) {
                                member.teleport(backTown.getLocation());
                                member.setGameMode(GameMode.ADVENTURE);
                                member.sendMessage(ChatColor.GOLD + "Dungeon prize: " + prizeItem.getItemMeta().getDisplayName());
                                InventoryUtils.giveItemToPlayer(member, prizeItem);
                            }
                        }
                    }
                    isInGame = false;
                } else {
                    for (Party team : teams) {
                        for (Player member : team.getMembers()) {
                            if (member.isOnline()) {
                                member.sendMessage(ChatColor.AQUA.toString() + "You will be teleported in " + (25 - (count * 5)) + " seconds.");
                            }
                        }
                    }
                    count++;
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 20 * 5L);
    }

    @Override
    public void forceTeamParties() {
        if (playersInQueue.size() > 1) {
            for (Player player : playersInQueue) {
                if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
                    if (PartyManager.inParty(player)) {
                        Party party = PartyManager.getParty(player);
                        party.leave(player);
                    }
                }
            }
            Party party = new Party(playersInQueue, playersInQueue.size());
            teams.set(0, party);
        }
    }

    @Override
    public int getQueueTimeLimitInMinutes() {
        return queueTimeLimitInMinutes;
    }

    @Override
    public BukkitRunnable getQueueCountDown() {
        return queueCountDown;
    }

    @Override
    public boolean joinQueue(Player player) {
        //start countdown
        this.queueCountDown = new BukkitRunnable() {

            int count = 0;

            @Override
            public void run() {
                if (count == 12) { // 12
                    //start dungeon
                    cancel();
                    startGame();
                } else {
                    for (Party team : teams) {
                        for (Player member : team.getMembers()) {
                            if (member.isOnline()) {
                                member.sendMessage(ChatColor.AQUA.toString() + (120 - (10 * count)) + " seconds left to start of the dungeon " + getDungeonName());
                            }
                        }
                    }
                    count++;
                }
            }
        };
        this.queueCountDown.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20 * 10L);
        return false;
    }

    @Override
    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public int getTeamAmount() {
        return teamAmount;
    }

    public String getDungeonName() {
        return this.theme.getName() + " (Room-" + this.roomNo + ")";
    }

    public void onBossKill() {
        this.bossKill = true;
        endGame();
    }
}
