package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AddPiercingToArrowShootFromCrossbowTrigger extends TriggerComponent {

    private final List<Integer> piercingLevel;

    LivingEntity caster;
    int skillLevel;

    public AddPiercingToArrowShootFromCrossbowTrigger(List<Integer> piercingLevel) {
        this.piercingLevel = piercingLevel;
    }

    public AddPiercingToArrowShootFromCrossbowTrigger(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("piercingLevels")) {
            configLoadError("piercingLevels");
        }

        this.piercingLevel = configurationSection.getIntegerList("piercingLevels");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        this.caster = caster;
        this.skillLevel = skillLevel;

        AddPiercingToArrowShootFromCrossbowTrigger rangedAttackTrigger = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    if (target instanceof Player) {
                        TriggerListener.startListeningAddPiercingToArrowShootFromCrossbowTrigger((Player) target, rangedAttackTrigger);
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 10L);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.GOLD + "Pierce amount: " + piercingLevel.get(skillLevel));
        } else if (skillLevel == piercingLevel.size()) {
            additions.add(ChatColor.GOLD + "Pierce amount: " + piercingLevel.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.GOLD + "Pierce amount: " + piercingLevel.get(skillLevel - 1) + " -> " + piercingLevel.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    /**
     * The callback when player lands that applies child components
     */
    public void callback(Arrow arrow) {
        arrow.setPierceLevel(piercingLevel.get(skillLevel - 1));
    }
}
