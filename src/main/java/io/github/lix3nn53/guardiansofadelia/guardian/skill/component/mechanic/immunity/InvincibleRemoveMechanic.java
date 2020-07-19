package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class InvincibleRemoveMechanic extends MechanicComponent {

    private final int delay;

    public InvincibleRemoveMechanic(int delay) {
        this.delay = delay;
    }

    public InvincibleRemoveMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("delay")) {
            configLoadError("delay");
        }

        this.delay = configurationSection.getInt("delay");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            ImmunityListener.removeInvincible(ent);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
