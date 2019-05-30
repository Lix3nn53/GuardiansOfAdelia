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
import io.github.lix3nn53.guardiansofadelia.utilities.persistentDataContainerUtil;
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

    private final static List<Player> deathPetPlayerList = new ArrayList<>();
    private static HashMap<LivingEntity, Player> petToPlayer = new HashMap<>();
    private static HashMap<Player, LivingEntity> playerToPet = new HashMap<>();
    private static double PET_MOVEMENT_SPEED = 0.75D;
    private static long respawnDelay = 20 * 300L;
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

    public static void onPetHeal(LivingEntity livingEntity, double currentHealth, double healAmount) {
        if (!livingEntity.isDead()) {
            if (PetManager.isPet(livingEntity)) {
                int currentHealthInteger = (int) (currentHealth + 0.5);
                int nextHealth = (int) ((currentHealth + healAmount) + 0.5);
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
                    if (persistentDataContainerUtil.hasInteger(itemOnSlot, "petCurrentHealth")) {
                        itemOnSlot = persistentDataContainerUtil.putInteger("petCurrentHealth", nextHealth, itemOnSlot);
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
                        if (persistentDataContainerUtil.hasString(egg, "petCode")) {
                            if (persistentDataContainerUtil.hasInteger(egg, "petExp")) {
                                String petCode = persistentDataContainerUtil.getString(egg, "petCode");
                                int petCurrentHealth = persistentDataContainerUtil.getInteger(egg, "petCurrentHealth");
                                int petExp = persistentDataContainerUtil.getInteger(egg, "petExp");
                                int levelFromExp = PetExperienceManager.getLevelFromExp(petExp);

                                spawnPet(player, petCode, petCurrentHealth, levelFromExp);
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

    public static void onPlayerMove(Player player) {
        if (hasActivePet(player)) {
            LivingEntity activePet = getActivePet(player);
            if (!player.isOnline() || activePet.isDead()) {
                return;
            }

            Location target = player.getLocation();

            if (!target.getWorld().getName().equals(activePet.getLocation().getWorld().getName())) {
                PetManager.teleportPet(player, activePet, null);
                if (activePet.getType().equals(EntityType.WOLF)) { //clear wolf target
                    Wolf wolf = (Wolf) activePet;
                    wolf.setTarget(null);
                }
                return;
            }

            final double distance = target.distance(activePet.getLocation());
            if (distance > 20D) {
                PetManager.teleportPet(player, activePet, null);
                if (activePet.getType().equals(EntityType.WOLF)) { //clear wolf target
                    Wolf wolf = (Wolf) activePet;
                    wolf.setTarget(null);
                }
            } else if (distance < 6D) {
                return;
            } else if (activePet.getType().equals(EntityType.WOLF)) { //if distance is between and pet is wolf which has a target, return
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
            case 2:
                return 250;
            case 3:
                return 600;
            case 4:
                return 1000;
            case 5:
                return 1600;
            case 6:
                return 2400;
            case 7:
                return 3200;
            case 8:
                return 4000;
            case 9:
                return 5000;
            case 10:
                return 6000;
            case 11:
                return 7500;
            case 12:
                return 9000;
        }
        return 120;
    }

    public static int getCompanionDamage(int petLevel) {
        switch (petLevel) {
            case 2:
                return 24;
            case 3:
                return 60;
            case 4:
                return 120;
            case 5:
                return 200;
            case 6:
                return 300;
            case 7:
                return 400;
            case 8:
                return 500;
            case 9:
                return 700;
            case 10:
                return 900;
            case 11:
                return 1200;
            case 12:
                return 1500;
        }
        return 12;
    }

    public static int getMountHealth(int petLevel) {
        switch (petLevel) {
            case 2:
                return 400;
            case 3:
                return 900;
            case 4:
                return 1500;
            case 5:
                return 2400;
            case 6:
                return 3600;
            case 7:
                return 4800;
            case 8:
                return 6000;
            case 9:
                return 7400;
            case 10:
                return 9000;
            case 11:
                return 12000;
            case 12:
                return 15000;
        }
        return 200;
    }

    public static double getMountSpeed(int petLevel) {
        switch (petLevel) {
            case 2:
                return 0.4;
            case 3:
                return 0.45;
            case 4:
                return 0.5;
            case 5:
                return 0.55;
            case 6:
                return 0.6;
            case 7:
                return 0.65;
            case 8:
                return 0.7;
            case 9:
                return 0.75;
            case 10:
                return 0.8;
            case 11:
                return 0.85;
            case 12:
                return 0.9;
        }
        return 0.35;
    }

    public static double getMountJump(int petLevel) {
        switch (petLevel) {
            case 2:
                return 0.75;
            case 3:
                return 0.8;
            case 4:
                return 0.85;
            case 5:
                return 0.9;
            case 6:
                return 0.95;
            case 7:
                return 1;
            case 8:
                return 1.05;
            case 9:
                return 1.1;
            case 10:
                return 1.15;
            case 11:
                return 1.2;
            case 12:
                return 1.25;
        }
        return 0.7;
    }
}
