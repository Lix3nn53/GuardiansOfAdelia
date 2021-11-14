package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.List;

public class AttributeBuffRemoveMechanic extends MechanicComponent {

    private final Attribute attributeType;

    public AttributeBuffRemoveMechanic(Attribute attributeType) {
        super(false);

        this.attributeType = attributeType;
    }

    public AttributeBuffRemoveMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("attributeType")) {
            configLoadError("attributeType");
        }

        this.attributeType = Attribute.valueOf(configurationSection.getString("attributeType"));
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            AttributeInstance attribute = ent.getAttribute(attributeType);
            if (attribute == null) {
                GuardiansOfAdelia.getInstance().getLogger().severe("AttributeBuffRemoveMechanic ATTR NULL");
                continue;
            }

            Collection<AttributeModifier> modifiers = attribute.getModifiers();
            for (AttributeModifier modifier : modifiers) {
                String name = modifier.getName();
                if (name.equals("goaskill")) {
                    attribute.removeModifier(modifier);
                    break;
                }
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
