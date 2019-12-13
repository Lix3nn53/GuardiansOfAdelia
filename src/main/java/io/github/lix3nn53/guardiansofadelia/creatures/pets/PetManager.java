package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import com.comphenix.protocol.utility.MinecraftReflection;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.EggSlot;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Bukkit;
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
    private static double PET_FOLLOW_MOVEMENT_SPEED = 0.75D;
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

    private static LivingEntity getCompanionPet(Player owner, String petCode, int currentHP, int petLevel, double baseDamage, double baseHealth) {
        Companion companion = Companion.valueOf(petCode);
        String petName = companion.getName();
        int damage = getCompanionDamage(petLevel, baseDamage);
        int maxHP = getCompanionHealth(petLevel, baseHealth);

        if (currentHP <= 0) {
            currentHP = (int) ((maxHP * 0.4) + 0.5);
        } else if (currentHP > maxHP) {
            currentHP = maxHP;
        }

        Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);
        petName += " " + ChatColor.GOLD + petLevel + ChatColor.WHITE + " <" + owner.getName().substring(0, 3) + ">" + ChatColor.GREEN + " " + currentHP + "/" + maxHP + "❤";
        Wolf wolf = (Wolf) EntityUtils.create(spawnLoc, petName, maxHP, EntityType.WOLF);
        wolf.setAdult();
        wolf.setTamed(true);
        wolf.setOwner(owner);
        wolf.setSilent(true);
        wolf.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(PET_FOLLOW_MOVEMENT_SPEED);
        wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHP);
        wolf.setHealth(currentHP);
        companion.disguise(wolf);
        return wolf;
    }

    private static LivingEntity getMountPet(Player owner, String petCode, int currentHP, int petLevel, double baseHealth, double baseSpeed, double baseJump) {
        Mount mount = Mount.valueOf(petCode);
        String petName = mount.getName();
        Horse.Color color = mount.getColor();
        ItemStack armor = new ItemStack(Material.AIR);

        int maxHP = getMountHealth(petLevel, baseHealth);
        double mountSpeed = getMountSpeed(petLevel, baseSpeed);
        double jumpStrength = getMountJump(petLevel, baseJump);

        if (currentHP <= 0) {
            currentHP = (int) ((maxHP * 0.4) + 0.5);
        } else if (currentHP > maxHP) {
            currentHP = maxHP;
        }

        Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);
        petName += " " + ChatColor.GOLD + petLevel + ChatColor.WHITE + " <" + owner.getName().substring(0, 3) + ">" + ChatColor.GREEN + " " + currentHP + "/" + maxHP + "❤";
        Horse horse = (Horse) EntityUtils.create(spawnLoc, petName, maxHP, EntityType.HORSE);
        horse.setTamed(true);
        horse.setAdult();
        horse.setStyle(Horse.Style.NONE);
        horse.getInventory().setSaddle(OtherItems.getSaddle());
        horse.setColor(color);
        horse.setOwner(owner);
        horse.setHealth(currentHP);
        horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(PET_FOLLOW_MOVEMENT_SPEED);
        horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(jumpStrength);
        if (!armor.getType().equals(Material.AIR)) {
            horse.getInventory().setArmor(armor);
        }
        PersistentDataContainerUtil.putDouble("mountSpeed", mountSpeed, horse);
        return horse;
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

    public static void onMount(LivingEntity mount) {
        if (PersistentDataContainerUtil.hasDouble(mount, "mountSpeed")) {
            double mountSpeed = PersistentDataContainerUtil.getDouble(mount, "mountSpeed");
            mount.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(mountSpeed);
        }
    }

    public static void onDismount(LivingEntity mount) {
        mount.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(PET_FOLLOW_MOVEMENT_SPEED);
    }

    public static void onPetDeath(LivingEntity livingEntity) {
        if (isPet(livingEntity)) {
            Player owner = getOwner(livingEntity);
            owner.sendMessage(ChatColor.RED + "Your pet is dead. Respawning in 2 minutes");
            deathPetPlayerList.add(owner);
            new BukkitRunnable() {
                @Override
                public void run() {
                    deathPetPlayerList.remove(owner);
                    if (owner.isOnline()) {
                        updateCurrentHealthSavedInEgg(livingEntity, (int) (livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2));
                        respawnPet(owner);
                        owner.sendMessage(ChatColor.GREEN + "Your pet is respawned");
                    }
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 60 * 2L);
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
        if (isPet(livingEntity)) {
            UUID uuid = PetManager.getOwner(livingEntity).getUniqueId();
            if (GuardianDataManager.hasGuardianData(uuid)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                    EggSlot eggSlot = activeCharacter.getRpgInventory().getEggSlot();
                    if (!eggSlot.isEmpty()) {
                        ItemStack itemOnSlot = eggSlot.getItemOnSlot();
                        if (PersistentDataContainerUtil.hasInteger(itemOnSlot, "petCurrentHealth")) {
                            PersistentDataContainerUtil.putInteger("petCurrentHealth", nextHealth, itemOnSlot);
                            eggSlot.setItemOnSlot(itemOnSlot);
                        }
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

    private static void spawnCompanionPet(Player player, String petCode, int petCurrentHealth, int petLevel, double baseDamage, double baseHealth) {
        LivingEntity pet = getCompanionPet(player, petCode, petCurrentHealth, petLevel, baseDamage, baseHealth);
        if (pet != null) {
            petToPlayer.put(pet, player);
            playerToPet.put(player, pet);
        }
    }

    private static void spawnMountPet(Player player, String petCode, int petCurrentHealth, int petLevel, double baseHealth, double baseSpeed, double baseJump) {
        LivingEntity pet = getMountPet(player, petCode, petCurrentHealth, petLevel, baseHealth, baseSpeed, baseJump);
        if (pet != null) {
            petToPlayer.put(pet, player);
            playerToPet.put(player, pet);
        }
    }

    public static void onEggEquip(Player player) {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                EggSlot eggSlot = activeCharacter.getRpgInventory().getEggSlot();
                if (!eggSlot.isEmpty()) {
                    ItemStack egg = eggSlot.getItemOnSlot();
                    if (!egg.getType().equals(Material.AIR)) {
                        if (PersistentDataContainerUtil.hasString(egg, "petCode")) {
                            String petCode = PersistentDataContainerUtil.getString(egg, "petCode");
                            int petCurrentHealth = PersistentDataContainerUtil.getInteger(egg, "petCurrentHealth");
                            int petExp = PersistentDataContainerUtil.getInteger(egg, "petExp");
                            int levelFromExp = PetExperienceManager.getLevelFromExp(petExp);

                            //new values
                            double petBaseHealth = PersistentDataContainerUtil.getInteger(egg, "petBaseHealth");
                            if (isCompanionCode(petCode)) {
                                double petBaseDamage = PersistentDataContainerUtil.getInteger(egg, "petBaseDamage");
                                spawnCompanionPet(player, petCode, petCurrentHealth, levelFromExp, petBaseDamage, petBaseHealth);
                            } else if (isMountCode(petCode)) {
                                double petBaseSpeed = PersistentDataContainerUtil.getDouble(egg, "petBaseSpeed");
                                double petBaseJump = PersistentDataContainerUtil.getDouble(egg, "petBaseJump");
                                spawnMountPet(player, petCode, petCurrentHealth, levelFromExp, petBaseHealth, petBaseSpeed, petBaseJump);
                            }
                        }
                    }
                } else {
                    removePet(player);
                }
            }
        }
    }

    public static void onEggUnequip(Player player) {
        removePet(player);
    }

    public static void onPlayerQuit(Player player) {
        removePet(player);
    }

    public static void respawnPet(Player player) {
        if (hasActivePet(player)) {
            removePet(player);
            Bukkit.getScheduler().runTaskLater(GuardiansOfAdelia.getInstance(), () -> onEggEquip(player), 20L);
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

            double speedModifier = PET_FOLLOW_MOVEMENT_SPEED;

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

    public static int getCompanionHealth(int petLevel, double baseHealth) {
        double multiplier = 1;

        switch (petLevel) {
            case 2:
                multiplier = 1.1;
                break;
            case 3:
                multiplier = 1.2;
                break;
            case 4:
                multiplier = 1.3;
                break;
            case 5:
                multiplier = 1.4;
                break;
            case 6:
                multiplier = 1.5;
                break;
            case 7:
                multiplier = 1.6;
                break;
            case 8:
                multiplier = 1.7;
                break;
            case 9:
                multiplier = 1.8;
                break;
            case 10:
                multiplier = 1.9;
                break;
            case 11:
                multiplier = 2.0;
                break;
            case 12:
                multiplier = 2.1;
                break;
        }

        return (int) (baseHealth * multiplier + 0.5);
    }

    public static int getCompanionDamage(int petLevel, double baseDamage) {
        double multiplier = 1;

        switch (petLevel) {
            case 2:
                multiplier = 1.1;
                break;
            case 3:
                multiplier = 1.2;
                break;
            case 4:
                multiplier = 1.3;
                break;
            case 5:
                multiplier = 1.4;
                break;
            case 6:
                multiplier = 1.5;
                break;
            case 7:
                multiplier = 1.6;
                break;
            case 8:
                multiplier = 1.7;
                break;
            case 9:
                multiplier = 1.8;
                break;
            case 10:
                multiplier = 1.9;
                break;
            case 11:
                multiplier = 2.0;
                break;
            case 12:
                multiplier = 2.1;
                break;
        }

        return (int) (baseDamage * multiplier + 0.5);
    }

    public static int getMountHealth(int petLevel, double baseHealth) {
        double multiplier = 1;

        switch (petLevel) {
            case 2:
                multiplier = 1.1;
                break;
            case 3:
                multiplier = 1.2;
                break;
            case 4:
                multiplier = 1.3;
                break;
            case 5:
                multiplier = 1.4;
                break;
            case 6:
                multiplier = 1.5;
                break;
            case 7:
                multiplier = 1.6;
                break;
            case 8:
                multiplier = 1.7;
                break;
            case 9:
                multiplier = 1.8;
                break;
            case 10:
                multiplier = 1.9;
                break;
            case 11:
                multiplier = 2.0;
                break;
            case 12:
                multiplier = 2.1;
                break;
        }

        return (int) (baseHealth * multiplier + 0.5);
    }

    public static double getMountSpeed(int petLevel, double baseSpeed) {
        double multiplier = 1;

        switch (petLevel) {
            case 2:
                multiplier = 1.05;
                break;
            case 3:
                multiplier = 1.1;
                break;
            case 4:
                multiplier = 1.15;
                break;
            case 5:
                multiplier = 1.2;
                break;
            case 6:
                multiplier = 1.25;
                break;
            case 7:
                multiplier = 1.3;
                break;
            case 8:
                multiplier = 1.35;
                break;
            case 9:
                multiplier = 1.4;
                break;
            case 10:
                multiplier = 1.45;
                break;
            case 11:
                multiplier = 1.5;
                break;
            case 12:
                multiplier = 1.55;
                break;
        }

        return (int) (baseSpeed * multiplier + 0.5);
    }

    public static double getMountJump(int petLevel, double baseJump) {
        double multiplier = 1;

        switch (petLevel) {
            case 2:
                multiplier = 1.05;
                break;
            case 3:
                multiplier = 1.1;
                break;
            case 4:
                multiplier = 1.15;
                break;
            case 5:
                multiplier = 1.2;
                break;
            case 6:
                multiplier = 1.25;
                break;
            case 7:
                multiplier = 1.3;
                break;
            case 8:
                multiplier = 1.35;
                break;
            case 9:
                multiplier = 1.4;
                break;
            case 10:
                multiplier = 1.45;
                break;
            case 11:
                multiplier = 1.5;
                break;
            case 12:
                multiplier = 1.55;
                break;
        }

        return (int) (baseJump * multiplier + 0.5);
    }
}
