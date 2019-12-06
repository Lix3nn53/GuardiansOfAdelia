package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntity;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class SpawnEntityMechanic extends MechanicComponent {

    private final List<AdeliaEntity> adeliaEntity;
    private final int amountPerSpawn;

    public SpawnEntityMechanic(List<AdeliaEntity> adeliaEntity, int amountPerSpawn) {
        this.adeliaEntity = adeliaEntity;
        this.amountPerSpawn = amountPerSpawn;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            for (AdeliaEntity ent : adeliaEntity) {
                for (int i = 0; i < amountPerSpawn; i++) {
                    ent.getMob(target.getLocation());
                }
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
