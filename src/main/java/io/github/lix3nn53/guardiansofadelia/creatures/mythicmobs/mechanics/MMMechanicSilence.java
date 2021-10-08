package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectType;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import org.bukkit.entity.LivingEntity;

public class MMMechanicSilence extends SkillMechanic implements ITargetedEntitySkill {

    protected final int duration;

    public MMMechanicSilence(MythicLineConfig config) {
        super(config.getLine(), config);
        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);

        this.duration = config.getInteger(new String[]{"duration", "d"}, 100);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity abstractEntity) {
        LivingEntity target = (LivingEntity) BukkitAdapter.adapt(abstractEntity);

        StatusEffectType effectType = StatusEffectType.SILENCE;

        StatusEffectManager.addStatus(target, effectType, duration);
        return true;
    }
}
