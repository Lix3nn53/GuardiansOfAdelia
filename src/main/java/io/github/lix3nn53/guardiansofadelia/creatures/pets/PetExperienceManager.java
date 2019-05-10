package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.PetSlot;
import io.github.lix3nn53.guardiansofadelia.utilities.persistentDataContainerUtil;
import net.md_5.bungee.api.ChatColor;
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
                int eggLevel = getEggLevel(owner);
                if (eggLevel > 0) {
                    if (eggLevel < 12) {
                        int nextExperience = eggExperience + expToGive;
                        int expReq = getExpReq(eggLevel);

                        if (nextExperience > expReq) {
                            int nextLevel = eggLevel + 1;
                            levelUp(owner, nextLevel, getExpReq(nextLevel));
                        } else {
                            updateEggExp(owner, nextExperience, expReq);
                        }
                    }
                }
            }
        }
    }

    public static int getExpReq(int eggLevel) {
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
        }
        return 500; //level 1
    }

    private static void updateEggExp(Player player, int nextExperience, int expReq) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                PetSlot petSlot = activeCharacter.getRpgInventory().getPetSlot();
                if (!petSlot.isEmpty()) {
                    ItemStack egg = persistentDataContainerUtil.putInteger("petExp", nextExperience, petSlot.getItemOnSlot());

                    ItemMeta itemMeta = egg.getItemMeta();
                    List<String> lore = itemMeta.getLore();
                    lore.set(6, ChatColor.LIGHT_PURPLE + "Experience: " + ChatColor.GRAY + nextExperience + " / " + expReq);
                    itemMeta.setLore(lore);
                    egg.setItemMeta(itemMeta);

                    petSlot.setItemOnSlot(egg);
                }
            }
        }
    }

    private static void levelUp(Player player, int nextLevel, int nextExpReq) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                PetSlot petSlot = activeCharacter.getRpgInventory().getPetSlot();
                if (!petSlot.isEmpty()) {
                    ItemStack egg = persistentDataContainerUtil.putInteger("petLevel", nextLevel, petSlot.getItemOnSlot());
                    egg = persistentDataContainerUtil.putInteger("petExp", 1, egg);

                    ItemMeta itemMeta = egg.getItemMeta();
                    List<String> lore = itemMeta.getLore();
                    lore.set(5, ChatColor.GOLD + "Level: " + ChatColor.GRAY + nextLevel);
                    lore.set(6, ChatColor.LIGHT_PURPLE + "Experience: " + ChatColor.GRAY + "1 / " + nextExpReq);

                    if (lore.get(1).contains("Companion")) {
                        int damage = PetManager.getCompanionDamage(nextLevel);
                        int maxHP = PetManager.getCompanionHealth(nextLevel);
                        lore.set(7, ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + maxHP);
                        lore.set(8, ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + damage);
                        player.sendMessage(ChatColor.GOLD + "DEBUG pet companion");
                    } else {
                        double movementSpeed = PetManager.getMountSpeed(nextLevel);
                        double jumpStrength = PetManager.getMountJump(nextLevel);
                        int maxHP = PetManager.getMountHealth(nextLevel);
                        lore.set(7, ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + maxHP);
                        lore.set(8, ChatColor.AQUA + "⇨ Speed: " + ChatColor.GRAY + movementSpeed);
                        lore.set(9, ChatColor.YELLOW + "⇪ Jump: " + ChatColor.GRAY + jumpStrength);
                        player.sendMessage(ChatColor.GOLD + "DEBUG pet mount");
                    }

                    itemMeta.setLore(lore);

                    egg.setItemMeta(itemMeta);
                    petSlot.setItemOnSlot(egg);

                    PetManager.repsawnPet(player);
                    player.sendMessage(ChatColor.GOLD + "Your pet has leveled up!");
                }
            }
        }
    }

    private static int getEggLevel(Player player) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                PetSlot petSlot = activeCharacter.getRpgInventory().getPetSlot();
                if (!petSlot.isEmpty()) {
                    ItemStack itemOnSlot = petSlot.getItemOnSlot();
                    if (persistentDataContainerUtil.hasInteger(itemOnSlot, "petLevel")) {
                        return persistentDataContainerUtil.getInteger(itemOnSlot, "petLevel");
                    }
                }
            }
        }
        return -1;
    }

    private static int getEggExperience(Player player) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                PetSlot petSlot = activeCharacter.getRpgInventory().getPetSlot();
                if (!petSlot.isEmpty()) {
                    ItemStack itemOnSlot = petSlot.getItemOnSlot();
                    if (persistentDataContainerUtil.hasInteger(itemOnSlot, "petExp")) {
                        return persistentDataContainerUtil.getInteger(itemOnSlot, "petExp");
                    }
                }
            }
        }
        return -1;
    }
}
