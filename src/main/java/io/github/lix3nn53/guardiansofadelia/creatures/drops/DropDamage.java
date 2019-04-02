package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

final class DropDamage {

    private final Entity entity;
    private final HashMap<Player, Integer> playerDamages = new HashMap<Player, Integer>();
    private final HashMap<Party, Integer> partyDamages = new HashMap<Party, Integer>();

    public DropDamage(Entity entity) {
        this.entity = entity;
    }

    public void addDamage(Player player, int damage) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            if (PartyManager.inParty(player)) {
                Party party = PartyManager.getParty(player);
                if (partyDamages.containsKey(party)) {
                    int current = partyDamages.get(party);
                    partyDamages.put(party, current + damage);
                } else {
                    partyDamages.put(party, damage);
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
    }

    public boolean isMostDamageDealerParty() {
        int maxPlayerDamage = 0;

        if (!this.playerDamages.isEmpty()) {
            Collection<Integer> playerDamages = this.playerDamages.values();
            maxPlayerDamage = Collections.max(playerDamages);
        }

        int maxPartyDamage = 0;

        if (!this.partyDamages.isEmpty()) {
            Collection<Integer> partyDamages = this.partyDamages.values();
            maxPartyDamage = Collections.max(partyDamages);
        }

        return maxPartyDamage > maxPlayerDamage;
    }

    public Party getBestParty() {
        if (!partyDamages.isEmpty()) {
            return Collections.max(partyDamages.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        }
        return null;
    }

    public Player getBestPlayer() {
        if (!playerDamages.isEmpty()) {
            return Collections.max(playerDamages.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        }
        return null;
    }

}
