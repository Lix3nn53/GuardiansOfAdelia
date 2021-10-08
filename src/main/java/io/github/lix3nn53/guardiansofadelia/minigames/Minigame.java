package io.github.lix3nn53.guardiansofadelia.minigames;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.CheckpointManager;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard.BoardWithPlayers;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Minigame {

    private final String gameTypeName;
    private final ChatPalette gameColor;
    private final String mapName;
    private final HashMap<Integer, Integer> teamToScore = new HashMap<>();
    private final List<Location> startLocations;
    private final Location backLocation;
    private final int levelReq;
    private final int instanceNo;
    private final int queueTimeLimitInTenSeconds;
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

    private final HashMap<Player, Player> playerToLastDamager = new HashMap<>();
    private final List<Checkpoint> checkpoints;
    private final HashMap<Integer, Checkpoint> teamToCheckpoint = new HashMap<>();

    private GameMode gameModeOnWin = GameMode.SPECTATOR;
    private int countDownIn5SecondsOnWin = 4;

    public Minigame(String gameTypeName, ChatPalette gameColor, String mapName, int instanceNo, int levelReq, int teamSize, int teamAmount, List<Location> startLocations, int timeLimitInMinutes,
                    int queueTimeLimitInTenSeconds, Location backLocation, int maxLives, int minTeamsAlive, int respawnDelayInSeconds, int requiredPlayerAmountToStart, List<Checkpoint> checkpoints) {
        this.gameTypeName = gameTypeName;
        this.gameColor = gameColor;
        this.mapName = mapName;
        this.backLocation = backLocation;
        this.levelReq = levelReq;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.startLocations = startLocations;
        this.instanceNo = instanceNo;
        this.queueTimeLimitInTenSeconds = queueTimeLimitInTenSeconds;
        this.teamSize = teamSize;
        this.teamAmount = teamAmount;
        this.maxLives = maxLives;
        this.minTeamsAlive = minTeamsAlive;
        this.respawnDelayInSeconds = respawnDelayInSeconds;
        this.requiredPlayerAmountToStart = requiredPlayerAmountToStart;
        this.checkpoints = checkpoints;
        for (Checkpoint checkpoint : checkpoints) {
            CheckpointManager.addCheckpoint(checkpoint);
        }

        this.gameCountDown = new BukkitRunnable() {
            @Override
            public void run() {
                cancel();
            }
        };
        gameCountDown.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 20L);

        for (int i = 1; i <= teamAmount; i++) {
            BoardWithPlayers boardWithPlayers = new BoardWithPlayers(new ArrayList<>(), gameTypeName, getScoreboardTopLines(), getTeamTextColor(i));
            teams.put(i, new Party(new ArrayList<>(), teamSize, 1, boardWithPlayers));
            teamToScore.put(i, 0);
        }
    }

    public void reformParties() {
        for (int teamNo : teams.keySet()) {
            Party party = teams.get(teamNo);
            for (Player member : party.getMembers()) {
                PartyManager.removeMember(member);
            }
        }
        teams.clear();
        for (int i = 1; i <= teamAmount; i++) {
            BoardWithPlayers boardWithPlayers = new BoardWithPlayers(new ArrayList<>(), gameTypeName, getScoreboardTopLines(), getTeamTextColor(i));
            Party party = new Party(new ArrayList<>(), teamSize, 1, boardWithPlayers);
            teams.put(i, party);
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
                        PetManager.onEggUnequip(member);

                        member.teleport(startLocations.get(teamNo - 1));

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                PetManager.onEggEquip(member);

                                if (GuardianDataManager.hasGuardianData(member)) {
                                    GuardianData guardianData = GuardianDataManager.getGuardianData(member);
                                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                                    String rpgClassStr = activeCharacter.getRpgClassStr();
                                    activeCharacter.getRpgCharacterStats().recalculateEquipment(rpgClassStr);
                                }
                            }
                        }.runTaskLater(GuardiansOfAdelia.getInstance(), 40);
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
                        onGameTick();
                        secondsPass++;
                    }
                }
            };
            gameCountDown.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 20L);
        }
    }

    public void onGameTick() {

    }

    public void endGame() {
        gameCountDown.cancel();
        List<Integer> winnerTeams = getWinnerTeams();
        for (Integer teamNo : teams.keySet()) {
            Party party = teams.get(teamNo);
            for (Player member : party.getMembers()) {
                if (member.isOnline()) {
                    MessageUtils.sendCenteredMessage(member, ChatPalette.GRAY + "------------------------");
                    if (winnerTeams.contains(teamNo)) {
                        if (winnerTeams.size() == 1) {
                            member.sendTitle(ChatPalette.GREEN_DARK + "Congratulations!", ChatPalette.YELLOW + "", 30, 80, 30);
                            MessageUtils.sendCenteredMessage(member, "You have have won the " + ChatPalette.GREEN_DARK + getMinigameName() + " !");
                        } else {
                            member.sendTitle(ChatPalette.GREEN_DARK + "Tie!", ChatPalette.YELLOW + "", 30, 80, 30);
                            MessageUtils.sendCenteredMessage(member, "You are sharing first place with another team in " + ChatPalette.GREEN_DARK + getMinigameName());
                        }
                    } else {
                        member.sendTitle(ChatPalette.RED + "Failed..", ChatPalette.YELLOW + "", 30, 80, 30);
                        MessageUtils.sendCenteredMessage(member, "You lose the " + ChatPalette.GREEN_DARK + getMinigameName());
                    }
                    MessageUtils.sendCenteredMessage(member, ChatPalette.GRAY + "------------------------");
                    member.setGameMode(gameModeOnWin);
                }
            }
        }

        new BukkitRunnable() {

            int count = 0;
            final List<Player> playersInGame = getPlayersInGame();

            @Override
            public void run() {
                if (count == countDownIn5SecondsOnWin) {
                    cancel();

                    if (isInGame) {
                        List<List<Player>> teamsList = new ArrayList<>();
                        for (Party party : teams.values()) {
                            teamsList.add(new ArrayList<>(party.getMembers()));
                        }

                        for (Player member : playersInGame) {
                            MiniGameManager.removePlayer(member);
                            member.setGameMode(GameMode.ADVENTURE);
                            member.teleport(backLocation);
                            if (PartyManager.inParty(member)) {
                                Party party = PartyManager.getParty(member);
                                party.leave(member);
                            }
                        }

                        isInGame = false;
                        teams.clear();
                        teamToScore.clear();
                        teamDeathCount.clear();
                        teamToCheckpoint.clear();
                        for (int i = 1; i <= teamAmount; i++) {
                            BoardWithPlayers boardWithPlayers = new BoardWithPlayers(new ArrayList<>(), gameTypeName, getScoreboardTopLines(), getTeamTextColor(i));
                            Minigame.this.teams.put(i, new Party(new ArrayList<>(), teamSize, 1, boardWithPlayers));
                            teamToScore.put(i, 0);
                        }

                        createNormalPartyAfterMinigame(teamsList);
                    }
                } else {
                    for (Player member : playersInGame) {
                        if (member.isOnline()) {
                            MessageUtils.sendCenteredMessage(member, getGameColor() + "You will be teleported in " + ((countDownIn5SecondsOnWin - count) * 5) + " seconds");
                        }
                    }
                    count++;
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 20 * 5L);
    }

    private void createNormalPartyAfterMinigame(List<List<Player>> teamsList) {
        for (List<Player> players : teamsList) {
            if (players.size() > 1 && players.size() <= 4) {
                Party party = new Party(players, 4, 2, ChatColor.AQUA);

                PartyManager.addParty(players, party);
            }
        }
    }

    public ChatPalette getGameColor() {
        return gameColor;
    }

    public int getQueueTimeLimitInTenSeconds() {
        return queueTimeLimitInTenSeconds;
    }

    public BukkitRunnable getQueueCountDown() {
        return queueCountDown;
    }

    public void setQueueCountDown(BukkitRunnable queueCountDown) {
        this.queueCountDown = queueCountDown;
    }

    public boolean addPlayerNoCheck(Player player) {
        if (PartyManager.inParty(player)) {
            Party party = PartyManager.getParty(player);
            party.leave(player);
        }
        for (Integer team : teams.keySet()) {
            Party party = teams.get(team);
            if (party.getMembers().size() < this.teamSize) {
                party.addMember(player);
                teams.put(team, party);
                return true;
            }
        }
        return false;
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
        if (PartyManager.inParty(player)) {
            Party party = PartyManager.getParty(player);
            List<Player> partyMembers = party.getMembers();
            boolean canPartyJoin = canPartyJoin(partyMembers);
            if (canPartyJoin) {
                party.destroy();

                for (Player partyMember : partyMembers) {
                    addPlayerNoCheck(partyMember);
                    for (Player member : getPlayersInGame()) {
                        MessageUtils.sendCenteredMessage(member, partyMember.getName() + getGameColor() + " joined queue for " + getMinigameName());
                    }
                    MiniGameManager.addPlayer(partyMember, this);
                    onPlayerJoinQueueCountdownCheck(true);
                }

                return true;
            }

            return false;
        }

        return addPlayerWithChecks(player);
    }

    private boolean canPartyJoin(List<Player> partyMembers) {
        if (!this.isInGame) {
            int emptySize = getEmptySize();
            if (partyMembers.size() > emptySize) {
                partyMembers.get(0).sendMessage(ChatPalette.RED + "There is not enough space for your party");
                return false;
            }
            for (Player player : partyMembers) {
                if (!player.getWorld().getName().equals("world")) {
                    player.sendMessage(ChatPalette.RED + "You must be in normal world");
                    return false;
                }
                if (!MiniGameManager.isInMinigame(player)) {
                    if (!getPlayersInGame().contains(player) && getPlayersInGame().size() < this.teamAmount * this.teamSize) {
                        return true;
                    }
                } else {
                    player.sendMessage(ChatPalette.RED + "You are already in a minigame");
                    return false;
                }
            }
        }
        return false;
    }

    private boolean addPlayerWithChecks(Player player) {
        if (!this.isInGame) {
            /*if (!player.getWorld().getName().equals("world")) {
                player.sendMessage(ChatPalette.RED + "You must be in normal world");
                return false;
            }*/
            if (!MiniGameManager.isInMinigame(player)) {
                if (!getPlayersInGame().contains(player) && getPlayersInGame().size() < this.teamAmount * this.teamSize) {
                    addPlayerNoCheck(player);
                    for (Player member : getPlayersInGame()) {
                        MessageUtils.sendCenteredMessage(member, player.getName() + getGameColor() + " joined queue for " + getMinigameName());
                    }
                    MiniGameManager.addPlayer(player, this);
                    onPlayerJoinQueueCountdownCheck(true);
                    return true;
                }
            } else {
                player.sendMessage(ChatPalette.RED + "You are already in a minigame");
                return false;
            }
        }

        return false;
    }

    public void onPlayerJoinQueueCountdownCheck(boolean checkPlayerSize) {
        if (checkPlayerSize && this.requiredPlayerAmountToStart != getPlayersInGame().size()) return;

        for (Player member : getPlayersInGame()) {
            MessageUtils.sendCenteredMessage(member, getGameColor() + "Countdown for " + getMinigameName() + " has started");
        }
        //start countdown
        this.queueCountDown = new BukkitRunnable() {

            int count = 0;

            @Override
            public void run() {
                if (count * 10 == queueTimeLimitInTenSeconds * 10) {
                    //start minigame
                    cancel();
                    startGame();
                } else {
                    for (Player member : getPlayersInGame()) {
                        if (member.isOnline()) {
                            MessageUtils.sendCenteredMessage(member, getGameColor().toString() + (queueTimeLimitInTenSeconds * 10 - (10 * count)) +
                                    " seconds left until " + getMinigameName() + getGameColor().toString() + " starts");
                        }
                    }
                    count++;
                }
            }
        };
        this.queueCountDown.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20 * 10L);
    }

    private void onPlayerLeaveQueueCountdownCheck() {
        if (this.requiredPlayerAmountToStart > getPlayersInGame().size()) {
            if (this.queueCountDown != null) {
                if (!this.queueCountDown.isCancelled()) {
                    for (Player member : getPlayersInGame()) {
                        MessageUtils.sendCenteredMessage(member, ChatPalette.RED + "Countdown for " + getMinigameName() + " is canceled");
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
        return this.gameColor + this.gameTypeName + " #" + getInstanceNo();
    }

    public Location getStartLocation(int teamNo) {
        return startLocations.get(teamNo - 1).clone();
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

    public int getEmptySize() {
        int playersInGameAmount = getPlayersInGame().size();
        int maxPlayerSize = getMaxPlayerSize();
        return maxPlayerSize - playersInGameAmount;
    }

    public int getPlayersInGameSize() {
        return getPlayersInGame().size();
    }

    public void leave(Player player) {
        if (player.isOnline()) {
            if (!player.getLocation().getWorld().getName().equals("world")) {
                player.teleport(this.backLocation);
            }
            MessageUtils.sendCenteredMessage(player, ChatPalette.RED + "You have left " + getMinigameName());
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
        if (getPlayersInGame().isEmpty()) {
            endGame();
        }
    }

    public List<Integer> getWinnerTeams() {
        List<Integer> teamsAtBestScore = new ArrayList<>();
        int bestScore = 0;
        for (int team : getTeams().keySet()) {
            int teamScore = getScoreOfTeam(team);
            if (teamScore > bestScore) {
                bestScore = teamScore;
            }
        }
        for (int team : getTeams().keySet()) {
            int teamScore = getScoreOfTeam(team);
            if (teamScore == bestScore) {
                teamsAtBestScore.add(team);
            }
        }
        return teamsAtBestScore;
    }

    public int getScoreOfTeam(int teamNo) {
        return teamToScore.get(teamNo);
    }

    public List<Location> getStartLocations() {
        return startLocations;
    }

    public int getInstanceNo() {
        return instanceNo;
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
        int teamNo = getTeamOfPlayer(player);
        if (teamToScore.containsKey(teamNo)) {
            Integer score = teamToScore.get(teamNo);
            score += scoreToAdd;
            teamToScore.put(teamNo, score);
            updateTeamScoresOnScoreBoard(teamNo, score);
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

    public HashMap<Integer, Integer> getTeamToScore() {
        return teamToScore;
    }

    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add(ChatColor.YELLOW + "Time remaining: " + ChatColor.RESET + this.timeLimitInMinutes * 60);
        for (int i = 0; i < teamAmount; i++) {
            topLines.add(getTeamTextColor(i + 1) + "Team" + (i + 1) + " score: " + getScoreOfTeam(i + 1));
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
                        board.setLine(getTeamTextColor(teamNoToChange) + "Team" + teamNoToChange + " score: " + newScore, k);
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
                        board.setLine(ChatColor.YELLOW + "Time remaining: " + ChatColor.RESET + seconds, k);
                        break;
                    }
                }
            }
        }
    }

    public String getGameTypeName() {
        return gameTypeName;
    }

    private void updateTeamLivesOnScoreBoard(int teamNoToChange, int teamLives) {
        for (Integer teamNo : getTeams().keySet()) {
            Party party = getTeams().get(teamNo);
            if (!party.getMembers().isEmpty()) {
                BoardWithPlayers board = party.getBoard();
                for (int k : board.getRowLines().keySet()) {
                    String s = board.getRowLines().get(k);
                    if (s.contains("Team" + teamNoToChange + " lives: ")) {
                        board.setLine(getTeamTextColor(teamNoToChange) + "Team" + teamNoToChange + " lives: " + teamLives, k);
                        break;
                    }
                }
            }
        }
    }

    private Location getStartWatchLocation(Player player, int teamOfPlayer) {
        Location startWatchLocation = getStartLocation(teamOfPlayer);
        if (this.teamSize > 1) {
            Party party = teams.get(teamOfPlayer);
            for (Player member : party.getMembers()) {
                if (!member.getUniqueId().equals(player.getUniqueId())) {
                    startWatchLocation = member.getLocation();
                    break;
                }
            }
        } else if (teamToCheckpoint.containsKey(teamOfPlayer)) {
            Checkpoint checkpoint = teamToCheckpoint.get(teamOfPlayer);
            startWatchLocation = checkpoint.getLocation();
        }
        return startWatchLocation;
    }

    public void onPlayerDealDamageToPlayer(Player attacker, Player defender) {
        playerToLastDamager.put(defender, attacker);
    }

    public boolean onPlayerDealDamageToEntity(Player attacker, LivingEntity defender) {
        return true;
    }

    public Material getTeamWool(int teamNo) {
        if (teamNo == 1) {
            return Material.LIGHT_BLUE_WOOL;
        } else if (teamNo == 2) {
            return Material.RED_WOOL;
        } else if (teamNo == 3) {
            return Material.YELLOW_WOOL;
        } else if (teamNo == 4) {
            return Material.LIME_WOOL;
        }
        return Material.WHITE_WOOL;
    }

    public ChatColor getTeamTextColor(int teamNo) {
        if (teamNo == 1) {
            return ChatColor.BLUE;
        } else if (teamNo == 2) {
            return ChatColor.RED;
        } else if (teamNo == 3) {
            return ChatColor.YELLOW;
        } else if (teamNo == 4) {
            return ChatColor.GREEN;
        }

        return ChatColor.WHITE;
    }

    public boolean onCheckpointSet(Player player, Checkpoint checkpoint) {
        int teamOfPlayer = getTeamOfPlayer(player);
        if (teamOfPlayer > -1) {
            if (checkpoints.contains(checkpoint)) {
                teamToCheckpoint.put(teamOfPlayer, checkpoint);
                Party party = teams.get(teamOfPlayer);
                for (Player member : party.getMembers()) {
                    MessageUtils.sendCenteredMessage(member, ChatPalette.GREEN_DARK + "-- " + ChatPalette.GREEN_DARK + "New Checkpoint Set" + ChatPalette.GREEN_DARK + " --");
                }

                return true;
            }
        }

        return false;
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        checkpoints.add(checkpoint);
        checkpoint.createModel();
    }

    public void onPlayerDeath(Player player) {
        if (playerToLastDamager.containsKey(player)) {
            Player attacker = playerToLastDamager.get(player);
            addScore(attacker, 1);
        }

        int teamOfPlayer = getTeamOfPlayer(player);
        Location startWatchLocation = getStartWatchLocation(player, teamOfPlayer);
        int deathCount = 0;
        if (teamDeathCount.containsKey(teamOfPlayer)) {
            deathCount = teamDeathCount.get(teamOfPlayer);
        }
        deathCount++;
        teamDeathCount.put(teamOfPlayer, deathCount);
        updateTeamLivesOnScoreBoard(teamOfPlayer, getLivesOfTeam(teamOfPlayer));
        if (deathCount >= this.maxLives) {
            fail(teamOfPlayer, startWatchLocation);
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
                                Location respawnLocation = startLocations.get(teamNo - 1);
                                if (teamToCheckpoint.containsKey(teamNo)) {
                                    respawnLocation = teamToCheckpoint.get(teamNo).getLocation();
                                }
                                player.setGameMode(GameMode.ADVENTURE);
                                player.teleport(respawnLocation);
                                PetManager.onEggEquip(player);
                                break;
                            }
                        }
                        cancel();
                    } else {
                        if (count == 0) {
                            player.teleport(startWatchLocation);
                        }
                        player.sendTitle(ChatPalette.PURPLE + "Respawn in", ChatPalette.PURPLE_LIGHT.toString() + (respawnDelayInSeconds - count) + " seconds", 0, 20, 0);
                        count++;
                    }
                }
            }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20L);
        }
    }

    public void fail(int teamNo, Location startWatchLocation) {

        for (Player player : teams.get(teamNo).getMembers()) {
            player.setGameMode(GameMode.SPECTATOR);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(startWatchLocation);
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

    public void setGameModeOnWin(GameMode gameModeOnWin) {
        this.gameModeOnWin = gameModeOnWin;
    }

    public void setCountDownIn5SecondsOnWin(int countDownIn5SecondsOnWin) {
        this.countDownIn5SecondsOnWin = countDownIn5SecondsOnWin;
    }
}
