package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TutorialEndAction implements Action {

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                RPGInventory rpgInventory = activeCharacter.getRpgInventory();
                rpgInventory.clearInventory(player);

                player.getInventory().clear();

                InventoryUtils.setMenuItemPlayer(player);

                RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                rpgCharacterStats.setTotalExp(0);
                rpgCharacterStats.resetAttributes();

                rpgCharacterStats.setCurrentHealth(rpgCharacterStats.getTotalMaxHealth());
                rpgCharacterStats.setCurrentMana(rpgCharacterStats.getTotalMaxMana());

                activeCharacter.getSkillBar().resetSkillPoints();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.removePotionEffect(PotionEffectType.WITHER);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 4));
                        QuestNPCManager.setAllNpcHologramForPlayer(player);
                        activeCharacter.getRpgCharacterStats().recalculateEquipment(activeCharacter.getRpgClassStr());
                        activeCharacter.getRpgCharacterStats().recalculateRPGInventory(rpgInventory);
                    }
                }.runTaskLater(GuardiansOfAdelia.getInstance(), 5L);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        DatabaseManager.writeGuardianDataWithCurrentCharacter(player, guardianData);
                    }
                }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20L);
            }
        }
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
