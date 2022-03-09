package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.MMManager;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.SkillManager;
import io.lumine.mythic.core.skills.SkillMechanic;
import org.bukkit.entity.LivingEntity;

public class MMMechanicElementResistance extends SkillMechanic implements ITargetedEntitySkill {

    protected final String internalName;
    protected final ElementType elementType;
    protected final float resistance;

    public MMMechanicElementResistance(SkillManager skillManager, MythicLineConfig config) {
        super(skillManager, config.getLine(), config);

        internalName = config.getKey();

        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);

        this.elementType = ElementType.valueOf(config.getString(new String[]{"element", "e"}, "FIRE"));
        this.resistance = config.getFloat(new String[]{"resistance", "r"}, 1.0f);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity abstractEntity) {
        LivingEntity target = (LivingEntity) BukkitAdapter.adapt(abstractEntity);

        String name = abstractEntity.getName();
        GuardiansOfAdelia.getInstance().getLogger().info("name: " + name);
        GuardiansOfAdelia.getInstance().getLogger().info("internalName: " + internalName);

        MMManager.addElementResistance(internalName, elementType, resistance);

        return SkillResult.SUCCESS;
    }
}
