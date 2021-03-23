package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class PushMechanic extends MechanicComponent {

    private final PushType pushType;
    private final List<Double> speedList;
    private final boolean centerSelf;
    private final double offsetY;

    public PushMechanic(PushType pushType, List<Double> speedList, boolean centerSelf, double offsetY) {
        super(false);

        this.pushType = pushType;
        this.speedList = speedList;
        this.centerSelf = centerSelf;
        this.offsetY = offsetY;
    }

    public PushMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("pushType")) {
            configLoadError("pushType");
        }

        if (!configurationSection.contains("speedList")) {
            configLoadError("speedList");
        }

        if (!configurationSection.contains("centerSelf")) {
            configLoadError("centerSelf");
        }

        if (configurationSection.contains("offsetY")) {
            this.offsetY = configurationSection.getDouble("offsetY");
        } else {
            offsetY = 0;
        }

        this.pushType = PushType.valueOf(configurationSection.getString("pushType"));
        this.speedList = configurationSection.getDoubleList("speedList");
        this.centerSelf = configurationSection.getBoolean("centerSelf");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        Location center;
        if (centerSelf) {
            center = caster.getLocation();
        } else {
            center = targets.get(0).getLocation();
            targets.remove(0);
        }

        if (offsetY != 0) {
            center = center.add(0, offsetY, 0);
        }

        for (LivingEntity target : targets) {
            final Vector vel = target.getLocation().subtract(center).toVector();
            if (vel.lengthSquared() == 0) {
                continue;
            } else if (pushType.equals(PushType.INVERSE)) {
                vel.multiply(speedList.get(skillLevel - 1));
            } else if (pushType.equals(PushType.FIXED)) {
                vel.multiply(speedList.get(skillLevel - 1) / vel.length());
            } else { // SCALED
                vel.multiply(speedList.get(skillLevel - 1) / vel.lengthSquared());
            }
            target.setVelocity(vel);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatColor.AQUA + "Push speed: " + speedList.get(skillLevel));
        } else if (skillLevel == speedList.size()) {
            additions.add(ChatColor.AQUA + "Push speed: " + speedList.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.AQUA + "Push speed: " + speedList.get(skillLevel - 1) + " -> " + speedList.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    public enum PushType {
        FIXED,
        INVERSE,
        SCALED
    }
}
