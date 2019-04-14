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

    private final String gameName;
    private final String mapName;
    private final HashMap<Integer, Integer> teamToScore = new HashMap<>();
    private final List<Location> startLocations;
    private final Town backTown;
    private final int levelReq;
    private final int roomNo;
    private final int queueTimeLimitInMinutes;
    private final int timeLimitInMinutes;
    private final HashMap<Integer, Party> teams = new HashMap<>();
    private final int teamSize;

    private final int teamAmount;
    private final int maxLives;
    private final int minTeamsAlive;
    private final HashMap<Integer, Integer> teamDeathCount = new HashMap<>();
    private final int respawnDelayInSeconds;
    private final int requiredPlayerAmountToStart;
    private BukkitRunnable gameCountDown;
    private BukkitRunnable queueCountDown;
    private boolean isInGame = false;

    public Minigame(String gameName, String mapName, int roomNo, int levelReq, int teamSize, int teamAmount, List<Location> startLocations, int timeLimitInMinutes,
                    int queueTimeLimitInMinutes, Town backTown, int maxLives, int minTeamsAlive, int respawnDelayInSeconds, int requiredPlayerAmountToStart) {
        this.gameName = gameName;
        this.mapName = mapName;
        this.backTown = backTown;
        this.levelReq = levelReq;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.startLocations = startLocations;
        this.roomNo = roomNo;
        this.queueTimeLimitInMinutes = queueTimeLimitInMinutes;
        this.teamSize = teamSize;
        this.teamAmount = teamAmount;
        this.maxLives = maxLives;
        this.minTeamsAlive = minTeamsAlive;
        this.respawnDelayInSeconds = respawnDelayInSeconds;
        this.requiredPlayerAmountToStart = requiredPlayerAmountToStart;
        for (int i = 1; i <= teamAmount; i++) {
            BoardWithPlayers boardWithPlayers = new BoardWithPlayers(new ArrayList<>(), gameName, getScoreboardTopLines());
            teams.put(i, new Party(new ArrayList<>(), teamSize, 1, boardWithPlayers));
            teamToScore.put(i, 0);
        }
    }

    public int getLevelReq() {
        return levelReq;
    }

    public HashMap<Integer, Party> getTeams() {
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

            for (int teamNo : teams.keySet()) {
                Party party = teams.get(teamNo);
                for (Player member : party.getMembers()) {
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
        if (!gameCountDown.isCancelled()) {
            gameCountDown.cancel();
            int winnerTeam = getWinnerTeam();
            for (Integer teamNo : teams.keySet()) {
                Party party = teams.get(teamNo);
                for (Player member : party.getMembers()) {
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

                        if (isInGame) {
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
                            teamToScore.clear();
                            for (int i = 1; i <= teamAmount; i++) {
                                BoardWithPlayers boardWithPlayers = new BoardWithPlayers(new ArrayList<>(), gameName, getScoreboardTopLines());
                                teams.put(i, new Party(new ArrayList<>(), teamSize, 1, boardWithPlayers));
                                teamToScore.put(i, 0);
                            }
                            teamDeathCount.clear();
                            gameCountDown.cancel();
                        }
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
    }

    public int getQueueTimeLimitInMinutes() {
        return queueTimeLimitInMinutes;
    }

    public BukkitRunnable getQueueCountDown() {
        return queueCountDown;
    }

    private void addPlayer(Player player) {
        if (PartyManager.inParty(player)) {
            Party party = PartyManager.getParty(player);
            party.leave(player);
        }
        for (Integer team : teams.keySet()) {
            Party party = teams.get(team);
            if (party.getMembers().size() < this.teamSize) {
                party.addMember(player);
                teams.put(team, party);
                break;
            }
        }
    }

    private void removePlayer(Player player) {
        for (Integer team : teams.keySet()) {
            Party party = teams.get(team);
            if (party.getMembers().contains(player)) {
                party.leave(player);
                teams.put(team, party);
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
            this.queueCountDown.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20 * 1L);
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

        int teamOfPlayer = getTeamOfPlayer(player);

        removePlayer(player);

        if (getTeams().get(teamOfPlayer).getMembers().isEmpty()) {
            setLivesOfTeam(teamOfPlayer, 0);
        }

        if (getAliveTeams().size() == minTeamsAlive) {
            endGame();
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
            Party party = teams.get(teamNo);
            playersInGame.addAll(party.getMembers());
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

    public void addScore(int teamNo, int scoreToAdd) {
        if (teamToScore.containsKey(teamNo)) {
            Integer score = teamToScore.get(teamNo);
            score += scoreToAdd;
            teamToScore.put(teamNo, score);
            updateTeamScoresOnScoreBoard(teamNo, score);
        }
    }

    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Time remaining: " + this.timeLimitInMinutes * 60);
        for (int i = 0; i < teamAmount; i++) {
            topLines.add("Team" + (i + 1) + " score: " + getMaxLives());
        }
        return topLines;
    }

    public void updateTeamScoresOnScoreBoard(int teamNoToChange, int newScore) {
        for (Integer teamNo : teams.keySet()) {
            Party party = teams.get(teamNo);
            if (!party.getMembers().isEmpty()) {
                BoardWithPlayers board = party.getBoard();
                for (int k : board.getRowLines().keySet()) {
                    String s = board.getRowLines().get(k);
                    if (s.contains("Team" + teamNoToChange + " score: ")) {
                        board.setLine("Team" + teamNoToChange + " score: " + newScore, k);
                        break;
                    }
                }
            }
        }
    }

    public void updateTimeOnScoreBoards(int seconds) {
        for (Integer teamNo : teams.keySet()) {
            Party party = teams.get(teamNo);
            if (!party.getMembers().isEmpty()) {
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

    public String getGameName() {
        return gameName;
    }

    private void updateTeamLivesOnScoreBoard(int teamNoToChange, int teamLives) {
        for (Integer teamNo : getTeams().keySet()) {
            Party party = getTeams().get(teamNo);
            if (!party.getMembers().isEmpty()) {
                BoardWithPlayers board = party.getBoard();
                for (int k : board.getRowLines().keySet()) {
                    String s = board.getRowLines().get(k);
                    if (s.contains("Team" + teamNoToChange + " lives: ")) {
                        board.setLine("Team" + teamNoToChange + " lives: " + teamLives, k);
                        break;
                    }
                }
            }
        }
    }

    public void onPlayerDeath(Player player, Location deathLocation) {
        int teamOfPlayer = getTeamOfPlayer(player);
        if (maxLives > 1) {
            int deathCount = 0;
            if (teamDeathCount.containsKey(teamOfPlayer)) {
                deathCount = teamDeathCount.get(teamOfPlayer);
            }
            deathCount++;
            teamDeathCount.put(teamOfPlayer, deathCount);
            updateTeamLivesOnScoreBoard(teamOfPlayer, getLivesOfTeam(teamOfPlayer));
            if (deathCount >= this.maxLives) {
                fail(teamOfPlayer, deathLocation);
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
                                Party party = teams.get(teamNo);
                                if (party.getMembers().contains(player)) {
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
            fail(teamOfPlayer, deathLocation);
        }
    }

    public void fail(int teamNo, Location deathLocation) {
        for (Player player : teams.get(teamNo).getMembers()) {
            player.setGameMode(GameMode.SPECTATOR);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(deathLocation);
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), 5L);
        }
        //check game end
        if (getAliveTeams().size() == minTeamsAlive) {
            endGame();
        }
    }

    public int getLivesOfTeam(int teamNo) {
        int deathCount = 0;
        if (teamDeathCount.containsKey(teamNo)) {
            deathCount = teamDeathCount.get(teamNo);
        }
        return maxLives - deathCount;
    }

    public int getTeamOfPlayer(Player player) {
        for (Integer teamNo : teams.keySet()) {
            Party party = teams.get(teamNo);
            if (party.getMembers().contains(player)) {
                return teamNo;
            }
        }
        return -1;
    }

    public int getMaxLives() {
        return maxLives;
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

    public void setLivesOfTeam(int teamNo, int lives) {
        int deathCount = this.maxLives - lives;
        if (deathCount < 0) {
            deathCount = 0;
        }
        teamDeathCount.put(teamNo, deathCount);
        updateTeamLivesOnScoreBoard(teamNo, lives);
    }

    public String getMapName() {
        return mapName;
    }

    public int getNonEmptyTeamAmount() {
        int i = 0;
        for (int teamNo : teams.keySet()) {
            if (!teams.get(teamNo).getMembers().isEmpty()) {
                i++;
            }
        }
        return i;
    }
}
