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
import java.util.List;

public abstract class Minigame {

    private final String name;
    private final HashMap<Integer, Integer> teamToScore = new HashMap<>();
    private final List<Location> startLocations;
    private final Town backTown;
    private final int levelReq;
    private final int roomNo;
    private final int queueTimeLimitInMinutes;
    private final int timeLimitInMinutes;
    private final HashMap<Integer, List<Player>> teams = new HashMap<>();
    private final int teamSize;

    private final int teamAmount;
    private final int maxLives;
    private final HashMap<Player, Integer> playerDeathCount = new HashMap<>();
    private final int respawnDelayInSeconds;
    private final int requiredPlayerAmountToStart;
    private BukkitRunnable gameCountDown;
    private BukkitRunnable queueCountDown;
    private boolean isInGame = false;

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
        for (int i = 1; i <= teamAmount; i++) {
            teams.put(i, new ArrayList<>());
        }
    }

    public int getLevelReq() {
        return levelReq;
    }

    public HashMap<Integer, List<Player>> getTeams() {
        return teams;
    }

    public BukkitRunnable getGameCountDown() {
        return gameCountDown;
    }

    public int getTimeLimitInMinutes() {
        return timeLimitInMinutes;
    }

    public void startGame() {
        if (!getPlayersInGame().isEmpty()) {
            isInGame = true;
            forceTeamParties();

            for (int teamNo : teams.keySet()) {
                List<Player> party = teams.get(teamNo);
                for (Player member : party) {
                    if (member.isOnline()) {
                        member.teleport(startLocations.get(teamNo - 1));
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
        for (Integer teamNo : teams.keySet()) {
            List<Player> party = teams.get(teamNo);
            for (Player member : party) {
                if (member.isOnline()) {
                    if (winnerTeam == teamNo) {
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

                    for (Player member : getPlayersInGame()) {
                        MiniGameManager.removePlayer(member);
                        member.teleport(backTown.getLocation());
                        member.setGameMode(GameMode.ADVENTURE);
                        if (PartyManager.inParty(member)) {
                            Party party = PartyManager.getParty(member);
                            party.leave(member);
                        }
                    }

                    isInGame = false;
                    teams.clear();
                    for (int i = 1; i <= teamAmount; i++) {
                        teams.put(i, new ArrayList<>());
                    }
                    playerDeathCount.clear();
                    teamToScore.clear();
                    gameCountDown.cancel();
                } else {
                    for (Player member : getPlayersInGame()) {
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
        for (Player player : getPlayersInGame()) {
            if (PartyManager.inParty(player)) {
                Party party = PartyManager.getParty(player);
                party.leave(player);
            }
        }

        for (Integer team : teams.keySet()) {
            List<Player> players = teams.get(team);
            BoardWithPlayers boardWithPlayers = new BoardWithPlayers(players, this.name, getScoreboardTopLines());
            new Party(players, players.size(), boardWithPlayers);
            teamToScore.put(team, 0);
        }
    }

    public int getQueueTimeLimitInMinutes() {
        return queueTimeLimitInMinutes;
    }

    public BukkitRunnable getQueueCountDown() {
        return queueCountDown;
    }

    private void addPlayer(Player player) {
        for (Integer team : teams.keySet()) {
            List<Player> players = teams.get(team);
            if (players.size() < this.teamSize) {
                players.add(player);
                teams.put(team, players);
                break;
            }
        }
    }

    private void removePlayer(Player player) {
        for (Integer team : teams.keySet()) {
            List<Player> players = teams.get(team);
            if (players.contains(player)) {
                players.remove(player);
                teams.put(team, players);
                break;
            }
        }
    }

    public boolean joinQueue(Player player) {
        if (!this.isInGame) {
            if (!MiniGameManager.isInMinigame(player)) {
                if (!getPlayersInGame().contains(player) && getPlayersInGame().size() < this.teamAmount * this.teamSize) {
                    addPlayer(player);
                    for (Player member : getPlayersInGame()) {
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
        if (this.requiredPlayerAmountToStart == getPlayersInGame().size()) {
            for (Player member : getPlayersInGame()) {
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
                        for (Player member : getPlayersInGame()) {
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
        if (this.requiredPlayerAmountToStart > getPlayersInGame().size()) {
            if (this.queueCountDown != null) {
                if (!this.queueCountDown.isCancelled()) {
                    for (Player member : getPlayersInGame()) {
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

    public Location getStartLocation(int teamNo) {
        return startLocations.get(teamNo - 1);
    }

    public boolean isInGame() {
        return isInGame;
    }

    public boolean isFull() {
        return getPlayersInGame().size() >= this.teamAmount * this.teamSize;
    }

    public int getMaxPlayerSize() {
        return this.teamAmount * this.teamSize;
    }

    public int getPlayersInGameSize() {
        return getPlayersInGame().size();
    }

    public void leave(Player player) {
        if (player.isOnline()) {
            if (!player.getLocation().getWorld().getName().equals("world")) {
                player.teleport(this.backTown.getLocation());
            }
        }
        removePlayer(player);
        if (getPlayersInGameSize() <= 0) {
            isInGame = false;
            teams.clear();
            for (int i = 1; i <= teamAmount; i++) {
                teams.put(i, new ArrayList<>());
            }
            playerDeathCount.clear();
            teamToScore.clear();
            gameCountDown.cancel();
        }
        MiniGameManager.removePlayer(player);
        player.setGameMode(GameMode.ADVENTURE);
        onPlayerLeaveQueueCountdownCheck();
    }

    public int getWinnerTeam() {
        int bestTeamIndex = -1;
        int bestScore = 0;
        for (Integer team : teams.keySet()) {
            Integer integer = teamToScore.get(team);
            if (integer > bestScore) {
                bestScore = integer;
                bestTeamIndex = team;
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
        List<Player> playersInGame = new ArrayList<>();
        for (Integer teamNo : teams.keySet()) {
            List<Player> party = teams.get(teamNo);
            playersInGame.addAll(party);
        }
        return playersInGame;
    }

    public void addScore(Player player, int scoreToAdd) {
        int teamOfPlayer = getTeamOfPlayer(player);
        if (teamToScore.containsKey(teamOfPlayer)) {
            Integer integer = teamToScore.get(teamOfPlayer);
            integer += scoreToAdd;
            teamToScore.put(teamOfPlayer, integer);
        }
    }

    public void addScore(int teamIndex, int scoreToAdd) {
        if (teamToScore.containsKey(teamIndex)) {
            Integer score = teamToScore.get(teamIndex);
            score += scoreToAdd;
            teamToScore.put(teamIndex, score);
            updateScoreboardScore(teamIndex, score);
        }
    }

    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Time remaining: " + this.timeLimitInMinutes * 60);
        topLines.add("Score: " + 0);
        return topLines;
    }

    public void updateScoreboardScore(int teamIndex, int newScore) {
        List<Player> players = teams.get(teamIndex);
        if (!players.isEmpty()) {
            if (PartyManager.inParty(players.get(0))) {
                Party party = PartyManager.getParty(players.get(0));
                BoardWithPlayers board = party.getBoard();
                for (int k : board.getRowLines().keySet()) {
                    String s = board.getRowLines().get(k);
                    if (s.contains("Score: ")) {
                        board.setLine("Score: " + newScore, k);
                        break;
                    }
                }
            }
        }
    }

    public void updateTimeOnScoreBoards(int seconds) {
        for (Integer teamNo : teams.keySet()) {
            List<Player> players = teams.get(teamNo);
            if (!players.isEmpty()) {
                if (PartyManager.inParty(players.get(0))) {
                    Party party = PartyManager.getParty(players.get(0));
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
                            for (Integer teamNo : teams.keySet()) {
                                List<Player> players = teams.get(teamNo);
                                if (players.contains(player)) {
                                    player.teleport(startLocations.get(teamNo - 1));
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
        List<Player> party = teams.get(index);
        int lives = 0;

        for (Player player : party) {
            lives += getLivesOfPlayer(player);
        }

        return lives;
    }

    public int getTeamOfPlayer(Player player) {
        for (Integer teamNo : teams.keySet()) {
            List<Player> party = teams.get(teamNo);
            if (party.contains(player)) {
                return teamNo;
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

    public List<Integer> getAliveTeams() {
        List<Integer> aliveTeams = new ArrayList<>();
        for (Integer teamNo : teams.keySet()) {
            int livesOfTeam = getLivesOfTeam(teamNo);
            if (livesOfTeam > 0) {
                aliveTeams.add(teamNo);
            }
        }
        return aliveTeams;
    }

    public void setLivesOfPlayer(Player player, int lives) {
        int deathCount = this.maxLives - (this.maxLives - lives);
        if (deathCount < 0) {
            deathCount = 0;
        }
        playerDeathCount.put(player, deathCount);
    }
}
