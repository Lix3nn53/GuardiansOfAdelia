package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.TargetHelper;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.EggSlot;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import io.lumine.xikage.mythicmobs.io.MythicConfig;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PetManager {

    public static final long RESPAWN_DELAY = 20 * 300L;
    // Pets Only
    private final static List<Player> deathPetPlayerList = new ArrayList<>();
    private static final HashMap<Player, LivingEntity> ownerToPet = new HashMap<>();
    protected final static List<Player> petSkillOnCooldown = new ArrayList<>();
    // Pets and Companions
    private static final HashMap<LivingEntity, Player> companionToOwner = new HashMap<>();
    // Companions Only
    private static final HashMap<Player, List<LivingEntity>> ownerToCompanions = new HashMap<>();
    protected final static List<Player> companionLogicRunnerActive = new ArrayList<>();
    private static final double COMPANION_ATTACK_RADIUS = 16;

    public static Player getOwner(LivingEntity entity) {
        return companionToOwner.get(entity);
    }

    public static boolean hasPet(Player owner) {
        return ownerToPet.containsKey(owner);
    }

    public static LivingEntity getPet(Player owner) {
        return ownerToPet.get(owner);
    }

    public static boolean hasCompanion(Player owner) {
        return ownerToCompanions.containsKey(owner);
    }

    public static boolean isCompanion(LivingEntity entity) {
        return companionToOwner.containsKey(entity);
    }

    public static boolean isCompanionAlsoPet(LivingEntity entity) {
        if (companionToOwner.containsKey(entity)) {
            Player player = companionToOwner.get(entity);

            if (ownerToPet.containsKey(player)) {
                LivingEntity pet = ownerToPet.get(player);

                return entity.equals(pet);
            }
        }

        return false;
    }

    public static List<LivingEntity> getCompanions(Player owner) {
        return ownerToCompanions.get(owner);
    }

    public static List<LivingEntity> getCompanions(Player owner, String mobCode) {
        List<LivingEntity> companions = new ArrayList<>();

        if (!ownerToCompanions.containsKey(owner)) return companions;

        List<LivingEntity> livingEntities = ownerToCompanions.get(owner);

        if (livingEntities.isEmpty()) return companions;

        BukkitAPIHelper apiHelper = MythicMobs.inst().getAPIHelper();
        for (LivingEntity entity : livingEntities) {
            boolean mythicMob = apiHelper.isMythicMob(entity);
            if (mythicMob) {
                ActiveMob mythicMobInstance = apiHelper.getMythicMobInstance(entity);
                String internalName = mythicMobInstance.getType().getInternalName();
                if (mobCode.equals(internalName)) {
                    companions.add(entity);
                }
            }
        }

        return companions;
    }

    private static LivingEntity spawnMythicMobTameable(Player owner, String petCode, int petLevel) {
        Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);

        GuardiansOfAdelia.getInstance().getLogger().info("petLevel: " + petLevel);

        BukkitAPIHelper apiHelper = MythicMobs.inst().getAPIHelper();
        Entity entity = null;
        try {
            entity = apiHelper.spawnMythicMob(petCode, spawnLoc, petLevel);
        } catch (InvalidMobTypeException e) {
            GuardiansOfAdelia.getInstance().getLogger().info("getPet mythicmob code error: " + petCode);
            e.printStackTrace();
        }
        if (entity == null) return null;
        if (!(entity instanceof LivingEntity)) {
            GuardiansOfAdelia.getInstance().getLogger().info("Pet is not LivingEntity, petCode: " + petCode);
            return null;
        }

        LivingEntity pet = (LivingEntity) entity;

        // health
        int maxHP = getHealth(petCode, petLevel);
        double attackDamage = getDamage(petCode, petLevel);
        double movementSpeed = pet.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();

        if (pet instanceof Tameable) {
            Tameable tameable = (Tameable) pet;
            tameable.setOwner(owner);

            // Taming the entity resets its values so set them again
            pet.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHP);
            pet.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(movementSpeed);

            if (pet.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null) {
                pet.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamage);
            }
        }

        pet.setHealth(maxHP);

        // name
        String petName = pet.getCustomName();
        petName += " " + ChatColor.GOLD + petLevel + ChatColor.WHITE + " " + owner.getName().substring(0, 3) + ChatColor.GREEN + " " + maxHP + "❤";
        pet.setCustomName(petName);

        // name if disguised
        boolean disguised = DisguiseAPI.isDisguised(pet);
        if (disguised) {
            Disguise disguise = DisguiseAPI.getDisguise(pet);
            FlagWatcher watcher = disguise.getWatcher();
            watcher.setCustomName(petName);
        }

        return pet;
    }

    private static LivingEntity spawnMythicMobArmorStand(Player owner, String petCode, int petLevel) {
        Location spawnLoc = LocationUtils.getRandomSafeLocationNearPoint(owner.getLocation(), 4);

        GuardiansOfAdelia.getInstance().getLogger().info("petLevel: " + petLevel);

        BukkitAPIHelper apiHelper = MythicMobs.inst().getAPIHelper();
        Entity entity = null;
        try {
            entity = apiHelper.spawnMythicMob(petCode, spawnLoc, petLevel);
        } catch (InvalidMobTypeException e) {
            GuardiansOfAdelia.getInstance().getLogger().info("getPet mythicmob code error: " + petCode);
            e.printStackTrace();
        }
        if (entity == null) return null;
        if (!(entity instanceof LivingEntity)) {
            GuardiansOfAdelia.getInstance().getLogger().info("Pet is not LivingEntity, petCode: " + petCode);
            return null;
        }

        LivingEntity pet = (LivingEntity) entity;

        // name
        String petName = pet.getCustomName();
        petName += " " + ChatColor.GOLD + petLevel + ChatColor.WHITE + " " + owner.getName().substring(0, 3);
        pet.setCustomName(petName);

        // name if disguised
        boolean disguised = DisguiseAPI.isDisguised(pet);
        if (disguised) {
            Disguise disguise = DisguiseAPI.getDisguise(pet);
            FlagWatcher watcher = disguise.getWatcher();
            watcher.setCustomName(petName);
        }

        return pet;
    }

    public static void onEntityDeath(LivingEntity livingEntity) {
        if (isCompanion(livingEntity)) {
            Player owner = getOwner(livingEntity);
            companionToOwner.remove(livingEntity);
            // On pet death
            if (isCompanionAlsoPet(livingEntity)) {
                owner.sendMessage(ChatPalette.RED + "Your pet is dead. Respawning in 2 minutes");

                ownerToPet.remove(owner);
                deathPetPlayerList.add(owner);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        deathPetPlayerList.remove(owner);

                        if (owner.isOnline()) {
                            // updateCurrentHealthSavedInEgg(livingEntity, (int) (livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2));

                            respawnPet(owner);
                            owner.sendMessage(ChatPalette.GREEN_DARK + "Your pet is respawned");
                        }
                    }
                }.runTaskLater(GuardiansOfAdelia.getInstance(), RESPAWN_DELAY);
            } else {
                // On companion death
                //TriggerListener.onPlayerCompanionDeath(owner, livingEntity); // REMOVED CUZ DEATH EVENT NOT RELIABLE
                if (ownerToCompanions.containsKey(owner)) {
                    List<LivingEntity> livingEntities = ownerToCompanions.get(owner);
                    boolean remove = livingEntities.remove(livingEntity);
                    if (remove && livingEntities.isEmpty()) {
                        ownerToCompanions.remove(owner);
                    }
                }
            }
        }
    }

    public static boolean isPetDead(Player player) {
        return deathPetPlayerList.contains(player);
    }

    public static void onTakeDamage(LivingEntity livingEntity, double currentHealth, double finalDamage) {
        if (!livingEntity.isDead()) {
            if (isCompanion(livingEntity)) {
                int currentHealthInteger = (int) (currentHealth + 0.5);
                int nextHealth = (int) ((currentHealth - finalDamage) + 0.5);

                updateName(currentHealthInteger, nextHealth, livingEntity);

                /*if (isCompanionAlsoPet(livingEntity)) {
                    updateCurrentHealthSavedInEgg(livingEntity, nextHealth);
                }*/
            }
        }
    }

    public static void onPetSetHealth(LivingEntity livingEntity, double currentHealth, int setHealth) {
        if (!livingEntity.isDead()) {
            if (isCompanion(livingEntity)) {
                int currentHealthInteger = (int) (currentHealth + 0.5);

                updateName(currentHealthInteger, setHealth, livingEntity);

                /*if (isCompanionAlsoPet(livingEntity)) {
                    updateCurrentHealthSavedInEgg(livingEntity, setHealth);
                }*/
            }
        }
    }

    public static void onHeal(LivingEntity livingEntity, double currentHealth, double healAmount) {
        if (!livingEntity.isDead()) {
            if (isCompanion(livingEntity)) {
                int currentHealthInteger = (int) (currentHealth + 0.5);
                int nextHealth = (int) ((currentHealth + healAmount) + 0.5);

                updateName(currentHealthInteger, nextHealth, livingEntity);

                /*if (isCompanionAlsoPet(livingEntity)) {
                    updateCurrentHealthSavedInEgg(livingEntity, nextHealth);
                }*/
            }
        }
    }

    private static void updateName(int currentHealth, int nextHealth, LivingEntity companion) {
        String customName = companion.getCustomName();
        String replace = customName.replace(currentHealth + "❤", nextHealth + "❤");

        companion.setCustomName(replace);

        boolean disguised = DisguiseAPI.isDisguised(companion);
        if (disguised) {
            Disguise disguise = DisguiseAPI.getDisguise(companion);
            FlagWatcher watcher = disguise.getWatcher();
            watcher.setCustomName(replace);
        }
    }

    /*private static void updateCurrentHealthSavedInEgg(LivingEntity livingEntity, int nextHealth) {
        if (isCompanionAlsoPet(livingEntity)) {
            Player owner = getOwner(livingEntity);
            if (GuardianDataManager.hasGuardianData(owner)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(owner);
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
    }*/

    private static void removePet(Player player) {
        if (hasPet(player)) {
            LivingEntity activePet = getPet(player);
            companionToOwner.remove(activePet);
            ownerToPet.remove(player);
            activePet.remove();
        }
    }

    public static void removeCompanions(Player player) {
        if (ownerToCompanions.containsKey(player)) {
            List<LivingEntity> companions = ownerToCompanions.get(player);

            for (LivingEntity companion : companions) {
                companionToOwner.remove(companion);
                companion.remove();
            }
        }

        ownerToCompanions.remove(player);
    }

    public static void removeCompanion(Player player, LivingEntity livingEntity) {
        if (ownerToCompanions.containsKey(player)) {
            List<LivingEntity> companions = ownerToCompanions.get(player);

            boolean remove = companions.remove(livingEntity);
            if (remove && companions.isEmpty()) {
                ownerToCompanions.remove(player);
            }

            livingEntity.remove();
        }
    }

    private static LivingEntity spawnPet(Player player, String petCode, int petLevel) {
        LivingEntity pet = spawnMythicMobArmorStand(player, petCode, petLevel);
        if (pet == null) return null;
        companionToOwner.put(pet, player);
        ownerToPet.put(player, pet);

        return pet;
    }

    public static LivingEntity spawnCompanion(Player player, String petCode, int petLevel) {
        LivingEntity pet = spawnMythicMobTameable(player, petCode, petLevel);
        if (pet == null) return null;
        companionToOwner.put(pet, player);

        List<LivingEntity> companions;
        if (ownerToCompanions.containsKey(player)) {
            companions = ownerToCompanions.get(player);
        } else {
            companions = new ArrayList<>();

        }
        companions.add(pet);
        ownerToCompanions.put(player, companions);

        TriggerListener.onPlayerCompanionSpawn(player, pet);

        if (!companionLogicRunnerActive.contains(player)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!ownerToCompanions.containsKey(player)) {
                        cancel();
                        companionLogicRunnerActive.remove(player);
                        return;
                    }

                    List<LivingEntity> livingEntities = ownerToCompanions.get(player);
                    if (livingEntities.isEmpty()) {
                        cancel();
                        ownerToCompanions.remove(player);
                        companionLogicRunnerActive.remove(player);
                        return;
                    }

                    ArrayList<Mob> mobsWithoutTarget = new ArrayList<>();
                    for (LivingEntity livingEntity : livingEntities) {
                        if (!(livingEntity instanceof Mob)) continue;
                        Mob mob = (Mob) livingEntity;
                        LivingEntity target = mob.getTarget();
                        if (target == null) {
                            mobsWithoutTarget.add(mob);
                        }
                    }

                    if (mobsWithoutTarget.isEmpty()) return;

                    List<LivingEntity> nearbySphere = TargetHelper.getNearbySphere(player.getLocation(), COMPANION_ATTACK_RADIUS);

                    for (LivingEntity nearby : nearbySphere) {
                        if (nearby.getType().equals(EntityType.ARMOR_STAND)) continue;
                        boolean isEnemy = EntityUtils.canAttack(player, nearby);
                        if (isEnemy) {
                            for (Mob mob : mobsWithoutTarget) {
                                mob.setTarget(nearby);
                            }
                            break;
                        }
                    }
                }
            }.runTaskTimer(GuardiansOfAdelia.getInstance(), 40L, 40L);

            companionLogicRunnerActive.add(player);
        }

        return pet;
    }

    public static void onEggEquip(Player player) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                EggSlot eggSlot = activeCharacter.getRpgInventory().getEggSlot();
                if (!eggSlot.isEmpty()) {
                    ItemStack egg = eggSlot.getItemOnSlot();
                    if (!egg.getType().equals(Material.AIR)) {
                        if (PersistentDataContainerUtil.hasString(egg, "petCode")) {
                            String petCode = PersistentDataContainerUtil.getString(egg, "petCode");
                            int petExp = PersistentDataContainerUtil.getInteger(egg, "petExp");
                            GuardiansOfAdelia.getInstance().getLogger().info("petExp: " + petExp);
                            int levelFromExp = PetExperienceManager.getLevelFromExp(petExp);

                            LivingEntity pet = spawnPet(player, petCode, levelFromExp);

                            if (pet == null) return;

                            PetMovement.onPetSpawn(player, petCode, pet, levelFromExp);
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

        player.removePotionEffect(PotionEffectType.SPEED);

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                rpgCharacterStats.reapplyGearSetEffects();
            }
        }
    }

    public static void onPlayerQuit(Player player) {
        removePet(player);
    }

    public static void respawnPet(Player player) {
        if (hasPet(player)) {
            removePet(player);
            Bukkit.getScheduler().runTaskLater(GuardiansOfAdelia.getInstance(), () -> onEggEquip(player), 20L);
        }
    }

    public static void onPlayerMove(Player player) {
        List<LivingEntity> companions;

        if (hasCompanion(player)) {
            companions = getCompanions(player);
        } else {
            companions = new ArrayList<>();
        }

        /*if (hasPet(player)) {
            LivingEntity pet = getPet(player);
            companions.add(pet);
        }*/

        if (!companions.isEmpty()) {
            Location playerLocation = player.getLocation();

            String name = playerLocation.getWorld().getName();
            for (LivingEntity companion : companions) {
                String name1 = companion.getLocation().getWorld().getName();
                if (!name.equals(name1)) {
                    return;
                }

                final double distance = playerLocation.distanceSquared(companion.getLocation());
                if (distance > 400) {
                    PetManager.teleportPet(player, companion, playerLocation);

                    if (companion instanceof Mob) { //clear pet target
                        Mob mob = (Mob) companion;
                        mob.setTarget(null);
                    }
                }
            }
        }
    }

    public static void setPetAndCompanionsTarget(Player player, LivingEntity target) {
        List<LivingEntity> companions;

        if (hasCompanion(player)) {
            companions = getCompanions(player);
        } else {
            companions = new ArrayList<>();
        }

        if (hasPet(player)) {
            LivingEntity pet = getPet(player);
            companions.add(pet);
        }

        if (!companions.isEmpty()) {
            for (LivingEntity companion : companions) {
                if (companion instanceof Mob) {
                    Mob pet = (Mob) companion;
                    if (pet.getTarget() == null) {
                        pet.setTarget(target);
                    }
                }
            }
        }
    }

    private static void teleportPet(final Player player, final LivingEntity pet, final Location to) {
        Location newPetLoc = LocationUtils.getRandomSafeLocationNearPoint(to, 4);
        pet.teleport(newPetLoc);
    }

    public static int getDamage(String key, int petLevel) {
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(key);

        double base = mythicMob.getDamage().get();
        double perLevel = mythicMob.getPerLevelDamage();

        //GuardiansOfAdelia.getInstance().getLogger().info("base damage: " + base);
        //GuardiansOfAdelia.getInstance().getLogger().info("perLevel damage: " + perLevel);

        return (int) (base + (perLevel * (petLevel - 1)) + 0.5);
    }

    public static int getHealth(String key, int petLevel) {
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(key);

        double base = mythicMob.getHealth().get();
        double perLevel = mythicMob.getPerLevelHealth();

        //GuardiansOfAdelia.getInstance().getLogger().info("base health: " + base);
        //GuardiansOfAdelia.getInstance().getLogger().info("perLevel health: " + perLevel);

        return (int) (base + (perLevel * (petLevel - 1)) + 0.5);
    }

    public static double getMovementSpeed(String key, int petLevel) {
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(key);

        MythicConfig config = mythicMob.getConfig();

        double base = config.getPlaceholderDouble("Options.MovementSpeed", "0").get();
        double perLevel = config.getDouble("LevelModifiers.MovementSpeed", -1.0D);

        GuardiansOfAdelia.getInstance().getLogger().info("base movement speed: " + base);
        GuardiansOfAdelia.getInstance().getLogger().info("perLevel movement speed: " + perLevel);

        return base + (perLevel * (petLevel - 1));
    }

    public static String getName(String key) {
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(key);

        return mythicMob.getDisplayName().get();
    }
}
