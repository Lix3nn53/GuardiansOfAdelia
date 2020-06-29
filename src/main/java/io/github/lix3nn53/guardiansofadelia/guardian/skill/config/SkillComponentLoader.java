package io.github.lix3nn53.guardiansofadelia.guardian.skill.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.HealthCondition;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.hologram.HologramMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.RootMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.SilenceMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.FilterCurrentTargets;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.LandTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookMeleeDamageTrigger;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TookPhysicalDamageTrigger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class SkillComponentLoader {

    public static SkillComponent loadSection(ConfigurationSection configurationSection) {
        GuardiansOfAdelia.getInstance().getLogger().info(configurationSection.getCurrentPath());
        GuardiansOfAdelia.getInstance().getLogger().info(configurationSection.toString());
        SkillComponent skillComponent = loadComponent(configurationSection);

        boolean hasChild = hasChild(configurationSection);

        if (hasChild) {
            List<SkillComponent> children = loadChildrenOfSection(configurationSection);

            for (SkillComponent child : children) {
                skillComponent.addChildren(child);
            }
        }

        return skillComponent;
    }

    private static SkillComponent loadComponent(ConfigurationSection configurationSection) {
        String componentType = configurationSection.getString("componentType");

        if (componentType == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NULL COMPONENT TYPE");
            return null;
        }

        if (componentType.equals(ParticleMechanic.class.getSimpleName())) {
            return new ParticleMechanic(configurationSection);
        } else if (componentType.equals(AreaTarget.class.getSimpleName())) {
            return new AreaTarget(configurationSection);
        } else if (componentType.equals(SoundMechanic.class.getSimpleName())) {
            return new SoundMechanic(configurationSection);
        } else if (componentType.equals(HoloMessageMechanic.class.getSimpleName())) {
            return new HoloMessageMechanic(configurationSection);
        } else if (componentType.equals(DelayMechanic.class.getSimpleName())) {
            return new DelayMechanic(configurationSection);
        } else if (componentType.equals(SpawnEntityMechanic.class.getSimpleName())) {
            return new SpawnEntityMechanic(configurationSection);
        } else if (componentType.equals(SelfTarget.class.getSimpleName())) {
            return new SelfTarget();
        } else if (componentType.equals(PushMechanic.class.getSimpleName())) {
            return new PushMechanic(configurationSection);
        } else if (componentType.equals(PotionEffectMechanic.class.getSimpleName())) {
            return new PotionEffectMechanic(configurationSection);
        } else if (componentType.equals(LaunchMechanic.class.getSimpleName())) {
            return new LaunchMechanic(configurationSection);
        } else if (componentType.equals(HealMechanic.class.getSimpleName())) {
            return new HealMechanic(configurationSection);
        } else if (componentType.equals(DamageMechanic.class.getSimpleName())) {
            return new DamageMechanic(configurationSection);
        } else if (componentType.equals(ProjectileMechanic.class.getSimpleName())) {
            try {
                return new ProjectileMechanic(configurationSection);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (componentType.equals(FilterCurrentTargets.class.getSimpleName())) {
            return new FilterCurrentTargets(configurationSection);
        } else if (componentType.equals(HologramMechanic.class.getSimpleName())) {
            return new HologramMechanic(configurationSection);
        } else if (componentType.equals(RepeatMechanic.class.getSimpleName())) {
            return new RepeatMechanic(configurationSection);
        } else if (componentType.equals(BuffMechanic.class.getSimpleName())) {
            return new BuffMechanic(configurationSection);
        } else if (componentType.equals(InitializeTrigger.class.getSimpleName())) {
            return new InitializeTrigger();
        } else if (componentType.equals(TookPhysicalDamageTrigger.class.getSimpleName())) {
            return new TookPhysicalDamageTrigger(configurationSection);
        } else if (componentType.equals(HealthCondition.class.getSimpleName())) {
            return new HealthCondition(configurationSection);
        } else if (componentType.equals(ParticleAnimationMechanic.class.getSimpleName())) {
            return new ParticleAnimationMechanic(configurationSection);
        } else if (componentType.equals(ImmunityMechanic.class.getSimpleName())) {
            return new ImmunityMechanic(configurationSection);
        } else if (componentType.equals(LandTrigger.class.getSimpleName())) {
            return new LandTrigger();
        } else if (componentType.equals(ImmunityRemoveMechanic.class.getSimpleName())) {
            return new ImmunityRemoveMechanic(configurationSection);
        } else if (componentType.equals(ImmunityRemoveMechanic.class.getSimpleName())) {
            return new ImmunityRemoveMechanic(configurationSection);
        } else if (componentType.equals(SilenceMechanic.class.getSimpleName())) {
            return new SilenceMechanic(configurationSection);
        } else if (componentType.equals(TookMeleeDamageTrigger.class.getSimpleName())) {
            return new TookMeleeDamageTrigger(configurationSection);
        } else if (componentType.equals(RootMechanic.class.getSimpleName())) {
            return new RootMechanic(configurationSection);
        } else if (componentType.equals(MessageMechanic.class.getSimpleName())) {
            return new MessageMechanic(configurationSection);
        } else if (componentType.equals(FireMechanic.class.getSimpleName())) {
            return new FireMechanic(configurationSection);
        }

        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NO SUCH COMPONENT IN LOADER");

        return null;
    }

    private static boolean hasChild(ConfigurationSection configurationSection) {
        int childComponentCount = configurationSection.getInt("childComponentCount");

        return childComponentCount > 0;
    }

    private static List<SkillComponent> loadChildrenOfSection(ConfigurationSection configurationSection) {
        int childComponentCount = configurationSection.getInt("childComponentCount");

        List<SkillComponent> children = new ArrayList<>();

        for (int i = 1; i <= childComponentCount; i++) {
            ConfigurationSection childSection = configurationSection.getConfigurationSection("child" + i);

            SkillComponent child = loadComponent(childSection);
            children.add(child);

            boolean hasChild = hasChild(childSection);

            if (hasChild) {
                List<SkillComponent> childrenOfChild = loadChildrenOfSection(childSection);

                for (SkillComponent childOfChild : childrenOfChild) {
                    child.addChildren(childOfChild);
                }
            }
        }

        return children;
    }
}
