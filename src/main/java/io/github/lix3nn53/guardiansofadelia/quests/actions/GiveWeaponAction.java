package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GiveWeaponAction implements Action {

    private final int gearLevel;
    private final ItemTier tier;
    private final String itemTag;
    private final int minStatValue;
    private final int maxStatValue;
    private final int minNumberOfStats;

    public GiveWeaponAction(int gearLevel, ItemTier tier, String itemTag, int minStatValue, int maxStatValue, int minNumberOfStats) {
        this.gearLevel = gearLevel;
        this.tier = tier;
        this.itemTag = itemTag;
        this.minStatValue = minStatValue;
        this.maxStatValue = maxStatValue;
        this.minNumberOfStats = minNumberOfStats;
    }

    @Override
    public void perform(Player player) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                String rpgClassStr = activeCharacter.getRpgClassStr();
                RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                WeaponGearType defaultWeaponGearType = rpgClass.getDefaultWeaponGearType();

                ItemStack weapon = WeaponManager.get(defaultWeaponGearType, gearLevel, 0, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
                InventoryUtils.giveItemToPlayer(player, weapon);
            }
        }
    }
}
