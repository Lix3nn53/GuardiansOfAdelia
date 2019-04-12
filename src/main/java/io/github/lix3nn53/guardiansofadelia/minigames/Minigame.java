package io.github.lix3nn53.guardiansofadelia.minigames;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard.BoardWithPlayers;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class Minigame {

    private final String name;
    private final HashMap<Party, Integer> teamToScore = new HashMap<>();
    private final List<Location> startLocations;
    private final Town backTown;
    private final int levelReq;
    private final int roomNo;
    private final int queueTimeLimitInMinutes;
    private final int timeLimitInMinutes;
    private final List<Party> teams = new ArrayList<>();
    private final int teamSize;

    private final int teamAmount;
    private final List<Player> playersInGame = new ArrayList<>();
    private BukkitRunnable gameCountDown;
    private BukkitRunnable queueCountDown;
    private boolean isInGame = false;
    private final int maxLives;
    private final HashMap<Player, Integer> playerDeathCount = new HashMap<>();
    private final int respawnDelayInSeconds;
    private final int requiredPlayerAmountToStart;

    public Minigame(String name, int roomNo, int levelReq, int teamSize, int teamAmount, List<Location> startLocations, int timeLimitInMinutes,
                    int queueTimeLimitInMinutes, Town backTown, int maxLives, int respawnDelayInSeconds, int requiredPlayerAmountToStart) {
        this.name = name;
        this.backTown = backTown;
        this.levelReq = levelReq;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.startLocations = startLocations;
        this.roomNo = roomNo;
        this.queueTimeLimitInMinutes = queueTimeLimitInMinutes;
        this.teamSize = teamSize;
        this.teamAmount = teamAmount;
        this.maxLives = maxLives;
        this.respawnDelayInSeconds = respawnDelayInSeconds;
        this.requiredPlayerAmountToStart = requiredPlayerAmountToStart;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public List<Party> getTeams() {
        return teams;
    }

    public BukkitRunnable getGameCountDown() {
        return gameCountDown;
    }

    public int getTimeLimitInMinutes() {
        return timeLimitInMinutes;
    }

    public void startGame() {
        if (!playersInGame.isEmpty()) {
            isInGame = true;
            forceTeamParties();

            for (int i = 0; i < teams.size(); i++) {
                Party party = teams.get(i);
                for (Player member : party.getMembers()) {
                    if (member.isOnline()) {
                        member.teleport(startLocations.get(i));
                    }
                }
            }

            this.gameCountDown = new BukkitRunnable() {

                int secondsPass = 0;

                @Override
                public void run() {
                    if (secondsPass == timeLimitInMinutes * 60) {
                        //end minigame
                        cancel();
                        endGame();
                    } else {
                        updateTimeOnScoreBoards(timeLimitInMinutes * 60 - secondsPass);
                        secondsPass++;
                    }
                }
            };
            gameCountDown.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 20L);
        }
    }

    public void endGame() {
        gameCountDown.cancel();
        int winnerTeam = getWinnerTeam();
        for (int i = 0; i < teams.size(); i++) {
            Party party = teams.get(i);
            for (Player member : party.getMembers()) {
                if (member.isOnline()) {
                    if (winnerTeam == i) {
                        member.sendTitle(ChatColor.GREEN + "Congratulations!", ChatColor.YELLOW + "", 30, 80, 30);
                        member.sendMessage("You have have won the " + ChatColor.GREEN + getMinigameName() + " !");
                    } else {
                        member.sendTitle(ChatColor.RED + "Failed..", ChatColor.YELLOW + "", 30, 80, 30);
                        member.sendMessage("You lose the " + ChatColor.GREEN + getMinigameName());
                    }
                    member.setGameMode(GameMode.SPECTATOR);
                }
            }
        }

        new BukkitRunnable() {

            int count = 0;

            @Override
            public void run() {
                if (count == 3) {
                    cancel();

                    for (Player member : playersInGame) {
                        MiniGameManager.removePlayer(member);
                        member.teleport(backTown.getLocation());
                        member.setGameMode(GameMode.ADVENTURE);
                    }

                    for (Party party : teams) {
                        party.destroy();
                    }

                    isInGame = false;
                    teams.clear();
                    playersInGame.clear();
                    playerDeathCount.clear();
                } else {
                    for (Player member : playersInGame) {
                        if (member.isOnline()) {
                            member.sendMessage(ChatColor.AQUA.toString() + "You will be teleported in " + (15 - (count * 5)) + " seconds.");
                        }
                    }
                    count++;
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 20 * 5L);
    }

    public void forceTeamParties() {
        teams.clear();

        for (Player player : playersInGame) {
            if (PartyManager.inParty(player)) {
                Party party = PartyManager.getParty(player);
                party.leave(player);
            }
        }

        int y = 0;
        for (int i = this.teamSize; i < playersInGame.size(); i += this.teamSize) {
            List<Player> players = playersInGame.subList(i - this.teamSize, i);
            BoardWithPlayers boardWithPlayers = new BoardWithPlayers(players, this.name, getScoreboardTopLines());
            Party party = new Party(players, players.size(), boardWithPlayers);
            teams.add(party);
            teamToScore.put(party, 0);
            y = i;
        }

        List<Player> players = playersInGame.subList(y, playersInGame.size());

        BoardWithPlayers boardWithPlayers = new BoardWithPlayers(players, this.name, getScoreboardTopLines());
        Party party = new Party(players, players.size(), boardWithPlayers);
        teams.add(party);
        teamToScore.put(party, 0);
    }

    public int getQueueTimeLimitInMinutes() {
        return queueTimeLimitInMinutes;
    }

    public BukkitRunnable getQueueCountDown() {
        return queueCountDown;
    }

    public boolean joinQueue(Player player) {
        if (!this.isInGame) {
            if (!MiniGameManager.isInMinigame(player)) {
                if (playersInGame.isEmpty()) {
                    playersInGame.add(player);
                    MiniGameManager.addPlayer(player, this);
                    onPlayerJoinQueueCountdownCheck();
                    for (Player member : playersInGame) {
                        member.sendMessage(ChatColor.AQUA + player.getName() + " joined queue for " + getMinigameName());
                    }
                    return true;
                } else if (!playersInGame.contains(player) && playersInGame.size() < this.teamAmount * this.teamSize) {
                    playersInGame.add(player);
                    for (Player member : playersInGame) {
                        member.sendMessage(ChatColor.AQUA + player.getName() + " joined queue for " + getMinigameName());
                    }
                    MiniGameManager.addPlayer(player, this);
                    onPlayerJoinQueueCountdownCheck();
                    return true;
                }
            } else {
                player.sendMessage(ChatColor.RED + "You are already in a minigame");
            }
        }
        return false;
    }

    private void onPlayerJoinQueueCountdownCheck() {
        if (this.requiredPlayerAmountToStart == playersInGame.size()) {
            for (Player member : playersInGame) {
                member.sendMessage(ChatColor.AQUA + "Begin start countdown for " + getMinigameName());
            }
            //start countdown
            this.queueCountDown = new BukkitRunnable() {

                int count = 0;

                @Override
                public void run() {
                    if (count * 10 == queueTimeLimitInMinutes * 60) {
                        //start minigame
                        cancel();
                        startGame();
                    } else {
                        for (Player member : playersInGame) {
                            if (member.isOnline()) {
                                member.sendMessage(ChatColor.AQUA.toString() + (queueTimeLimitInMinutes * 60 - (10 * count)) + " seconds left until " + getMinigameName() + " starts");
                            }
                        }
                        count++;
                    }
                }
            };
            this.queueCountDown.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20 * 10L);
        }
    }

    private void onPlayerLeaveQueueCountdownCheck() {
        if (this.requiredPlayerAmountToStart > playersInGame.size()) {
            if (this.queueCountDown != null) {
                if (!this.queueCountDown.isCancelled()) {
                    for (Player member : playersInGame) {
                        member.sendMessage(ChatColor.RED + "Cancel start countdown for " + getMinigameName());
                    }
                    this.queueCountDown.cancel();
                }
            }
        }
    }

    public int getTeamSize() {
        return teamSize;
    }

    public int getTeamAmount() {
        return teamAmount;
    }

    public String getMinigameName() {
        return "minigame default";
    }

    public Location getStartLocation(int index) {
        return startLocations.get(index);
    }

    public boolean isInGame() {
        return isInGame;
    }

    public boolean isFull() {
        return playersInGame.size() >= this.teamAmount * this.teamSize;
    }

    public int getMaxPlayerSize() {
        return this.teamAmount * this.teamSize;
    }

    public int getPlayersInGameSize() {
        return playersInGame.size();
    }

    public void leave(Player player) {
        if (PartyManager.inParty(player)) {
            Party party = PartyManager.getParty(player);
            party.leave(player);
        }
        if (player.isOnline()) {
            if (!player.getLocation().getWorld().getName().equals("world")) {
                player.teleport(this.backTown.getLocation());
            }
        }
        playersInGame.remove(player);
        if (getPlayersInGameSize() <= 0) {
            isInGame = false;
            teams.clear();
            playersInGame.clear();
            playerDeathCount.clear();
        }
        MiniGameManager.removePlayer(player);
        player.setGameMode(GameMode.ADVENTURE);
        onPlayerLeaveQueueCountdownCheck();
    }

    public int getWinnerTeam() {
        int bestTeamIndex = -1;
        int bestScore = 0;
        for (int i = 0; i < teams.size(); i++) {
            Party party = teams.get(i);
            Integer integer = teamToScore.get(party);
            if (integer > bestScore) {
                bestScore = integer;
                bestTeamIndex = i;
            }
        }
        if (bestScore == 0) {
            return -1;
        }
        return bestTeamIndex;
    }

    public List<Location> getStartLocations() {
        return startLocations;
    }

    public Town getBackTown() {
        return backTown;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public List<Player> getPlayersInGame() {
        return playersInGame;
    }

    public void addScore(Player player, int scoreToAdd) {
        if (PartyManager.inParty(player)) {
            Party party = PartyManager.getParty(player);
            if (teamToScore.containsKey(party)) {
                Integer integer = teamToScore.get(party);
                integer += scoreToAdd;
                teamToScore.put(party, integer);
            }
        }
    }

    public void addScore(int teamIndex, int scoreToAdd) {
        Party party = teams.get(teamIndex);
        if (teamToScore.containsKey(party)) {
            Integer score = teamToScore.get(party);
            score += scoreToAdd;
            teamToScore.put(party, score);
            updateScoreboardScore(party, score);
        }
    }

    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Time remaining: " + this.timeLimitInMinutes * 60);
        topLines.add("Score: " + 0);
        return topLines;
    }

    public void updateScoreboardScore(Party party, int newScore) {
        BoardWithPlayers board = party.getBoard();
        for (int k : board.getRowLines().keySet()) {
            String s = board.getRowLines().get(k);
            if (s.contains("Score: ")) {
                board.setLine("Score: " + newScore, k);
                break;
            }
        }
    }

    public void updateTimeOnScoreBoards(int seconds) {
        for (Party party : this.teams) {
            BoardWithPlayers board = party.getBoard();
            for (int k : board.getRowLines().keySet()) {
                String s = board.getRowLines().get(k);
                if (s.contains("Time remaining: ")) {
                    board.setLine("Time remaining: " + seconds, k);
                    break;
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void onPlayerDeath(Player player, Location deathLocation) {
        if (maxLives > 1) {
            int deathCount = 0;
            if (playerDeathCount.containsKey(player)) {
                deathCount = playerDeathCount.get(player);
            }
            deathCount++;
            playerDeathCount.put(player, deathCount);

            if (deathCount >= this.maxLives) {
                fail(player, deathLocation);
            } else {
                player.setGameMode(GameMode.SPECTATOR);
                //respawn countdown
                new BukkitRunnable() {

                    int count = 0;

                    @Override
                    public void run() {
                        if (count == respawnDelayInSeconds) {
                            //respawn
                            for (int i = 0; i < teams.size(); i++) {
                                Party party = teams.get(i);
                                if (party.getMembers().contains(player)) {
                                    player.teleport(startLocations.get(i));
                                    player.setGameMode(GameMode.ADVENTURE);
                                    break;
                                }
                            }
                            cancel();
                        } else {
                            if (count == 0) {
                                player.teleport(deathLocation);
                            }
                            player.sendTitle(ChatColor.DARK_PURPLE + "Respawn in", ChatColor.LIGHT_PURPLE.toString() + (respawnDelayInSeconds - count) + " seconds", 0, 20, 0);
                            count++;
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20L);
            }
        } else {
            fail(player, deathLocation);
        }
    }

    public void fail(Player player, Location deathLocation) {
        player.setGameMode(GameMode.SPECTATOR);
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(deathLocation);
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 5L);
    }

    public int getLivesOfPlayer(Player player) {
        int deathCount = 0;
        if (playerDeathCount.containsKey(player)) {
            deathCount = playerDeathCount.get(player);
        }
        return maxLives - deathCount;
    }

    public int getLivesOfTeam(int index) {
        Party party = teams.get(index);
        int lives = 0;

        for (Player player : party.getMembers()) {
            lives += getLivesOfPlayer(player);
        }

        return lives;
    }

    public int getTeamOfPlayer(Player player) {
        for (int i = 0; i < teams.size(); i++) {
            Party party = teams.get(i);
            if (party.getMembers().contains(player)) {
                return i;
            }
        }
        return -1;
    }

    public int getMaxLives() {
        return maxLives;
    }

    public int getMaxLivesOfTeam() {
        return maxLives * this.teamSize;
    }

    public List<Party> getAliveTeams() {
        List<Party> aliveTeams = new ArrayList<>();
        for (int i = 0; i < getTeams().size(); i++) {
            int livesOfTeam = getLivesOfTeam(i);
            if (livesOfTeam > 0) {
                aliveTeams.add(getTeams().get(i));
            }
        }
        return aliveTeams;
    }
}
