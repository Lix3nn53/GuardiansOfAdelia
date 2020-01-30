package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class RootMechanic extends MechanicComponent {

    private final List<Integer> duration;
    private final boolean lockY;

    public RootMechanic(List<Integer> duration, boolean lockY) {
        this.duration = duration;
        this.lockY = lockY;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            StatusEffectManager.setRooted(target, lockY);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (LivingEntity target : targets) {
                    StatusEffectManager.removeRooted(target);
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), duration.get(skillLevel - 1));

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.AQUA + "Root duration: " + ((duration.get(skillLevel) / 20)) + " seconds");
        } else if (skillLevel == duration.size()) {
            additions.add(ChatColor.AQUA + "Root duration: " + ((duration.get(skillLevel - 1) / 20)) + " seconds");
        } else {
            additions.add(ChatColor.AQUA + "Root duration: " + ((duration.get(skillLevel - 1) / 20)) + " seconds -> " + (int) ((duration.get(skillLevel) / 20) + 0.5));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
