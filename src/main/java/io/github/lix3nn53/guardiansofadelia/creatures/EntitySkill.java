package io.github.lix3nn53.guardiansofadelia.creatures;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum EntitySkill {
    PROJECTILE_FIREBALL,
    PROJECTILE_POISON,
    PROJECTILE_ZOMBIE_SPIT,
    PROJECTILE_PIRATE_GUN,
    PROJECTILE_VOLLEY,
    AOE_DAMAGE,
    AOE_PULL,
    AOE_LAUNCH,
    AOE_HEAL,
    AOE_EXPLOSION,
    AOE_CONFUSE,
    AOE_SILENCE,
    AOE_BURN,
    SKYFALL,
    TELEPORT;

    private static HashMap<EntitySkill, TargetComponent> skillTrigger = new HashMap<>();

    static {
        //PROJECTILE_FIREBALL

        ProjectileMechanic projectileFireball = new ProjectileMechanic(SpreadType.CONE, 1.9, singleProjectile(), 30,
                0, 1, 0, 200, true, Fireball.class);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, smallAreaRadius());
        SelfTarget fireballTrigger = new SelfTarget();
        fireballTrigger.addChildren(new HoloMessageMechanic(ChatColor.RED + "Buuuurrrn!", 70));
        DelayMechanic delayMechanic = new DelayMechanic(25);
        fireballTrigger.addChildren(delayMechanic);
        delayMechanic.addChildren(projectileFireball);
        delayMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));
        projectileFireball.addChildren(areaTarget);
        areaTarget.addChildren(new DamageMechanic(magicDamages(), DamageMechanic.DamageType.MAGIC));
        skillTrigger.put(PROJECTILE_FIREBALL, fireballTrigger);

        //AOE_DAMAGE

        SelfTarget aoeSmallAroundTrigger = new SelfTarget();
        SelfTarget selfTargetForSound = new SelfTarget();
        areaTarget = new AreaTarget(false, true, false, 999, smallAreaRadius());
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_LIGHTNING_NORMAL));
        DamageMechanic damageMechanic = new DamageMechanic(meleeDamages(), DamageMechanic.DamageType.MELEE);
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.VILLAGER_ANGRY, ArrangementParticle.CIRCLE, 3, 20, 0, 0, 0, 0, 0.5, 0, 0, null);
        aoeSmallAroundTrigger.addChildren(new HoloMessageMechanic(ChatColor.AQUA + "Lightning!", 70));
        delayMechanic = new DelayMechanic(25);
        aoeSmallAroundTrigger.addChildren(delayMechanic);
        delayMechanic.addChildren(areaTarget);
        selfTargetForSound.addChildren(particleMechanic);
        areaTarget.addChildren(damageMechanic);
        skillTrigger.put(AOE_DAMAGE, aoeSmallAroundTrigger);

        //PROJECTILE_POISON

        ProjectileMechanic projectilePoisonArrow = new ProjectileMechanic(SpreadType.CONE, 2.7, singleProjectile(), 30,
                0, 1, 0, 200, true, Arrow.class, Particle.REDSTONE,
                ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(Color.LIME, 2), false);
        SelfTarget poisonArrowTrigger = new SelfTarget();
        poisonArrowTrigger.addChildren(new HoloMessageMechanic(ChatColor.RED + "Green!", 70));
        delayMechanic = new DelayMechanic(25);
        poisonArrowTrigger.addChildren(delayMechanic);
        delayMechanic.addChildren(projectilePoisonArrow);
        delayMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));
        projectilePoisonArrow.addChildren(new DamageMechanic(rangedDamages(), DamageMechanic.DamageType.RANGED));
        PotionEffectMechanic potionEffectMechanic = new PotionEffectMechanic(PotionEffectType.POISON, poisonDuration(), poisonAmplifiers());
        projectilePoisonArrow.addChildren(potionEffectMechanic);
        skillTrigger.put(PROJECTILE_FIREBALL, poisonArrowTrigger);
    }

    public static TargetComponent getSkillTrigger(EntitySkill entitySkill) {
        return skillTrigger.get(entitySkill);
    }

    //EntitySkills needs 10 skill level so 10 value for each list attribute of skill

    private static List<Double> magicDamages() {
        List<Double> magicDamages = new ArrayList<>();
        magicDamages.add(180D);
        magicDamages.add(292D);
        magicDamages.add(405D);
        magicDamages.add(540D);
        magicDamages.add(675D);
        magicDamages.add(900D);
        magicDamages.add(900D);
        magicDamages.add(900D);
        magicDamages.add(900D);
        magicDamages.add(900D);

        return magicDamages;
    }

    private static List<Double> meleeDamages() {
        List<Double> physicalDamages = new ArrayList<>();
        physicalDamages.add(180D);
        physicalDamages.add(292D);
        physicalDamages.add(405D);
        physicalDamages.add(540D);
        physicalDamages.add(675D);
        physicalDamages.add(900D);
        physicalDamages.add(900D);
        physicalDamages.add(900D);
        physicalDamages.add(900D);
        physicalDamages.add(900D);

        return physicalDamages;
    }

    private static List<Double> rangedDamages() {
        List<Double> physicalDamages = new ArrayList<>();
        physicalDamages.add(180D);
        physicalDamages.add(292D);
        physicalDamages.add(405D);
        physicalDamages.add(540D);
        physicalDamages.add(675D);
        physicalDamages.add(900D);
        physicalDamages.add(900D);
        physicalDamages.add(900D);
        physicalDamages.add(900D);
        physicalDamages.add(900D);

        return physicalDamages;
    }

    private static List<Integer> singleProjectile() {
        List<Integer> singleProjectile = new ArrayList<>();
        singleProjectile.add(1);
        singleProjectile.add(1);
        singleProjectile.add(1);
        singleProjectile.add(1);
        singleProjectile.add(1);
        singleProjectile.add(1);
        singleProjectile.add(1);
        singleProjectile.add(1);
        singleProjectile.add(1);
        singleProjectile.add(1);

        return singleProjectile;
    }

    private static List<Double> smallAreaRadius() {
        List<Double> smallAreaRadius = new ArrayList<>();
        smallAreaRadius.add(2.5D);
        smallAreaRadius.add(2.75D);
        smallAreaRadius.add(3D);
        smallAreaRadius.add(3.25D);
        smallAreaRadius.add(3.5D);
        smallAreaRadius.add(4D);
        smallAreaRadius.add(4D);
        smallAreaRadius.add(4D);
        smallAreaRadius.add(4D);
        smallAreaRadius.add(4D);

        return smallAreaRadius;
    }

    private static List<Integer> poisonDuration() {
        List<Integer> poisonDuration = new ArrayList<>();
        poisonDuration.add(50);
        poisonDuration.add(50);
        poisonDuration.add(50);
        poisonDuration.add(50);
        poisonDuration.add(50);
        poisonDuration.add(50);
        poisonDuration.add(50);
        poisonDuration.add(50);
        poisonDuration.add(50);
        poisonDuration.add(50);

        return poisonDuration;
    }

    private static List<Integer> poisonAmplifiers() {
        List<Integer> poisonAmplifiers = new ArrayList<>();
        poisonAmplifiers.add(1);
        poisonAmplifiers.add(1);
        poisonAmplifiers.add(1);
        poisonAmplifiers.add(1);
        poisonAmplifiers.add(2);
        poisonAmplifiers.add(2);
        poisonAmplifiers.add(2);
        poisonAmplifiers.add(2);
        poisonAmplifiers.add(3);
        poisonAmplifiers.add(3);

        return poisonAmplifiers;
    }
}
