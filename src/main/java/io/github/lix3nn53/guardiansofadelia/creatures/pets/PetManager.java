package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import com.comphenix.protocol.utility.MinecraftReflection;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PetManager {

    private static HashMap<LivingEntity, Player> petToPlayer = new HashMap<>();
    private static HashMap<Player, LivingEntity> playerToPet = new HashMap<>();
    private static double PET_MOVEMENT_SPEED = 0.75D;

    private final static List<Player> deathPetPlayerList = new ArrayList<>();
    private static long respawnDelay = 20 * 300L;

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
            int damage = getCompanionDamage(petLevel);
            int maxHP = getCompanionHealth(petLevel);

            if (currentHP <= 0) {
                currentHP = (int) ((maxHP * 0.4) + 0.5);
            }

            Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);
            petName += " " + ChatColor.WHITE + "<" + owner.getName() + ">" + ChatColor.GOLD + "LvL-" + petLevel + ChatColor.GREEN + " " + currentHP + "/" + maxHP + "❤";
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

            double movementSpeed = getMountSpeed(petLevel);
            double jumpStrength = getMountJump(petLevel);
            int maxHP = getMountHealth(petLevel);

            if (currentHP <= 0) {
                currentHP = (int) ((maxHP * 0.4) + 0.5);
            }

            Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);
            petName += " " + ChatColor.WHITE + "<" + owner.getName() + ">" + ChatColor.GOLD + "LvL-" + petLevel + ChatColor.GREEN + " " + currentHP + "/" + maxHP + "❤";
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

    public static void onPetDeath(LivingEntity livingEntity) {
        if (isPet(livingEntity)) {
            Player owner = getOwner(livingEntity);
            deathPetPlayerList.add(owner);
            new BukkitRunnable() {
                @Override
                public void run() {
                    deathPetPlayerList.remove(owner);
                    if (owner.isOnline()) {
                        updateCurrentHealthSavedInEgg(livingEntity, (int) (livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2));
                        repsawnPet(owner);
                    }
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 60 * 5L);
        }
    }

    public static boolean isPetDead(Player player) {
        return deathPetPlayerList.contains(player);
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

    public static void repsawnPet(Player player) {
        if (hasActivePet(player)) {
            removePet(player);
            onEggEquipEvent(player);
        }
    }

    private static Method craftEntity_getHandle, navigationAbstract_a, entityInsentient_getNavigation;
    private static Class<?> entityInsentientClass = MinecraftReflection.getMinecraftClass("EntityInsentient");

    static {
        try {
            craftEntity_getHandle = MinecraftReflection.getCraftEntityClass().getDeclaredMethod("getHandle");
            entityInsentient_getNavigation = entityInsentientClass.getDeclaredMethod("getNavigation");
            navigationAbstract_a = MinecraftReflection.getMinecraftClass("NavigationAbstract")
                    .getDeclaredMethod("a", double.class, double.class, double.class, double.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void onPlayerMove(Player player) {
        if (hasActivePet(player)) {
            LivingEntity activePet = getActivePet(player);
            if (!player.isOnline() || activePet.isDead()) {
                return;
            }

            Location target = player.getLocation();

            if (!target.getWorld().getName().equals(activePet.getLocation().getWorld().getName())) {
                PetManager.teleportPet(player, activePet, null);
                if (activePet.getType().equals(EntityType.WOLF)){ //clear wolf target
                    Wolf wolf = (Wolf) activePet;
                    wolf.setTarget(null);
                }
                return;
            }

            final double distance = target.distance(activePet.getLocation());
            if (distance > 20D) {
                PetManager.teleportPet(player, activePet, null);
                if (activePet.getType().equals(EntityType.WOLF)){ //clear wolf target
                    Wolf wolf = (Wolf) activePet;
                    wolf.setTarget(null);
                }
            } else if (distance < 6D) {
                return;
            } else if (activePet.getType().equals(EntityType.WOLF)){ //if distance is between and pet is wolf which has a target, return
                Wolf wolf = (Wolf) activePet;
                LivingEntity wolfTarget = wolf.getTarget();
                if (wolfTarget != null) {
                    return;
                }
            }

            double speedModifier = PET_MOVEMENT_SPEED;

            try {
                Object insentient = entityInsentientClass.cast(craftEntity_getHandle.invoke(activePet));
                Object navigation = entityInsentient_getNavigation.invoke(insentient);
                navigationAbstract_a.invoke(navigation, target.getX(), target.getY(), target.getZ(), speedModifier);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static void teleportPet(final Player player, final LivingEntity pet, final Location to) {
        Location baseLocation = to != null ? to : player.getLocation();
        Location newPetLoc = LocationUtils.getRandomSafeLocationNearPoint(baseLocation, 4);
        pet.teleport(newPetLoc);
    }

    public static int getCompanionHealth(int petLevel) {
        switch (petLevel) {
            case 1:
                return 201;
            case 2:
                return 201;
            case 3:
                return 200;
            case 4:
                return 200;
            case 5:
                return 200;
            case 6:
                return 200;
            case 7:
                return 200;
            case 8:
                return 200;
            case 9:
                return 200;
            case 10:
                return 200;
            case 11:
                return 200;
            case 12:
                return 200;
        }
        return 200;
    }

    public static int getCompanionDamage(int petLevel) {
        switch (petLevel) {
            case 1:
                return 201;
            case 2:
                return 201;
            case 3:
                return 200;
            case 4:
                return 200;
            case 5:
                return 200;
            case 6:
                return 200;
            case 7:
                return 200;
            case 8:
                return 200;
            case 9:
                return 200;
            case 10:
                return 200;
            case 11:
                return 200;
            case 12:
                return 200;
        }
        return 200;
    }

    public static int getMountHealth(int petLevel) {
        switch (petLevel) {
            case 1:
                return 201;
            case 2:
                return 201;
            case 3:
                return 200;
            case 4:
                return 200;
            case 5:
                return 200;
            case 6:
                return 200;
            case 7:
                return 200;
            case 8:
                return 200;
            case 9:
                return 200;
            case 10:
                return 200;
            case 11:
                return 200;
            case 12:
                return 200;
        }
        return 200;
    }

    public static double getMountSpeed(int petLevel) {
        switch (petLevel) {
            case 1:
                return 201;
            case 2:
                return 201;
            case 3:
                return 200;
            case 4:
                return 200;
            case 5:
                return 200;
            case 6:
                return 200;
            case 7:
                return 200;
            case 8:
                return 200;
            case 9:
                return 200;
            case 10:
                return 200;
            case 11:
                return 200;
            case 12:
                return 200;
        }
        return 200;
    }

    public static double getMountJump(int petLevel) {
        switch (petLevel) {
            case 1:
                return 201;
            case 2:
                return 201;
            case 3:
                return 200;
            case 4:
                return 200;
            case 5:
                return 200;
            case 6:
                return 200;
            case 7:
                return 200;
            case 8:
                return 200;
            case 9:
                return 200;
            case 10:
                return 200;
            case 11:
                return 200;
            case 12:
                return 200;
        }
        return 200;
    }
}
