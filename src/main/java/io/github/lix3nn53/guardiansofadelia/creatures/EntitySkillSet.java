package io.github.lix3nn53.guardiansofadelia.creatures;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntitySkillSet {

    private final List<EntitySkill> skills;
    private final List<Integer> skillLevels;
    private final long cooldown;

    public EntitySkillSet(List<EntitySkill> skills, List<Integer> skillLevels, long cooldown) {
        this.skills = skills;
        this.skillLevels = skillLevels;
        this.cooldown = cooldown;
    }

    public void startSkillLoop(LivingEntity livingEntity) {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (!livingEntity.isValid()) { //stop loop if entity is not valid
                    cancel();
                    return;
                }

                if (livingEntity instanceof Mob) { //do not cast skill if there is no target
                    LivingEntity target = ((Mob) livingEntity).getTarget();
                    if (target == null) return;
                }

                int index = 0;

                if (skills.size() > 1) {
                    index = getRandomIndex();
                }

                EntitySkill skill = skills.get(index);
                int level = skillLevels.get(index);

                EntitySkill.getSkillTrigger(skill).execute(livingEntity, level, new ArrayList<>());
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 40L, cooldown);
    }

    private int getRandomIndex() {
        int size = skills.size();
        return new Random().nextInt(size);
    }
}
