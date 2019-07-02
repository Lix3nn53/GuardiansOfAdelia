package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ImmunityMechanic extends MechanicComponent {

    private final EntityDamageEvent.DamageCause damageCause;
    private final long ticks;

    /**
     * @param damageCause
     * @param ticks       0 for infinite
     */
    public ImmunityMechanic(EntityDamageEvent.DamageCause damageCause, long ticks) {
        this.damageCause = damageCause;
        this.ticks = ticks;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            ImmunityListener.addImmunity(ent, damageCause);
        }

        if (ticks > 0) {
            new BukkitRunnable() { //remove buffs from buffed players

                @Override
                public void run() {
                    for (LivingEntity ent : targets) {
                        ImmunityListener.removeImmunity(ent, damageCause);
                    }
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), ticks);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }
}
