package io.github.lix3nn53.guardiansofadelia.creatures;

import io.github.lix3nn53.guardiansofadelia.Items.list.MonsterItem;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum AdeliaEntity {
    //VILLAGER
    VILLAGER_1,
    VILLAGER_2,
    VILLAGER_3,
    VILLAGER_4,
    VILLAGER_5,
    VILLAGER_HOBBIT,
    VILLAGER_SAILOR,
    VILLAGER_FARMER,
    //TUTORIAL
    TUTORIAL_1,
    TUTORIAL_2,
    TUTORIAL_BOSS,
    //HUNTING
    COW_BABY,
    SHEEP_BABY,
    COW,
    SHEEP,
    //AREA-1
    LIZARD,
    LIZARD_POISONOUS,
    SLIME,
    SLIME_STICKY,
    //AREA-2
    ZOMBIE,
    ZOMBIE_VILLAGER,
    ZOMBIE_SHAMAN,
    ZOMBIE_SPLITTER,
    ZOMBIE_TANK,
    //AREA-3
    SKELETON_ARCHER,
    SKELETON_FIGHTER,
    SKELETON_ROGUE,
    SKELETON_MONK,
    SKELETON_MAGE,
    //AREA-4
    CREEPER,
    VEX,
    SHULKER,
    JELLYBEAN,
    //AREA-5
    PIRATE_SHOOTER,
    PIRATE_FIGHTER,
    PIRATE_SHARPSHOOTER,
    PIRATE_DUEL_MASTER,
    PIRATE_DROWNED,
    //AREA-6
    FROZEN_ROGUE,
    FROZEN_ILLUSIONER,
    FROZEN_ARCHER,
    ZOMBIE_JOCKEY,
    TIMBERMAN,
    //AREA-7
    MUMMY,
    MUMMY_GHOST,
    DESERT_SKELETON_CAVALRY,
    DESERT_SKELETON_ARCHER,
    SPIDER,
    SPIDER_GHOST,
    //AREA-8
    WITCH,
    GOBLIN_ROGUE,
    GOBLIN_FIGHTER,
    GOBLIN_MAGE,
    GOBLIN_JOCKEY,
    GOBLIN_SHAMAN,
    //AREA-9
    ORC_FIGHTER,
    ORC_MAGE,
    ORC_JOCKEY,
    ORC_SHAMAN,
    ORC_GLADIATOR,
    MAGMA_CUBE,
    BLAZE,
    //AREA-10
    ENDERMAN,
    PHANTOM,
    PILLAGER_ROGUE,
    PILLAGER_MAGE,
    PILLAGER_RAVAGER,
    PILLAGER_SHAMAN,
    PILLAGER_FIGHTER,
    //DUNGEON-BOSSES
    BOSS_SLIME,
    BOSS_ZOMBIE,
    BOSS_SKELETON,
    BOSS_COOK,
    BOSS_PIRATE,
    BOSS_ICE,
    BOSS_DESERT,
    BOSS_SWAMP,
    BOSS_LAVA,
    BOSS_DARKNESS;

    /**
     * Use this is entity doesn't have the GENERIC_ATTACK_DAMAGE attribute but can attack
     * For example slimes and killer rabbits
     *
     * @param entity
     * @param customDamage
     */
    private static void setCustomDamage(Entity entity, int customDamage) {
        PersistentDataContainerUtil.putInteger("customDamage", customDamage, entity);
    }

    private static void setEntityExperience(Entity entity, int experience) {
        PersistentDataContainerUtil.putInteger("experience", experience, entity);
    }

    private static void setEntityDropTableNo(Entity entity, int dropTableNumber) {
        PersistentDataContainerUtil.putInteger("dropTableNumber", dropTableNumber, entity);
    }

    private void startSkillLoop(LivingEntity livingEntity) {
        List<EntitySkill> skills = new ArrayList<>();
        List<Integer> skillLevels = new ArrayList<>();
        long cooldown = 120;

        switch (this) {
            case LIZARD_POISONOUS:
                skills.add(EntitySkill.AOE_SMALL_AROUND);
                skillLevels.add(1);
                break;
            case SLIME_STICKY:
                skills.add(EntitySkill.AOE_SMALL_AROUND);
                skillLevels.add(1);
                break;
            case ZOMBIE_VILLAGER:
                skills.add(EntitySkill.AOE_SMALL_AROUND);
                skillLevels.add(1);
                break;
            case ZOMBIE_SPLITTER:
                skills.add(EntitySkill.AOE_SMALL_AROUND);
                skillLevels.add(1);
                break;
            case ZOMBIE_SHAMAN:
                skills.add(EntitySkill.AOE_SMALL_AROUND);
                skillLevels.add(1);
                break;
            case SKELETON_MAGE:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case CREEPER:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case JELLYBEAN:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case PIRATE_SHARPSHOOTER:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case PIRATE_DUEL_MASTER:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case FROZEN_ILLUSIONER:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case MUMMY_GHOST:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case WITCH:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case GOBLIN_MAGE:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case GOBLIN_SHAMAN:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case ORC_GLADIATOR:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case ORC_SHAMAN:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case ORC_MAGE:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case PILLAGER_MAGE:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case PILLAGER_SHAMAN:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case BOSS_SLIME:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case BOSS_ZOMBIE:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case BOSS_SKELETON:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case BOSS_COOK:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case BOSS_PIRATE:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case BOSS_ICE:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case BOSS_DESERT:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case BOSS_SWAMP:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case BOSS_LAVA:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
            case BOSS_DARKNESS:
                skills.add(EntitySkill.PROJECTILE_FIREBALL);
                skillLevels.add(1);
                cooldown = 80;
                break;
        }

        EntitySkillSet entitySkillSet = new EntitySkillSet(skills, skillLevels, cooldown);
        entitySkillSet.startSkillLoop(livingEntity);
    }

    public LivingEntity getMob(Location loc) {
        LivingEntity livingEntity = null;
        switch (this) {
            case VILLAGER_HOBBIT: {
                Villager villager = (Villager) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Hobbit", 20000D, EntityType.VILLAGER);
                villager.setBaby();
                villager.setAgeLock(true);

                livingEntity = villager;
                break;
            }
            case VILLAGER_1: {
                Villager villager = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Roumen Villager", 40000D, EntityType.VILLAGER);
                villager.setAdult();
                Villager.Profession[] values = Villager.Profession.values();
                int i = new Random().nextInt(values.length);
                Villager.Profession value = values[i];
                villager.setProfession(value);
                villager.setVillagerLevel(2);
                livingEntity = villager;
                break;
            }
            case VILLAGER_2: {
                Villager villager = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Veloa Villager", 60000D, EntityType.VILLAGER);
                villager.setAdult();
                Villager.Profession[] values = Villager.Profession.values();
                int i = new Random().nextInt(values.length);
                Villager.Profession value = values[i];
                villager.setProfession(value);
                villager.setVillagerLevel(2);
                livingEntity = villager;
                break;
            }
            case VILLAGER_3: {
                Villager villager = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Elderine Villager", 80000D, EntityType.VILLAGER);
                villager.setAdult();
                Villager.Profession[] values = Villager.Profession.values();
                int i = new Random().nextInt(values.length);
                Villager.Profession value = values[i];
                villager.setProfession(value);
                villager.setVillagerLevel(2);
                livingEntity = villager;
                break;
            }
            case VILLAGER_4: {
                Villager villager = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Uruga Villager", 120000D, EntityType.VILLAGER);
                villager.setAdult();
                Villager.Profession[] values = Villager.Profession.values();
                int i = new Random().nextInt(values.length);
                Villager.Profession value = values[i];
                villager.setProfession(value);
                villager.setVillagerLevel(2);
                livingEntity = villager;
                break;
            }
            case VILLAGER_5: {
                Villager villager = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Aberstol Villager", 200000D, EntityType.VILLAGER);
                villager.setAdult();
                Villager.Profession[] values = Villager.Profession.values();
                int i = new Random().nextInt(values.length);
                Villager.Profession value = values[i];
                villager.setProfession(value);
                villager.setVillagerLevel(2);
                livingEntity = villager;
                break;
            }
            case VILLAGER_SAILOR: {
                Villager villager = (Villager) EntityUtils.create(loc, ChatColor.AQUA + "Sailor", 100000D, EntityType.VILLAGER);
                villager.setAdult();
                villager.setProfession(Villager.Profession.WEAPONSMITH);
                villager.setVillagerLevel(2);
                livingEntity = villager;
                break;
            }
            case VILLAGER_FARMER: {
                Villager villager = (Villager) EntityUtils.create(loc, ChatColor.YELLOW + "Farmer", 80000D, EntityType.VILLAGER);
                villager.setAdult();
                villager.setProfession(Villager.Profession.FARMER);
                villager.setVillagerLevel(2);
                livingEntity = villager;
                break;
            }
            case TUTORIAL_1: {
                WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.RED + "Aleesia's Soldier", 1800D, EntityType.WITHER_SKELETON);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(120D);
                ItemStack sword = MonsterItem.SWORD_DARK.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);
                livingEntity = entity;
                break;
            }
            case TUTORIAL_2: {
                Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Aleesia's Ranger", 1200D, EntityType.STRAY);
                ItemStack bow = MonsterItem.BOW_DARK.getItem(500);
                entity.getEquipment().setItemInMainHand(bow);
                livingEntity = entity;
                break;
            }
            case TUTORIAL_BOSS:
                double maxHealth = 200000D;
                Wither wither = (Wither) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Aleesia", maxHealth, EntityType.WITHER);
                wither.setCustomNameVisible(true);

                MobDisguise disguise = new MobDisguise(DisguiseType.WITHER_SKELETON, false);

                LivingWatcher watcher = disguise.getWatcher();

                watcher.setCustomNameVisible(true);
                watcher.setCustomName(ChatColor.DARK_PURPLE + "Aleesia");
                watcher.setInvisible(true);
                watcher.setNoGravity(true);
                watcher.addPotionEffect(PotionEffectType.GLOWING);
                watcher.setMaxHealth(maxHealth);
                watcher.setHealth((float) maxHealth);

                EntityEquipment mobEquipment = watcher.getEquipment();

                ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(10000011);
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                mobEquipment.setHelmet(itemStack);

                DisguiseAPI.disguiseToAll(wither, disguise);

                livingEntity = wither;
                break;
            case COW: {
                Cow entity = (Cow) EntityUtils.create(loc, ChatColor.GREEN + "Cow", 1420D, EntityType.COW);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
                entity.setBreed(false);
                entity.setAdult();
                livingEntity = entity;
                break;
            }
            case COW_BABY: {
                Cow entity = (Cow) EntityUtils.create(loc, ChatColor.GREEN + "Baby Cow", 142D, EntityType.COW);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
                entity.setBreed(false);
                entity.setBaby();
                entity.setAgeLock(true);
                livingEntity = entity;
                break;
            }
            case SHEEP: {
                Sheep entity = (Sheep) EntityUtils.create(loc, ChatColor.GREEN + "Sheep", 1420D, EntityType.SHEEP);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
                entity.setBreed(false);
                entity.setAdult();
                livingEntity = entity;
                break;
            }
            case SHEEP_BABY: {
                Sheep entity = (Sheep) EntityUtils.create(loc, ChatColor.GREEN + "Baby Sheep", 142D, EntityType.SHEEP);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
                entity.setBreed(false);
                entity.setBaby();
                entity.setAgeLock(true);
                livingEntity = entity;
                break;
            }
            case LIZARD: {
                Silverfish entity = (Silverfish) EntityUtils.create(loc, ChatColor.GREEN + "Wild Lizard", 20D, EntityType.SILVERFISH);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6D);
                setEntityExperience(entity, 2);
                setEntityDropTableNo(entity, 0);
                livingEntity = entity;
                break;
            }
            case LIZARD_POISONOUS: {
                Silverfish entity = (Silverfish) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Poisonous Lizard", 20D, EntityType.SILVERFISH);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6D);
                setEntityExperience(entity, 2);
                setEntityDropTableNo(entity, 0);
                livingEntity = entity;
                break;
            }
            case SLIME: {
                Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GREEN + "Baby Slime", 30D, EntityType.SLIME);
                entity.setSize(2);
                setEntityExperience(entity, 5);
                setEntityDropTableNo(entity, 0);
                setCustomDamage(entity, 9);
                livingEntity = entity;
                break;
            }
            case SLIME_STICKY: {
                Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GREEN + "Sticky Slime", 40D, EntityType.SLIME);
                entity.setSize(3);
                setEntityExperience(entity, 7);
                setEntityDropTableNo(entity, 0);
                setCustomDamage(entity, 12);
                livingEntity = entity;
                break;
            }
            case ZOMBIE: {
                Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie", 50D, EntityType.ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20D);
                entity.setBaby(false);
                entity.getEquipment().clear();
                setEntityExperience(entity, 12);
                setEntityDropTableNo(entity, 1);
                livingEntity = entity;
                break;
            }
            case ZOMBIE_VILLAGER: {
                ZombieVillager entity = (ZombieVillager) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Villager", 60D, EntityType.ZOMBIE_VILLAGER);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(24D);
                entity.setBaby(false);
                entity.getEquipment().clear();
                ItemStack axe = MonsterItem.AXE_WOODEN.getItem(0);
                entity.getEquipment().setItemInMainHand(axe);
                setEntityExperience(entity, 14);
                setEntityDropTableNo(entity, 1);
                livingEntity = entity;
                break;
            }
            case ZOMBIE_SPLITTER: {
                Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Splitter Zombie", 50D, EntityType.ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10D);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2D);
                entity.setBaby(false);
                entity.getEquipment().clear();
                setEntityExperience(entity, 14);
                setEntityDropTableNo(entity, 1);
                livingEntity = entity;
                break;
            }
            case ZOMBIE_SHAMAN: {
                Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Shaman Zombie", 50D, EntityType.ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(15D);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.18D);
                entity.setBaby(false);
                entity.getEquipment().clear();
                ItemStack wand = MonsterItem.STAFF_LEAF.getItem(0);
                entity.getEquipment().setItemInMainHand(wand);
                ItemStack circlet = MonsterItem.HELMET_GOLDEN.getItem(0);
                entity.getEquipment().setHelmet(circlet);
                setEntityExperience(entity, 16);
                setEntityDropTableNo(entity, 1);
                livingEntity = entity;
                break;
            }
            case ZOMBIE_TANK: {
                Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Tank Zombie", 80D, EntityType.ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20D);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
                entity.setBaby(false);
                entity.getEquipment().clear();
                ItemStack shield = MonsterItem.SHIELD_WOODEN.getItem(0);
                entity.getEquipment().setItemInOffHand(shield);
                setEntityExperience(entity, 18);
                setEntityDropTableNo(entity, 1);
                livingEntity = entity;
                break;
            }
            case SKELETON_ARCHER: {
                Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Archer Skeleton", 120D, EntityType.SKELETON);
                entity.getEquipment().clear();
                ItemStack bow = MonsterItem.BOW_WOODEN.getItem(300);
                entity.getEquipment().setItemInMainHand(bow);
                setEntityExperience(entity, 53);
                setEntityDropTableNo(entity, 2);
                livingEntity = entity;
                break;
            }
            case SKELETON_FIGHTER: {
                Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Fighter Skeleton", 150D, EntityType.SKELETON);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(60D);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.SWORD_WOODEN.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);
                setEntityExperience(entity, 53);
                setEntityDropTableNo(entity, 2);
                livingEntity = entity;
                break;
            }
            case SKELETON_ROGUE: {
                Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Rogue Skeleton", 120D, EntityType.SKELETON);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(60D);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.DAGGER_WOOD.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);
                entity.getEquipment().setItemInOffHand(sword);
                setEntityExperience(entity, 53);
                setEntityDropTableNo(entity, 2);
                livingEntity = entity;
                break;
            }
            case SKELETON_MONK: {
                Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Monk Skeleton", 140D, EntityType.SKELETON);
                entity.getEquipment().clear();
                ItemStack spear = MonsterItem.SPEAR_STEEL.getItem(0);
                entity.getEquipment().setItemInMainHand(spear);
                setEntityExperience(entity, 60);
                setEntityDropTableNo(entity, 2);
                livingEntity = entity;
                break;
            }
            case SKELETON_MAGE: {
                Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Mage Skeleton", 120D, EntityType.SKELETON);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
                entity.getEquipment().clear();
                ItemStack wand = MonsterItem.STAFF_BONE.getItem(0);
                entity.getEquipment().setItemInMainHand(wand);
                ItemStack helmet = MonsterItem.HELMET_GOLDEN.getItem(0);
                entity.getEquipment().setHelmet(helmet);
                setEntityExperience(entity, 68);
                setEntityDropTableNo(entity, 2);
                livingEntity = entity;
                break;
            }
            case CREEPER: {
                Creeper entity = (Creeper) EntityUtils.create(loc, ChatColor.AQUA + "Popping Rainbow", 320D, EntityType.CREEPER);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
                setEntityExperience(entity, 153);
                setEntityDropTableNo(entity, 3);
                livingEntity = entity;
                break;
            }
            case VEX: {
                Vex entity = (Vex) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Taffy Spirit", 280D, EntityType.VEX);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4D);
                setEntityExperience(entity, 164);
                setEntityDropTableNo(entity, 3);
                livingEntity = entity;
                break;
            }
            case SHULKER: {
                Shulker entity = (Shulker) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Candy Box", 400D, EntityType.SHULKER);
                setEntityExperience(entity, 142);
                setEntityDropTableNo(entity, 3);
                livingEntity = entity;
                break;
            }
            case JELLYBEAN: {
                Endermite entity = (Endermite) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Jellybean", 360D, EntityType.ENDERMITE);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4D);
                setEntityExperience(entity, 164);
                setEntityDropTableNo(entity, 3);
                livingEntity = entity;
                break;
            }
            case PIRATE_SHOOTER: {
                Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Shooter Pirate", 640D, EntityType.SKELETON);
                entity.getEquipment().clear();
                ItemStack pistol = MonsterItem.PIRATE_PISTOL.getItem(400);
                entity.getEquipment().setItemInMainHand(pistol);
                ItemStack helmet = MonsterItem.PIRATE_HAT.getItem(0);
                entity.getEquipment().setHelmet(helmet);
                setEntityExperience(entity, 253);
                setEntityDropTableNo(entity, 4);
                livingEntity = entity;
                break;
            }
            case PIRATE_FIGHTER: {
                Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Fighter Pirate", 720D, EntityType.SKELETON);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(220D);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.SWORD_STEEL.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);
                ItemStack helmet = MonsterItem.PIRATE_HAT.getItem(0);
                entity.getEquipment().setHelmet(helmet);
                setEntityExperience(entity, 253);
                setEntityDropTableNo(entity, 4);
                livingEntity = entity;
                break;
            }
            case PIRATE_SHARPSHOOTER: {
                WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Sharpshooter Pirate", 700D, EntityType.WITHER_SKELETON);
                entity.getEquipment().clear();
                ItemStack pistol = MonsterItem.PIRATE_PISTOL.getItem(400);
                entity.getEquipment().setItemInMainHand(pistol);
                ItemStack helmet = MonsterItem.PIRATE_HAT.getItem(0);
                entity.getEquipment().setHelmet(helmet);
                setEntityExperience(entity, 282);
                setEntityDropTableNo(entity, 4);
                livingEntity = entity;
                break;
            }
            case PIRATE_DUEL_MASTER: {
                WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Duel Master Pirate", 780D, EntityType.WITHER_SKELETON);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(260D);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.KATANA.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);
                ItemStack helmet = MonsterItem.PIRATE_HAT.getItem(0);
                entity.getEquipment().setHelmet(helmet);
                setEntityExperience(entity, 282);
                setEntityDropTableNo(entity, 4);
                livingEntity = entity;
                break;
            }
            case PIRATE_DROWNED: {
                Drowned entity = (Drowned) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Drowned Pirate", 700D, EntityType.DROWNED);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(260D);
                entity.getEquipment().clear();
                entity.setBaby(false);
                ItemStack sword = MonsterItem.SPEAR_STEEL.getItem(400);
                entity.getEquipment().setItemInMainHand(sword);
                setEntityExperience(entity, 282);
                setEntityDropTableNo(entity, 4);
                livingEntity = entity;
                break;
            }
            case FROZEN_ROGUE: {
                Stray entity = (Stray) EntityUtils.create(loc, ChatColor.AQUA + "Frozen Rogue", 1400D, EntityType.STRAY);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(380D);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
                entity.getEquipment().clear();
                ItemStack dagger = MonsterItem.DAGGER_CRIMSON.getItem(0);
                entity.getEquipment().setItemInMainHand(dagger);
                entity.getEquipment().setItemInOffHand(dagger);
                setEntityExperience(entity, 420);
                setEntityDropTableNo(entity, 5);
                livingEntity = entity;
                break;
            }
            case FROZEN_ILLUSIONER: {
                Stray entity = (Stray) EntityUtils.create(loc, ChatColor.AQUA + "Frozen Illusioner", 1200D, EntityType.STRAY);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(380D);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
                entity.getEquipment().clear();
                ItemStack dagger = MonsterItem.STAFF_OCEAN.getItem(0);
                entity.getEquipment().setItemInMainHand(dagger);
                setEntityExperience(entity, 420);
                setEntityDropTableNo(entity, 5);
                livingEntity = entity;
                break;
            }
            case FROZEN_ARCHER: {
                Stray entity = (Stray) EntityUtils.create(loc, ChatColor.AQUA + "Frozen Ranger", 1200D, EntityType.STRAY);
                entity.getEquipment().clear();
                ItemStack bow = MonsterItem.BOW_DARK.getItem(350);
                entity.getEquipment().setItemInMainHand(bow);
                setEntityExperience(entity, 420);
                setEntityDropTableNo(entity, 5);
                livingEntity = entity;
                break;
            }
            case ZOMBIE_JOCKEY: {
                Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Jockey Zombie", 900D, EntityType.ZOMBIE);
                entity.getEquipment().clear();
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(320D);
                entity.setBaby(true);

                Chicken mount = (Chicken) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Chicken", 400D, EntityType.CHICKEN);
                mount.addPassenger(entity);

                setEntityExperience(mount, 253);
                setEntityExperience(entity, 400);
                setEntityDropTableNo(entity, 5);
                livingEntity = entity;
                break;
            }
            case TIMBERMAN: {
                Vindicator entity = (Vindicator) EntityUtils.create(loc, ChatColor.AQUA + "Frozen Timberman", 1500D, EntityType.VINDICATOR);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(420D);
                entity.getEquipment().clear();
                ItemStack axe = MonsterItem.AXE_WOODEN.getItem(0);
                entity.getEquipment().setItemInMainHand(axe);
                setEntityExperience(entity, 450);
                setEntityDropTableNo(entity, 5);
                livingEntity = entity;
                break;
            }
            case MUMMY: {
                Husk entity = (Husk) EntityUtils.create(loc, ChatColor.YELLOW + "Mummy", 1900D, EntityType.HUSK);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(520D);
                entity.getEquipment().clear();
                setEntityExperience(entity, 553);
                setEntityDropTableNo(entity, 6);
                livingEntity = entity;
                break;
            }
            case MUMMY_GHOST: {
                Husk entity = (Husk) EntityUtils.create(loc, ChatColor.YELLOW + "Ghost Mummy", 2100D, EntityType.HUSK);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(540D);
                entity.getEquipment().clear();
                PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
                entity.addPotionEffect(invis);
                PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1);
                entity.addPotionEffect(glow);
                setEntityExperience(entity, 584);
                setEntityDropTableNo(entity, 6);
                livingEntity = entity;
                break;
            }
            case DESERT_SKELETON_ARCHER: {
                Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.YELLOW + "Desert Archer Skeleton", 1700D, EntityType.SKELETON);
                entity.getEquipment().clear();
                ItemStack bow = MonsterItem.BOW_SATET.getItem(600);
                entity.getEquipment().setItemInMainHand(bow);

                Spider mount = (Spider) EntityUtils.create(loc, ChatColor.WHITE + "Desert Spider", 600D, EntityType.SPIDER);
                mount.addPassenger(entity);

                setEntityExperience(mount, 420);
                setEntityExperience(entity, 553);
                setEntityDropTableNo(entity, 6);
                livingEntity = entity;
                break;
            }
            case DESERT_SKELETON_CAVALRY: {
                Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.YELLOW + "Desert Cavalry Skeleton", 1900D, EntityType.SKELETON);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(540D);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.SPEAR_RED.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);

                Spider mount = (Spider) EntityUtils.create(loc, ChatColor.WHITE + "Desert Spider", 600D, EntityType.SPIDER);
                mount.addPassenger(entity);

                setEntityExperience(mount, 420);
                setEntityExperience(entity, 553);
                setEntityDropTableNo(entity, 6);
                livingEntity = entity;
                break;
            }
            case SPIDER: {
                Spider entity = (Spider) EntityUtils.create(loc, ChatColor.YELLOW + "Desert Spider", 1900D, EntityType.SPIDER);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(520D);
                setEntityExperience(entity, 584);
                setEntityDropTableNo(entity, 6);
                livingEntity = entity;
                break;
            }
            case SPIDER_GHOST: {
                CaveSpider entity = (CaveSpider) EntityUtils.create(loc, ChatColor.YELLOW + "Ghost Spider", 2100D, EntityType.CAVE_SPIDER);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(520D);
                PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
                entity.addPotionEffect(invis);
                PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1);
                entity.addPotionEffect(glow);
                setEntityExperience(entity, 584);
                setEntityDropTableNo(entity, 6);
                livingEntity = entity;
                break;
            }
            case WITCH: {
                Witch entity = (Witch) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Witch", 3400D, EntityType.WITCH);
                setEntityExperience(entity, 764);
                setEntityDropTableNo(entity, 7);
                livingEntity = entity;
                break;
            }
            case GOBLIN_JOCKEY: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Jockey Goblin", 2000D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(640D);
                entity.getEquipment().clear();
                entity.setBaby(true);
                ItemStack sword = MonsterItem.SWORD_WOODEN.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);

                Chicken mount = (Chicken) EntityUtils.create(loc, ChatColor.YELLOW + "Goblin Chicken", 900D, EntityType.CHICKEN);
                mount.addPassenger(entity);

                setEntityExperience(mount, 553);
                setEntityExperience(entity, 720);
                setEntityDropTableNo(entity, 7);
                livingEntity = entity;
                break;
            }
            case GOBLIN_FIGHTER: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Fighter Goblin", 3200D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(740D);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.SWORD_WOODEN.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);
                entity.setBaby(true);
                setEntityExperience(entity, 750);
                setEntityDropTableNo(entity, 7);
                livingEntity = entity;
                break;
            }
            case GOBLIN_ROGUE: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Rogue Goblin", 2800D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(740D);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.DAGGER_WOOD.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);
                entity.getEquipment().setItemInOffHand(sword);
                entity.setBaby(true);
                setEntityExperience(entity, 750);
                setEntityDropTableNo(entity, 7);
                livingEntity = entity;
                break;
            }
            case GOBLIN_MAGE: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Mage Goblin", 2400D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(700D);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
                entity.getEquipment().clear();
                ItemStack wand = MonsterItem.STAFF_WOODEN.getItem(0);
                ItemStack circlet = MonsterItem.HELMET_GOLDEN.getItem(0);
                entity.getEquipment().setItemInMainHand(wand);
                entity.getEquipment().setHelmet(circlet);
                entity.setBaby(true);
                setEntityExperience(entity, 750);
                setEntityDropTableNo(entity, 7);
                livingEntity = entity;
                break;
            }
            case GOBLIN_SHAMAN: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Shaman Goblin", 2400D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(700D);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
                entity.getEquipment().clear();
                ItemStack wand = MonsterItem.STAFF_LEAF.getItem(0);
                ItemStack circlet = MonsterItem.HELMET_GOLDEN.getItem(0);
                entity.getEquipment().setItemInMainHand(wand);
                entity.getEquipment().setHelmet(circlet);
                entity.setBaby(true);
                setEntityExperience(entity, 750);
                setEntityDropTableNo(entity, 7);
                livingEntity = entity;
                break;
            }
            case ORC_FIGHTER: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Fighter Orc", 4500D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(920D);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.SWORD_WOODEN.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);
                entity.setBaby(false);
                setEntityExperience(entity, 880);
                setEntityDropTableNo(entity, 8);
                livingEntity = entity;
                break;
            }
            case ORC_JOCKEY: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Jockey Orc", 2800D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(820D);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.SWORD_WOODEN.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);
                entity.setBaby(false);

                Chicken mount = (Chicken) EntityUtils.create(loc, ChatColor.YELLOW + "Orc Chicken", 1200D, EntityType.CHICKEN);
                mount.addPassenger(entity);

                setEntityExperience(mount, 720);
                setEntityExperience(entity, 860);
                setEntityDropTableNo(entity, 8);
                livingEntity = entity;
                break;
            }
            case ORC_GLADIATOR: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Gladiator Orc", 5000D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(960D);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.SWORD_STEEL.getItem(0);
                ItemStack chest = MonsterItem.CHESTPLATE_IRON.getItem(0);
                ItemStack leg = MonsterItem.LEGGINGS_IRON.getItem(0);
                ItemStack boot = MonsterItem.BOOTS_IRON.getItem(0);
                ItemStack shield = MonsterItem.SHIELD_KNIGHT.getItem(0);
                entity.getEquipment().setChestplate(chest);
                entity.getEquipment().setLeggings(leg);
                entity.getEquipment().setBoots(boot);
                entity.getEquipment().setItemInMainHand(sword);
                entity.getEquipment().setItemInOffHand(shield);
                entity.setBaby(false);
                setEntityExperience(entity, 920);
                setEntityDropTableNo(entity, 8);
                livingEntity = entity;
                break;
            }
            case ORC_SHAMAN: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Shaman Orc", 3400D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
                entity.getEquipment().clear();
                ItemStack wand = MonsterItem.STAFF_LEAF.getItem(0);
                ItemStack circlet = MonsterItem.HELMET_GOLDEN.getItem(0);
                entity.getEquipment().setItemInMainHand(wand);
                entity.getEquipment().setHelmet(circlet);
                entity.setBaby(false);
                setEntityExperience(entity, 920);
                setEntityDropTableNo(entity, 8);
                livingEntity = entity;
                break;
            }
            case ORC_MAGE: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Mage Orc", 3400D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
                entity.getEquipment().clear();
                ItemStack wand = MonsterItem.STAFF_FIRE.getItem(0);
                ItemStack circlet = MonsterItem.HELMET_GOLDEN.getItem(0);
                entity.getEquipment().setItemInMainHand(wand);
                entity.getEquipment().setHelmet(circlet);
                entity.setBaby(false);
                setEntityExperience(entity, 920);
                setEntityDropTableNo(entity, 8);
                livingEntity = entity;
                break;
            }
            case MAGMA_CUBE: {
                MagmaCube entity = (MagmaCube) EntityUtils.create(loc, ChatColor.RED + "Magma Cube", 4000D, EntityType.MAGMA_CUBE);
                setCustomDamage(entity, 860);
                entity.setSize(3);
                setEntityExperience(entity, 880);
                setEntityDropTableNo(entity, 8);
                livingEntity = entity;
                break;
            }
            case BLAZE: {
                Blaze entity = (Blaze) EntityUtils.create(loc, ChatColor.RED + "Blaze", 2800D, EntityType.BLAZE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
                setEntityExperience(entity, 880);
                setEntityDropTableNo(entity, 8);
                livingEntity = entity;
                break;
            }
            case ENDERMAN: {
                Enderman entity = (Enderman) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Void", 7200D, EntityType.ENDERMAN);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
                setEntityExperience(entity, 1000);
                setEntityDropTableNo(entity, 9);
                livingEntity = entity;
                break;
            }
            case PHANTOM: {
                Phantom entity = (Phantom) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Phantom", 5400D, EntityType.PHANTOM);
                setEntityExperience(entity, 164);
                setEntityDropTableNo(entity, 3);
                livingEntity = entity;
                break;
            }
            case PILLAGER_ROGUE: {
                Pillager entity = (Pillager) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Rogue Pillager", 8000D, EntityType.PILLAGER);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
                entity.getEquipment().clear();

                ItemStack item = MonsterItem.DAGGER_DARKEST.getItem(0);

                entity.getEquipment().setItemInMainHand(item);
                entity.getEquipment().setItemInOffHand(item);

                setEntityExperience(entity, 1000);
                setEntityDropTableNo(entity, 9);
                livingEntity = entity;
                break;
            }
            case PILLAGER_MAGE: {
                Pillager entity = (Pillager) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Mage Pillager", 7000D, EntityType.PILLAGER);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
                entity.getEquipment().clear();
                ItemStack item = MonsterItem.STAFF_FIRE.getItem(0);
                entity.getEquipment().setItemInMainHand(item);

                setEntityExperience(entity, 1000);
                setEntityDropTableNo(entity, 9);
                livingEntity = entity;
                break;
            }
            case PILLAGER_RAVAGER: {
                Pillager entity = (Pillager) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Commander Pillager", 10000D, EntityType.PILLAGER);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
                entity.getEquipment().clear();
                ItemStack item = MonsterItem.CROSSBOW_DARK.getItem(0);
                entity.getEquipment().setItemInMainHand(item);

                Ravager mount = (Ravager) EntityUtils.create(loc, ChatColor.YELLOW + "Ravager", 2400D, EntityType.RAVAGER);
                mount.addPassenger(entity);

                setEntityExperience(mount, 553);
                setEntityExperience(entity, 1000);
                setEntityDropTableNo(entity, 9);
                livingEntity = entity;
                break;
            }
            case PILLAGER_SHAMAN: {
                Pillager entity = (Pillager) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Shaman Pillager", 7000D, EntityType.PILLAGER);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);

                ItemStack item = MonsterItem.STAFF_MOON.getItem(0);
                entity.getEquipment().setItemInMainHand(item);

                setEntityExperience(entity, 1000);
                setEntityDropTableNo(entity, 9);
                livingEntity = entity;
                break;
            }
            case PILLAGER_FIGHTER: {
                Pillager entity = (Pillager) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Fighter Pillager", 9000D, EntityType.PILLAGER);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);

                ItemStack item = MonsterItem.MACE_DARK.getItem(0);
                ItemStack shield = MonsterItem.SHIELD_DARK.getItem(0);

                entity.getEquipment().setItemInMainHand(item);
                entity.getEquipment().setItemInOffHand(shield);

                setEntityExperience(entity, 1000);
                setEntityDropTableNo(entity, 9);
                livingEntity = entity;
                break;
            }
            case BOSS_SLIME: {
                Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GOLD + "King Slime", 600D, EntityType.SLIME);
                entity.setSize(6);
                setCustomDamage(entity, 24);
                livingEntity = entity;
                break;
            }
            case BOSS_ZOMBIE: {
                Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Subject#471", 1000D, EntityType.ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(60D);
                entity.setBaby(false);
                entity.getEquipment().clear();
                ItemStack sword = MonsterItem.SWORD_STEEL.getItem(0);
                entity.getEquipment().setItemInMainHand(sword);
                ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
                ItemStack leg = new ItemStack(Material.IRON_LEGGINGS);
                ItemStack boots = new ItemStack(Material.IRON_BOOTS);
                entity.getEquipment().setChestplate(chest);
                entity.getEquipment().setLeggings(leg);
                entity.getEquipment().setBoots(boots);
                livingEntity = entity;
                break;
            }
            case BOSS_SKELETON: {
                Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Dark Magician Nimzuth", 2400D, EntityType.SKELETON);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(150D);
                entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.18D);
                entity.getEquipment().clear();
                ItemStack wand = MonsterItem.STAFF_FIRE.getItem(0);
                entity.getEquipment().setItemInMainHand(wand);
                ItemStack circlet = MonsterItem.HELMET_GOLDEN.getItem(0);
                entity.getEquipment().setHelmet(circlet);
                ItemStack chest = MonsterItem.CHESTPLATE_GOLDEN.getItem(0);
                ItemStack leg = MonsterItem.LEGGINGS_GOLDEN.getItem(0);
                ItemStack boots = MonsterItem.BOOTS_GOLDEN.getItem(0);
                entity.getEquipment().setChestplate(chest);
                entity.getEquipment().setLeggings(leg);
                entity.getEquipment().setBoots(boots);
                livingEntity = entity;
                break;
            }
            case BOSS_COOK: {
                Evoker entity = (Evoker) EntityUtils.create(loc, ChatColor.YELLOW + "Evil Cook", 7500D, EntityType.EVOKER);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(360D);
                entity.getEquipment().clear();

                livingEntity = entity;
                break;
            }
            case BOSS_PIRATE: {
                WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Captain Barbaros", 15000D, EntityType.WITHER_SKELETON);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(600D);
                ItemStack sword = MonsterItem.KATANA.getItem(0);
                ItemStack helmet = MonsterItem.PIRATE_HAT.getItem(0);
                ItemStack pistol = MonsterItem.PIRATE_PISTOL.getItem(500);
                entity.getEquipment().clear();
                entity.getEquipment().setItemInMainHand(pistol);
                entity.getEquipment().setItemInOffHand(sword);
                entity.getEquipment().setHelmet(helmet);
                livingEntity = entity;
                break;
            }
            case BOSS_ICE: {
                Vindicator entity = (Vindicator) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Necromancer King", 30000D, EntityType.VINDICATOR);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
                ItemStack axe = MonsterItem.AXE_FROST.getItem(0);
                entity.getEquipment().clear();
                entity.getEquipment().setItemInMainHand(axe);
                livingEntity = entity;
                break;
            }
            case BOSS_DESERT: {
                Husk entity = (Husk) EntityUtils.create(loc, ChatColor.YELLOW + "Pharaoh", 45000D, EntityType.HUSK);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
                entity.getEquipment().clear();
                livingEntity = entity;
                break;
            }
            case BOSS_SWAMP: {
                Witch entity = (Witch) EntityUtils.create(loc, ChatColor.BLUE + "Guardian Esobel", 70000D, EntityType.WITCH);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1800D);
                entity.getEquipment().clear();
                ItemStack bow = MonsterItem.BOW_SATET.getItem(500);
                entity.getEquipment().setItemInMainHand(bow);
                livingEntity = entity;
                break;
            }
            case BOSS_LAVA: {
                PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.RED + "Warchief Drogoth", 100000D, EntityType.PIG_ZOMBIE);
                entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2500D);
                entity.setBaby(false);
                ItemStack sword = MonsterItem.AXE_TITAN.getItem(0);
                ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
                ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
                ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS);
                entity.getEquipment().clear();
                entity.getEquipment().setItemInMainHand(sword);
                entity.getEquipment().setChestplate(chest);
                entity.getEquipment().setLeggings(leg);
                entity.getEquipment().setBoots(boot);
                livingEntity = entity;
                break;
            }
            case BOSS_DARKNESS:
                livingEntity = TUTORIAL_BOSS.getMob(loc);
                break;
        }

        startSkillLoop(livingEntity);

        return livingEntity;
    }
}
