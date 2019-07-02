package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FlagSetMechanic extends MechanicComponent {

    private final String key;
    private final long ticks;

    public FlagSetMechanic(String key, long ticks) {
        this.key = key;
        this.ticks = ticks;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        SkillDataManager.addFlag(castKey, key);

        if (ticks > 0) {
            new BukkitRunnable() { //remove buffs from buffed players

                @Override
                public void run() {
                    SkillDataManager.removeFlag(castKey, key);
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), ticks);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }
}
