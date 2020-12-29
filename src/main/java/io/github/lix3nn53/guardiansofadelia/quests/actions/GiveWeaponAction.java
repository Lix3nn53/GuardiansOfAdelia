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
    private final String gearSet;

    public GiveWeaponAction(int gearLevel, ItemTier tier, String gearSet) {
        this.gearLevel = gearLevel;
        this.tier = tier;
        this.gearSet = gearSet;
    }

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                String rpgClassStr = activeCharacter.getRpgClassStr();
                RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                WeaponGearType defaultWeaponGearType = rpgClass.getDefaultWeaponGearType();

                ItemStack weapon = WeaponManager.get(defaultWeaponGearType, gearLevel, 0, tier, false, gearSet);
                InventoryUtils.giveItemToPlayer(player, weapon);
            }
        }
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
