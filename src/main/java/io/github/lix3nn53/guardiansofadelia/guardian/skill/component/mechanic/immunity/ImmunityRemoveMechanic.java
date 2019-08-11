package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ImmunityRemoveMechanic extends MechanicComponent {

    private final EntityDamageEvent.DamageCause damageCause;
    private final int delay;

    public ImmunityRemoveMechanic(EntityDamageEvent.DamageCause damageCause, int delay) {
        this.damageCause = damageCause;
        this.delay = delay;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
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
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
