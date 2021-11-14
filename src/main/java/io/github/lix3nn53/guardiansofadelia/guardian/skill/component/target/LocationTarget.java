package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.creatures.custom.TemporaryEntity;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ArrangementSingle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class LocationTarget extends TargetComponent {

    private final List<Integer> range;
    // PARTICLE
    private final float gap;
    private final ArrangementSingle arrangementSingle;

    public LocationTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("ranges")) {
            configLoadError("ranges");
        }

        this.range = configurationSection.getIntegerList("ranges");

        // PARTICLE
        if (!configurationSection.contains("particle")) {
            configLoadError("particle");
        }

        ConfigurationSection particleSection = configurationSection.getConfigurationSection("particle");

        this.arrangementSingle = new ArrangementSingle(particleSection);
        this.gap = particleSection.contains("gap") ? (float) particleSection.getDouble("gap") : 0;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> temporaryEntities = new ArrayList<>();

        for (LivingEntity target : targets) {
            HashSet<Material> transparentBlocks = new HashSet<>();
            transparentBlocks.add(Material.AIR);
            transparentBlocks.add(Material.WATER);

            Block targetBlock = target.getTargetBlock(transparentBlocks, range.get(skillLevel - 1));

            TemporaryEntity temporaryEntity = new TemporaryEntity(targetBlock.getLocation().clone().add(0, 0.5, 0), caster);

            temporaryEntities.add(temporaryEntity);

            Location eyeLocation = target.getEyeLocation();
            Location targetLocation = temporaryEntity.getLocation().add(0, 0.5, 0);
            ParticleShapes.drawLineBetween(eyeLocation.getWorld(), eyeLocation.toVector(), arrangementSingle, targetLocation.toVector(), gap);
        }

        if (temporaryEntities.isEmpty()) return false;

        temporaryEntities = determineTargets(caster, temporaryEntities);

        if (temporaryEntities.isEmpty()) return false;

        List<LivingEntity> targetsNew = new ArrayList<>();
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                temporaryEntities.addAll(targets);
                targetsNew = temporaryEntities;
            } else {
                targetsNew.addAll(targets);
                targetsNew.addAll(temporaryEntities);
            }
        } else {
            targetsNew = temporaryEntities;
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatPalette.YELLOW + "Range: " + range.get(skillLevel));
        } else if (skillLevel == range.size()) {
            additions.add(ChatPalette.YELLOW + "Range: " + range.get(skillLevel - 1));
        } else {
            additions.add(ChatPalette.YELLOW + "Range: " + range.get(skillLevel - 1) + " -> " + range.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
