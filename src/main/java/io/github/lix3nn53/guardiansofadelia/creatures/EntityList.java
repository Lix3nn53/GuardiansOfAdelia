package io.github.lix3nn53.guardiansofadelia.creatures;

import io.github.lix3nn53.guardiansofadelia.Items.list.MonsterItems;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
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

public class EntityList {

    public static Entity getMob(Location loc, String mobcode) {
        if (mobcode.equals("hobbit")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Hobbit", 20000D, EntityType.VILLAGER);
            entity.setBaby();
            entity.setAgeLock(true);
            double d = Math.random();
            if (d < 0.2) {
                entity.setProfession(Villager.Profession.BLACKSMITH);
            } else if (d < 0.4) {
                entity.setProfession(Villager.Profession.BUTCHER);
            } else if (d < 0.6) {
                entity.setProfession(Villager.Profession.FARMER);
            } else if (d < 0.8) {
                entity.setProfession(Villager.Profession.LIBRARIAN);
            } else if (d < 1) {
                entity.setProfession(Villager.Profession.PRIEST);
            }
            return entity;
        } else if (mobcode.equals("villager1")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Roumen Villager", 40000D, EntityType.VILLAGER);
            entity.setAdult();
            double d = Math.random();
            if (d < 0.2) {
                entity.setProfession(Villager.Profession.BLACKSMITH);
            } else if (d < 0.4) {
                entity.setProfession(Villager.Profession.BUTCHER);
            } else if (d < 0.6) {
                entity.setProfession(Villager.Profession.FARMER);
            } else if (d < 0.8) {
                entity.setProfession(Villager.Profession.LIBRARIAN);
            } else if (d < 1) {
                entity.setProfession(Villager.Profession.PRIEST);
            }
            return entity;
        } else if (mobcode.equals("villager15")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Veloa Villager", 60000D, EntityType.VILLAGER);
            entity.setAdult();
            double d = Math.random();
            if (d < 0.2) {
                entity.setProfession(Villager.Profession.BLACKSMITH);
            } else if (d < 0.4) {
                entity.setProfession(Villager.Profession.BUTCHER);
            } else if (d < 0.6) {
                entity.setProfession(Villager.Profession.FARMER);
            } else if (d < 0.8) {
                entity.setProfession(Villager.Profession.LIBRARIAN);
            } else if (d < 1) {
                entity.setProfession(Villager.Profession.PRIEST);
            }
            return entity;
        } else if (mobcode.equals("villager2")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Elderine Villager", 80000D, EntityType.VILLAGER);
            entity.setAdult();
            double d = Math.random();
            if (d < 0.2) {
                entity.setProfession(Villager.Profession.BLACKSMITH);
            } else if (d < 0.4) {
                entity.setProfession(Villager.Profession.BUTCHER);
            } else if (d < 0.6) {
                entity.setProfession(Villager.Profession.FARMER);
            } else if (d < 0.8) {
                entity.setProfession(Villager.Profession.LIBRARIAN);
            } else if (d < 1) {
                entity.setProfession(Villager.Profession.PRIEST);
            }
            return entity;
        } else if (mobcode.equals("villager3")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Uruga Villager", 120000D, EntityType.VILLAGER);
            entity.setAdult();
            double d = Math.random();
            if (d < 0.2) {
                entity.setProfession(Villager.Profession.BLACKSMITH);
            } else if (d < 0.4) {
                entity.setProfession(Villager.Profession.BUTCHER);
            } else if (d < 0.6) {
                entity.setProfession(Villager.Profession.FARMER);
            } else if (d < 0.8) {
                entity.setProfession(Villager.Profession.LIBRARIAN);
            } else if (d < 1) {
                entity.setProfession(Villager.Profession.PRIEST);
            }
            return entity;
        } else if (mobcode.equals("villager4")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.GREEN + "Aberstol Villager", 200000D, EntityType.VILLAGER);
            entity.setAdult();
            double d = Math.random();
            if (d < 0.2) {
                entity.setProfession(Villager.Profession.BLACKSMITH);
            } else if (d < 0.4) {
                entity.setProfession(Villager.Profession.BUTCHER);
            } else if (d < 0.6) {
                entity.setProfession(Villager.Profession.FARMER);
            } else if (d < 0.8) {
                entity.setProfession(Villager.Profession.LIBRARIAN);
            } else if (d < 1) {
                entity.setProfession(Villager.Profession.PRIEST);
            }
            return entity;
        } else if (mobcode.equals("sailor")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.AQUA + "Sailor", 100000D, EntityType.VILLAGER);
            entity.setAdult();
            double d = Math.random();
            if (d < 0.2) {
                entity.setProfession(Villager.Profession.BLACKSMITH);
            } else if (d < 0.4) {
                entity.setProfession(Villager.Profession.BUTCHER);
            } else if (d < 0.6) {
                entity.setProfession(Villager.Profession.FARMER);
            } else if (d < 0.8) {
                entity.setProfession(Villager.Profession.LIBRARIAN);
            } else if (d < 1) {
                entity.setProfession(Villager.Profession.PRIEST);
            }
            return entity;
        } else if (mobcode.equals("farmer")) {
            Villager entity = (Villager) EntityUtils.create(loc, ChatColor.YELLOW + "Farmer", 80000D, EntityType.VILLAGER);
            entity.setAdult();
            double d = Math.random();
            if (d < 0.2) {
                entity.setProfession(Villager.Profession.BLACKSMITH);
            } else if (d < 0.4) {
                entity.setProfession(Villager.Profession.BUTCHER);
            } else if (d < 0.6) {
                entity.setProfession(Villager.Profession.FARMER);
            } else if (d < 0.8) {
                entity.setProfession(Villager.Profession.LIBRARIAN);
            } else if (d < 1) {
                entity.setProfession(Villager.Profession.PRIEST);
            }
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
            Silverfish entity = (Silverfish) EntityUtils.create(loc, ChatColor.GREEN + "Wild Lizard", 50D, EntityType.SILVERFISH);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4D);
            return entity;
        } else if (mobcode.equals("beginner2")) {
            Silverfish entity = (Silverfish) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Poisonous Lizard", 70D, EntityType.SILVERFISH);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4D);
            return entity;
        } else if (mobcode.equals("beginner3")) {
            Endermite entity = (Endermite) EntityUtils.create(loc, ChatColor.GREEN + "Wild Turtle", 60D, EntityType.ENDERMITE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4D);
            entity.setSilent(true);
            return entity;
        } else if (mobcode.equals("beginner4")) {
            Endermite entity = (Endermite) EntityUtils.create(loc, ChatColor.AQUA + "Water Turtle", 70D, EntityType.ENDERMITE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2D);
            entity.setSilent(true);
            return entity;
        } else if (mobcode.equals("beginner5")) {
            Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GREEN + "Baby Slime", 100D, EntityType.SLIME);
            entity.setSize(2);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2D);
            return entity;
        } else if (mobcode.equals("beginner6")) {
            Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GREEN + "Sticky Slime", 120D, EntityType.SLIME);
            entity.setSize(3);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2D);
            return entity;
        } else if (mobcode.equals("zombie1")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie", 180D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(16D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            return entity;
        } else if (mobcode.equals("zombie2")) {
            ZombieVillager entity = (ZombieVillager) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Villager", 200D, EntityType.ZOMBIE_VILLAGER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            ItemStack axe = MonsterItems.getItem("axe1");
            entity.getEquipment().setItemInMainHand(axe);
            return entity;
        } else if (mobcode.equals("zombie3")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Splitter Zombie", 180D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(5D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            return entity;
        } else if (mobcode.equals("zombie4")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Shaman Zombie", 200D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(5D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand1");
            entity.getEquipment().setItemInMainHand(wand);
            ItemStack circlet = MonsterItems.getItem("circlet1");
            entity.getEquipment().setHelmet(circlet);
            return entity;
        } else if (mobcode.equals("zombie5")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zomtank", 240D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(18D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.setBaby(false);
            entity.getEquipment().clear();
            ItemStack shield = MonsterItems.getItem("shield1");
            entity.getEquipment().setItemInOffHand(shield);
            return entity;
        } else if (mobcode.equals("forest1")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Archer Skeleton", 300D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("bow1");
            entity.getEquipment().setItemInMainHand(bow);
            return entity;
        } else if (mobcode.equals("forest2")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Warrior Skeleton", 340D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(30D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("sword1");
            entity.getEquipment().setItemInMainHand(sword);
            return entity;
        } else if (mobcode.equals("forest3")) {
            Rabbit entity = (Rabbit) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Cursed Rabbit", 200D, EntityType.RABBIT);
            entity.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
            entity.getEquipment().clear();
            return entity;
        } else if (mobcode.equals("forest4")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Hunter Skeleton", 400D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("bow2");
            entity.getEquipment().setItemInMainHand(bow);
            ItemStack helmet = MonsterItems.getItem("helmet1");
            entity.getEquipment().setHelmet(helmet);
            return entity;
        } else if (mobcode.equals("forest5")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.WHITE + "Mage Skeleton", 420D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand2");
            entity.getEquipment().setItemInMainHand(wand);
            ItemStack helmet = MonsterItems.getItem("helmet1");
            entity.getEquipment().setHelmet(helmet);
            return entity;
        } else if (mobcode.equals("sugar1")) {
            Rabbit entity = (Rabbit) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Strawberry Jellybeans", 340D, EntityType.RABBIT);
            entity.setRabbitType(Rabbit.Type.SALT_AND_PEPPER);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4D);
            return entity;
        } else if (mobcode.equals("sugar2")) {
            MushroomCow entity = (MushroomCow) EntityUtils.create(loc, ChatColor.YELLOW + "Chocolate Cupcake", 530D, EntityType.MUSHROOM_COW);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            return entity;
        } else if (mobcode.equals("sugar3")) {
            Creeper entity = (Creeper) EntityUtils.create(loc, ChatColor.AQUA + "Popping Rainbow", 640D, EntityType.CREEPER);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            return entity;
        } else if (mobcode.equals("sugar4")) {
            Snowman entity = (Snowman) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "IceCream", 720D, EntityType.SNOWMAN);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4D);
            entity.setDerp(true);
            return entity;
        } else if (mobcode.equals("pirate1")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Shooter Pirate", 780D, EntityType.SKELETON);
            entity.getEquipment().clear();
            ItemStack pistol = MonsterItems.getItem("piratepistol");
            entity.getEquipment().setItemInMainHand(pistol);
            ItemStack helmet = MonsterItems.getItem("piratehat");
            entity.getEquipment().setHelmet(helmet);
            return entity;
        } else if (mobcode.equals("pirate2")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Fighter Pirate", 820D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(64D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("piratesword");
            entity.getEquipment().setItemInMainHand(sword);
            ItemStack helmet = MonsterItems.getItem("piratehat");
            entity.getEquipment().setHelmet(helmet);
            return entity;
        } else if (mobcode.equals("pirate3")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Sharpshooter Pirate", 900D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(64D);
            entity.getEquipment().clear();
            ItemStack pistol = MonsterItems.getItem("piratepistol");
            entity.getEquipment().setItemInMainHand(pistol);
            ItemStack helmet = MonsterItems.getItem("piratehat");
            entity.getEquipment().setHelmet(helmet);
            return entity;
        } else if (mobcode.equals("pirate4")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Duel Master Pirate", 1000D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(64D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("katana");
            entity.getEquipment().setItemInMainHand(sword);
            ItemStack helmet = MonsterItems.getItem("piratehat");
            entity.getEquipment().setHelmet(helmet);
            return entity;
        } else if (mobcode.equals("frost1")) {
            PolarBear entity = (PolarBear) EntityUtils.create(loc, ChatColor.AQUA + "PolarBear", 1400D, EntityType.POLAR_BEAR);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(82D);
            entity.setAdult();
            return entity;
        } else if (mobcode.equals("frost2")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Archer Ninja", 1250D, EntityType.STRAY);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("frostbow");
            entity.getEquipment().setItemInMainHand(bow);
            return entity;
        } else if (mobcode.equals("frost3")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Jockey Zombie", 700D, EntityType.ZOMBIE);
            entity.getEquipment().clear();
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(74D);
            entity.setBaby(true);

            Chicken c = (Chicken) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Chicken", 350D, EntityType.CHICKEN);
            c.addPassenger(entity);

            return entity;
        } else if (mobcode.equals("frost4")) {
            Vindicator entity = (Vindicator) EntityUtils.create(loc, ChatColor.AQUA + "Frozen Timberman", 1700D, EntityType.VINDICATOR);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(91D);
            entity.getEquipment().clear();
            ItemStack axe = MonsterItems.getItem("frostaxe");
            entity.getEquipment().setItemInMainHand(axe);
            return entity;
        } else if (mobcode.equals("frost5")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Star Ninja", 1250D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2D);
            entity.getEquipment().clear();
            ItemStack star = MonsterItems.getItem("ninjastar");
            entity.getEquipment().setItemInMainHand(star);
            return entity;
        } else if (mobcode.equals("frost6")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.RED + "Ninja", 1250D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(81D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3D);
            entity.getEquipment().clear();
            ItemStack dagger = MonsterItems.getItem("frostdagger");
            entity.getEquipment().setItemInMainHand(dagger);
            entity.getEquipment().setItemInOffHand(dagger);
            return entity;
        } else if (mobcode.equals("desert1")) {
            Husk entity = (Husk) EntityUtils.create(loc, ChatColor.YELLOW + "Mummy", 1700D, EntityType.HUSK);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(102D);
            entity.getEquipment().clear();
            return entity;
        } else if (mobcode.equals("desert2")) {
            Husk entity = (Husk) EntityUtils.create(loc, ChatColor.YELLOW + "Ghost Mummy", 1940D, EntityType.HUSK);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(108D);
            entity.getEquipment().clear();
            PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(invis, false);
            PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(glow, false);
            return entity;
        } else if (mobcode.equals("desert3")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.YELLOW + "Cavalry Archer Skeleton", 1420D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(108D);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("desertbow");
            entity.getEquipment().setItemInMainHand(bow);

            Spider c = (Spider) EntityUtils.create(loc, ChatColor.WHITE + "Skeleton Spider", 450D, EntityType.SPIDER);
            c.addPassenger(entity);

            return entity;
        } else if (mobcode.equals("desert4")) {
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.YELLOW + "Cavalry Skeleton", 1540D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(114D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("desertsword");
            entity.getEquipment().setItemInMainHand(sword);

            Spider c = (Spider) EntityUtils.create(loc, ChatColor.WHITE + "Skeleton Spider", 450D, EntityType.SPIDER);
            c.addPassenger(entity);

            return entity;
        } else if (mobcode.equals("desert5")) {
            CaveSpider entity = (CaveSpider) EntityUtils.create(loc, ChatColor.YELLOW + "Ghost Spider", 1640D, EntityType.CAVE_SPIDER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(120D);
            PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(invis, false);
            PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(glow, false);
            return entity;
        } else if (mobcode.equals("desert6")) {
            Creeper entity = (Creeper) EntityUtils.create(loc, ChatColor.YELLOW + "Ghost Creeper", 1720D, EntityType.CREEPER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(120D);
            PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(invis, false);
            PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1);
            entity.addPotionEffect(glow, false);
            return entity;
        } else if (mobcode.equals("swamp1")) {
            Witch entity = (Witch) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Witch", 2750D, EntityType.WITCH);
            return entity;
        } else if (mobcode.equals("swamp2")) {
            CaveSpider entity = (CaveSpider) EntityUtils.create(loc, ChatColor.RED + "Spider", 1820D, EntityType.CAVE_SPIDER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(132D);
            return entity;
        } else if (mobcode.equals("swamp3")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Jockey Goblin", 1420D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(120D);
            entity.getEquipment().clear();
            entity.setBaby(true);
            ItemStack sword = MonsterItems.getItem("sword1");
            entity.getEquipment().setItemInMainHand(sword);

            Chicken c = (Chicken) EntityUtils.create(loc, ChatColor.YELLOW + "Goblin Chicken", 620D, EntityType.CHICKEN);
            c.addPassenger(entity);

            return entity;
        } else if (mobcode.equals("swamp4")) {
            Illusioner entity = (Illusioner) EntityUtils.create(loc, ChatColor.YELLOW + "Illusioner", 4530D, EntityType.ILLUSIONER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(134D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("swampbow");
            entity.getEquipment().setItemInMainHand(sword);
            return entity;
        } else if (mobcode.equals("swamp5")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Goblin", 2040D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(140D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("sword1");
            entity.getEquipment().setItemInMainHand(sword);
            entity.setBaby(true);
            return entity;
        } else if (mobcode.equals("swamp6")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Mage Goblin", 2210D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(100D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand1");
            ItemStack circlet = MonsterItems.getItem("circlet");
            entity.getEquipment().setItemInMainHand(wand);
            entity.getEquipment().setHelmet(circlet);
            entity.setBaby(true);
            return entity;
        } else if (mobcode.equals("swamp7")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Shaman Goblin", 2210D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(100D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand2");
            ItemStack circlet = MonsterItems.getItem("circlet");
            entity.getEquipment().setItemInMainHand(wand);
            entity.getEquipment().setHelmet(circlet);
            entity.setBaby(true);
            return entity;
        } else if (mobcode.equals("lava1")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Orc", 3420D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(224D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("sword1");
            entity.getEquipment().setItemInMainHand(sword);
            entity.setBaby(false);
            return entity;
        } else if (mobcode.equals("lava2")) {
            MagmaCube entity = (MagmaCube) EntityUtils.create(loc, ChatColor.RED + "Magma Cube", 2940D, EntityType.MAGMA_CUBE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(224D);
            entity.setSize(3);
            return entity;
        } else if (mobcode.equals("lava3")) {
            WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.RED + "Wither Knight", 2760D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(272D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("frostsword");
            entity.getEquipment().setItemInMainHand(sword);
            return entity;
        } else if (mobcode.equals("lava4")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Jockey Orc", 2640D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(208D);
            entity.getEquipment().clear();
            ItemStack sword = MonsterItems.getItem("sword1");
            entity.getEquipment().setItemInMainHand(sword);
            entity.setBaby(false);

            Chicken c = (Chicken) EntityUtils.create(loc, ChatColor.YELLOW + "Orc Chicken", 780D, EntityType.CHICKEN);
            c.addPassenger(entity);

            return entity;
        } else if (mobcode.equals("lava5")) {
            Blaze entity = (Blaze) EntityUtils.create(loc, ChatColor.RED + "Blaze", 3080D, EntityType.BLAZE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(253D);
            return entity;
        } else if (mobcode.equals("lava6")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Gladiator Orc", 4200D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(242D);
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
            return entity;
        } else if (mobcode.equals("lava7")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Shaman Orc", 3720D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(200D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("wand2");
            ItemStack circlet = MonsterItems.getItem("circlet");
            entity.getEquipment().setItemInMainHand(wand);
            entity.getEquipment().setHelmet(circlet);
            entity.setBaby(false);
            return entity;
        } else if (mobcode.equals("lava8")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Mage Orc", 3240D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(200D);
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15D);
            entity.getEquipment().clear();
            ItemStack wand = MonsterItems.getItem("lavawand");
            ItemStack circlet = MonsterItems.getItem("circlet");
            entity.getEquipment().setItemInMainHand(wand);
            entity.getEquipment().setHelmet(circlet);
            entity.setBaby(false);
            return entity;
        } else if (mobcode.equals("lava9")) {
            MagmaCube entity = (MagmaCube) EntityUtils.create(loc, ChatColor.RED + "Baby Magma", 2420D, EntityType.MAGMA_CUBE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(200D);
            entity.setSize(2);
            return entity;
        } else if (mobcode.equals("void1")) {
            Enderman entity = (Enderman) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Void Keeper", 7420D, EntityType.ENDERMAN);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(341D);
            return entity;
        } else if (mobcode.equals("void2")) {
            IronGolem entity = (IronGolem) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Void Giant", 10420D, EntityType.IRON_GOLEM);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(341D);
            return entity;
        } else if (mobcode.equals("void3")) {
            Evoker entity = (Evoker) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Void Mage", 9260D, EntityType.EVOKER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(100D);
            return entity;
        } else if (mobcode.equals("void4")) {
            Shulker entity = (Shulker) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Void Box", 5200D, EntityType.SHULKER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(100D);
            return entity;
        } else if (mobcode.equals("void5")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Slave Mage", 7260D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(100D);
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
            return entity;
        } else if (mobcode.equals("void6")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Slave Knight", 9280D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(281D);
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
            return entity;
        } else if (mobcode.equals("void7")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Slave Archer", 7260D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(100D);
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
            return entity;
        } else if (mobcode.equals("void8")) {
            Stray entity = (Stray) EntityUtils.create(loc, ChatColor.LIGHT_PURPLE + "Slave Warrior", 8400D, EntityType.STRAY);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(342D);
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
            return entity;
        } else if (mobcode.equals("slimeboss")) {
            Slime entity = (Slime) EntityUtils.create(loc, ChatColor.GOLD + "King Slime", 840D, EntityType.SLIME);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10D);
            entity.setSize(6);
            return entity;
        } else if (mobcode.equals("zombieboss")) {
            Zombie entity = (Zombie) EntityUtils.create(loc, ChatColor.DARK_GREEN + "Zombie Leader", 1400D, EntityType.ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(18D);
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
            Skeleton entity = (Skeleton) EntityUtils.create(loc, ChatColor.DARK_PURPLE + "Dark Magic Master", 2120D, EntityType.SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(8D);
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
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.YELLOW + "Mad Cook", 3240D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(242D);
            entity.setBaby(false);
            entity.getEquipment().clear();

            ItemStack snowball = new ItemStack(Material.SNOWBALL);
            ItemStack cookie = new ItemStack(Material.COOKIE);

            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta sim = (SkullMeta) head.getItemMeta();
            sim.setOwningPlayer(Bukkit.getPlayer("SheepKid"));
            sim.setUnbreakable(true);
            sim.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
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
            WitherSkeleton entity = (WitherSkeleton) EntityUtils.create(loc, ChatColor.DARK_AQUA + "Captain Barbaros", 4840D, EntityType.WITHER_SKELETON);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(78D);
            ItemStack sword = MonsterItems.getItem("katana");
            ItemStack helmet = MonsterItems.getItem("piratehat");
            ItemStack pistol = MonsterItems.getItem("piratepistol");
            entity.getEquipment().clear();
            entity.getEquipment().setItemInMainHand(pistol);
            entity.getEquipment().setItemInOffHand(sword);
            entity.getEquipment().setHelmet(helmet);
            return entity;
        } else if (mobcode.equals("iceboss")) {
            Vindicator entity = (Vindicator) EntityUtils.create(loc, ChatColor.AQUA + "Makvurn", 8640D, EntityType.VINDICATOR);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(91D);
            ItemStack axe = MonsterItems.getItem("frostaxe");
            entity.getEquipment().clear();
            entity.getEquipment().setItemInMainHand(axe);
            return entity;
        } else if (mobcode.equals("desertboss")) {
            Vindicator entity = (Vindicator) EntityUtils.create(loc, ChatColor.YELLOW + "Selket", 12800D, EntityType.HUSK);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(102D);
            entity.getEquipment().clear();
            return entity;
        } else if (mobcode.equals("swampboss")) {
            Illusioner entity = (Illusioner) EntityUtils.create(loc, ChatColor.BLUE + "Mage Woz", 18720D, EntityType.ILLUSIONER);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(134D);
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("swampbow");
            entity.getEquipment().setItemInMainHand(bow);
            return entity;
        } else if (mobcode.equals("lavaboss")) {
            PigZombie entity = (PigZombie) EntityUtils.create(loc, ChatColor.RED + "War Chief Drogoth", 44680D, EntityType.PIG_ZOMBIE);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(242D);
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
            entity.getEquipment().clear();
            ItemStack bow = MonsterItems.getItem("swampbow");
            entity.getEquipment().setItemInMainHand(bow);
            return entity;
        }
        return null;
    }
}
