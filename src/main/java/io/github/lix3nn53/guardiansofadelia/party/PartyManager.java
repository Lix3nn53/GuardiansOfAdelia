package io.github.lix3nn53.guardiansofadelia.party;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PartyManager {

    private static HashMap<Player, Party> playerToParty = new HashMap<>();

    public static void addParty(Player player1, Player player2, Party party) {
        playerToParty.put(player1, party);
        playerToParty.put(player2, party);
    }

    public static Party getParty(Player player) {
        return playerToParty.get(player);
    }

    public static boolean inParty(Player player) {
        return playerToParty.containsKey(player);
    }

    public static void removeMember(Player player) {
        playerToParty.remove(player);
    }

    public static void addMember(Player player, Party party) {
        playerToParty.put(player, party);
    }
}
