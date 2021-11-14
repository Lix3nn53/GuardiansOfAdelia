package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class HeadRotationMechanic extends MechanicComponent {

    public HeadRotationMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;
        if (targets.size() < 2) return false;

        LivingEntity centerEntity = targets.get(0);
        LivingEntity targetEntity = targets.get(1);

        Location centerLoc = centerEntity.getLocation();
        Vector center = centerLoc.toVector();
        Vector target = targetEntity.getLocation().toVector();

        Vector subtract = target.subtract(center);
        Location location = centerLoc.setDirection(subtract);
        centerEntity.teleport(location);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {

        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
