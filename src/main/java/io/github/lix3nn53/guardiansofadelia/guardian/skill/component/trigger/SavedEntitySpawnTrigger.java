package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SavedEntitySpawnTrigger extends TriggerComponent {

    private final List<Integer> cooldowns;
    LivingEntity caster;
    int skillLevel;
    int castCounter;

    public SavedEntitySpawnTrigger(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (configurationSection.contains("cooldowns")) {
            this.cooldowns = configurationSection.getIntegerList("cooldowns");
        } else {
            this.cooldowns = new ArrayList<>();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        this.caster = caster;
        this.skillLevel = skillLevel;
        this.castCounter = castCounter;

        SavedEntitySpawnTrigger savedEntitySpawnTrigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    if (target instanceof Player) {
                        TriggerListener.startListeningSavedEntitySpawn((Player) target, savedEntitySpawnTrigger);
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

        SavedEntitySpawnTrigger trigger = this;

        if (cooldowns.isEmpty()) {
            TriggerListener.startListeningSavedEntitySpawn(player, trigger);
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    TriggerListener.startListeningSavedEntitySpawn(player, trigger);
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldowns.get(skillLevel - 1));
        }

        return true;
    }
}
