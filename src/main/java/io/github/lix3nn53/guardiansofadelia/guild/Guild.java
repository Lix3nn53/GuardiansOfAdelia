package io.github.lix3nn53.guardiansofadelia.guild;

import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Guild {

    private HashMap<UUID, PlayerRankInGuild> members = new HashMap<UUID, PlayerRankInGuild>();
    private ItemStack[] guildStorage;
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
        GuildManager.addGuildToMemory(this);
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

    public boolean addMember(UUID player) {
        if (members.size() < 20) {
            members.put(player, PlayerRankInGuild.SOLDIER);
            return true;
        }
        return false;
    }

    public void removeMember(UUID player) {
        if (members.containsKey(player)) {
            if (!members.get(player).equals(PlayerRankInGuild.LEADER)) {
                members.remove(player);
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
        GuildManager.removeGuildFromMemory(this);
        //remove from sql database
        DatabaseManager.clearGuild(this.name);
    }

    public ItemStack[] getGuildStorage() {
        return guildStorage;
    }

    public void setGuildStorage(ItemStack[] guildStorage) {
        this.guildStorage = guildStorage;
    }


    public void openGuildStorage(Player player) {
        GuiGeneric storageGui = new GuiGeneric(getBankLevel() * 9, "Guild " + getName() + "'s storage", 0);
        storageGui.setContents(this.guildStorage);
        storageGui.openInventory(player);
    }
}
