package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

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

                player.getInventory().clear();

                InventoryUtils.setMenuItemPlayer(player);

                String className = SkillAPIUtils.getClassName(player, SkillAPIUtils.getActiveCharacterNo(player));
                String newClass = className.replace("Tutorial", "");

                SkillAPIUtils.refunAttributes(player);
                SkillAPIUtils.clearBonuses(player);
                SkillAPIUtils.resetClass(player);

                SkillAPIUtils.setClass(player, newClass);

                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 1D);
                SkillAPIUtils.recoverMana(player);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.removePotionEffect(PotionEffectType.WITHER);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 4));
                        QuestNPCManager.setAllNpcHologramForPlayer(player);
                    }
                }.runTaskLater(GuardiansOfAdelia.getInstance(), 5L);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        UUID uuid = player.getUniqueId();
                        if (GuardianDataManager.hasGuardianData(uuid)) {
                            DatabaseManager.writeGuardianDataWithCurrentCharacter(player, guardianData);
                        }
                    }
                }.runTaskAsynchronously(GuardiansOfAdelia.getInstance());
            }
        }
    }

}
