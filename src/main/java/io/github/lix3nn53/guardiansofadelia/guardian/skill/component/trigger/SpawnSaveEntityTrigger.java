package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpawnSaveEntityTrigger extends TriggerComponent {

    private final List<Integer> cooldown;
    LivingEntity caster;
    int skillLevel;
    int castCounter;

    public SpawnSaveEntityTrigger(List<Integer> cooldown) {
        this.cooldown = cooldown;
    }

    public SpawnSaveEntityTrigger(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("cooldowns")) {
            configLoadError("cooldowns");
        }

        this.cooldown = configurationSection.getIntegerList("cooldowns");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        this.caster = caster;
        this.skillLevel = skillLevel;
        this.castCounter = castCounter;

        SpawnSaveEntityTrigger rangedAttackTrigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    if (target instanceof Player) {
                        TriggerListener.startListeningSpawnSavedEntity((Player) target, rangedAttackTrigger);
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 10L);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    /**
     * The callback when player lands that applies child components
     */
    public boolean callback(Player player, LivingEntity created) {
        List<LivingEntity> targets = new ArrayList<>();
        targets.add(created);
        boolean cast = executeChildren(caster, skillLevel, targets, castCounter);

        if (!cast) return false;

        SpawnSaveEntityTrigger trigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                TriggerListener.startListeningSpawnSavedEntity(player, trigger);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldown.get(skillLevel - 1) * 20);

        return true;
    }
}
