package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class WarpTargetMechanic extends MechanicComponent {

    private final boolean toTarget;
    private final boolean toCaster;

    public WarpTargetMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("toTarget")) {
            configLoadError("toTarget");
        }

        if (!configurationSection.contains("toCaster")) {
            configLoadError("toCaster");
        }

        this.toTarget = configurationSection.getBoolean("toTarget");
        this.toCaster = configurationSection.getBoolean("toCaster");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        Location casterStartLocation = caster.getLocation();

        if (toTarget) {
            LivingEntity target = targets.get(0);
            caster.teleport(target);
        }

        if (toCaster) {
            for (LivingEntity target : targets) {
                target.teleport(casterStartLocation);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
