package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class HeadRotationMechanic extends MechanicComponent {

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
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
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
