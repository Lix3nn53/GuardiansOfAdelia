package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashSet;
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
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        HashSet<Player> players = new HashSet<>();

        for (LivingEntity target : targets) {
            for (PotionEffectType potionEffectType : potionEffectTypes) {
                target.removePotionEffect(potionEffectType);
            }

            if (target instanceof Player) {
                players.add((Player) target);
            }
        }

        for (Player player : players) {
            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                    RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                    rpgCharacterStats.reapplyGearSetEffects();
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
