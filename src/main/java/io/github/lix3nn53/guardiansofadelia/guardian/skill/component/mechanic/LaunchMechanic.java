package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class LaunchMechanic extends MechanicComponent {

    private final Relative relative;
    private final List<Double> forwardSpeedList;
    private final List<Double> upwardSpeedList;
    private final List<Double> rightSpeedList;

    public LaunchMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("relative")) {
            configLoadError("relative");
        }

        if (!configurationSection.contains("forwardSpeedList")) {
            configLoadError("forwardSpeedList");
        }

        if (!configurationSection.contains("upwardSpeedList")) {
            configLoadError("upwardSpeedList");
        }

        if (!configurationSection.contains("rightSpeedList")) {
            configLoadError("rightSpeedList");
        }

        this.relative = Relative.valueOf(configurationSection.getString("relative"));
        this.forwardSpeedList = configurationSection.getDoubleList("forwardSpeedList");
        this.upwardSpeedList = configurationSection.getDoubleList("upwardSpeedList");
        this.rightSpeedList = configurationSection.getDoubleList("rightSpeedList");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            final Vector dir;

            if (relative.equals(Relative.CASTER)) {
                dir = caster.getLocation().getDirection().setY(0).normalize();
            } else if (relative.equals(Relative.BETWEEN)) {
                dir = ent.getLocation().toVector().subtract(caster.getLocation().toVector()).setY(0).normalize();
            } else { // Relative.TARGET
                dir = ent.getLocation().getDirection().setY(0).normalize();
            }

            final Vector nor = dir.clone().crossProduct(UP);
            dir.multiply(forwardSpeedList.get(skillLevel - 1));
            dir.add(nor.multiply(rightSpeedList.get(skillLevel - 1))).setY(upwardSpeedList.get(skillLevel - 1));

            ent.setVelocity(dir);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatColor.AQUA + "Launch forward: " + forwardSpeedList.get(skillLevel));
            additions.add(ChatColor.AQUA + "Launch upward: " + upwardSpeedList.get(skillLevel));
            additions.add(ChatColor.AQUA + "Launch right: " + rightSpeedList.get(skillLevel));
        } else if (skillLevel == upwardSpeedList.size()) {
            additions.add(ChatColor.AQUA + "Launch forward: " + forwardSpeedList.get(skillLevel - 1));
            additions.add(ChatColor.AQUA + "Launch upward: " + upwardSpeedList.get(skillLevel - 1));
            additions.add(ChatColor.AQUA + "Launch right: " + rightSpeedList.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.AQUA + "Launch forward: " + forwardSpeedList.get(skillLevel - 1) + " -> " + forwardSpeedList.get(skillLevel));
            additions.add(ChatColor.AQUA + "Launch upward: " + upwardSpeedList.get(skillLevel - 1) + " -> " + upwardSpeedList.get(skillLevel));
            additions.add(ChatColor.AQUA + "Launch right: " + rightSpeedList.get(skillLevel - 1) + " -> " + rightSpeedList.get(skillLevel));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    public enum Relative {
        CASTER,
        BETWEEN,
        TARGET
    }
}
