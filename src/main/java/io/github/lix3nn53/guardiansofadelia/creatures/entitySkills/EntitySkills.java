package io.github.lix3nn53.guardiansofadelia.creatures.entitySkills;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.DelayMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.HoloMessageMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.ParticleMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.SoundMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.SpreadType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;

import java.util.ArrayList;
import java.util.List;

public class EntitySkills {

    public static SkillComponent getSkillProjectileFireball(String holoWarnMessage, int projectileAmount, List<SkillComponent> children) {
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 1.9, projectileAmountToList(projectileAmount), 30,
                0, 1, 0, 200, true, Fireball.class);
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
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, smallAreaRadius);
        SelfTarget trigger = new SelfTarget();
        trigger.addChildren(new HoloMessageMechanic(holoWarnMessage, 70));
        DelayMechanic delayMechanic = new DelayMechanic(25);
        trigger.addChildren(delayMechanic);
        delayMechanic.addChildren(projectileMechanic);
        delayMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));
        projectileMechanic.addChildren(areaTarget);
        for (SkillComponent child : children) {
            areaTarget.addChildren(child);
        }

        return trigger;
    }

    public static SkillComponent getSkillProjectileArrow(String holoWarnMessage, int projectileAmount, List<SkillComponent> children) {
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 2.7, projectileAmountToList(projectileAmount), 30,
                0, 1, 0, 200, true, Arrow.class, Particle.REDSTONE,
                ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(Color.LIME, 2), false);
        SelfTarget trigger = new SelfTarget();
        trigger.addChildren(new HoloMessageMechanic(holoWarnMessage, 70));
        DelayMechanic delayMechanic = new DelayMechanic(25);
        trigger.addChildren(delayMechanic);
        delayMechanic.addChildren(projectileMechanic);
        delayMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));
        for (SkillComponent child : children) {
            projectileMechanic.addChildren(child);
        }

        return trigger;
    }

    public static SkillComponent getSkillProjectileParticle(String holoWarnMessage, int projectileAmount, double speed, Color color, List<SkillComponent> children) {
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, speed, projectileAmountToList(projectileAmount), 30,
                0, 1, 0, 200, true, Arrow.class, Particle.REDSTONE,
                ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(color, 2), true);
        SelfTarget trigger = new SelfTarget();
        trigger.addChildren(new HoloMessageMechanic(holoWarnMessage, 70));
        DelayMechanic delayMechanic = new DelayMechanic(25);
        trigger.addChildren(delayMechanic);
        delayMechanic.addChildren(projectileMechanic);
        delayMechanic.addChildren(new SoundMechanic(GoaSound.SKILL_FIRE_SLASH));
        for (SkillComponent child : children) {
            projectileMechanic.addChildren(child);
        }

        return trigger;
    }

    public static SkillComponent getSkillAoeAround(String holoWarnMessage, List<SkillComponent> children) {
        SelfTarget trigger = new SelfTarget();
        SelfTarget selfTargetForSound = new SelfTarget();
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
        AreaTarget areaTarget = new AreaTarget(false, true, false, 999, smallAreaRadius);
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(GoaSound.SKILL_LIGHTNING_NORMAL));
        ParticleMechanic particleMechanic = new ParticleMechanic(Particle.VILLAGER_ANGRY, ArrangementParticle.CIRCLE, 3, 20, 0, 0, 0, 0, 0.5, 0, 0, null);
        trigger.addChildren(new HoloMessageMechanic(holoWarnMessage, 70));
        DelayMechanic delayMechanic = new DelayMechanic(25);
        trigger.addChildren(delayMechanic);
        delayMechanic.addChildren(areaTarget);
        selfTargetForSound.addChildren(particleMechanic);
        for (SkillComponent child : children) {
            areaTarget.addChildren(child);
        }

        return trigger;
    }

    public static List<Integer> projectileAmountToList(int projectileAmount) {
        List<Integer> singleProjectile = new ArrayList<>();
        singleProjectile.add(projectileAmount);
        singleProjectile.add(projectileAmount);
        singleProjectile.add(projectileAmount);
        singleProjectile.add(projectileAmount);
        singleProjectile.add(projectileAmount);
        singleProjectile.add(projectileAmount);
        singleProjectile.add(projectileAmount);
        singleProjectile.add(projectileAmount);
        singleProjectile.add(projectileAmount);
        singleProjectile.add(projectileAmount);

        return singleProjectile;
    }
}
