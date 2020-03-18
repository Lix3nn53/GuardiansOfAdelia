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

        /*List<SkillComponent> skills = new ArrayList<>();
        List<Integer> skillLevels = new ArrayList<>();
        long cooldown = 150;

        if (this == AdeliaEntity.LIZARD_POISONOUS) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(EntitySkillComponents.getComponentPotionEffectMechanic(PotionEffectType.POISON, 50, 1));
            SkillComponent trigger = EntitySkill.getSkillProjectileParticle(ChatColor.DARK_GREEN + "Shoosh!", 40, children, GoaSound.SKILL_POISON_SLASH, Color.LIME, 1, 2.7);
            skills.add(trigger);
            skillLevels.add(1);
        } else if (this == AdeliaEntity.SLIME_STICKY) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(EntitySkillComponents.getComponentPullMechanic());
            ParticleMechanic particleMechanic = EntitySkillComponents.getComponentParticleMechanic(Particle.SLIME, 3.6, 12, null);
            SkillComponent trigger = EntitySkill.getSkillAoeAround(ChatColor.GREEN + "Kyaa!", 40, children, 7, GoaSound.SKILL_SPLASH, particleMechanic, true);
            skills.add(trigger);
            skillLevels.add(1);
        } else if (this == AdeliaEntity.ZOMBIE_VILLAGER) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(EntitySkillComponents.getComponentDamageMechanic(DamageMechanic.DamageType.MELEE));
            ParticleMechanic particleMechanic = EntitySkillComponents.getComponentParticleMechanic(Particle.VILLAGER_ANGRY, 2.8, 8, null);
            SkillComponent trigger = EntitySkill.getSkillAoeAround(ChatColor.DARK_GREEN + "Grr!", 40, children, 3.2, GoaSound.SKILL_LIGHTNING_NORMAL, particleMechanic, true);
            skills.add(trigger);
            skillLevels.add(2);
        } else if (this == AdeliaEntity.ZOMBIE_SPLITTER) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(EntitySkillComponents.getComponentPotionEffectMechanic(PotionEffectType.SLOW, 80, 2));
            SkillComponent trigger = EntitySkill.getSkillProjectileParticle(ChatColor.DARK_GREEN + "Brgrgg!", 40, children, GoaSound.SKILL_SPLASH, Color.LIME, 3, 1.4);
            skills.add(trigger);
            skillLevels.add(2);
        } else if (this == AdeliaEntity.SKELETON_MAGE) {
            List<SkillComponent> children = new ArrayList<>();
            AreaTarget componentAreaTarget = EntitySkillComponents.getComponentAreaTarget(2.8);
            componentAreaTarget.addChildren(EntitySkillComponents.getComponentDamageMechanic(DamageMechanic.DamageType.MAGIC));
            children.add(componentAreaTarget);
            SkillComponent trigger = EntitySkill.getSkillProjectileFireball(ChatColor.RED + "Burrn!", 40, children, GoaSound.SKILL_PROJECTILE_FIRE, 1);
            skills.add(trigger);
            skillLevels.add(3);
        } else if (this == AdeliaEntity.CREEPER) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(EntitySkillComponents.getComponentDamageMechanic(DamageMechanic.DamageType.MAGIC));
            children.add(EntitySkillComponents.getComponentPushMechanic());
            ParticleMechanic particleMechanic = EntitySkillComponents.getComponentParticleMechanic(Particle.EXPLOSION_HUGE, 4.2, 4, null);
            SkillComponent trigger = EntitySkill.getSkillAoeAround(ChatColor.AQUA + "BOOM!", 40, children, 4.2, GoaSound.SKILL_SONIC_BOOM, particleMechanic, true);
            skills.add(trigger);
            skillLevels.add(4);
        } else if (this == AdeliaEntity.PIRATE_SHARPSHOOTER) {
            List<SkillComponent> children = new ArrayList<>();
            AreaTarget componentAreaTarget = EntitySkillComponents.getComponentAreaTarget(4);
            componentAreaTarget.addChildren(EntitySkillComponents.getComponentDamageMechanic(DamageMechanic.DamageType.RANGED));
            componentAreaTarget.addChildren(EntitySkillComponents.getComponentPushMechanic());
            children.add(componentAreaTarget);
            SkillComponent trigger = EntitySkill.getSkillProjectileArrow(ChatColor.RED + "Firee!", 40, children, GoaSound.SKILL_SONIC_BOOM, Color.ORANGE, 1);
            skills.add(trigger);
            skillLevels.add(5);
        } else if (this == AdeliaEntity.PIRATE_DUEL_MASTER) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(EntitySkillComponents.getComponentDamageMechanic(DamageMechanic.DamageType.MELEE));
            ParticleMechanic particleMechanic = EntitySkillComponents.getComponentParticleMechanic(Particle.REDSTONE, 2.8, 8, new Particle.DustOptions(Color.AQUA, 2));
            SkillComponent trigger = EntitySkill.getSkillAoeAround(ChatColor.AQUA + "Slash!", 40, children, 3.2, GoaSound.SKILL_SWORD_MULTI_SLASH, particleMechanic, true);
            skills.add(trigger);
            skillLevels.add(5);
        } else if (this == AdeliaEntity.MUMMY_GHOST) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(new TeleportTargetMechanic(true, true));
            SkillComponent trigger = EntitySkill.getSkillProjectileParticle(ChatColor.DARK_GREEN + "Shoosh!", 40, children, GoaSound.SKILL_VOID_SIGNAL, Color.YELLOW, 1, 2.7);
            skills.add(trigger);
            skillLevels.add(7);
        } else if (this == AdeliaEntity.WITCH) {
            List<SkillComponent> children = new ArrayList<>();
            AreaTarget componentAreaTarget = EntitySkillComponents.getComponentAreaTarget(3.6);
            componentAreaTarget.addChildren(EntitySkillComponents.getComponentDamageMechanic(DamageMechanic.DamageType.MAGIC));
            componentAreaTarget.addChildren(EntitySkillComponents.getComponentLaunchMechanic());
            children.add(componentAreaTarget);
            ParticleMechanic particleMechanic = new ParticleMechanic(Particle.EXPLOSION_HUGE, ArrangementParticle.SPHERE, 4, 4,
                    0, 0, 0, 0, 1, 0, 0, null);
            children.add(particleMechanic);
            SkillComponent projectileNova = EntitySkill.getSkillProjectileNova(ChatColor.DARK_PURPLE + "Dragon Fireball!", 40, children, GoaSound.SKILL_FIRE_AURA, 1, Particle.DRAGON_BREATH);

            skills.add(projectileNova);
            skillLevels.add(8);
        } else if (this == AdeliaEntity.GOBLIN_MAGE) {
            List<SkillComponent> children = new ArrayList<>();
            AreaTarget componentAreaTarget = EntitySkillComponents.getComponentAreaTarget(2.8);
            componentAreaTarget.addChildren(EntitySkillComponents.getComponentDamageMechanic(DamageMechanic.DamageType.MAGIC));
            children.add(componentAreaTarget);
            SkillComponent trigger = EntitySkill.getSkillProjectileFireball(ChatColor.RED + "Burrn!", 40, children, GoaSound.SKILL_PROJECTILE_FIRE, 1);
            skills.add(trigger);
            skillLevels.add(8);
        } else if (this == AdeliaEntity.GOBLIN_SHAMAN) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(EntitySkillComponents.getComponentHealByAmount());
            ParticleMechanic particleMechanic = EntitySkillComponents.getComponentParticleMechanic(Particle.HEART, 8, 12, null);
            SkillComponent trigger = EntitySkill.getSkillAoeAround(ChatColor.AQUA + "Don't fall, fools!", 10, children, 8, GoaSound.SKILL_HEAL, particleMechanic, false);
            skills.add(trigger);
            skillLevels.add(8);
        } else if (this == AdeliaEntity.ORC_GLADIATOR) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(EntitySkillComponents.getComponentDamageMechanic(DamageMechanic.DamageType.MELEE));
            ParticleMechanic particleMechanic = EntitySkillComponents.getComponentParticleMechanic(Particle.REDSTONE, 2.8, 8, new Particle.DustOptions(Color.AQUA, 2));
            SkillComponent trigger = EntitySkill.getSkillAoeAround(ChatColor.AQUA + "Slash!", 40, children, 2.8, GoaSound.SKILL_SWORD_MULTI_SLASH, particleMechanic, true);
            skills.add(trigger);
            skillLevels.add(9);
        } else if (this == AdeliaEntity.ORC_SHAMAN) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(EntitySkillComponents.getComponentHealByAmount());
            ParticleMechanic particleMechanic = EntitySkillComponents.getComponentParticleMechanic(Particle.HEART, 8, 12, null);
            SkillComponent trigger = EntitySkill.getSkillAoeAround(ChatColor.AQUA + "Stop them, fools!", 10, children, 8, GoaSound.SKILL_HEAL, particleMechanic, false);
            skills.add(trigger);
            skillLevels.add(9);
        } else if (this == AdeliaEntity.ORC_MAGE) {
            List<SkillComponent> children = new ArrayList<>();
            AreaTarget componentAreaTarget = EntitySkillComponents.getComponentAreaTarget(2.8);
            componentAreaTarget.addChildren(EntitySkillComponents.getComponentDamageMechanic(DamageMechanic.DamageType.MAGIC));
            children.add(componentAreaTarget);
            SkillComponent trigger = EntitySkill.getSkillProjectileFireball(ChatColor.RED + "Burrn!", 40, children, GoaSound.SKILL_PROJECTILE_FIRE, 1);
            skills.add(trigger);
            skillLevels.add(9);
        } else if (this == AdeliaEntity.PILLAGER_MAGE) {
            List<SkillComponent> children = new ArrayList<>();
            AreaTarget componentAreaTarget = EntitySkillComponents.getComponentAreaTarget(3.6);
            componentAreaTarget.addChildren(EntitySkillComponents.getComponentDamageMechanic(DamageMechanic.DamageType.MAGIC));
            componentAreaTarget.addChildren(EntitySkillComponents.getComponentLaunchMechanic());
            children.add(componentAreaTarget);
            ParticleMechanic particleMechanic = new ParticleMechanic(Particle.EXPLOSION_HUGE, ArrangementParticle.SPHERE, 4, 4,
                    0, 0, 0, 0, 1, 0, 0, null);
            children.add(particleMechanic);
            SkillComponent projectileNova = EntitySkill.getSkillProjectileNova(ChatColor.DARK_PURPLE + "Dragon Fireball!", 40, children, GoaSound.SKILL_FIRE_AURA, 1, Particle.DRAGON_BREATH);

            skills.add(projectileNova);
            skillLevels.add(10);
        } else if (this == AdeliaEntity.PILLAGER_SHAMAN) {
            List<SkillComponent> children = new ArrayList<>();
            children.add(EntitySkillComponents.getComponentHealByAmount());
            ParticleMechanic particleMechanic = EntitySkillComponents.getComponentParticleMechanic(Particle.HEART, 8, 12, null);
            SkillComponent trigger = EntitySkill.getSkillAoeAround(ChatColor.AQUA + "Stop them, fools!", 10, children, 8, GoaSound.SKILL_HEAL, particleMechanic, false);
            skills.add(trigger);
            skillLevels.add(10);
        } else if (this == AdeliaEntity.BOSS_SLIME) {
            List<SkillComponent> children = new ArrayList<>();
            List<AdeliaEntity> adeliaEntities = new ArrayList<>();
            adeliaEntities.add(SLIME);
            children.add(EntitySkillComponents.getComponentSpawnEntity(adeliaEntities, 2));
            ParticleMechanic particleMechanic = EntitySkillComponents.getComponentParticleMechanic(Particle.REDSTONE, 8, 8, new Particle.DustOptions(Color.AQUA, 2));
            SkillComponent trigger = EntitySkill.getSkillAoeAround(ChatColor.GREEN + "Heelp!", 10, children, 8, GoaSound.SKILL_HEAL, particleMechanic, true);
            skills.add(trigger);
            skillLevels.add(10);
        } else if (this == AdeliaEntity.BOSS_ZOMBIE) {

        } else if (this == AdeliaEntity.BOSS_SKELETON) {

        } else if (this == AdeliaEntity.BOSS_COOK) {

        } else if (this == AdeliaEntity.BOSS_PIRATE) {

        } else if (this == AdeliaEntity.BOSS_ICE) {

        } else if (this == AdeliaEntity.BOSS_DESERT) {

        } else if (this == AdeliaEntity.BOSS_SWAMP) {

        } else if (this == AdeliaEntity.BOSS_LAVA) {

        } else if (this == AdeliaEntity.BOSS_DARKNESS) {

        }

        EntitySkillSet entitySkillSet = new EntitySkillSet(skills, skillLevels, cooldown);
        entitySkillSet.startSkillLoop(livingEntity);*/
    }

    public String getAdeliaEntityKey() {
        return adeliaEntityKey;
    }
}
