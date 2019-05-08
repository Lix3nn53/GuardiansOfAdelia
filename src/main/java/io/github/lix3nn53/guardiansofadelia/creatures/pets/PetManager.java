package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.PetSlot;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.NBTTagUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class PetManager {

    private static HashMap<LivingEntity, Player> petToPlayer = new HashMap<>();
    private static HashMap<Player, LivingEntity> playerToPet = new HashMap<>();
    private static long respawnDelay = 20 * 60L;

    public static void setPet(Player owner, LivingEntity pet) {
        petToPlayer.put(pet, owner);
    }

    public static Player getOwner(LivingEntity entity) {
        return petToPlayer.get(entity);
    }

    public static LivingEntity getActivePet(Player owner) {
        return playerToPet.get(owner);
    }

    public static boolean hasActivePet(Player player) {
        return playerToPet.containsKey(player);
    }

    public static boolean isPet(LivingEntity entity) {
        return petToPlayer.containsKey(entity);
    }

    public static long getRespawnDelay() {
        return respawnDelay;
    }

    private static LivingEntity getPet(Player owner, String petCode, int currentHP, int petLevel) {
        if (isCompanionCode(petCode)) {
            Companion companion = Companion.valueOf(petCode);
            String petName = companion.getName();
            int damage = 1;
            int maxHP = 100;
            switch (petLevel) {
                case 2:
                    damage = 3;
                    maxHP = 201;
                    break;
                case 3:
                    damage = 2;
                    maxHP = 200;
                    break;
                case 4:
                    damage = 2;
                    maxHP = 200;
                    break;
                case 5:
                    damage = 2;
                    maxHP = 200;
                    break;
                case 6:
                    damage = 2;
                    maxHP = 200;
                    break;
                case 7:
                    damage = 2;
                    maxHP = 200;
                    break;
                case 8:
                    damage = 2;
                    maxHP = 200;
                    break;
                case 9:
                    damage = 2;
                    maxHP = 200;
                    break;
                case 10:
                    damage = 2;
                    maxHP = 200;
                    break;
                case 11:
                    damage = 2;
                    maxHP = 200;
                    break;
                case 12:
                    damage = 2;
                    maxHP = 200;
                    break;
            }

            if (currentHP <= 0) {
                currentHP = (int) ((maxHP * 0.4) + 0.5);
            }

            Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);
            petName += " " + ChatColor.WHITE + "<" + owner.getName() + ">" + ChatColor.GREEN + " " + currentHP + "/" + maxHP + "❤";
            Wolf wolf = (Wolf) EntityUtils.create(spawnLoc, petName, maxHP, EntityType.WOLF);
            wolf.setAdult();
            wolf.setTamed(true);
            wolf.setOwner(owner);
            wolf.setHealth(currentHP);
            wolf.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.75D);
            wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
            companion.disguise(wolf);
            return wolf;
        } else if (isMountCode(petCode)) {
            Mount mount = Mount.valueOf(petCode);
            String petName = mount.getName();
            Horse.Color color = mount.getColor();
            ItemStack armor = new ItemStack(Material.AIR);

            double movementSpeed = 1;
            double jumpStrength = 1;
            int maxHP = 100;
            switch (petLevel) {
                case 2:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 201;
                    break;
                case 3:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 200;
                    break;
                case 4:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 200;
                    break;
                case 5:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 200;
                    break;
                case 6:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 200;
                    break;
                case 7:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 200;
                    break;
                case 8:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 200;
                    break;
                case 9:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 200;
                    break;
                case 10:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 200;
                    break;
                case 11:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 200;
                    break;
                case 12:
                    movementSpeed = 3;
                    jumpStrength = 3;
                    maxHP = 200;
                    break;
            }

            if (currentHP <= 0) {
                currentHP = (int) ((maxHP * 0.4) + 0.5);
            }

            Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);
            petName += " " + ChatColor.WHITE + "<" + owner.getName() + ">" + ChatColor.GREEN + " " + currentHP + "/" + maxHP + "❤";
            Horse horse = (Horse) EntityUtils.create(spawnLoc, petName, maxHP, EntityType.HORSE);
            horse.setTamed(true);
            horse.setAdult();
            horse.setStyle(Horse.Style.NONE);
            horse.getInventory().setSaddle(OtherItems.getSaddle());
            horse.setColor(color);
            horse.setOwner(owner);
            horse.setHealth(currentHP);
            horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(movementSpeed);
            horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(jumpStrength);
            if (!armor.getType().equals(Material.AIR)) {
                horse.getInventory().setArmor(armor);
            }
            return horse;
        }
        return null;
    }


    private static boolean isCompanionCode(String id) {
        try {
            Companion result = Companion.valueOf(id);
            return true;
        } catch (IllegalArgumentException exception) {

        }
        return false;
    }

    private static boolean isMountCode(String id) {
        try {
            Mount result = Mount.valueOf(id);
            return true;
        } catch (IllegalArgumentException exception) {

        }
        return false;
    }

    public static void onPetTakeDamage(LivingEntity livingEntity, double currentHealth, double finalDamage) {
        if (!livingEntity.isDead()) {
            if (PetManager.isPet(livingEntity)) {
                int currentHealthInteger = (int) (currentHealth + 0.5);
                int nextHealth = (int) ((currentHealth - finalDamage) + 0.5);
                String customName = livingEntity.getCustomName();
                String replace = customName.replace(currentHealthInteger + "/", nextHealth + "/");
                livingEntity.setCustomName(replace);

                updateCurrentHealthSavedInEgg(livingEntity, nextHealth);
            }
        }
    }

    private static void updateCurrentHealthSavedInEgg(LivingEntity livingEntity, int nextHealth) {
        UUID uuid = PetManager.getOwner(livingEntity).getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                PetSlot petSlot = activeCharacter.getRpgInventory().getPetSlot();
                if (!petSlot.isEmpty()) {
                    ItemStack itemOnSlot = petSlot.getItemOnSlot();
                    if (NBTTagUtils.hasTag(itemOnSlot, "petCurrentHealth")) {
                        itemOnSlot = NBTTagUtils.putInteger("petCurrentHealth", nextHealth, itemOnSlot);
                        petSlot.setItemOnSlot(itemOnSlot);
                    }
                }
            }
        }
    }

    private static void removePet(Player player) {
        if (hasActivePet(player)) {
            LivingEntity activePet = getActivePet(player);
            petToPlayer.remove(activePet);
            playerToPet.remove(player);
            activePet.remove();
        }
    }

    private static void spawnPet(Player player, String petCode, int petCurrentHealth, int petLevel) {
        LivingEntity pet = getPet(player, petCode, petCurrentHealth, petLevel);
        if (pet != null) {
            petToPlayer.put(pet, player);
            playerToPet.put(player, pet);
        }
    }

    public static void onEggEquipEvent(Player player) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                PetSlot petSlot = activeCharacter.getRpgInventory().getPetSlot();
                if (!petSlot.isEmpty()) {
                    ItemStack egg = petSlot.getItemOnSlot();
                    if (!egg.getType().equals(Material.AIR)) {
                        if (NBTTagUtils.hasTag(egg, "petCode")) {
                            if (NBTTagUtils.hasTag(egg, "petLevel")) {
                                String petCode = NBTTagUtils.getString(egg, "petCode");
                                int petLevel = NBTTagUtils.getInteger(egg, "petLevel");
                                int petCurrentHealth = NBTTagUtils.getInteger(egg, "petCurrentHealth");

                                spawnPet(player, petCode, petCurrentHealth, petLevel);
                            }
                        }
                    }
                } else {
                    removePet(player);
                }
            }
        }
    }
}
