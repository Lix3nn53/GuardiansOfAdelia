package io.github.lix3nn53.guardiansofadelia.creatures.entitySkills;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.EmptyComponent;
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
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Fireball;

import java.util.ArrayList;
import java.util.List;

public class EntitySkills {

    public static SkillComponent getSkillProjectileFireball(String holoWarnMessage, int delay, List<SkillComponent> children, GoaSound goaSound, int projectileAmount) {
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
        AreaTarget areaTarget = new AreaTarget(false, true, false, 99, smallAreaRadius);
        SelfTarget trigger = new SelfTarget();
        trigger.addChildren(new HoloMessageMechanic(holoWarnMessage, 70));
        DelayMechanic delayMechanic = new DelayMechanic(delay);
        trigger.addChildren(delayMechanic);
        delayMechanic.addChildren(projectileMechanic);
        delayMechanic.addChildren(new SoundMechanic(goaSound));
        projectileMechanic.addChildren(areaTarget);
        for (SkillComponent child : children) {
            areaTarget.addChildren(child);
        }

        return trigger;
    }

    public static SkillComponent getSkillProjectileArrow(String holoWarnMessage, int delay, List<SkillComponent> children, GoaSound goaSound, Color color, int projectileAmount) {
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, 2.7, projectileAmountToList(projectileAmount), 30,
                0, 1, 0, 200, true, Arrow.class, Particle.REDSTONE,
                ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(color, 2), false);
        SelfTarget trigger = new SelfTarget();
        trigger.addChildren(new HoloMessageMechanic(holoWarnMessage, 70));
        DelayMechanic delayMechanic = new DelayMechanic(delay);
        trigger.addChildren(delayMechanic);
        delayMechanic.addChildren(projectileMechanic);
        delayMechanic.addChildren(new SoundMechanic(goaSound));
        for (SkillComponent child : children) {
            projectileMechanic.addChildren(child);
        }

        return trigger;
    }

    public static SkillComponent getSkillProjectileParticle(String holoWarnMessage, int delay, List<SkillComponent> children, GoaSound goaSound, Color color, int projectileAmount, double speed) {
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.CONE, speed, projectileAmountToList(projectileAmount), 30,
                0, 1, 0, 200, true, Arrow.class, Particle.REDSTONE,
                ArrangementParticle.CIRCLE, 0.5, 4, new Particle.DustOptions(color, 2), true);
        SelfTarget trigger = new SelfTarget();
        trigger.addChildren(new HoloMessageMechanic(holoWarnMessage, 70));
        DelayMechanic delayMechanic = new DelayMechanic(delay);
        trigger.addChildren(delayMechanic);
        delayMechanic.addChildren(projectileMechanic);
        delayMechanic.addChildren(new SoundMechanic(goaSound));
        for (SkillComponent child : children) {
            projectileMechanic.addChildren(child);
        }

        return trigger;
    }

    public static SkillComponent getSkillProjectileNova(String holoWarnMessage, int delay, List<SkillComponent> children, GoaSound goaSound, int projectileAmount, Particle particle) {
        ProjectileMechanic projectileMechanic = new ProjectileMechanic(SpreadType.RAIN, 8, 12, 0.4, projectileAmountToList(projectileAmount), 0,
                0, 0, 200, false, DragonFireball.class, particle,
                ArrangementParticle.SPHERE, 2, 32, null, false);
        EmptyComponent emptyComponent = new EmptyComponent();
        SelfTarget selfTarget = new SelfTarget();
        selfTarget.addChildren(new HoloMessageMechanic(holoWarnMessage, 70));
        emptyComponent.addChildren(selfTarget);
        DelayMechanic delayMechanic = new DelayMechanic(delay);
        emptyComponent.addChildren(delayMechanic);
        delayMechanic.addChildren(projectileMechanic);
        delayMechanic.addChildren(new SoundMechanic(goaSound));
        for (SkillComponent child : children) {
            projectileMechanic.addChildren(child);
        }

        return emptyComponent;
    }

    public static SkillComponent getSkillAoeAround(String holoWarnMessage, int delay, List<SkillComponent> children, double radius, GoaSound goaSound, ParticleMechanic particleMechanic) {
        SelfTarget trigger = new SelfTarget();
        SelfTarget selfTargetForSound = new SelfTarget();
        List<Double> radiusList = new ArrayList<>();
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        radiusList.add(radius);
        AreaTarget areaTarget = new AreaTarget(false, true, false, 99, radiusList);
        areaTarget.addChildren(selfTargetForSound);
        selfTargetForSound.addChildren(new SoundMechanic(goaSound));
        trigger.addChildren(new HoloMessageMechanic(holoWarnMessage, 70));
        DelayMechanic delayMechanic = new DelayMechanic(delay);
        trigger.addChildren(delayMechanic);
        for (SkillComponent child : children) {
            areaTarget.addChildren(child);
        }
        delayMechanic.addChildren(areaTarget);
        selfTargetForSound.addChildren(particleMechanic);

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
