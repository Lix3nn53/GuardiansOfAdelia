package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class InvincibleRemoveMechanic extends MechanicComponent {

    private final int delay;

    public InvincibleRemoveMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("delay")) {
            configLoadError("delay");
        }

        this.delay = configurationSection.getInt("delay");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        new BukkitRunnable() {

            @Override
            public void run() {
                for (LivingEntity ent : targets) {
                    ImmunityListener.removeInvincible(ent);
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), delay);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
