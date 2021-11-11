package io.github.lix3nn53.guardiansofadelia.creatures.killProtection;

import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

final class PlayerDamage {

    private final HashMap<Player, Integer> playerDamages = new HashMap<>();

    void dealDamage(Player player, float damageFloat) {
        int damage = (int) (damageFloat + 0.5);
        if (PartyManager.inParty(player)) {
            Party party = PartyManager.getParty(player);
            List<Player> members = party.getMembers();
            for (Player member : members) {
                if (playerDamages.containsKey(member)) {
                    int current = playerDamages.get(member);
                    playerDamages.put(member, current + damage);
                } else {
                    playerDamages.put(member, damage);
                }
            }
        } else {
            if (playerDamages.containsKey(player)) {
                int current = playerDamages.get(player);
                playerDamages.put(player, current + damage);
            } else {
                playerDamages.put(player, damage);
            }
        }
    }

    List<Player> getBestPlayers() {
        List<Player> players = new ArrayList<>();
        if (!playerDamages.isEmpty()) {
            float max = Collections.max(playerDamages.values());

            return playerDamages.entrySet().stream()
                    .filter(entry -> entry.getValue() == max)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
        return players;
    }

}
