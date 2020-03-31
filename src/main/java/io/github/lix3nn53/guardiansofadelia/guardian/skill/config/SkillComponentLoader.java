package io.github.lix3nn53.guardiansofadelia.guardian.skill.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.AreaTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
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
