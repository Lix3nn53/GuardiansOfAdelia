package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.MMManager;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import org.bukkit.entity.LivingEntity;

public class MMMechanicElementResistance extends SkillMechanic implements ITargetedEntitySkill {

    protected final String internalName;
    protected final ElementType elementType;
    protected final float resistance;

    public MMMechanicElementResistance(MythicLineConfig config) {
        super(config.getLine(), config);

        internalName = config.getKey();

        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);

        this.elementType = ElementType.valueOf(config.getString(new String[]{"element", "e"}, "FIRE"));
        this.resistance = config.getFloat(new String[]{"resistance", "r"}, 1.0f);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity abstractEntity) {
        LivingEntity target = (LivingEntity) BukkitAdapter.adapt(abstractEntity);

        String name = abstractEntity.getName();
        GuardiansOfAdelia.getInstance().getLogger().info("name: " + name);
        GuardiansOfAdelia.getInstance().getLogger().info("internalName: " + internalName);

        MMManager.addElementResistance(internalName, elementType, resistance);

        return true;
    }
}
