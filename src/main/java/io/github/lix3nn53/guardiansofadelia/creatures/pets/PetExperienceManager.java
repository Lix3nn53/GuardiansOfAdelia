package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.EggSlot;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PetExperienceManager {

    public static void giveExperienceToActivePet(Player owner, int expToGive) {
        if (PetManager.hasPet(owner)) {
            int eggExperience = getEggExperience(owner);
            if (eggExperience > -1) {
                int currentLevel = getLevelFromExp(eggExperience);
                int nextExperience = eggExperience + expToGive;
                int nextLevel = getLevelFromExp(nextExperience);

                updateEggExp(owner, nextExperience, nextLevel);

                if (currentLevel < nextLevel) {
                    levelUp(owner, nextLevel);
                }
            }
        }
    }

    public static int getLevelFromExp(int eggExp) {
        int totalExp = 0;
        for (int level = 1; level < 12; level++) {
            totalExp += getNextExperienceTarget(level);

            if (totalExp > eggExp) return level;
        }

        return 1;
    }

    public static int getNextExperienceTarget(int eggLevel) {
        if (eggLevel == 2) {
            return 2000;
        } else if (eggLevel == 3) {
            return 5000;
        } else if (eggLevel == 4) {
            return 20000;
        } else if (eggLevel == 5) {
            return 50000;
        } else if (eggLevel == 6) {
            return 120000;
        } else if (eggLevel == 7) {
            return 200000;
        } else if (eggLevel == 8) {
            return 350000;
        } else if (eggLevel == 9) {
            return 550000;
        } else if (eggLevel == 10) {
            return 800000;
        } else if (eggLevel == 11) {
            return 1000000;
        } else if (eggLevel == 12) {
            return 9000000;
        }
        return 500;
    }

    private static void updateEggExp(Player player, int nextExperience, int currentLevel) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                EggSlot eggSlot = activeCharacter.getRpgInventory().getEggSlot();
                if (!eggSlot.isEmpty()) {
                    ItemStack egg = eggSlot.getItemOnSlot();
                    PersistentDataContainerUtil.putInteger("petExp", nextExperience, eggSlot.getItemOnSlot());

                    ItemMeta itemMeta = egg.getItemMeta();
                    List<String> lore = itemMeta.getLore();
                    lore.set(6, ChatColor.LIGHT_PURPLE + "Experience: " + ChatColor.GRAY + nextExperience + " / " + getNextExperienceTarget(currentLevel));
                    itemMeta.setLore(lore);
                    egg.setItemMeta(itemMeta);

                    eggSlot.setItemOnSlot(egg);
                }
            }
        }
    }

    private static void levelUp(Player owner, int nextLevel) {
        if (GuardianDataManager.hasGuardianData(owner)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(owner);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                EggSlot eggSlot = activeCharacter.getRpgInventory().getEggSlot();
                if (!eggSlot.isEmpty()) {
                    ItemStack egg = eggSlot.getItemOnSlot();
                    String petCode = PersistentDataContainerUtil.getString(egg, "petCode");

                    ItemMeta itemMeta = egg.getItemMeta();
                    List<String> lore = itemMeta.getLore();
                    lore.set(5, ChatColor.GOLD + "Level: " + ChatColor.GRAY + nextLevel);

                    if (lore.get(2).contains("Companion")) {
                        int damage = PetManager.getDamage(petCode, nextLevel);
                        int maxHP = PetManager.getHealth(petCode, nextLevel);
                        lore.set(8, ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + maxHP);
                        lore.set(9, ChatColor.RED + "✦ Element Damage: " + ChatColor.GRAY + damage);
                        owner.sendMessage(ChatColor.GOLD + "DEBUG pet COMPANION level up");
                    } else {
                        int maxHP = PetManager.getHealth(petCode, nextLevel);
                        lore.set(8, ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + maxHP);
                        owner.sendMessage(ChatColor.GOLD + "DEBUG pet MOUNT level up");
                    }

                    itemMeta.setLore(lore);

                    egg.setItemMeta(itemMeta);
                    eggSlot.setItemOnSlot(egg);

                    PetManager.respawnPet(owner);
                    MessageUtils.sendCenteredMessage(owner, ChatColor.GOLD + "Your pet has leveled up!");
                }
            }
        }
    }

    private static int getEggExperience(Player player) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                EggSlot eggSlot = activeCharacter.getRpgInventory().getEggSlot();
                if (!eggSlot.isEmpty()) {
                    ItemStack itemOnSlot = eggSlot.getItemOnSlot();
                    if (PersistentDataContainerUtil.hasInteger(itemOnSlot, "petExp")) {
                        return PersistentDataContainerUtil.getInteger(itemOnSlot, "petExp");
                    }
                }
            }
        }
        return -1;
    }
}
