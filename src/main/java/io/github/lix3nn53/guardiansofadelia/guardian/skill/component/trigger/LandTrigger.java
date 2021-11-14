package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class LandTrigger extends TriggerComponent {

    LivingEntity caster;
    int skillLevel;
    int castCounter;

    public LandTrigger(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        this.skillIndex = skillIndex;
        this.caster = caster;
        this.skillLevel = skillLevel;
        this.castCounter = castCounter;

        LandTrigger landTrigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    if (target instanceof Player) {
                        TriggerListener.add((Player) target, landTrigger);
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 10L);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }

    /**
     * The callback when player lands that applies child components
     */
    public boolean callback(Player player) {
        ArrayList<LivingEntity> targets = new ArrayList<>();
        targets.add(player);

        return executeChildren(caster, skillLevel, targets, castCounter, skillIndex);
    }
}
