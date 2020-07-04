package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.TemporaryEntity;
import org.bukkit.ChatColor;
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

    public LocationTarget(boolean allies, boolean enemy, boolean self, int max, List<Integer> range) {
        super(allies, enemy, self, max);
        this.range = range;
    }

    public LocationTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("ranges")) {
            configLoadError("ranges");
        }

        this.range = configurationSection.getIntegerList("ranges");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> temporaryEntities = new ArrayList<>();

        for (LivingEntity target : targets) {
            HashSet<Material> transparentBlocks = new HashSet<>();
            transparentBlocks.add(Material.AIR);
            transparentBlocks.add(Material.WATER);

            Block targetBlock = target.getTargetBlock(transparentBlocks, range.get(skillLevel - 1));

            TemporaryEntity temporaryEntity = new TemporaryEntity(targetBlock.getLocation().clone().add(0, 0.5, 0), caster);

            temporaryEntities.add(temporaryEntity);
        }

        if (temporaryEntities.isEmpty()) return false;

        temporaryEntities = determineTargets(caster, temporaryEntities);

        if (temporaryEntities.isEmpty()) return false;

        return executeChildren(caster, skillLevel, temporaryEntities, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.YELLOW + "Range: " + range.get(skillLevel));
        } else if (skillLevel == range.size()) {
            additions.add(ChatColor.YELLOW + "Range: " + range.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.YELLOW + "Range: " + range.get(skillLevel - 1) + " -> " + range.get(skillLevel));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
