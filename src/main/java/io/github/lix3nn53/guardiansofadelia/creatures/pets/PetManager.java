package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import com.comphenix.protocol.utility.MinecraftReflection;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.EggSlot;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
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
    private static final HashMap<LivingEntity, Player> petToPlayer = new HashMap<>();
    private static final HashMap<Player, LivingEntity> playerToPet = new HashMap<>();
    private static final double PET_FOLLOW_MOVEMENT_SPEED = 0.7D;
    private static final long respawnDelay = 20 * 300L;
    private static Method craftEntity_getHandle, navigationAbstract_a, entityInsentient_getNavigation;
    private static final Class<?> entityInsentientClass = MinecraftReflection.getMinecraftClass("EntityInsentient");

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

    private static LivingEntity getPet(Player owner, String petCode, int petLevel, int currentHP) {
        Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);

        MobManager mobManager = MythicMobs.inst().getMobManager();
        ActiveMob activeMob = mobManager.spawnMob(petCode, spawnLoc, petLevel);
        AbstractEntity entity = activeMob.getEntity();
        Tameable pet = (Tameable) entity.getBukkitEntity();
        pet.setSilent(true);
        pet.setTamed(true);
        pet.setOwner(owner);

        // health
        int maxHP = (int) (pet.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 0.5);
        if (currentHP <= 0) {
            currentHP = (int) ((maxHP * 0.4) + 0.5);
        } else if (currentHP > maxHP) {
            currentHP = maxHP;
        }
        pet.setHealth(currentHP);

        // name
        String petName = pet.getCustomName();
        petName += " " + ChatColor.GOLD + petLevel + ChatColor.WHITE + " <" + owner.getName().substring(0, 3) + ">" + ChatColor.GREEN + " " + currentHP + "/" + maxHP + "❤";
        pet.setCustomName(petName);

        // save current movement speed and set follow speed
        AttributeInstance movementSpeed = pet.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        PersistentDataContainerUtil.putDouble("mountSpeed", movementSpeed.getBaseValue(), pet);
        movementSpeed.setBaseValue(PET_FOLLOW_MOVEMENT_SPEED);

        return pet;
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

    public static void onPetSetHealth(LivingEntity livingEntity, double currentHealth, int setHealth) {
        if (!livingEntity.isDead()) {
            if (PetManager.isPet(livingEntity)) {
                int currentHealthInteger = (int) (currentHealth + 0.5);
                String customName = livingEntity.getCustomName();
                String replace = customName.replace(currentHealthInteger + "/", setHealth + "/");
                livingEntity.setCustomName(replace);

                updateCurrentHealthSavedInEgg(livingEntity, setHealth);
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

    private static void spawnPet(Player player, String petCode, int petCurrentHealth, int petLevel) {
        LivingEntity pet = getPet(player, petCode, petLevel, petCurrentHealth);
        petToPlayer.put(pet, player);
        playerToPet.put(player, pet);
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

                            spawnPet(player, petCode, levelFromExp, petCurrentHealth);
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

    public static int getDamage(String key, int petLevel) {
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(key);

        double base = mythicMob.getDamage().get();
        double perLevel = mythicMob.getPerLevelDamage();

        return (int) (base + (perLevel * petLevel) + 0.5);
    }

    public static int getHealth(String key, int petLevel) {
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(key);

        double base = mythicMob.getHealth().get();
        double perLevel = mythicMob.getPerLevelHealth();

        return (int) (base + (perLevel * petLevel) + 0.5);
    }

    public static String getName(String key) {
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(key);

        return mythicMob.getDisplayName().get();
    }
}
