package io.github.lix3nn53.guardiansofadelia.creatures;

import io.github.lix3nn53.guardiansofadelia.Items.list.MonsterItems;
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

public class EntityList {

    public static Entity getMob(Location loc, String mobcode) {
        if (mobcode.equals("hobbit")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Hobbit", 20000D, EntityType.VILLAGER);
            entity.setBaby();
            entity.setAgeLock(true);

            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            return entity;
        } else if (mobcode.equals("villager1")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Roumen Villager", 40000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            return entity;
        } else if (mobcode.equals("villager15")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Veloa Villager", 60000D, EntityType.VILLAGER);
            entity.setAdult();
            double d = Math.random();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            return entity;
        } else if (mobcode.equals("villager2")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Elderine Villager", 80000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            return entity;
        } else if (mobcode.equals("villager3")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Uruga Villager", 120000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            return entity;
        } else if (mobcode.equals("villager4")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Aberstol Villager", 200000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            return entity;
        } else if (mobcode.equals("sailor")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.AQUA + "Sailor", 100000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            return entity;
        } else if (mobcode.equals("farmer")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.YELLOW + "Farmer", 80000D, EntityType.VILLAGER);
            entity.setAdult();
            Villager.Profession[] values = Villager.Profession.values();
            int i = new Random().nextInt(values.length);
            Villager.Profession value = values[i];
            entity.setProfession(value);
            return entity;
        } else if (mobcode.equals("tuto1")) {
            WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.RED + "Malephar's Cavalry", 1800D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(120D);
            ItemStack sword = MonsterItems.getItem("sword0");
            entity.getEquipment().setItemInMainHand(sword);
            return entity;
        } else if (mobcode.equals("tuto2")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Malephar's Guard", 1200D, EntityType.STRAY);
            ItemStack bow = MonsterItems.getItem("bow0");
            entity.getEquipment().setItemInMainHand(bow);
            return entity;
        } else if (mobcode.equals("tutob")) {
            Wither entity = (Wither) EntityUtils.create(loc, ChatColor.RED + "Malephar", 100000D, EntityType.WITHER);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            return entity;
        } else if (mobcode.equals("cow")) {
            Cow entity = (Cow) EntityUtils.create(loc, ChatColor.GREEN + "Cow", 1420D, EntityType.COW);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            entity.setBreed(false);
            entity.setAdult();
            return entity;
        } else if (mobcode.equals("cowb")) {
            Cow entity = (Cow) EntityUtils.create(loc, ChatColor.GREEN + "Baby Cow", 142D, EntityType.COW);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            entity.setBreed(false);
            entity.setBaby();
            entity.setAgeLock(true);
            return entity;
        } else if (mobcode.equals("sheep")) {
            Sheep entity = (Sheep) EntityUtils.create(loc, ChatColor.GREEN + "Sheep", 1420D, EntityType.SHEEP);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            entity.setBreed(false);
            entity.setAdult();
            return entity;
        } else if (mobcode.equals("sheepb")) {
            Sheep entity = (Sheep) EntityUtils.create(loc, ChatColor.GREEN + "Baby Sheep", 142D, EntityType.SHEEP);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            entity.setBreed(false);
            entity.setBaby();
            entity.setAgeLock(true);
            return entity;
        } else if (mobcode.equals("beginner1")) {
            Silverfish entity = (Silverfish) EntityUtils.create(loc, ChatColor.GREEN + "Wild Lizard", 25D, EntityType.SILVERFISH);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6D);
            setEntityExperience(entity, 2);
            setEntityDropTableNo(entity, 0);
            return entity;
        } else if (mobcode.equals("beginner2")) {
            Silverfish entity = (Silverfish) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Poisonous Lizard", 30D, EntityType.SILVERFISH);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6D);
            setEntityExperience(entity, 2);
            setEntityDropTableNo(entity, 0);
            return entity;
        } else if (mobcode.equals("beginner3")) {
            Endermite entity = (Endermite) EntityUtils.create(loc, ChatColor.GREEN + "Wild Turtle", 30D, EntityType.ENDERMITE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6D);
            entity.setSilent(true);
            setEntityExperience(entity, 3);
            setEntityDropTableNo(entity, 0);
            return entity;
        } else if (mobcode.equals("beginner4")) {
            Endermite entity = (Endermite) EntityUtils.create(loc, ChatColor.AQUA + "Water Turtle", 40D, EntityType.ENDERMITE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(6D);
            entity.setSilent(true);
            setEntityExperience(entity, 3);
            setEntityDropTableNo(entity, 0);
            return entity;
        } else if (mobcode.equals("beginner5")) {
            Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GREEN + "Baby Slime", 50D, EntityType.SLIME);
            entity.setSize(2);
            setEntityExperience(entity, 5);
            setEntityDropTableNo(entity, 0);
            setDamage(entity, 9);
            return entity;
        } else if (mobcode.equals("beginner6")) {
            Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GREEN + "Sticky Slime", 70D, EntityType.SLIME);
            entity.setSize(3);
            setEntityExperience(entity, 7);
            setEntityDropTableNo(entity, 0);
            setDamage(entity, 12);
            return entity;
        } else if (mobcode.equals("zombie1")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie", 100D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            setEntityExperience(entity, 12);
            setEntityDropTableNo(entity, 1);
            return entity;
        } else if (mobcode.equals("zombie2")) {
            ZombieVillager entity = (ZombieVillager) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Villager", 120D, EntityType.ZOMBIE_VILLAGER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(24D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            ItemStack axe = MonsterItems.getItem("axe1");
            entity.getEquipment().setItemInMainHand(axe);
            setEntityExperience(entity, 14);
            setEntityDropTableNo(entity, 1);
            return entity;
        } else if (mobcode.equals("zombie3")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Splitter Zombie", 80D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            setEntityExperience(entity, 14);
            setEntityDropTableNo(entity, 1);
            return entity;
        } else if (mobcode.equals("zombie4")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Shaman Zombie", 80D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(15D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand1");
            entity.getEquipment().setItemInMainHand(wand);
            ItemStack circlet = MonsterItems.getItem("circlet1");
            entity.getEquipment().setHelmet(circlet);
            setEntityExperience(entity, 16);
            setEntityDropTableNo(entity, 1);
            return entity;
        } else if (mobcode.equals("zombie5")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zomtank", 150D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            ItemStack shield = MonsterItems.getItem("shield1");
            entity.getEquipment().setItemInOffHand(shield);
            setEntityExperience(entity, 18);
            setEntityDropTableNo(entity, 1);
            return entity;
        } else if (mobcode.equals("forest1")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Archer Skeleton", 220D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("bow1");
            entity.getEquipment().setItemInMainHand(bow);
            setEntityExperience(entity, 53);
            setEntityDropTableNo(entity, 2);
            return entity;
        } else if (mobcode.equals("forest2")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Warrior Skeleton", 280D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(60D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("sword1");
            entity.getEquipment().setItemInMainHand(sword);
            setEntityExperience(entity, 53);
            setEntityDropTableNo(entity, 2);
            return entity;
        } else if (mobcode.equals("forest3")) {
            Rabbit entity = (Rabbit) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Cursed Rabbit", 200D, EntityType.RABBIT);
            entity.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
            setEntityExperience(entity, 53);
            setEntityDropTableNo(entity, 2);
            setDamage(entity, 45);
            return entity;
        } else if (mobcode.equals("forest4")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Hunter Skeleton", 260D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("bow2");
            entity.getEquipment().setItemInMainHand(bow);
            ItemStack helmet = MonsterItems.getItem("helmet1");
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 60);
            setEntityDropTableNo(entity, 2);
            return entity;
        } else if (mobcode.equals("forest5")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Mage Skeleton", 300D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand2");
            entity.getEquipment().setItemInMainHand(wand);
            ItemStack helmet = MonsterItems.getItem("helmet1");
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 68);
            setEntityDropTableNo(entity, 2);
            return entity;
        } else if (mobcode.equals("sugar1")) {
            Rabbit entity = (Rabbit) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Strawberry Jellybeans", 500D, EntityType.RABBIT);
            entity.setRabbitType(Rabbit.Type.SALT_AND_PEPPER);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4D);
            setEntityExperience(entity, 142);
            setEntityDropTableNo(entity, 3);
            return entity;
        } else if (mobcode.equals("sugar2")) {
            MushroomCow entity = (MushroomCow) EntityUtils.create(loc, ChatColor.YELLOW + "Chocolate Cupcake", 680D, EntityType.MUSHROOM_COW);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            setEntityExperience(entity, 148);
            setEntityDropTableNo(entity, 3);
            return entity;
        } else if (mobcode.equals("sugar3")) {
            Creeper entity = (Creeper) EntityUtils.create(loc, ChatColor.AQUA + "Popping Rainbow", 620D, EntityType.CREEPER);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            setEntityExperience(entity, 153);
            setEntityDropTableNo(entity, 3);
            return entity;
        } else if (mobcode.equals("sugar4")) {
            Snowman entity = (Snowman) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "IceCream", 720D, EntityType.SNOWMAN);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4D);
            entity.setDerp(true);
            setEntityExperience(entity, 164);
            setEntityDropTableNo(entity, 3);
            return entity;
        } else if (mobcode.equals("pirate1")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Shooter Pirate", 860D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack pistol = MonsterItems.getItem("piratepistol");
            entity.getEquipment().setItemInMainHand(pistol);
            ItemStack helmet = MonsterItems.getItem("piratehat");
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 253);
            setEntityDropTableNo(entity, 4);
            return entity;
        } else if (mobcode.equals("pirate2")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Fighter Pirate", 980D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(220D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("piratesword");
            entity.getEquipment().setItemInMainHand(sword);
            ItemStack helmet = MonsterItems.getItem("piratehat");
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 253);
            setEntityDropTableNo(entity, 4);
            return entity;
        } else if (mobcode.equals("pirate3")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Sharpshooter Pirate", 1000D, EntityType.WITHER_SKELETON);
            entity.getEquipment().clear();
            ItemStack pistol = MonsterItems.getItem("piratepistol");
            entity.getEquipment().setItemInMainHand(pistol);
            ItemStack helmet = MonsterItems.getItem("piratehat");
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 282);
            setEntityDropTableNo(entity, 4);
            return entity;
        } else if (mobcode.equals("pirate4")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Duel Master Pirate", 1200D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(260D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("katana");
            entity.getEquipment().setItemInMainHand(sword);
            ItemStack helmet = MonsterItems.getItem("piratehat");
            entity.getEquipment().setHelmet(helmet);
            setEntityExperience(entity, 282);
            setEntityDropTableNo(entity, 4);
            return entity;
        } else if (mobcode.equals("frost1")) {
            PolarBear entity = (PolarBear) EntityUtils.create(loc, ChatColor.AQUA + "PolarBear", 1800D, EntityType.POLAR_BEAR);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(400D);
            entity.setAdult();
            setEntityExperience(entity, 450);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (mobcode.equals("frost2")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Archer Rogue", 1500D, EntityType.STRAY);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("frostbow");
            entity.getEquipment().setItemInMainHand(bow);
            setEntityExperience(entity, 420);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (mobcode.equals("frost3")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Jockey Zombie", 1400D, EntityType.ZOMBIE);
            entity.getEquipment().clear();
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(320D);
            entity.setBaby(true);

            Chicken c = (Chicken) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Chicken", 700D, EntityType.CHICKEN);
            c.addPassenger(entity);

            setEntityExperience(c, 253);
            setEntityExperience(entity, 400);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (mobcode.equals("frost4")) {
            Vindicator entity = (Vindicator) EntityUtils.create(loc, ChatColor.AQUA + "Frozen Timberman", 1700D, EntityType.VINDICATOR);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(420D);
            entity.getEquipment().clear();
            ItemStack axe = MonsterItems.getItem("frostaxe");
            entity.getEquipment().setItemInMainHand(axe);
            setEntityExperience(entity, 450);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (mobcode.equals("frost5")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Star Rogue", 1500D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(380D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2D);
            entity.getEquipment().clear();
            ItemStack star = MonsterItems.getItem("roguestar");
            entity.getEquipment().setItemInMainHand(star);
            setEntityExperience(entity, 420);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (mobcode.equals("frost6")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Rogue", 1500D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(380D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            entity.getEquipment().clear();
            ItemStack dagger = MonsterItems.getItem("frostdagger");
            entity.getEquipment().setItemInMainHand(dagger);
            entity.getEquipment().setItemInOffHand(dagger);
            setEntityExperience(entity, 420);
            setEntityDropTableNo(entity, 5);
            return entity;
        } else if (mobcode.equals("desert1")) {
            Husk entity = (Husk) EntityUtils.create(loc, ChatColor.YELLOW + "Mummy", 2000D, EntityType.HUSK);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(520D);
            entity.getEquipment().clear();
            setEntityExperience(entity, 553);
            setEntityDropTableNo(entity, 6);
            return entity;
        } else if (mobcode.equals("desert2")) {
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
        } else if (mobcode.equals("desert3")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.YELLOW + "Cavalry Archer Skeleton", 1800D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("desertbow");
            entity.getEquipment().setItemInMainHand(bow);

            Spider c = (Spider) EntityUtils.create(loc, ChatColor.WHITE + "Skeleton Spider", 900D, EntityType.SPIDER);
            c.addPassenger(entity);

            setEntityExperience(c, 420);
            setEntityExperience(entity, 553);
            setEntityDropTableNo(entity, 6);
            return entity;
        } else if (mobcode.equals("desert4")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.YELLOW + "Cavalry Skeleton", 2000D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(540D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("desertsword");
            entity.getEquipment().setItemInMainHand(sword);

            Spider c = (Spider) EntityUtils.create(loc, ChatColor.WHITE + "Skeleton Spider", 900D, EntityType.SPIDER);
            c.addPassenger(entity);

            setEntityExperience(c, 420);
            setEntityExperience(entity, 553);
            setEntityDropTableNo(entity, 6);
            return entity;
        } else if (mobcode.equals("desert5")) {
            CaveSpider entity = (CaveSpider) EntityUtils.create(loc, ChatColor.YELLOW + "Ghost Spider", 2200D, EntityType.CAVE_SPIDER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(520D);
            PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(invis);
            PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(glow);
            setEntityExperience(entity, 584);
            setEntityDropTableNo(entity, 6);
            return entity;
        } else if (mobcode.equals("desert6")) {
            Creeper entity = (Creeper) EntityUtils.create(loc, ChatColor.YELLOW + "Ghost Creeper", 2420D, EntityType.CREEPER);
            PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(invis);
            PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(glow);
            setEntityExperience(entity, 584);
            setEntityDropTableNo(entity, 6);
            return entity;
        } else if (mobcode.equals("swamp1")) {
            Witch entity = (Witch) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Witch", 3420D, EntityType.WITCH);
            setEntityExperience(entity, 764);
            setEntityDropTableNo(entity, 7);
            return entity;
        } else if (mobcode.equals("swamp2")) {
            CaveSpider entity = (CaveSpider) EntityUtils.create(loc, ChatColor.RED + "Spider", 3000D, EntityType.CAVE_SPIDER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(720D);
            setEntityExperience(entity, 720);
            setEntityDropTableNo(entity, 7);
            return entity;
        } else if (mobcode.equals("swamp3")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Jockey Goblin", 2400D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(640D);
            entity.getEquipment().clear();
            entity.setBaby(true);
            ItemStack sword = MonsterItems.getItem("sword1");
            entity.getEquipment().setItemInMainHand(sword);

            Chicken c = (Chicken) EntityUtils.create(loc, ChatColor.YELLOW + "Goblin Chicken", 1200D, EntityType.CHICKEN);
            c.addPassenger(entity);

            setEntityExperience(c, 553);
            setEntityExperience(entity, 720);
            setEntityDropTableNo(entity, 7);
            return entity;
        } else if (mobcode.equals("swamp4")) {
            Illusioner entity = (Illusioner) EntityUtils.create(loc, ChatColor.YELLOW + "Illusioner", 4500D, EntityType.ILLUSIONER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("swampbow");
            entity.getEquipment().setItemInMainHand(sword);
            setEntityExperience(entity, 900);
            setEntityDropTableNo(entity, 7);
            return entity;
        } else if (mobcode.equals("swamp5")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Goblin", 2800D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(740D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("sword1");
            entity.getEquipment().setItemInMainHand(sword);
            entity.setBaby(true);
            setEntityExperience(entity, 750);
            setEntityDropTableNo(entity, 7);
            return entity;
        } else if (mobcode.equals("swamp6")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Mage Goblin", 2400D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(700D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand1");
            ItemStack circlet = MonsterItems.getItem("circlet");
            entity.getEquipment().setItemInMainHand(wand);
            entity.getEquipment().setHelmet(circlet);
            entity.setBaby(true);
            setEntityExperience(entity, 750);
            setEntityDropTableNo(entity, 7);
            return entity;
        } else if (mobcode.equals("swamp7")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Shaman Goblin", 2400D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(700D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand2");
            ItemStack circlet = MonsterItems.getItem("circlet");
            entity.getEquipment().setItemInMainHand(wand);
            entity.getEquipment().setHelmet(circlet);
            entity.setBaby(true);
            setEntityExperience(entity, 750);
            setEntityDropTableNo(entity, 7);
            return entity;
        } else if (mobcode.equals("lava1")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Orc", 3750D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(920D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("sword1");
            entity.getEquipment().setItemInMainHand(sword);
            entity.setBaby(false);
            setEntityExperience(entity, 880);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (mobcode.equals("lava2")) {
            MagmaCube entity = (MagmaCube) EntityUtils.create(loc, ChatColor.RED + "Magma Cube", 3500D, EntityType.MAGMA_CUBE);
            setDamage(entity, 860);
            entity.setSize(3);
            setEntityExperience(entity, 880);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (mobcode.equals("lava3")) {
            WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.RED + "Wither Knight", 3000D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(820D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("frostsword");
            entity.getEquipment().setItemInMainHand(sword);
            setEntityExperience(entity, 880);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (mobcode.equals("lava4")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Jockey Orc", 3000D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(820D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("sword1");
            entity.getEquipment().setItemInMainHand(sword);
            entity.setBaby(false);

            Chicken c = (Chicken) EntityUtils.create(loc, ChatColor.YELLOW + "Orc Chicken", 1500D, EntityType.CHICKEN);
            c.addPassenger(entity);

            setEntityExperience(c, 720);
            setEntityExperience(entity, 860);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (mobcode.equals("lava5")) {
            Blaze entity = (Blaze) EntityUtils.create(loc, ChatColor.RED + "Blaze", 3500D, EntityType.BLAZE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
            setEntityExperience(entity, 880);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (mobcode.equals("lava6")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Gladiator Orc", 4200D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(960D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("sword1");
            ItemStack chest = MonsterItems.getItem("ironchest");
            ItemStack leg = MonsterItems.getItem("ironleg");
            ItemStack boot = MonsterItems.getItem("ironboots");
            ItemStack shield = MonsterItems.getItem("shield");
            entity.getEquipment().setChestplate(chest);
            entity.getEquipment().setLeggings(leg);
            entity.getEquipment().setBoots(boot);
            entity.getEquipment().setItemInMainHand(sword);
            entity.getEquipment().setItemInOffHand(shield);
            entity.setBaby(false);
            setEntityExperience(entity, 920);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (mobcode.equals("lava7")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Shaman Orc", 3000D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand2");
            ItemStack circlet = MonsterItems.getItem("circlet");
            entity.getEquipment().setItemInMainHand(wand);
            entity.getEquipment().setHelmet(circlet);
            entity.setBaby(false);
            setEntityExperience(entity, 920);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (mobcode.equals("lava8")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Mage Orc", 3000D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("lavawand");
            ItemStack circlet = MonsterItems.getItem("circlet");
            entity.getEquipment().setItemInMainHand(wand);
            entity.getEquipment().setHelmet(circlet);
            entity.setBaby(false);
            setEntityExperience(entity, 920);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (mobcode.equals("lava9")) {
            MagmaCube entity = (MagmaCube) EntityUtils.create(loc, ChatColor.RED + "Baby Magma", 2600D, EntityType.MAGMA_CUBE);
            setDamage(entity, 820);
            entity.setSize(2);
            setEntityExperience(entity, 820);
            setEntityDropTableNo(entity, 8);
            return entity;
        } else if (mobcode.equals("void1")) {
            Enderman entity = (Enderman) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Void Enderman", 6000D, EntityType.ENDERMAN);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
            setEntityExperience(entity, 1000);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (mobcode.equals("void2")) {
            IronGolem entity = (IronGolem) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Void Golem", 9000D, EntityType.IRON_GOLEM);
            setDamage(entity, 1200);
            setEntityExperience(entity, 1000);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (mobcode.equals("void3")) {
            Evoker entity = (Evoker) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Void Evoker", 5000D, EntityType.EVOKER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
            setEntityExperience(entity, 1000);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (mobcode.equals("void4")) {
            Shulker entity = (Shulker) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Void Shulker", 5000D, EntityType.SHULKER);
            setDamage(entity, 1200);
            setEntityExperience(entity, 1000);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (mobcode.equals("void5")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Slave Mage", 6000D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1400D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            ItemStack helmet = new ItemStack(Material.GOLDEN_HELMET);
            ItemStack chest = new ItemStack(Material.GOLDEN_CHESTPLATE);
            ItemStack leg = new ItemStack(Material.GOLDEN_LEGGINGS);
            ItemStack boots = new ItemStack(Material.GOLDEN_BOOTS);
            ItemStack wand = MonsterItems.getItem("voidwand");
            entity.getEquipment().clear();
            entity.getEquipment().setHelmet(helmet);
            entity.getEquipment().setChestplate(chest);
            entity.getEquipment().setLeggings(leg);
            entity.getEquipment().setBoots(boots);
            entity.getEquipment().setItemInMainHand(wand);
            setEntityExperience(entity, 1200);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (mobcode.equals("void6")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Slave Knight", 8000D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1400D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
            ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
            ItemStack sword = MonsterItems.getItem("voidsword");
            ItemStack shield = MonsterItems.getItem("voidshield");
            entity.getEquipment().clear();
            entity.getEquipment().setHelmet(helmet);
            entity.getEquipment().setChestplate(chest);
            entity.getEquipment().setLeggings(leg);
            entity.getEquipment().setBoots(boots);
            entity.getEquipment().setItemInMainHand(sword);
            entity.getEquipment().setItemInOffHand(shield);
            setEntityExperience(entity, 1200);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (mobcode.equals("void7")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Slave Archer", 6000D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1400D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
            ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            ItemStack leg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
            ItemStack bow = MonsterItems.getItem("voidbow");
            entity.getEquipment().clear();
            entity.getEquipment().setHelmet(helmet);
            entity.getEquipment().setChestplate(chest);
            entity.getEquipment().setLeggings(leg);
            entity.getEquipment().setBoots(boots);
            entity.getEquipment().setItemInMainHand(bow);
            setEntityExperience(entity, 1200);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (mobcode.equals("void8")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Slave Warrior", 7000D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1700D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            ItemStack helmet = new ItemStack(Material.IRON_HELMET);
            ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
            ItemStack leg = new ItemStack(Material.IRON_LEGGINGS);
            ItemStack boots = new ItemStack(Material.IRON_BOOTS);
            ItemStack axe = MonsterItems.getItem("voidaxe");
            entity.getEquipment().clear();
            entity.getEquipment().setHelmet(helmet);
            entity.getEquipment().setChestplate(chest);
            entity.getEquipment().setLeggings(leg);
            entity.getEquipment().setBoots(boots);
            entity.getEquipment().setItemInMainHand(axe);
            setEntityExperience(entity, 1200);
            setEntityDropTableNo(entity, 9);
            return entity;
        } else if (mobcode.equals("slimeboss")) {
            Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GOLD + "King Slime", 600D, EntityType.SLIME);
            entity.setSize(6);
            setDamage(entity, 24);
            return entity;
        } else if (mobcode.equals("zombieboss")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Leader", 2000D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(60D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("sword");
            entity.getEquipment().setItemInMainHand(sword);
            entity.getEquipment().setItemInOffHand(sword);
            ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
            ItemStack leg = new ItemStack(Material.IRON_LEGGINGS);
            ItemStack boots = new ItemStack(Material.IRON_BOOTS);
            entity.getEquipment().setChestplate(chest);
            entity.getEquipment().setLeggings(leg);
            entity.getEquipment().setBoots(boots);
            return entity;
        } else if (mobcode.equals("magicboss")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Dark Magic Master", 5000D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(150D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.18D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand1");
            entity.getEquipment().setItemInMainHand(wand);
            ItemStack circlet = MonsterItems.getItem("circlet");
            entity.getEquipment().setHelmet(circlet);
            ItemStack chest = new ItemStack(Material.GOLDEN_CHESTPLATE);
            ItemStack leg = new ItemStack(Material.GOLDEN_LEGGINGS);
            ItemStack boots = new ItemStack(Material.GOLDEN_BOOTS);
            entity.getEquipment().setChestplate(chest);
            entity.getEquipment().setLeggings(leg);
            entity.getEquipment().setBoots(boots);
            return entity;
        } else if (mobcode.equals("sugarboss")) {
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
        } else if (mobcode.equals("pirateboss")) {
            WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Captain Barbaros", 20000D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(600D);
            ItemStack sword = MonsterItems.getItem("katana");
            ItemStack helmet = MonsterItems.getItem("piratehat");
            ItemStack pistol = MonsterItems.getItem("piratepistol");
            entity.getEquipment().clear();
            entity.getEquipment().setItemInMainHand(pistol);
            entity.getEquipment().setItemInOffHand(sword);
            entity.getEquipment().setHelmet(helmet);
            return entity;
        } else if (mobcode.equals("iceboss")) {
            Vindicator entity = (Vindicator) EntityUtils.create(loc, ChatColor.AQUA + "Makvurn", 32500D, EntityType.VINDICATOR);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(900D);
            ItemStack axe = MonsterItems.getItem("frostaxe");
            entity.getEquipment().clear();
            entity.getEquipment().setItemInMainHand(axe);
            return entity;
        } else if (mobcode.equals("desertboss")) {
            Vindicator entity = (Vindicator) EntityUtils.create(loc, ChatColor.YELLOW + "Selket", 45000D, EntityType.HUSK);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1200D);
            entity.getEquipment().clear();
            return entity;
        } else if (mobcode.equals("swampboss")) {
            Illusioner entity = (Illusioner) EntityUtils.create(loc, ChatColor.BLUE + "Mage Woz", 60000D, EntityType.ILLUSIONER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1800D);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("swampbow");
            entity.getEquipment().setItemInMainHand(bow);
            return entity;
        } else if (mobcode.equals("lavaboss")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.RED + "War Chief Drogoth", 75000D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2500D);
            entity.setBaby(false);
            ItemStack sword = MonsterItems.getItem("lavaboss");
            ItemStack shield = MonsterItems.getItem("lavashield");
            ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemStack leg = new ItemStack(Material.DIAMOND_LEGGINGS);
            ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS);
            entity.getEquipment().clear();
            entity.getEquipment().setItemInMainHand(sword);
            entity.getEquipment().setChestplate(chest);
            entity.getEquipment().setLeggings(leg);
            entity.getEquipment().setBoots(boot);
            entity.getEquipment().setItemInOffHand(shield);
            return entity;
        } else if (mobcode.equals("darkboss")) {
            Wither entity = (Wither) EntityUtils.create(loc, ChatColor.DARK_RED + "Malephar", 100000D, EntityType.WITHER);
            //4000 damage
            return entity;
        }
        return null;
    }

    private static void setEntityExperience(Entity entity, int experience) {
        PersistentDataContainerUtil.putInteger("experience", experience, entity);
    }

    private static void setEntityDropTableNo(Entity entity, int dropTableNumber) {
        PersistentDataContainerUtil.putInteger("dropTableNumber", dropTableNumber, entity);
    }


    /**
     * Use this is entity doesn't have the GENERIC_ATTACK_DAMAGE attribute but can attack
     * For example slimes and killer rabbits
     *
     * @param entity
     * @param customDamage
     */
    private static void setDamage(Entity entity, int customDamage) {
        PersistentDataContainerUtil.putInteger("customDamage", customDamage, entity);
    }
}
