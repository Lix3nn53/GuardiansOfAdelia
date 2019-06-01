package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

final class DropDamage {

    private final HashMap<Player, Integer> playerDamages = new HashMap<>();

    public void dealDamage(Player player, double damageDouble) {
        int damage = (int) (damageDouble + 0.5);
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

    public List<Player> getBestPlayers() {
        List<Player> players = new ArrayList<>();
        if (!playerDamages.isEmpty()) {
            double max = Collections.max(playerDamages.values());

            return playerDamages.entrySet().stream()
                    .filter(entry -> entry.getValue() == max)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
        return players;
    }

}
