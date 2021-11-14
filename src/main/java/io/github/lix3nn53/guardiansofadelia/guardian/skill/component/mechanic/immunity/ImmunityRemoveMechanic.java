package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ImmunityRemoveMechanic extends MechanicComponent {

    private final EntityDamageEvent.DamageCause damageCause;
    private final int delay;

    public ImmunityRemoveMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("damageCause")) {
            configLoadError("damageCause");
        }

        if (!configurationSection.contains("delay")) {
            configLoadError("delay");
        }

        this.damageCause = EntityDamageEvent.DamageCause.valueOf(configurationSection.getString("damageCause"));
        this.delay = configurationSection.getInt("delay");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        new BukkitRunnable() {

            @Override
            public void run() {
                for (LivingEntity ent : targets) {
                    ImmunityListener.removeImmunity(ent, damageCause);
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), delay);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
