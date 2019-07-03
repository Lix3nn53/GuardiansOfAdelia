package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TookMagicalDamageTrigger extends TriggerComponent {

    private final long cooldown;
    LivingEntity caster;
    int skillLevel;
    String castKey;

    public TookMagicalDamageTrigger(long cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        this.caster = caster;
        this.skillLevel = skillLevel;
        this.castKey = castKey;

        TookMagicalDamageTrigger tookMagicalDamageTrigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    if (target instanceof Player) {
                        TriggerListener.startListeningMagicalDamage((Player) target, tookMagicalDamageTrigger);
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 10L);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }

    /**
     * The callback when player lands that applies child components
     */
    public void callback(Player target) {
        ArrayList<LivingEntity> targets = new ArrayList<>();
        targets.add(target);
        executeChildren(caster, skillLevel, targets, castKey);

        TookMagicalDamageTrigger trigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                TriggerListener.startListeningMagicalDamage(target, trigger);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldown);
    }
}
