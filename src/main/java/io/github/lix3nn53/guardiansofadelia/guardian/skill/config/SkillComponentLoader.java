package io.github.lix3nn53.guardiansofadelia.guardian.skill.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.InvincibleMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.InvincibleRemoveMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.particles.ParticleAnimationMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.particles.ParticleMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile.ProjectileMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.*;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.config.ConfigurationUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class SkillComponentLoader {

    public static SkillComponent loadSection(ConfigurationSection configurationSection) {
        GuardiansOfAdelia.getInstance().getLogger().info(configurationSection.getCurrentPath());
        SkillComponent skillComponent = loadComponent(configurationSection);

        int childComponentCount = ConfigurationUtils.getChildComponentCount(configurationSection, "child");

        if (childComponentCount > 0) {
            List<SkillComponent> children = loadChildrenOfSection(configurationSection, childComponentCount);

            for (SkillComponent child : children) {
                skillComponent.addChildren(child);
            }
        }

        return skillComponent;
    }

    private static SkillComponent loadComponent(ConfigurationSection configurationSection) {
        String componentType = configurationSection.getString("componentType");

        if (componentType == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "NULL COMPONENT TYPE");
            return null;
        }

        if (componentType.equals(ParticleMechanic.class.getSimpleName())) {
            return new ParticleMechanic(configurationSection);
        } else if (componentType.equals(AreaTarget.class.getSimpleName())) {
            return new AreaTarget(configurationSection);
        } else if (componentType.equals(ConeTarget.class.getSimpleName())) {
            return new ConeTarget(configurationSection);
        } else if (componentType.equals(CubeTarget.class.getSimpleName())) {
            return new CubeTarget(configurationSection);
        } else if (componentType.equals(SoundMechanic.class.getSimpleName())) {
            return new SoundMechanic(configurationSection);
        } else if (componentType.equals(HoloMessageMechanic.class.getSimpleName())) {
            return new HoloMessageMechanic(configurationSection);
        } else if (componentType.equals(DelayWithHoloMessageMechanic.class.getSimpleName())) {
            return new DelayWithHoloMessageMechanic(configurationSection);
        } else if (componentType.equals(DelayMechanic.class.getSimpleName())) {
            return new DelayMechanic(configurationSection);
        } else if (componentType.equals(SpawnCompanionMechanic.class.getSimpleName())) {
            return new SpawnCompanionMechanic(configurationSection);
        } else if (componentType.equals(SpawnEntityMechanic.class.getSimpleName())) {
            return new SpawnEntityMechanic(configurationSection);
        } else if (componentType.equals(SelfTarget.class.getSimpleName())) {
            return new SelfTarget(configurationSection);
        } else if (componentType.equals(SavedEntitiesTarget.class.getSimpleName())) {
            return new SavedEntitiesTarget(configurationSection);
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
        } else if (componentType.equals(SavedCompanionsTarget.class.getSimpleName())) {
            return new SavedCompanionsTarget(configurationSection);
        } else if (componentType.equals(HologramMechanic.class.getSimpleName())) {
            return new HologramMechanic(configurationSection);
        } else if (componentType.equals(CompanionsOfCasterTargetMechanic.class.getSimpleName())) {
            return new CompanionsOfCasterTargetMechanic(configurationSection);
        } else if (componentType.equals(RepeatMechanic.class.getSimpleName())) {
            return new RepeatMechanic(configurationSection);
        } else if (componentType.equals(BuffMechanic.class.getSimpleName())) {
            return new BuffMechanic(configurationSection);
        } else if (componentType.equals(InitializeTrigger.class.getSimpleName())) {
            return new InitializeTrigger(configurationSection);
        } else if (componentType.equals(CompanionSpawnTrigger.class.getSimpleName())) {
            return new CompanionSpawnTrigger(configurationSection);
        } else if (componentType.equals(TookDamageTrigger.class.getSimpleName())) {
            return new TookDamageTrigger(configurationSection);
        } else if (componentType.equals(HealthCondition.class.getSimpleName())) {
            return new HealthCondition(configurationSection);
        } else if (componentType.equals(SavedEntityCondition.class.getSimpleName())) {
            return new SavedEntityCondition(configurationSection);
        } else if (componentType.equals(ParticleAnimationMechanic.class.getSimpleName())) {
            return new ParticleAnimationMechanic(configurationSection);
        } else if (componentType.equals(ImmunityMechanic.class.getSimpleName())) {
            return new ImmunityMechanic(configurationSection);
        } else if (componentType.equals(LandTrigger.class.getSimpleName())) {
            return new LandTrigger(configurationSection);
        } else if (componentType.equals(ImmunityRemoveMechanic.class.getSimpleName())) {
            return new ImmunityRemoveMechanic(configurationSection);
        } else if (componentType.equals(StatusEffectMechanic.class.getSimpleName())) {
            return new StatusEffectMechanic(configurationSection);
        } else if (componentType.equals(NormalAttackTrigger.class.getSimpleName())) {
            return new NormalAttackTrigger(configurationSection);
        } else if (componentType.equals(MessageMechanic.class.getSimpleName())) {
            return new MessageMechanic(configurationSection);
        } else if (componentType.equals(FireMechanic.class.getSimpleName())) {
            return new FireMechanic(configurationSection);
        } else if (componentType.equals(FreezeMechanic.class.getSimpleName())) {
            return new FreezeMechanic(configurationSection);
        } else if (componentType.equals(SkillAttackTrigger.class.getSimpleName())) {
            return new SkillAttackTrigger(configurationSection);
        } else if (componentType.equals(SavedEntitySpawnTrigger.class.getSimpleName())) {
            return new SavedEntitySpawnTrigger(configurationSection);
        } else if (componentType.equals(ValueCondition.class.getSimpleName())) {
            return new ValueCondition(configurationSection);
        } else if (componentType.equals(ValueAddMechanic.class.getSimpleName())) {
            return new ValueAddMechanic(configurationSection);
        } else if (componentType.equals(LocationTarget.class.getSimpleName())) {
            return new LocationTarget(configurationSection);
        } else if (componentType.equals(AddPiercingToArrowShootFromCrossbowTrigger.class.getSimpleName())) {
            return new AddPiercingToArrowShootFromCrossbowTrigger(configurationSection);
        } else if (componentType.equals(NearbyEntityCondition.class.getSimpleName())) {
            return new NearbyEntityCondition(configurationSection);
        } else if (componentType.equals(FlagCondition.class.getSimpleName())) {
            return new FlagCondition(configurationSection);
        } else if (componentType.equals(RepeatCancelMechanic.class.getSimpleName())) {
            return new RepeatCancelMechanic(configurationSection);
        } else if (componentType.equals(FlagRemoveMechanic.class.getSimpleName())) {
            return new FlagRemoveMechanic(configurationSection);
        } else if (componentType.equals(RemoveSavedEntities.class.getSimpleName())) {
            return new RemoveSavedEntities(configurationSection);
        } else if (componentType.equals(FlagSetMechanic.class.getSimpleName())) {
            return new FlagSetMechanic(configurationSection);
        } else if (componentType.equals(ValueSetMechanic.class.getSimpleName())) {
            return new ValueSetMechanic(configurationSection);
        } else if (componentType.equals(SingleTarget.class.getSimpleName())) {
            return new SingleTarget(configurationSection);
        } else if (componentType.equals(DirectionCondition.class.getSimpleName())) {
            return new DirectionCondition(configurationSection);
        } else if (componentType.equals(SkillCastTrigger.class.getSimpleName())) {
            return new SkillCastTrigger(configurationSection);
        } else if (componentType.equals(ManaMechanic.class.getSimpleName())) {
            return new ManaMechanic(configurationSection);
        } else if (componentType.equals(TauntMechanic.class.getSimpleName())) {
            return new TauntMechanic(configurationSection);
        } else if (componentType.equals(WarpMechanic.class.getSimpleName())) {
            return new WarpMechanic(configurationSection);
        } else if (componentType.equals(WarpTargetMechanic.class.getSimpleName())) {
            return new WarpTargetMechanic(configurationSection);
        } else if (componentType.equals(InvincibleMechanic.class.getSimpleName())) {
            return new InvincibleMechanic(configurationSection);
        } else if (componentType.equals(InvincibleRemoveMechanic.class.getSimpleName())) {
            return new InvincibleRemoveMechanic(configurationSection);
        } else if (componentType.equals(DisguiseMechanic.class.getSimpleName())) {
            return new DisguiseMechanic(configurationSection);
        } else if (componentType.equals(ForEachTargetMechanic.class.getSimpleName())) {
            return new ForEachTargetMechanic(configurationSection);
        } else if (componentType.equals(CompanionCondition.class.getSimpleName())) {
            return new CompanionCondition(configurationSection);
        } else if (componentType.equals(GodDamageMechanic.class.getSimpleName())) {
            return new GodDamageMechanic(configurationSection);
        } else if (componentType.equals(CompanionsOfCasterWarpMechanic.class.getSimpleName())) {
            return new CompanionsOfCasterWarpMechanic(configurationSection);
        } else if (componentType.equals(TargetsEmptyCondition.class.getSimpleName())) {
            return new TargetsEmptyCondition(configurationSection);
        } else if (componentType.equals(ClearDeadTargets.class.getSimpleName())) {
            return new ClearDeadTargets();
        } else if (componentType.equals(HologramEntityMechanic.class.getSimpleName())) {
            return new HologramEntityMechanic(configurationSection);
        } else if (componentType.equals(HeadRotationMechanic.class.getSimpleName())) {
            return new HeadRotationMechanic(configurationSection);
        } else if (componentType.equals(AbsorptionHeartMechanic.class.getSimpleName())) {
            return new AbsorptionHeartMechanic(configurationSection);
        } else if (componentType.equals(CloneCurrentTargets.class.getSimpleName())) {
            return new CloneCurrentTargets(configurationSection);
        } else if (componentType.equals(ChangeSkillLevelMechanic.class.getSimpleName())) {
            return new ChangeSkillLevelMechanic(configurationSection);
        } else if (componentType.equals(TargetCountCondition.class.getSimpleName())) {
            return new TargetCountCondition(configurationSection);
        } else if (componentType.equals(SkillLevelCondition.class.getSimpleName())) {
            return new SkillLevelCondition(configurationSection);
        } else if (componentType.equals(DistanceCondition.class.getSimpleName())) {
            return new DistanceCondition(configurationSection);
        } else if (componentType.equals(GlowMechanic.class.getSimpleName())) {
            return new GlowMechanic(configurationSection);
        } else if (componentType.equals(PotionEffectRemoveMechanic.class.getSimpleName())) {
            return new PotionEffectRemoveMechanic(configurationSection);
        }

        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "NO SUCH COMPONENT IN LOADER: " + componentType);
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "NO SUCH COMPONENT IN LOADER: " + componentType);
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "NO SUCH COMPONENT IN LOADER: " + componentType);

        return null;
    }

    private static List<SkillComponent> loadChildrenOfSection(ConfigurationSection configurationSection, int childComponentCount) {
        List<SkillComponent> children = new ArrayList<>();

        for (int i = 1; i <= childComponentCount; i++) {
            GuardiansOfAdelia.getInstance().getLogger().info(configurationSection.getCurrentPath() + ".child" + i);
            ConfigurationSection childSection = configurationSection.getConfigurationSection("child" + i);

            SkillComponent child = loadComponent(childSection);
            children.add(child);

            int childComponentCountOfChild = ConfigurationUtils.getChildComponentCount(childSection, "child");

            if (childComponentCountOfChild > 0) {
                List<SkillComponent> childrenOfChild = loadChildrenOfSection(childSection, childComponentCountOfChild);

                for (SkillComponent childOfChild : childrenOfChild) {
                    child.addChildren(childOfChild);
                }
            }
        }

        return children;
    }
}
