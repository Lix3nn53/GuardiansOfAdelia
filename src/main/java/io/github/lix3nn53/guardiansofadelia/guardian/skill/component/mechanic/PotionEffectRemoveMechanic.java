package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectRemoveMechanic extends MechanicComponent {

    private final List<PotionEffectType> potionEffectTypes = new ArrayList<>();

    public PotionEffectRemoveMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("potionEffectType")) {
            configLoadError("potionEffectType");
        }

        List<String> potionEffectTypeStr = configurationSection.getStringList("potionEffectType");
        for (String potionStr : potionEffectTypeStr) {
            PotionEffectType byName = PotionEffectType.getByName(potionStr);
            potionEffectTypes.add(byName);
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (PotionEffectType potionEffectType : potionEffectTypes) {
            for (LivingEntity target : targets) {
                target.removePotionEffect(potionEffectType);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
