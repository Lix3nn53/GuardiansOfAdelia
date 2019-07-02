package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuffMechanic extends MechanicComponent {

    private final BuffType buffType;
    private final double multiplier;
    private final double multiplierBonusPerLevel;
    private final long ticks;

    public BuffMechanic(BuffType buffType, double multiplier, long ticks, double multiplierBonusPerLevel) {
        this.buffType = buffType;
        this.multiplier = multiplier;
        this.ticks = ticks;
        this.multiplierBonusPerLevel = multiplierBonusPerLevel;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        List<Player> buffedPlayers = new ArrayList<>();

        double multiplierToUse = multiplier + (multiplierBonusPerLevel * (skillLevel - 1));

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

                        buffedPlayers.add(player);
                    }
                }
            }
        }

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
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), ticks);

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        lore.add(buffType.name() + " multiplier: " + multiplier);
        lore.add(buffType.name() + " duration: " + (ticks / 20) + " seconds");
        return lore;
    }
}
