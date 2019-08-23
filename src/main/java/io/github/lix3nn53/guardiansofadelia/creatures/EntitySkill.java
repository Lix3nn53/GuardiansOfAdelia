package io.github.lix3nn53.guardiansofadelia.creatures;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.DamageMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.ParticleMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.SoundMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.Particle;
import org.bukkit.entity.Fireball;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum EntitySkill {
    PROJECTILE_FIREBALL,
    AOE_SMALL_AROUND;

    private static HashMap<EntitySkill, TargetComponent> skillTrigger = new HashMap<>();

    static {
        //PROJECTILE_FIREBALL

        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, singleProjectile(), 30,
                0, 1, 0, 200, true, Fireball.class);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, smallAreaRadius());
        SelfTarget fireballTrigger = new SelfTarget();
        fireballTrigger.addChildren(projectileMechanic);
        fireballTrigger.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));
        projectileMechanic.addChildren(areaTarget);
        areaTarget.addChildren(new DamageMechanic(magicDamages(), DamageMechanic.DamageType.MAGIC));
        skillTrigger.put(PROJECTILE_FIREBALL, fireballTrigger);

        //AOE_SMALL_AROUND

        SelfTarget aoeSmallAroundTrigger = new SelfTarget();
        areaTarget = new AreaTarget(false, true, false, 999, smallAreaRadius());
        SelfTarget selfTargetForSound = new SelfTarget();
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_LIGHTNING_NORMAL));
        DamageMechanic damageMechanic = new DamageMechanic(physicalDamages(), DamageMechanic.DamageType.MELEE);
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.VILLAGER_ANGRY, ArrangementParticle.CIRCLE, 3, 20, 0, 0, 0, 0, 0.5, 0, 0, null);
        aoeSmallAroundTrigger.addChildren(areaTarget);
        selfTargetForSound.addChildren(particleMechanic);
        areaTarget.addChildren(damageMechanic);
        skillTrigger.put(AOE_SMALL_AROUND, aoeSmallAroundTrigger);
    }

    public static TargetComponent getSkillTrigger(EntitySkill entitySkill) {
        return skillTrigger.get(entitySkill);
    }

    private static List<Double> magicDamages() {
        List<Double> magicDamages = new ArrayList<>();
        magicDamages.add(180D);
        magicDamages.add(292D);
        magicDamages.add(405D);
        magicDamages.add(540D);
        magicDamages.add(675D);
        magicDamages.add(900D);

        return magicDamages;
    }

    private static List<Double> physicalDamages() {
        List<Double> physicalDamages = new ArrayList<>();
        physicalDamages.add(180D);
        physicalDamages.add(292D);
        physicalDamages.add(405D);
        physicalDamages.add(540D);
        physicalDamages.add(675D);
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

        return smallAreaRadius;
    }
}
