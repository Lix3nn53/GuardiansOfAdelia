package io.github.lix3nn53.guardiansofadelia.creatures;

import io.github.lix3nn53.guardiansofadelia.Items.list.MonsterItem;
import io.github.lix3nn53.guardiansofadelia.creatures.entitySkills.EntitySkillSet;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;

public class AdeliaEntity {

    String adeliaEntityKey;

    EntityType entityType;
    String name;

    MonsterItem mainHand;
    MonsterItem offHand;
    MonsterItem helmet;
    MonsterItem chestplate;
    MonsterItem leggings;
    MonsterItem boots;

    int damage;
    int maxHealth;
    int experience;
    int dropTableNumber = -1;

    double movementSpeed;

    //Slime
    int size;

    //Ageable
    boolean isBaby;

    //Villager
    boolean isVillagerProfessionRandom;
    Villager.Profession villagerProfession;

    //Disguise
    AdeliaEntityDisguise disguise;

    //Mount
    EntityType mountType;
    String mountName;

    //Potion effects
    List<PotionEffectType> potionEffectTypeList;

    public AdeliaEntity(String adeliaEntityKey, EntityType entityType, String name, MonsterItem mainHand, MonsterItem offHand,
                        MonsterItem helmet, MonsterItem chestplate, MonsterItem leggings, MonsterItem boots, int damage, int maxHealth,
                        int experience, int dropTableNumber, double movementSpeed, int size, boolean isBaby, boolean isVillagerProfessionRandom,
                        Villager.Profession villagerProfession, AdeliaEntityDisguise disguise, EntityType mountType, String mountName,
                        List<PotionEffectType> potionEffectTypeList) {
        this.adeliaEntityKey = adeliaEntityKey;
        this.entityType = entityType;
        this.name = name;
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.damage = damage;
        this.maxHealth = maxHealth;
        this.experience = experience;
        this.dropTableNumber = dropTableNumber;
        this.movementSpeed = movementSpeed;
        this.size = size;
        this.isBaby = isBaby;
        this.isVillagerProfessionRandom = isVillagerProfessionRandom;
        this.villagerProfession = villagerProfession;
        this.disguise = disguise;
        this.mountType = mountType;
        this.mountName = mountName;
        this.potionEffectTypeList = potionEffectTypeList;
    }

    /**
     * Use this instead of minecraft attack attribute of entity
     *
     * @param entity
     * @param customDamage
     */
    public static void setCustomDamage(Entity entity, int customDamage) {
        PersistentDataContainerUtil.putInteger("customDamage", customDamage, entity);
    }

    private static void setEntityExperience(Entity entity, int experience) {
        PersistentDataContainerUtil.putInteger("experience", experience, entity);
    }

    private static void setEntityDropTableNo(Entity entity, int dropTableNumber) {
        PersistentDataContainerUtil.putInteger("dropTableNumber", dropTableNumber, entity);
    }

    public LivingEntity getMob(Location loc) {
        LivingEntity livingEntity = EntityUtils.create(loc, this.name, this.maxHealth, this.entityType);
        livingEntity.setCustomNameVisible(true);

        setCustomDamage(livingEntity, this.damage);
        setEntityExperience(livingEntity, this.experience);
        if (dropTableNumber >= 0) setEntityDropTableNo(livingEntity, this.dropTableNumber);

        EntityEquipment equipment = livingEntity.getEquipment();
        if (equipment != null) {
            if (this.mainHand != null) {
                ItemStack item = this.mainHand.getItem(this.damage);
                equipment.setItemInMainHand(item);
            }

            if (this.offHand != null) {
                ItemStack item = this.offHand.getItem(this.damage);
                equipment.setItemInOffHand(item);
            }

            if (this.helmet != null) {
                ItemStack item = this.helmet.getItem(this.damage);
                equipment.setHelmet(item);
            }

            if (this.chestplate != null) {
                ItemStack item = this.chestplate.getItem(this.damage);
                equipment.setChestplate(item);
            }

            if (this.leggings != null) {
                ItemStack item = this.leggings.getItem(this.damage);
                equipment.setLeggings(item);
            }

            if (this.boots != null) {
                ItemStack item = this.boots.getItem(this.damage);
                equipment.setBoots(item);
            }
        }

        if (livingEntity instanceof Ageable) {
            Ageable ageable = (Ageable) livingEntity;
            if (this.isBaby) {
                ageable.setBaby();
                ageable.setAgeLock(true);
            }
            ageable.setBreed(false);
        }

        if (livingEntity instanceof Villager) {
            Villager villager = (Villager) livingEntity;

            if (isVillagerProfessionRandom) {
                Villager.Profession[] values = Villager.Profession.values();
                int i = new Random().nextInt(values.length);
                Villager.Profession value = values[i];
                villager.setProfession(value);
            } else if (this.villagerProfession != null) {
                villager.setProfession(this.villagerProfession);
            }
        }

        if (livingEntity instanceof Slime) {
            Slime slime = (Slime) livingEntity;

            slime.setSize(this.size);
            livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.maxHealth);
        } else if (livingEntity instanceof Phantom) {
            Phantom phantom = (Phantom) livingEntity;

            phantom.setSize(this.size);
            livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.maxHealth);
        }

        if (this.movementSpeed > 0) {
            livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(this.movementSpeed);
        }

        livingEntity.setHealth(this.maxHealth);

        if (this.disguise != null) {
            MobDisguise mobDisguise = this.disguise.getDisguise(this.maxHealth);
            DisguiseAPI.disguiseToAll(livingEntity, mobDisguise);
        }

        if (this.mountType != null) {
            LivingEntity mount = EntityUtils.create(loc, this.mountName, this.maxHealth / 4D, this.mountType);
            mount.addPassenger(livingEntity);
            setEntityExperience(mount, this.experience / 2);
        }

        startSkillLoop(livingEntity);

        return livingEntity;
    }

    private void startSkillLoop(LivingEntity livingEntity) {
        EntitySkillSet skillSet = AdeliaEntityManager.getSkillSet(this.adeliaEntityKey);

        if (skillSet == null) return;

        skillSet.startSkillLoop(livingEntity);
    }

    public String getAdeliaEntityKey() {
        return adeliaEntityKey;
    }

    public String getName() {
        return name;
    }
}
