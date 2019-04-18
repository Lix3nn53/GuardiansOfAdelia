package io.github.lix3nn53.guardiansofadelia.minigames.guildwar;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.guild.PlayerRankInGuild;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard.BoardWithPlayers;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class GuildWar extends Minigame {

    private final List<Castle> castles = new ArrayList<>();
    private final List<Guild> guilds = new ArrayList<>();
    private final int maxPoint;

    public GuildWar(int maxPoint, String mapName, int roomNo, List<Location> startLocations, List<Location> flagGroundLocations) {
        super(ChatColor.DARK_PURPLE + "GuildWar", mapName, roomNo, 1, 10, 2, startLocations,
                15, 1, TownManager.getTown(1), 999, 0, 20, 0);
        this.maxPoint = maxPoint;
        for (Location flagGround : flagGroundLocations) {
            castles.add(new Castle(flagGround.getBlock()));
        }
        reformParties();
    }

    @Override
    public void onGameTick() {
        for (int i = 0; i < castles.size(); i++) {
            Castle castle = castles.get(i);
            int conqueror = castle.getConqueror();
            if (conqueror > 0) {
                addScore(conqueror, 1);
            }
            performCastleConquer(castle, i+1);
        }
    }

    @Override
    public void onPlayerKill(Player killer) {
        addScore(killer, 5);
    }

    @Override
    public void addScore(int teamNo, int scoreToAdd) {
        if (!getGameCountDown().isCancelled()) {
            if (getTeamToScore().containsKey(teamNo)) {
                Integer score = getTeamToScore().get(teamNo);
                score += scoreToAdd;
                getTeamToScore().put(teamNo, score);
                updateTeamScoresOnScoreBoard(teamNo, score);
                if (score >= this.maxPoint) {
                    endGame();
                }
            }
        }
    }

    @Override
    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Score to win: " + this.maxPoint);
        if (this.castles != null) {
            for (int i = 0; i < this.castles.size(); i++) {
                topLines.add("Castle-" + (i + 1) + ": " + 0);
            }
        }
        for (int i = 0; i < getTeamAmount(); i++) {
            topLines.add("Team" + (i + 1) + " score: " + 0);
        }
        return topLines;
    }

    @Override
    public String getMinigameName() {
        return "Guild War #" + getRoomNo();
    }

    private void performCastleConquer(Castle castle, int castleNo) {
        int conqueror = castle.getConqueror();

        Location flagGround = castle.getFlagGround().getLocation();
        Collection<Entity> nearbyEntities = flagGround.getWorld().getNearbyEntities(flagGround, 4, 4, 4);

        List<Integer> nearbyTeams = new ArrayList<>();
        for (Entity entity : nearbyEntities) {
            if (entity.getType().equals(EntityType.PLAYER)) {
                Player player = (Player) entity;
                int teamOfPlayer = getTeamOfPlayer(player);
                if (teamOfPlayer > 0) {
                    if (!nearbyTeams.contains(teamOfPlayer)) {
                        nearbyTeams.add(teamOfPlayer);
                        if (nearbyTeams.size() > 1) {
                            break;
                        }
                    }
                }
            }
        }

        if (nearbyTeams.size() == 1) {
            Integer teamNo = nearbyTeams.get(0);
            Material teamWool = getTeamWool(teamNo);
            int newConqueror = castle.conquer(teamNo, teamWool);
            if (newConqueror != conqueror) {
                updateCastleOwnerOnScoreBoard(castleNo, newConqueror);
            }
        }
    }

    private void updateCastleOwnerOnScoreBoard(int castleNo, int ownerTeam) {
        for (Integer teamNo : getTeams().keySet()) {
            Party party = getTeams().get(teamNo);
            if (!party.getMembers().isEmpty()) {
                BoardWithPlayers board = party.getBoard();
                for (int k : board.getRowLines().keySet()) {
                    String s = board.getRowLines().get(k);
                    if (s.contains("Castle-" + castleNo + ": ")) {
                        board.setLine(getTeamTextColor(teamNo) + "Castle-" + castleNo + ": " + ownerTeam, k);
                        break;
                    }
                }
            }
        }
    }

    private Material getTeamWool(int teamNo) {
        if (teamNo == 1) {
            return Material.LIGHT_BLUE_WOOL;
        } else if (teamNo == 2) {
            return Material.RED_WOOL;
        }
        return null;
    }

    private ChatColor getTeamTextColor(int teamNo) {
        if (teamNo == 1) {
            return ChatColor.AQUA;
        } else if (teamNo == 2) {
            return ChatColor.RED;
        }
        return null;
    }

    @Override
    public void onPlayerDeath(Player player) {
        super.onPlayerDeath(player);
        addScore(getTeamOfPlayer(player), -5);
    }

    @Override
    public boolean joinQueue(Player player) {
        if (!isInGame()) {
            if (!MiniGameManager.isInMinigame(player)) {
                if (GuildManager.inGuild(player)) {
                    Guild guild = GuildManager.getGuild(player);
                    if (guilds.contains(guild)) {
                        return tryJoinQueue(player);
                    } else {
                        if (guilds.size() < getTeamAmount()) {
                            PlayerRankInGuild rankInGuild = guild.getRankInGuild(player.getUniqueId());
                            if (rankInGuild.equals(PlayerRankInGuild.LEADER) || rankInGuild.equals(PlayerRankInGuild.COMMANDER)) {
                                guilds.add(guild);
                                if (tryJoinQueue(player)) {
                                    if (!guilds.isEmpty()) {
                                        for (Guild guildFromList : guilds) {
                                            for (UUID uuid : guildFromList.getMembers()) {
                                                Player member = Bukkit.getPlayer(uuid);
                                                if (member != null) {
                                                    if (member.isOnline()) {
                                                        member.sendMessage("Guild '" + guild.getName() + "' joined GuildWar #" + getRoomNo());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    return true;
                                }
                            } else {
                                player.sendMessage("You must be guild Leader or Commander to join a guild war");
                            }
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You are already in a minigame");
            }
        }
        return false;
    }

    private boolean tryJoinQueue(Player player) {
        if (!getPlayersInGame().contains(player) && getPlayersInGame().size() < getTeamAmount() * getTeamSize()) {
            if (addPlayer(player)) {
                for (Player member : getPlayersInGame()) {
                    member.sendMessage(ChatColor.AQUA + player.getName() + " joined queue for " + getMinigameName());
                }
                MiniGameManager.addPlayer(player, this);
                onPlayerJoinQueueCountdownCheck();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addPlayer(Player player) {
        if (GuildManager.inGuild(player)) {
            Guild guild = GuildManager.getGuild(player);
            if (guilds.contains(guild)) {
                int teamNo = guilds.indexOf(guild) + 1;
                if (PartyManager.inParty(player)) {
                    Party party = PartyManager.getParty(player);
                    party.leave(player);
                }
                Party party = getTeams().get(teamNo);
                if (party.getMembers().size() < getTeamSize()) {
                    party.addMember(player);
                    getTeams().put(teamNo, party);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPlayerJoinQueueCountdownCheck() {
        if (this.guilds.size() == getTeamAmount()) {
            for (Player member : getPlayersInGame()) {
                member.sendMessage(ChatColor.AQUA + "Begin start countdown for " + getMinigameName());
            }
            //start countdown
            BukkitRunnable queueCountDown = new BukkitRunnable() {

                int count = 0;

                @Override
                public void run() {
                    if (count * 10 == getQueueTimeLimitInMinutes() * 60) {
                        //start minigame
                        cancel();
                        startGame();
                    } else {
                        for (Player member : getPlayersInGame()) {
                            if (member.isOnline()) {
                                member.sendMessage(ChatColor.AQUA.toString() + (getQueueTimeLimitInMinutes() * 60 - (10 * count)) + " seconds left until " + getMinigameName() + " starts");
                            }
                        }
                        count++;
                    }
                }
            };
            queueCountDown.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20 * 1L);
            setQueueCountDown(queueCountDown);
        }
    }

    @Override
    public void startGame() {
        super.startGame();
        for (Castle castle : castles) {
            castle.reset();
        }
    }

    @Override
    public void endGame() {
        super.endGame();
        List<Integer> winnerTeams = getWinnerTeams();
        if (!winnerTeams.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            if (winnerTeams.size() == 1) {
                msg.append(ChatColor.GOLD + "Winner guild: ").append(this.guilds.get(winnerTeams.get(0) - 1).getName()).append(" ( ");
                Party winnerParty = getTeams().get(winnerTeams.get(0));
                for (Player player : winnerParty.getMembers()) {
                    msg.append(player.getName());
                    msg.append(" ");
                }
                msg.append(")");
            }
            for (Player player : getPlayersInGame()) {
                player.sendMessage(msg.toString());
            }
        }
    }
}
