package io.github.lix3nn53.guardiansofadelia.creatures.killProtection;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropProtectionManager;
import io.github.lix3nn53.guardiansofadelia.creatures.drops.MobDropGenerator;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetExperienceManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringType;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KillProtectionManager {

    private static final HashMap<LivingEntity, PlayerDamage> livingEntityToDamages = new HashMap<>();

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

    /**
     * @param livingTarget
     * @param mythicEvent
     */
    public static void onLivingEntityDeath(LivingEntity livingTarget, MythicMobDeathEvent mythicEvent) {
        if (livingEntityToDamages.containsKey(livingTarget)) {
            PlayerDamage playerDamage = livingEntityToDamages.get(livingTarget);
            livingEntityToDamages.remove(livingTarget);
            List<Player> bestPlayers = playerDamage.getBestPlayers();
            if (bestPlayers.isEmpty()) return;

            //drops
            int mobLevel = 0;
            if (mythicEvent != null) {
                mobLevel = (int) (mythicEvent.getMobLevel() + 0.5);
                List<ItemStack> drops = MobDropGenerator.getDrops(mobLevel);
                if (!drops.isEmpty()) {
                    for (ItemStack itemStack : drops) {
                        DropProtectionManager.setItem(itemStack, bestPlayers);
                    }
                    mythicEvent.getDrops().addAll(drops);
                }
            }

            //run only once
            Player once = bestPlayers.get(0);
            if (MiniGameManager.isInMinigame(once)) {
                if (!livingTarget.getType().equals(EntityType.PLAYER)) {
                    MiniGameManager.onMobKill(once, livingTarget);
                }
            }
            once.sendMessage("Killed mobLevel: " + mobLevel);

            //run for each player
            for (Player player : bestPlayers) {
                UUID uuid = player.getUniqueId();
                if (GuardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                        //exp
                        int expToGive = getExperience(mobLevel, player.getLevel(), bestPlayers.size());
                        if (expToGive > 0) {
                            rpgCharacterStats.giveExp(expToGive);
                            PetExperienceManager.giveExperienceToActivePet(player, expToGive);
                        }

                        //quest, progress kill tasks
                        List<Quest> questList = activeCharacter.getQuestList();
                        String internalName = mythicEvent.getMobType().getInternalName();
                        for (Quest quest : questList) {
                            quest.progressKillTasks(player, internalName);
                            quest.triggerQuestItemDrop(internalName, livingTarget.getLocation());
                        }

                        //hunting
                        if (livingTarget.getType().equals(EntityType.COW) || livingTarget.getType().equals(EntityType.SHEEP)) {
                            ItemStack itemStack = GatheringManager.finishGathering(player, null, GatheringType.HUNTING);
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

    private static int getExperience(int mobLevel, int playerLevel, int shareCount) {
        int exp = (int) (2 + Math.round(10 * Math.pow(mobLevel, 2) / 16) + 0.5);

        if (playerLevel > mobLevel) {
            exp = (int) (exp * (Math.pow(mobLevel, 3) / Math.pow(playerLevel, 3)) + 0.5);
        } else {
            exp = (int) (exp * (Math.pow(playerLevel, 3) / Math.pow(mobLevel, 3)) + 0.5);
        }

        //Share
        if (shareCount > 1) {
            double expMultiplier = 1 - (0.1 * shareCount);

            exp = (int) (exp * expMultiplier + 0.5);
        }

        return BoostPremiumManager.isBoostActive(BoostPremium.EXPERIENCE) ? exp * 2 : exp;
    }
}
