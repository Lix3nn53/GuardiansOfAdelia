package io.github.lix3nn53.guardiansofadelia.utilities;

import com.google.common.collect.ImmutableList;
import com.sucy.skill.dynamic.ComponentType;
import com.sucy.skill.dynamic.custom.CustomEffectComponent;
import com.sucy.skill.dynamic.custom.EditorOption;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class SkillAPIHologramDestroyMechanic extends CustomEffectComponent {

    private final String key = "Custom Hologram Destroy";

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.MECHANIC;
    }

    @Override
    public String getDescription() {
        return "Destroy a hologram";
    }

    @Override
    public List<EditorOption> getOptions() {
        return ImmutableList.of();
    }

    @Override
    public boolean execute(LivingEntity caster, int level, List<LivingEntity> list) {
        for (LivingEntity livingEntity : list) {
            if (livingEntity.getType().equals(EntityType.ARMOR_STAND)) {
                livingEntity.remove();
            }
        }
        return true;
    }
}
