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
import java.util.UUID;

public class PetExperienceManager {

    public static void giveExperienceToActivePet(Player owner, int expToGive) {
        if (PetManager.hasActivePet(owner)) {
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
        if (eggExp >= ) {
            return 12;
        } else if (eggExp >= 800000) {
            return 11;
        } else if (eggExp >= 550000) {
            return 10;
        } else if (eggExp >= 350000) {
            return 9;
        } else if (eggExp >= 200000) {
            return 8;
        } else if (eggExp >= 120000) {
            return 7;
        } else if (eggExp >= 50000) {
            return 6;
        } else if (eggExp >= 20000) {
            return 5;
        } else if (eggExp >= 5000) {
            return 4;
        } else if (eggExp >= 2000) {
            return 3;
        } else if (eggExp >= 500) {
            return 2;
        }
        return 1;
    }

    public static String getNextExperienceTarget(int eggLevel) {
        if (eggLevel == 2) {
            return "2000";
        } else if (eggLevel == 3) {
            return "5000";
        } else if (eggLevel == 4) {
            return "20000";
        } else if (eggLevel == 5) {
            return "50000";
        } else if (eggLevel == 6) {
            return "120000";
        } else if (eggLevel == 7) {
            return "200000";
        } else if (eggLevel == 8) {
            return "350000";
        } else if (eggLevel == 9) {
            return "550000";
        } else if (eggLevel == 10) {
            return "800000";
        } else if (eggLevel == 11) {
            return "";
        } else if (eggLevel == 12) {
            return "∞";
        }
        return "500";
    }

    private static void updateEggExp(Player player, int nextExperience, int currentLevel) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                EggSlot eggSlot = activeCharacter.getRpgInventory().getEggSlot();
                if (!eggSlot.isEmpty()) {
                    ItemStack egg = eggSlot.getItemOnSlot();
                    PersistentDataContainerUtil.putInteger("petExp", nextExperience, eggSlot.getItemOnSlot());

                    ItemMeta itemMeta = egg.getItemMeta();
                    List<String> lore = itemMeta.getLore();
                    lore.set(5, ChatColor.LIGHT_PURPLE + "Experience: " + ChatColor.GRAY + nextExperience + " / " + getNextExperienceTarget(currentLevel));
                    itemMeta.setLore(lore);
                    egg.setItemMeta(itemMeta);

                    eggSlot.setItemOnSlot(egg);
                }
            }
        }
    }

    private static void levelUp(Player player, int nextLevel) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                EggSlot eggSlot = activeCharacter.getRpgInventory().getEggSlot();
                if (!eggSlot.isEmpty()) {
                    ItemStack egg = eggSlot.getItemOnSlot();

                    ItemMeta itemMeta = egg.getItemMeta();
                    List<String> lore = itemMeta.getLore();
                    lore.set(4, ChatColor.GOLD + "Level: " + ChatColor.GRAY + nextLevel);

                    if (lore.get(1).contains("Companion")) {
                        double petBaseDamage = PersistentDataContainerUtil.getInteger(egg, "petBaseDamage");
                        double petBaseHealth = PersistentDataContainerUtil.getInteger(egg, "petBaseHealth");
                        int damage = PetManager.getCompanionDamage(nextLevel, petBaseDamage);
                        int maxHP = PetManager.getCompanionHealth(nextLevel, petBaseHealth);
                        lore.set(7, ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + maxHP);
                        lore.set(8, ChatColor.RED + "⸸ Damage: " + ChatColor.GRAY + damage);
                        player.sendMessage(ChatColor.GOLD + "DEBUG pet COMPANION level up");
                    } else {
                        double petBaseHealth = PersistentDataContainerUtil.getInteger(egg, "petBaseHealth");
                        int maxHP = PetManager.getMountHealth(nextLevel, petBaseHealth);
                        lore.set(7, ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + maxHP);
                        player.sendMessage(ChatColor.GOLD + "DEBUG pet MOUNT level up");
                    }

                    itemMeta.setLore(lore);

                    egg.setItemMeta(itemMeta);
                    eggSlot.setItemOnSlot(egg);

                    PetManager.respawnPet(player);
                    MessageUtils.sendCenteredMessage(player, ChatColor.GOLD + "Your pet has leveled up!");
                }
            }
        }
    }

    private static int getEggExperience(Player player) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
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
