package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
		double calcDamage = baseDamage + (levelMultiplier * skillLevel);

		if(caster instanceof Player) {
			UUID uniqueId = caster.getUniqueId();
			if (GuardianDataManager.hasGuardianData(uniqueId)) {
				GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
				if (guardianData.hasActiveCharacter()) {

					RPGCharacter activeCharacter = guardianData.getActiveCharacter();

					RPGCharacterStats targetRpgCharacterStats = activeCharacter.getRpgCharacterStats();
                    if (damageType == DamageType.MAGIC)
						calcDamage += targetRpgCharacterStats.getTotalMagicDamage((Player) caster, activeCharacter.getRpgClass());
                    else if (damageType == DamageType.MELEE)
						calcDamage += targetRpgCharacterStats.getTotalMeleeDamage((Player) caster, activeCharacter.getRpgClass());
                    else if (damageType == DamageType.RANGED)
						calcDamage += targetRpgCharacterStats.getTotalRangedDamage((Player) caster, activeCharacter.getRpgClass());
				}
			}
		}

        for (LivingEntity ent : targets) {
            SkillUtils.setDamageType(damageType);
            ent.setNoDamageTicks(0);
			ent.damage(calcDamage, caster);
            SkillUtils.clearSkillDamage();
		}
		return targets.size() > 0;
	}

	@Override
	public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        lore.add((damageType == DamageType.MAGIC ? "Magic Damage: " :
                (damageType == DamageType.MELEE ? "Melee damage: " : "Ranged damage:"))
				+ (baseDamage + (levelMultiplier * skillLevel)));
		return null;
	}


    public enum DamageType {
        MAGIC, MELEE, RANGED
    }
}
