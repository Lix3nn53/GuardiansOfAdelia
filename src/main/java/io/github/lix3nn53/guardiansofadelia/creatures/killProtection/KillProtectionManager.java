package io.github.lix3nn53.guardiansofadelia.creatures.killProtection;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropProtectionManager;
import io.github.lix3nn53.guardiansofadelia.creatures.drops.MobDropGenerator;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

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

            String internalName = mythicEvent.getMobType().getInternalName();

            //run only once
            Player once = bestPlayers.get(0);
            if (MiniGameManager.isInMinigame(once)) {
                if (!livingTarget.getType().equals(EntityType.PLAYER)) {
                    MiniGameManager.onMobKill(once, internalName);
                }
            }
            if (GatheringManager.dropsIngredient(internalName)) {
                ItemStack ingredient = GatheringManager.triggerIngredientDrop(internalName);
                if (ingredient != null) {
                    mythicEvent.getDrops().add(ingredient);
                }
            }
            once.sendMessage("Killed mobLevel: " + mobLevel);

            //run for each player
            for (Player player : bestPlayers) {
                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                        //exp
                        int expToGive = getExperience(mobLevel, player.getLevel(), bestPlayers.size());
                        once.sendMessage("expToGive: " + expToGive);
                        if (expToGive > 0) {
                            rpgCharacterStats.giveExp(expToGive);
                        }

                        //quest, progress kill tasks
                        List<Quest> questList = activeCharacter.getQuestList();
                        for (Quest quest : questList) {
                            quest.progressKillTasks(player, internalName);
                            ItemStack questItemDrop = quest.triggerQuestItemDrop(internalName);
                            if (questItemDrop != null) {
                                mythicEvent.getDrops().add(questItemDrop);
                            }
                        }
                    }
                }
            }
        }
    }

    private static int getExperience(int mobLevel, int playerLevel, int shareCount) {
        if (mobLevel == 0) mobLevel = 1;

        int exp = (int) (2 + Math.round(10 * Math.pow(mobLevel, 2) / 16) + 0.5);

        if (playerLevel > 9) { //do not reduce exp according to player level before level 10
            if (playerLevel > mobLevel) {
                exp = (int) (exp * (Math.pow(mobLevel, 1.2) / Math.pow(playerLevel, 1.2)) + 0.5);
            } else {
                exp = (int) (exp * (Math.pow(playerLevel, 1.2) / Math.pow(mobLevel, 1.2)) + 0.5);
            }
        }

        //Share
        if (shareCount > 1) {
            double expMultiplier = 1 - (0.1 * shareCount);

            exp = (int) (exp * expMultiplier + 0.5);
        }

        if (exp == 0) exp = 1;
        return BoostPremiumManager.isBoostActive(BoostPremium.EXPERIENCE) ? exp * 2 : exp;
    }
}
