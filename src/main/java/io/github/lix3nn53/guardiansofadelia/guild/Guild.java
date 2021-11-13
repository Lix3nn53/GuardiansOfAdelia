package io.github.lix3nn53.guardiansofadelia.guild;

import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.menu.merchant.storage.GuiGuildStorage;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.TablistUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Guild {

    private final GuiGuildStorage guildStorage = new GuiGuildStorage();
    private HashMap<UUID, PlayerRankInGuild> members = new HashMap<UUID, PlayerRankInGuild>();
    private String name;
    private String tag;
    private int warPoints = 0;
    private int hallLevel = 0;
    private int bankLevel = 0;
    private int labLevel = 0;
    private String announcement = "";

    public Guild(String name, String tag) {
        this.tag = tag;
        this.name = name;
    }

    public Set<UUID> getMembers() {
        return members.keySet();
    }

    public void setMembers(HashMap<UUID, PlayerRankInGuild> members) {
        this.members = members;
    }

    public HashMap<UUID, PlayerRankInGuild> getMembersWithRanks() {
        return members;
    }

    public boolean isMember(UUID player) {
        return members.containsKey(player);
    }

    public UUID getLeader() {
        for (UUID member : members.keySet()) {
            if (members.get(member).equals(PlayerRankInGuild.LEADER)) {
                return member;
            }
        }
        return null;
    }

    public void setLeader(UUID player) {
        if (members.containsKey(player)) {
            if (!members.get(player).equals(PlayerRankInGuild.LEADER)) {
                for (UUID member : members.keySet()) {
                    if (members.get(member).equals(PlayerRankInGuild.LEADER)) {
                        members.put(member, PlayerRankInGuild.COMMANDER);
                        break;
                    }
                }
                members.put(player, PlayerRankInGuild.LEADER);
            }
        }
    }

    public boolean addMember(UUID uuid) {
        if (members.size() < 20) {
            members.put(uuid, PlayerRankInGuild.SOLDIER);
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                GuildManager.addPlayerGuild(player, this);
            }
            return true;
        }
        return false;
    }

    public void removeMember(UUID uuid) {
        if (members.containsKey(uuid)) {
            if (!members.get(uuid).equals(PlayerRankInGuild.LEADER)) {
                members.remove(uuid);
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    GuildManager.removePlayer(player);
                }
                DatabaseManager.removeGuildOfPlayer(player.getUniqueId());
                TablistUtils.updateTablist(player);
                for (UUID memberUUID : members.keySet()) {
                    Player member = Bukkit.getPlayer(memberUUID);
                    if (member != null) {
                        if (member.isOnline()) {
                            member.sendMessage(ChatPalette.RED + player.getName() + " left your guild.");
                            TablistUtils.updateTablist(member);
                        }
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getWarPoints() {
        return warPoints;
    }

    public void setWarPoints(int warPoints) {
        this.warPoints = warPoints;
    }

    public void addWarPoints(int toAdd) {
        this.warPoints += toAdd;
    }

    public int getHallLevel() {
        return hallLevel;
    }

    public void setHallLevel(int hallLevel) {
        this.hallLevel = hallLevel;
    }

    public int getBankLevel() {
        return bankLevel;
    }

    public void setBankLevel(int bankLevel) {
        this.bankLevel = bankLevel;
    }

    public int getLabLevel() {
        return labLevel;
    }

    public void setLabLevel(int labLevel) {
        this.labLevel = labLevel;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public PlayerRankInGuild getRankInGuild(UUID player) {
        if (members.containsKey(player)) {
            PlayerRankInGuild rank = members.get(player);
            return rank;
        }
        return null;
    }

    public void setRankInGuild(UUID player, PlayerRankInGuild rank) {
        if (members.containsKey(player)) {
            members.put(player, rank);
        }
    }

    public void destroy() {
        GuildManager.removeGuild(this);
        //remove from sql database
        DatabaseManager.clearGuild(this.name);
    }

    public ItemStack[] getGuildStorage() {
        return guildStorage.getContents();
    }

    public void setGuildStorage(ItemStack[] guildStorage) {
        this.guildStorage.setContents(guildStorage);
    }


    public GuiGuildStorage getGuildStorageGui() {
        return this.guildStorage;
    }

    public boolean isEmpty() {
        return members.size() < 20;
    }
}
