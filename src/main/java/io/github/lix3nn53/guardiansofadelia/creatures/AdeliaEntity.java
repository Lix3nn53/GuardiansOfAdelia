package io.github.lix3nn53.guardiansofadelia.creatures;

import io.github.lix3nn53.guardiansofadelia.Items.list.MonsterItem;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
    TURTLE,
    TURTLE_WATER,
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
    SKELETON_WARRIOR,
    SKELETON_MONK,
    SKELETON_MAGE,
    //AREA-4
    CREEPER,
    VEX,
    EVOKER,
    SHULKER,
    PHANTOM,
    //AREA-5
    PIRATE_SHOOTER,
    PIRATE_FIGHTER,
    PIRATE_SHARPSHOOTER,
    PIRATE_DUEL_MASTER,
    PIRATE_DROWNED,
    PIRATE_DROWNED_JOCKEY,
    //AREA-6
    NINJA,
    NINJA_SWORD_MASTER,
    NINJA_ARCHER,
    NINJA_STAR,
    ZOMBIE_JOCKEY,
    TIMBERMAN,
    //AREA-7
    MUMMY,
    MUMMY_GHOST,
    CAVALRY_SKELETON,
    CAVALRY_SKELETON_ARCHER,
    SPIDER,
    //AREA-8
    WITCH,
    GOBLIN,
    GOBLIN_MAGE,
    GOBLIN_JOCKEY,
    GOBLIN_SHAMAN,
    //AREA-9
    ORC,
    ORC_MAGE,
    ORC_JOCKEY,
    ORC_SHAMAN,
    ORC_GLADIATOR,
    MAGMA_CUBE,
    BLAZE,
    //AREA-10
    ENDERMAN,
    ILLUSIONER,
    PILLAGER,
    PILLAGER_HUNTER,
    PILLAGER_RAVAGER,
    PILLAGER_SHAMAN,
    PILLAGER_WARRIOR,
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

    public Entity getMob(Location loc) {
        if (this.equals(VILLAGER_HOBBIT)) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Hobbit", 20000D, EntityType.VILLAGER);
            entity.setBaby();
            entity.setAgeLock(true);

            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            entity.setVillagerLevel(2);
            return entity;
        } else if (this.equals(VILLAGER_1)) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Roumen Villager", 40000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            entity.setVillagerLevel(2);
            return entity;
        } else if (this.equals(VILLAGER_2)) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Veloa Villager", 60000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            entity.setVillagerLevel(2);
            return entity;
        } else if (this.equals(VILLAGER_3)) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Elderine Villager", 80000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            entity.setVillagerLevel(2);
            return entity;
        } else if (this.equals(VILLAGER_4)) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Uruga Villager", 120000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            entity.setVillagerLevel(2);
            return entity;
        } else if (this.equals(VILLAGER_5)) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Aberstol Villager", 200000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            entity.setVillagerLevel(2);
            return entity;
        } else if (this.equals(VILLAGER_SAILOR)) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.AQUA + "Sailor", 100000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            entity.setVillagerLevel(2);
            return entity;
        } else if (this.equals(VILLAGER_FARMER)) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.YELLOW + "Farmer", 80000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            entity.setVillagerLevel(2);
            return entity;
        } else if (this.equals(TUTORIAL_1)) {
            WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.RED + "Malephar's Cavalry", 1800D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(120D);
            ItemStack sword = MonsterItem.SWORD_DARK.getItem(0);
            entity.getEquipment().setItemInMainHand(sword);
            return entity;
        } else if (this.equals(TUTORIAL_2)) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Malephar's Guard", 1200D, EntityType.STRAY);
            ItemStack bow = MonsterItem.BOW_DARK.getItem(500);
            entity.getEquipment().setItemInMainHand(bow);
            return entity;
        } else if (this.equals(TUTORIAL_BOSS)) {
            Wither entity = (Wither) EntityUtils.create(loc, ChatColor.RED + "Malephar", 100000D, EntityType.WITHER);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2D);
            return entity;
        } else if (this.equals(COW)) {
            Cow entity = (Cow) EntityUtils.create(loc, ChatColor.GREEN + "Cow", 1420D, EntityType.COW);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            entity.setBreed(false);
            entity.setAdult();
            return entity;
        } else if (this.equals(COW_BABY)) {
            Cow entity = (Cow) EntityUtils.create(loc, ChatColor.GREEN + "Baby Cow", 142D, EntityType.COW);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            entity.setBreed(false);
            entity.setBaby();
            entity.setAgeLock(true);
            return entity;
        } else if (this.equals(SHEEP)) {
            Sheep entity = (Sheep) EntityUtils.create(loc, ChatColor.GREEN + "Sheep", 1420D, EntityType.SHEEP);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            entity.setBreed(false);
            entity.setAdult();
            return entity;
        } else if (this.equals(SHEEP_BABY)) {
            Sheep entity = (Sheep) EntityUtils.create(loc, ChatColor.GREEN + "Baby Sheep", 142D, EntityType.SHEEP);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            entity.setBreed(false);
            entity.setBaby();
            entity.setAgeLock(true);
            return entity;
        } else if (this.equals(LIZARD)) {
            Silverfish entity = (Silverfish) EntityUtils.create(loc, ChatColor.GREEN + "Wild Lizard", 25D, EntityType.SILVERFISH);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6D);
            setEntityExperience(entity, 2);
            setEntityDropTableNo(entity, 0);
            return entity;
        } else if (this.equals(LIZARD_POISONOUS)) {
            Silverfish entity = (Silverfish) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Poisonous Lizard", 30D, EntityType.SILVERFISH);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6D);
            setEntityExperience(entity, 2);
            setEntityDropTableNo(entity, 0);
            return entity;
        } else if (this.equals(TURTLE)) {
            Endermite entity = (Endermite) EntityUtils.create(loc, ChatColor.GREEN + "Wild Turtle", 30D, EntityType.ENDERMITE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6D);
            entity.setSilent(true);
            setEntityExperience(entity, 3);
            setEntityDropTableNo(entity, 0);
            return entity;
        } else if (this.equals(TURTLE_WATER)) {
            Endermite entity = (Endermite) EntityUtils.create(loc, ChatColor.AQUA + "Water Turtle", 40D, EntityType.ENDERMITE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6D);
            entity.setSilent(true);
            setEntityExperience(entity, 3);
            setEntityDropTableNo(entity, 0);
            return entity;
        } else if (this.equals(SLIME)) {
            Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GREEN + "Baby Slime", 50D, EntityType.SLIME);
            entity.setSize(2);
            setEntityExperience(entity, 5);
            setEntityDropTableNo(entity, 0);
            setCustomDamage(entity, 9);
            return entity;
        } else if (this.equals(SLIME_STICKY)) {
            Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GREEN + "Sticky Slime", 70D, EntityType.SLIME);
            entity.setSize(3);
            setEntityExperience(entity, 7);
            setEntityDropTableNo(entity, 0);
            setCustomDamage(entity, 12);
            return entity;
        } else if (this.equals(ZOMBIE)) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie", 100D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            setEntityExperience(entity, 12);
            setEntityDropTableNo(entity, 1);
            return entity;
        } else if (this.equals(ZOMBIE_VILLAGER)) {
            ZombieVillager entity = (ZombieVillager) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Villager", 120D, EntityType.ZOMBIE_VILLAGER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(24D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            ItemStack axe = MonsterItem.AXE_WOODEN.getItem(0);
            entity.getEquipment().setItemInMainHand(axe);
            setEntityExperience(entity, 14);
            setEntityDropTableNo(entity, 1);
            return entity;
        } else if (this.equals(ZOMBIE_SPLITTER)) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Splitter Zombie", 80D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            setEntityExperience(entity, 14);
            setEntityDropTableNo(entity, 1);
            return entity;
        } else if (this.equals(ZOMBIE_SHAMAN)) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Shaman Zombie", 80D, EntityType.ZOMBIE);
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
            return entity;
        } else if (this.equals(ZOMBIE_TANK)) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zomtank", 150D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            ItemStack shield = MonsterItem.SHIELD_WOODEN.getItem(0);
            entity.getEquipment().setItemInOffHand(shield);
            setEntityExperience(entity, 18);
            setEntityDropTableNo(entity, 1);
            return entity;
        } else if (this.equals(SKELETON_ARCHER)) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Archer Skeleton", 220D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItem.BOW_WOODEN.getItem(300);
            entity.getEquipment().setItemInMainHand(bow);
            setEntityExperience(entity, 53);
            setEntityDropTableNo(entity, 2);
            return entity;
        } else if (this.equals(SKELETON_WARRIOR)) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Warrior Skeleton", 280D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(60D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItem.SWORD_WOODEN.getItem(0);
            entity.getEquipment().setItemInMainHand(sword);
            setEntityExperience(entity, 53);
            setEntityDropTableNo(entity, 2);
            return entity;
        } else if (this.equals(SKELETON_MONK)) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Hunter Skeleton", 260D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItem.SPEAR_STEEL.getItem(0);
            entity.getEquipment().setItemInMainHand(bow);
            ItemStack helmet = MonsterItem.HELMET_CHAINMAIL.getItem(0);
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 60);
            setEntityDropTableNo(entity, 2);
            return entity;
        } else if (this.equals(SKELETON_MAGE)) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Mage Skeleton", 300D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItem.STAFF_BONE.getItem(0);
            entity.getEquipment().setItemInMainHand(wand);
            ItemStack helmet = MonsterItem.HELMET_GOLDEN.getItem(0);
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 68);
            setEntityDropTableNo(entity, 2);
            return entity;
        } else if (this.equals(SHULKER)) {
            Shulker entity = (Shulker) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Strawberry Jellybeans", 500D, EntityType.SHULKER);
            setEntityExperience(entity, 142);
            setEntityDropTableNo(entity, 3);
            return entity;
        } else if (this.equals(EVOKER)) {
            Evoker entity = (Evoker) EntityUtils.create(loc, ChatColor.YELLOW + "Chocolate Cupcake", 680D, EntityType.EVOKER);
            setEntityExperience(entity, 148);
            setEntityDropTableNo(entity, 3);
            return entity;
        } else if (this.equals(CREEPER)) {
            Creeper entity = (Creeper) EntityUtils.create(loc, ChatColor.AQUA + "Popping Rainbow", 620D, EntityType.CREEPER);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            setEntityExperience(entity, 153);
            setEntityDropTableNo(entity, 3);
            return entity;
        } else if (this.equals(ILLUSIONER)) {
            Illusioner entity = (Illusioner) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "IceCream", 720D, EntityType.ILLUSIONER);
            setEntityExperience(entity, 164);
            setEntityDropTableNo(entity, 3);
            return entity;
        } else if (this.equals(PHANTOM)) {
            Phantom entity = (Phantom) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "IceCream", 720D, EntityType.PHANTOM);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4D);
            setEntityExperience(entity, 164);
            setEntityDropTableNo(entity, 3);
            return entity;
        } else if (this.equals(PIRATE_SHOOTER)) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Shooter Pirate", 860D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack pistol = MonsterItem.PIRATE_PISTOL.getItem(400);
            entity.getEquipment().setItemInMainHand(pistol);
            ItemStack helmet = MonsterItem.PIRATE_HAT.getItem(0);
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 253);
            setEntityDropTableNo(entity, 4);
            return entity;
        } else if (this.equals(PIRATE_FIGHTER)) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Fighter Pirate", 980D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(220D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItem.SWORD_STEEL.getItem(0);
            entity.getEquipment().setItemInMainHand(sword);
            ItemStack helmet = MonsterItem.PIRATE_HAT.getItem(0);
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 253);
            setEntityDropTableNo(entity, 4);
            return entity;
        } else if (this.equals(PIRATE_SHARPSHOOTER)) {
            WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Sharpshooter Pirate", 1000D, EntityType.WITHER_SKELETON);
            entity.getEquipment().clear();
            ItemStack pistol = MonsterItem.PIRATE_PISTOL.getItem(400);
            entity.getEquipment().setItemInMainHand(pistol);
            ItemStack helmet = MonsterItem.PIRATE_HAT.getItem(0);
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 282);
            setEntityDropTableNo(entity, 4);
            return entity;
        } else if (this.equals(PIRATE_DUEL_MASTER)) {
            WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Duel Master Pirate", 1200D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(260D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItem.KATANA.getItem(0);
            entity.getEquipment().setItemInMainHand(sword);
            ItemStack helmet = MonsterItem.PIRATE_HAT.getItem(0);
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 282);
            setEntityDropTableNo(entity, 4);
            return entity;
        } else if (this.equals(PIRATE_DROWNED)) {
            Drowned entity = (Drowned) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Duel Master Pirate", 1200D, EntityType.DROWNED);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(260D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItem.SPEAR_STEEL.getItem(400);
            entity.getEquipment().setItemInMainHand(sword);
            setEntityExperience(entity, 282);
            setEntityDropTableNo(entity, 4);
            return entity;
        } else if (this.equals(PIRATE_DROWNED_JOCKEY)) {
            Drowned entity = (Drowned) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Duel Master Pirate", 1200D, EntityType.DROWNED);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(260D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItem.SPEAR_STEEL.getItem(400);
            entity.getEquipment().setItemInMainHand(sword);
            entity.setBaby(true);

            Chicken mount = (Chicken) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Chicken", 700D, EntityType.CHICKEN);
            mount.addPassenger(entity);

            setEntityExperience(mount, 253);
            setEntityExperience(entity, 282);
            setEntityDropTableNo(entity, 4);
            return entity;
        } else if (this.equals(NINJA_STAR)) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Star Rogue", 1500D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(380D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2D);
            entity.getEquipment().clear();
            ItemStack star = MonsterItem.STAR.getItem(0);
            entity.getEquipment().setItemInMainHand(star);
            setEntityExperience(entity, 420);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (this.equals(NINJA)) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Rogue", 1500D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(380D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            entity.getEquipment().clear();
            ItemStack dagger = MonsterItem.DAGGER_CRIMSON.getItem(0);
            entity.getEquipment().setItemInMainHand(dagger);
            entity.getEquipment().setItemInOffHand(dagger);
            setEntityExperience(entity, 420);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (this.equals(NINJA_ARCHER)) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Archer Rogue", 1500D, EntityType.STRAY);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItem.BOW_DARK.getItem(350);
            entity.getEquipment().setItemInMainHand(bow);
            setEntityExperience(entity, 420);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (this.equals(ZOMBIE_JOCKEY)) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Jockey Zombie", 1400D, EntityType.ZOMBIE);
            entity.getEquipment().clear();
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(320D);
            entity.setBaby(true);

            Chicken mount = (Chicken) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Chicken", 700D, EntityType.CHICKEN);
            mount.addPassenger(entity);

            setEntityExperience(mount, 253);
            setEntityExperience(entity, 400);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (this.equals(TIMBERMAN)) {
            Vindicator entity = (Vindicator) EntityUtils.create(loc, ChatColor.AQUA + "Frozen Timberman", 1700D, EntityType.VINDICATOR);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(420D);
            entity.getEquipment().clear();
            ItemStack axe = MonsterItem.AXE_WOODEN.getItem(0);
            entity.getEquipment().setItemInMainHand(axe);
            setEntityExperience(entity, 450);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (this.equals(MUMMY)) {
            Husk entity = (Husk) EntityUtils.create(loc, ChatColor.YELLOW + "Mummy", 2000D, EntityType.HUSK);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(520D);
            entity.getEquipment().clear();
            setEntityExperience(entity, 553);
            setEntityDropTableNo(entity, 6);
            return entity;
        } else if (this.equals(MUMMY_GHOST)) {
            Husk entity = (Husk) EntityUtils.create(loc, ChatColor.YELLOW + "Ghost Mummy", 2250D, EntityType.HUSK);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(540D);
            entity.getEquipment().clear();
            PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(invis);
            PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(glow);
            setEntityExperience(entity, 584);
            setEntityDropTableNo(entity, 6);
            return entity;
        } else if (this.equals(CAVALRY_SKELETON_ARCHER)) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.YELLOW + "Cavalry Archer Skeleton", 1800D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItem.BOW_SATET.getItem(600);
            entity.getEquipment().setItemInMainHand(bow);

            Spider mount = (Spider) EntityUtils.create(loc, ChatColor.WHITE + "Desert Spider", 900D, EntityType.SPIDER);
            mount.addPassenger(entity);

            setEntityExperience(mount, 420);
            setEntityExperience(entity, 553);
            setEntityDropTableNo(entity, 6);
            return entity;
        } else if (this.equals(CAVALRY_SKELETON)) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.YELLOW + "Cavalry Skeleton", 2000D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(540D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItem.SPEAR_RED.getItem(0);
            entity.getEquipment().setItemInMainHand(sword);

            Spider mount = (Spider) EntityUtils.create(loc, ChatColor.WHITE + "Desert Spider", 900D, EntityType.SPIDER);
            mount.addPassenger(entity);

            setEntityExperience(mount, 420);
            setEntityExperience(entity, 553);
            setEntityDropTableNo(entity, 6);
            return entity;
        } else if (this.equals(SPIDER)) {
            CaveSpider entity = (CaveSpider) EntityUtils.create(loc, ChatColor.YELLOW + "Ghost Spider", 2200D, EntityType.CAVE_SPIDER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(520D);
            PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(invis);
            PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(glow);
            setEntityExperience(entity, 584);
            setEntityDropTableNo(entity, 6);
            return entity;
        } else if (this.equals(WITCH)) {
            Witch entity = (Witch) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Witch", 3420D, EntityType.WITCH);
            setEntityExperience(entity, 764);
            setEntityDropTableNo(entity, 7);
            return entity;
        } else if (this.equals(GOBLIN_JOCKEY)) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Jockey Goblin", 2400D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(640D);
            entity.getEquipment().clear();
            entity.setBaby(true);
            ItemStack sword = MonsterItem.SWORD_WOODEN.getItem(0);
            entity.getEquipment().setItemInMainHand(sword);

            Chicken mount = (Chicken) EntityUtils.create(loc, ChatColor.YELLOW + "Goblin Chicken", 1200D, EntityType.CHICKEN);
            mount.addPassenger(entity);

            setEntityExperience(mount, 553);
            setEntityExperience(entity, 720);
            setEntityDropTableNo(entity, 7);
            return entity;
        } else if (this.equals(GOBLIN)) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Goblin", 2800D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(740D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItem.SWORD_WOODEN.getItem(0);
            entity.getEquipment().setItemInMainHand(sword);
            entity.setBaby(true);
            setEntityExperience(entity, 750);
            setEntityDropTableNo(entity, 7);
            return entity;
        } else if (this.equals(GOBLIN_MAGE)) {
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
            return entity;
        } else if (this.equals(GOBLIN_SHAMAN)) {
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
            return entity;
        } else if (this.equals(ORC)) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Orc", 3750D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(920D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItem.SWORD_WOODEN.getItem(0);
            entity.getEquipment().setItemInMainHand(sword);
            entity.setBaby(false);
            setEntityExperience(entity, 880);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (this.equals(ORC_JOCKEY)) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Jockey Orc", 3000D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(820D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItem.SWORD_WOODEN.getItem(0);
            entity.getEquipment().setItemInMainHand(sword);
            entity.setBaby(false);

            Chicken mount = (Chicken) EntityUtils.create(loc, ChatColor.YELLOW + "Orc Chicken", 1500D, EntityType.CHICKEN);
            mount.addPassenger(entity);

            setEntityExperience(mount, 720);
            setEntityExperience(entity, 860);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (this.equals(ORC_GLADIATOR)) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Gladiator Orc", 4200D, EntityType.PIG_ZOMBIE);
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
            return entity;
        } else if (this.equals(ORC_SHAMAN)) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Shaman Orc", 3000D, EntityType.PIG_ZOMBIE);
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
            return entity;
        } else if (this.equals(ORC_MAGE)) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Mage Orc", 3000D, EntityType.PIG_ZOMBIE);
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
            return entity;
        } else if (this.equals(MAGMA_CUBE)) {
            MagmaCube entity = (MagmaCube) EntityUtils.create(loc, ChatColor.RED + "Magma Cube", 3500D, EntityType.MAGMA_CUBE);
            setCustomDamage(entity, 860);
            entity.setSize(3);
            setEntityExperience(entity, 880);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (this.equals(BLAZE)) {
            Blaze entity = (Blaze) EntityUtils.create(loc, ChatColor.RED + "Blaze", 3500D, EntityType.BLAZE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
            setEntityExperience(entity, 880);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (this.equals(ENDERMAN)) {
            Enderman entity = (Enderman) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Void Enderman", 6000D, EntityType.ENDERMAN);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
            setEntityExperience(entity, 1000);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (this.equals(PILLAGER)) {
            Pillager entity = (Pillager) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Pillager", 9000D, EntityType.PILLAGER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
            entity.getEquipment().clear();
            setEntityExperience(entity, 1000);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (this.equals(PILLAGER_HUNTER)) {
            Pillager entity = (Pillager) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Hunter Pillager", 9000D, EntityType.PILLAGER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
            entity.getEquipment().clear();
            ItemStack item = MonsterItem.CROSSBOW_DARK.getItem(0);
            entity.getEquipment().setItemInMainHand(item);

            setEntityExperience(entity, 1000);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (this.equals(PILLAGER_RAVAGER)) {
            Pillager entity = (Pillager) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Commander Pillager", 9000D, EntityType.PILLAGER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
            entity.getEquipment().clear();
            Ravager mount = (Ravager) EntityUtils.create(loc, ChatColor.YELLOW + "Ravager", 1500D, EntityType.RAVAGER);
            mount.addPassenger(entity);

            setEntityExperience(mount, 553);
            setEntityExperience(entity, 1000);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (this.equals(PILLAGER_SHAMAN)) {
            Pillager entity = (Pillager) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Shaman Pillager", 9000D, EntityType.PILLAGER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
            setEntityExperience(entity, 1000);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (this.equals(PILLAGER_WARRIOR)) {
            Pillager entity = (Pillager) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Warrior Pillager", 9000D, EntityType.PILLAGER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
            setEntityExperience(entity, 1000);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (this.equals(BOSS_SLIME)) {
            Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GOLD + "King Slime", 600D, EntityType.SLIME);
            entity.setSize(6);
            setCustomDamage(entity, 24);
            return entity;
        } else if (this.equals(BOSS_ZOMBIE)) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Leader", 2000D, EntityType.ZOMBIE);
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
            return entity;
        } else if (this.equals(BOSS_SKELETON)) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Dark Magic Master", 5000D, EntityType.SKELETON);
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
            return entity;
        } else if (this.equals(BOSS_COOK)) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Mad Cook", 12500D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(360D);
            entity.setBaby(false);
            entity.getEquipment().clear();

            ItemStack snowball = new ItemStack(Material.SNOWBALL);
            ItemStack cookie = new ItemStack(Material.COOKIE);

            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta sim = (SkullMeta) head.getItemMeta();
            sim.setOwningPlayer(Bukkit.getPlayer("SheepKid"));
            sim.setUnbreakable(true);
            sim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            head.setItemMeta(sim);

            ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
            ItemStack leg = new ItemStack(Material.LEATHER_LEGGINGS);
            ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);

            entity.getEquipment().setItemInMainHand(snowball);
            entity.getEquipment().setItemInOffHand(cookie);
            entity.getEquipment().setHelmet(head);
            entity.getEquipment().setChestplate(chest);
            entity.getEquipment().setLeggings(leg);
            entity.getEquipment().setBoots(boot);
            entity.setSilent(true);
            return entity;
        } else if (this.equals(BOSS_PIRATE)) {
            WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Captain Barbaros", 20000D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(600D);
            ItemStack sword = MonsterItem.KATANA.getItem(0);
            ItemStack helmet = MonsterItem.PIRATE_HAT.getItem(0);
            ItemStack pistol = MonsterItem.PIRATE_PISTOL.getItem(500);
            entity.getEquipment().clear();
            entity.getEquipment().setItemInMainHand(pistol);
            entity.getEquipment().setItemInOffHand(sword);
            entity.getEquipment().setHelmet(helmet);
            return entity;
        } else if (this.equals(BOSS_ICE)) {
            Vindicator entity = (Vindicator) EntityUtils.create(loc, ChatColor.AQUA + "Makvurn", 32500D, EntityType.VINDICATOR);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
            ItemStack axe = MonsterItem.AXE_FROST.getItem(0);
            entity.getEquipment().clear();
            entity.getEquipment().setItemInMainHand(axe);
            return entity;
        } else if (this.equals(BOSS_DESERT)) {
            Husk entity = (Husk) EntityUtils.create(loc, ChatColor.YELLOW + "Selket", 45000D, EntityType.HUSK);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
            entity.getEquipment().clear();
            return entity;
        } else if (this.equals(BOSS_SWAMP)) {
            Witch entity = (Witch) EntityUtils.create(loc, ChatColor.BLUE + "Mage Woz", 60000D, EntityType.WITCH);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1800D);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItem.BOW_SATET.getItem(500);
            entity.getEquipment().setItemInMainHand(bow);
            return entity;
        } else if (this.equals(BOSS_LAVA)) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.RED + "War Chief Drogoth", 75000D, EntityType.PIG_ZOMBIE);
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
            return entity;
        } else if (this.equals(BOSS_DARKNESS)) {
            Wither entity = (Wither) EntityUtils.create(loc, ChatColor.DARK_RED + "Malephar", 100000D, EntityType.WITHER);
            return entity;
        }
        return null;
    }
}
