package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class DamageMechanic extends MechanicComponent {

    private double baseDamage;
    private double levelMultiplier;
    private DamageType damageType;

    public DamageMechanic(double baseDamage, double levelMultiplier, DamageType damageType) {
        this.baseDamage = baseDamage;
        this.levelMultiplier = levelMultiplier;
        this.damageType = damageType;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        double calcDamage = baseDamage + (levelMultiplier * skillLevel);

        for (LivingEntity ent : targets) {
            SkillUtils.setDamageType(damageType);
            ent.setNoDamageTicks(0);
            ent.damage(calcDamage, caster);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        lore.add(damageType.name() + ": " + (int) (baseDamage + (levelMultiplier * skillLevel) + 0.5));
        return lore;
    }


    public enum DamageType {
        MAGIC, MELEE, RANGED
    }
}
