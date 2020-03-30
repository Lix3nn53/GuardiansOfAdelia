package io.github.lix3nn53.guardiansofadelia.creatures.entitySkills;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntitySkillSet {

    private final List<SkillComponent> skills;
    private final List<Integer> skillLevels;
    private final long cooldown;
    private int castCounter = 0;

    public EntitySkillSet(List<SkillComponent> skills, List<Integer> skillLevels, long cooldown) {
        this.skills = skills;
        this.skillLevels = skillLevels;
        this.cooldown = cooldown;
    }

    public void startSkillLoop(LivingEntity livingEntity) {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (skills.isEmpty()) {
                    cancel();
                    return;
                }

                if (!livingEntity.isValid()) { //stop loop if entity is not valid
                    cancel();
                    return;
                }

                List<LivingEntity> targets = new ArrayList<>();
                if (livingEntity instanceof Mob) { //do not cast skill if there is no target
                    LivingEntity target = ((Mob) livingEntity).getTarget();
                    if (target == null) return;
                    targets.add(target); //add current single target to skill target list
                }

                int index = 0;

                if (skills.size() > 1) {
                    index = getRandomIndex();
                }

                SkillComponent skill = skills.get(index);
                int level = skillLevels.get(index);

                GuardiansOfAdelia.getInstance().getLogger().info(livingEntity.toString());
                GuardiansOfAdelia.getInstance().getLogger().info("" + level);
                GuardiansOfAdelia.getInstance().getLogger().info("" + targets);
                GuardiansOfAdelia.getInstance().getLogger().info("" + castCounter);
                skill.execute(livingEntity, level, targets, castCounter);
                castCounter++;
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 40L, cooldown);
    }

    private int getRandomIndex() {
        int size = skills.size();
        return new Random().nextInt(size);
    }

    public List<SkillComponent> getSkills() {
        return skills;
    }
}
