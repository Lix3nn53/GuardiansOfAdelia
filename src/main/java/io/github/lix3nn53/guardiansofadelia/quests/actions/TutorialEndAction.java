package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class TutorialEndAction implements Action {

    public TutorialEndAction() {

    }

    @Override
    public void perform(Player player) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                RPGInventory rpgInventory = activeCharacter.getRpgInventory();
                rpgInventory.clearInventory(player);

                player.removePotionEffect(PotionEffectType.WITHER);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 4, 4));
                player.getInventory().clear();

                InventoryUtils.setMenuItemPlayer(player);

                String className = SkillAPIUtils.getClassName(player, SkillAPIUtils.getActiveCharacterNo(player));
                String newClass = className.replace("tutorial", "");

                SkillAPIUtils.refunAttributes(player);
                SkillAPIUtils.clearBonuses(player);
                SkillAPIUtils.resetClass(player);

                SkillAPIUtils.setClass(player, newClass);

                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 1D);
                SkillAPIUtils.recoverMana(player);

            }
        }
    }

}
