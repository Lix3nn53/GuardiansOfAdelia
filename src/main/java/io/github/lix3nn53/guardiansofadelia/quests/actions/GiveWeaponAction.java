package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveWeaponAction implements Action {

    private final GearLevel gearLevel;
    private final ItemTier tier;
    private final String gearSet;
    private final int itemIndex;

    public GiveWeaponAction(GearLevel gearLevel, ItemTier tier, String gearSet, int itemIndex) {
        this.gearLevel = gearLevel;
        this.tier = tier;
        this.gearSet = gearSet;
        this.itemIndex = itemIndex;
    }

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                String rpgClassStr = activeCharacter.getRpgClassStr();
                RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                WeaponGearType defaultWeaponGearType = rpgClass.getDefaultWeaponGearType();

                ItemStack weapon = WeaponManager.get(defaultWeaponGearType, gearLevel, tier, false, gearSet).get(itemIndex);
                InventoryUtils.giveItemToPlayer(player, weapon);
            }
        }
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
