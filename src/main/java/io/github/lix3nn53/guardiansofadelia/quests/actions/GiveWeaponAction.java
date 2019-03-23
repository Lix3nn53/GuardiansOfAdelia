package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GiveWeaponAction implements Action {

    private final int placementNumber;
    private final ItemTier tier;
    private final String itemTag;
    private final int minStatValue;
    private final int maxStatValue;
    private final int minNumberOfStats;

    public GiveWeaponAction(int placementNumber, ItemTier tier, String itemTag, int minStatValue, int maxStatValue, int minNumberOfStats) {
        this.placementNumber = placementNumber;
        this.tier = tier;
        this.itemTag = itemTag;
        this.minStatValue = minStatValue;
        this.maxStatValue = maxStatValue;
        this.minNumberOfStats = minNumberOfStats;
    }

    @Override
    public void perform(Player player) {
        GuardianDataManager guardianDataManager = GuardiansOfAdelia.getGuardianDataManager();
        UUID uuid = player.getUniqueId();
        if (guardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = guardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                RPGClass rpgClass = activeCharacter.getRPGClass();
                ItemStack weapon = Weapons.getWeapon(rpgClass, placementNumber, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
                InventoryUtils.giveItemToPlayer(player, weapon);
            }
        }
    }
}
