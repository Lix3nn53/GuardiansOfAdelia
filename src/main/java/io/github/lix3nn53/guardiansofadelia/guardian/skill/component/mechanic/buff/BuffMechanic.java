package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuffMechanic extends MechanicComponent {

    private final BuffType buffType;
    private final List<Double> multiplier;
    private final List<Integer> ticks;

    public BuffMechanic(BuffType buffType, List<Double> multiplier, List<Integer> ticks) {
        this.buffType = buffType;
        this.multiplier = multiplier;
        this.ticks = ticks;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        List<Player> buffedPlayers = new ArrayList<>();

        double multiplierToUse = multiplier.get(skillLevel - 1);

        boolean buffedAPlayer = false;
        for (LivingEntity ent : targets) {
            if (ent instanceof Player) {
                Player player = (Player) ent;
                UUID uuid = player.getUniqueId();
                if (GuardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                        rpgCharacterStats.addToBuffMultiplier(buffType, multiplierToUse);

                        //add +2 ticks to duration because of repeating buffs icons disappear otherwise. Amplifier 0 anyways
                        PotionEffect potionEffect = new PotionEffect(buffType.getPotionEffectType(), ticks.get(skillLevel - 1) + 2, 0);
                        player.addPotionEffect(potionEffect, true);

                        buffedPlayers.add(player);
                        buffedAPlayer = true;
                    }
                }
            }
        }

        if (!buffedAPlayer) return false;

        new BukkitRunnable() { //remove buffs from buffed players

            @Override
            public void run() {
                for (Player player : buffedPlayers) {
                    UUID uuid = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                            rpgCharacterStats.addToBuffMultiplier(buffType, -multiplierToUse);
                        }
                    }
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), ticks.get(skillLevel - 1));

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(buffType.toString() + " bonus: " + multiplier.get(skillLevel));
            additions.add(buffType.toString() + " duration: " + (ticks.get(skillLevel) / 20));
        } else if (skillLevel == multiplier.size()) {
            additions.add(buffType.toString() + " bonus: " + multiplier.get(skillLevel - 1));
            additions.add(buffType.toString() + " duration: " + (ticks.get(skillLevel - 1) / 20));
        } else {
            additions.add(buffType.toString() + " bonus: " + multiplier.get(skillLevel - 1) + "x -> " + multiplier.get(skillLevel) + "x");
            additions.add(buffType.toString() + " duration: " + (ticks.get(skillLevel - 1) / 20) + " seconds -> " + (ticks.get(skillLevel) / 20));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
