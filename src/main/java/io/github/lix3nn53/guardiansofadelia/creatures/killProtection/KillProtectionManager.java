package io.github.lix3nn53.guardiansofadelia.creatures.killProtection;

import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropProtectionManager;
import io.github.lix3nn53.guardiansofadelia.creatures.drops.MobDropGenerator;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetExperienceManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.jobs.GatheringType;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.socket.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.socket.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KillProtectionManager {

    private static HashMap<LivingEntity, PlayerDamage> livingEntityToDamages = new HashMap<>();

    public static void onPlayerDealDamageToLivingEntity(Player attacker, LivingEntity damaged, double damage) {
        if (livingEntityToDamages.containsKey(damaged)) {
            PlayerDamage playerDamage = livingEntityToDamages.get(damaged);
            playerDamage.dealDamage(attacker, damage);
        } else {
            PlayerDamage playerDamage = new PlayerDamage();
            playerDamage.dealDamage(attacker, damage);
            livingEntityToDamages.put(damaged, playerDamage);
        }
    }

    public static void onMobDeath(LivingEntity livingTarget, EntityDeathEvent event) {
        if (livingEntityToDamages.containsKey(livingTarget)) {
            PlayerDamage playerDamage = livingEntityToDamages.get(livingTarget);
            livingEntityToDamages.remove(livingTarget);
            List<Player> bestPlayers = playerDamage.getBestPlayers();
            if (bestPlayers.isEmpty()) return;

            //drops
            List<ItemStack> drops = MobDropGenerator.getDrops(livingTarget);
            if (!drops.isEmpty()) {
                for (ItemStack itemStack : drops) {
                    DropProtectionManager.setItem(itemStack, bestPlayers);
                }
                event.getDrops().addAll(drops);
            }

            //calculate before loop
            int expToGiveEachPlayer = getExperience(livingTarget);
            if (BoostPremiumManager.isBoostActive(BoostPremium.EXPERIENCE)) {
                expToGiveEachPlayer = expToGiveEachPlayer * 2;
            }
            if (expToGiveEachPlayer > 0) {
                if (bestPlayers.size() > 1) {
                    double expMultiplier = 1 - (0.1 * bestPlayers.size());
                    if (expMultiplier < 0.5D) {
                        expMultiplier = 0.5D;
                    }
                    expToGiveEachPlayer = (int) (expToGiveEachPlayer * expMultiplier);
                }
            }

            //run for each player
            for (Player player : bestPlayers) {
                UUID uuid = player.getUniqueId();
                if (GuardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                        //exp
                        if (expToGiveEachPlayer > 0) {
                            rpgCharacterStats.giveExp(expToGiveEachPlayer);
                            PetExperienceManager.giveExperienceToActivePet(player, expToGiveEachPlayer);
                        }

                        //quest, progress kill tasks
                        List<Quest> questList = activeCharacter.getQuestList();
                        for (Quest quest : questList) {
                            quest.progressKillTasks(player, livingTarget);
                            quest.triggerQuestItemDrop(player, livingTarget);
                        }

                        //hunting
                        if (livingTarget.getType().equals(EntityType.COW) || livingTarget.getType().equals(EntityType.SHEEP)) {
                            ItemStack itemStack = GatheringType.HUNTING.onHunt(player);
                            if (itemStack != null) {
                                Location targetLocation = livingTarget.getLocation();
                                Item item = livingTarget.getWorld().dropItemNaturally(targetLocation.add(0, 0.5, 0), itemStack);
                                DropProtectionManager.setItem(item.getItemStack(), player);
                            }
                        }
                    }
                }
            }
        }
    }

    private static int getExperience(Entity entity) {
        if (PersistentDataContainerUtil.hasInteger(entity, "experience")) {
            return PersistentDataContainerUtil.getInteger(entity, "experience");
        }
        return 0;
    }
}
