package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LaunchMechanic extends MechanicComponent {

    private final Relative relative;
    private final List<Float> forwardSpeedList;
    private final List<Float> upwardSpeedList;
    private final List<Float> rightSpeedList;

    private final String multiplyWithValue;

    public LaunchMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("relative")) {
            configLoadError("relative");
        }

        this.relative = Relative.valueOf(configurationSection.getString("relative"));
        this.forwardSpeedList = configurationSection.contains("forwardSpeedList") ? configurationSection.getFloatList("forwardSpeedList") : new ArrayList<>();
        this.upwardSpeedList = configurationSection.contains("upwardSpeedList") ? configurationSection.getFloatList("upwardSpeedList") : new ArrayList<>();
        this.rightSpeedList = configurationSection.contains("rightSpeedList") ? configurationSection.getFloatList("rightSpeedList") : new ArrayList<>();

        this.multiplyWithValue = configurationSection.contains("multiplyWithValue") ? configurationSection.getString("multiplyWithValue") : null;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        float forward = skillLevel - 1 < forwardSpeedList.size() ? forwardSpeedList.get(skillLevel - 1) : 0;
        float right = skillLevel - 1 < rightSpeedList.size() ? rightSpeedList.get(skillLevel - 1) : 0;
        float upward = skillLevel - 1 < upwardSpeedList.size() ? upwardSpeedList.get(skillLevel - 1) : 0;

        for (LivingEntity ent : targets) {
            final Vector dir;

            float forward2use = forward;
            float right2use = right;
            float upward2use = upward;

            if (multiplyWithValue != null) {
                int value = SkillDataManager.getValue(ent, multiplyWithValue);
                if (value > 0) {
                    forward2use *= value;
                    right2use *= value;
                    upward2use *= value;
                }
            }

            if (relative.equals(Relative.CASTER)) {
                dir = caster.getLocation().getDirection().setY(0).normalize();
            } else if (relative.equals(Relative.BETWEEN)) {
                dir = ent.getLocation().toVector().subtract(caster.getLocation().toVector()).setY(0).normalize();
            } else { // Relative.TARGET
                dir = ent.getLocation().getDirection().setY(0).normalize();
            }

            final Vector nor = dir.clone().crossProduct(UP);
            dir.multiply(forward2use);
            dir.add(nor.multiply(right2use)).setY(upward2use);

            ent.setVelocity(dir);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);


        if (skillLevel == 0) {
            float forward = skillLevel < forwardSpeedList.size() ? forwardSpeedList.get(skillLevel) : 0;
            float right = skillLevel < rightSpeedList.size() ? rightSpeedList.get(skillLevel) : 0;
            float upward = skillLevel < upwardSpeedList.size() ? upwardSpeedList.get(skillLevel) : 0;

            additions.add(ChatPalette.BLUE_LIGHT + "Launch[forward, upward, right]: " + forward + ", " + upward + ", " + right);
        } else if (skillLevel == upwardSpeedList.size()) {
            float forward = skillLevel - 1 < forwardSpeedList.size() ? forwardSpeedList.get(skillLevel - 1) : 0;
            float right = skillLevel - 1 < rightSpeedList.size() ? rightSpeedList.get(skillLevel - 1) : 0;
            float upward = skillLevel - 1 < upwardSpeedList.size() ? upwardSpeedList.get(skillLevel - 1) : 0;

            additions.add(ChatPalette.BLUE_LIGHT + "Launch[forward, upward, right]: " + forward + ", " + upward + ", " + right);
        } else {
            float forward = skillLevel - 1 < forwardSpeedList.size() ? forwardSpeedList.get(skillLevel - 1) : 0;
            float right = skillLevel - 1 < rightSpeedList.size() ? rightSpeedList.get(skillLevel - 1) : 0;
            float upward = skillLevel - 1 < upwardSpeedList.size() ? upwardSpeedList.get(skillLevel - 1) : 0;

            float forward2 = skillLevel < forwardSpeedList.size() ? forwardSpeedList.get(skillLevel) : 0;
            float right2 = skillLevel < rightSpeedList.size() ? rightSpeedList.get(skillLevel) : 0;
            float upward2 = skillLevel < upwardSpeedList.size() ? upwardSpeedList.get(skillLevel) : 0;

            additions.add(ChatPalette.BLUE_LIGHT + "Launch[forward, upward, right]: " + forward + ", " + upward + ", " + right +
                    " -> " + forward2 + ", " + upward2 + ", " + right2);
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    public enum Relative {
        CASTER,
        BETWEEN,
        TARGET
    }
}
