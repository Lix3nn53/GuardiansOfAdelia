package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DamageMechanic extends MechanicComponent {

	private double baseDamage;
	private double levelMultiplier;
	private SkillDamageType type;

	public DamageMechanic(double baseDamage, double levelMultiplier, SkillDamageType type) {
		this.baseDamage = baseDamage;
		this.levelMultiplier = levelMultiplier;
		this.type = type;
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
					if(type == SkillDamageType.MAGIC)
						calcDamage += targetRpgCharacterStats.getTotalMagicDamage((Player) caster, activeCharacter.getRpgClass());
					else if(type == SkillDamageType.MELEE)
						calcDamage += targetRpgCharacterStats.getTotalMeleeDamage((Player) caster, activeCharacter.getRpgClass());
					else if(type == SkillDamageType.RANGED)
						calcDamage += targetRpgCharacterStats.getTotalRangedDamage((Player) caster, activeCharacter.getRpgClass());
				}
			}
		}

        for (LivingEntity ent : targets) {
			//TODO SKILL DAMAGE DETECT
			ent.damage(calcDamage, caster);
		}
		return targets.size() > 0;
	}

	@Override
	public List<String> getSkillLoreAdditions(int skillLevel) {
		List<String> lore = new ArrayList<String>();
		lore.add((type == SkillDamageType.MAGIC ? "Magic Damage: " : 
			(type == SkillDamageType.MELEE ? "Melee damage: " : "Ranged damage:")) 
				+ (baseDamage + (levelMultiplier * skillLevel)));
		return null;
	}
	
	
	public enum SkillDamageType {
        MAGIC, MELEE, RANGED
    }
}
