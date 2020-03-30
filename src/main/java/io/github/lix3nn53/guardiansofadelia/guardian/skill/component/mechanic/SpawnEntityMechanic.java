package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntity;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntityManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class SpawnEntityMechanic extends MechanicComponent {

    private final List<String> adeliaEntity;
    private final int amountPerSpawn;

    public SpawnEntityMechanic(List<String> adeliaEntity, int amountPerSpawn) {
        this.adeliaEntity = adeliaEntity;
        this.amountPerSpawn = amountPerSpawn;
    }

    public SpawnEntityMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("adeliaEntityList")) {
            configLoadError("adeliaEntityList");
        }

        if (!configurationSection.contains("amountPerSpawn")) {
            configLoadError("amountPerSpawn");
        }

        List<String> adeliaEntityList = configurationSection.getStringList("adeliaEntityList");
        int amountPerSpawn = configurationSection.getInt("amountPerSpawn");

        this.adeliaEntity = adeliaEntityList;
        this.amountPerSpawn = amountPerSpawn;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            for (String ent : adeliaEntity) {
                for (int i = 0; i < amountPerSpawn; i++) {
                    AdeliaEntity entity = AdeliaEntityManager.getEntity(ent);
                    entity.getMob(target.getLocation());
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
