package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BuffMechanic extends MechanicComponent {

    private final BuffType buffType;
    private final List<Double> multiplier;
    private final List<Integer> ticks;

    public BuffMechanic(BuffType buffType, List<Double> multiplier, List<Integer> ticks) {
        super(false);

        this.buffType = buffType;
        this.multiplier = multiplier;
        this.ticks = ticks;
    }

    public BuffMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("buffType")) {
            configLoadError("buffType");
        }

        if (!configurationSection.contains("multipliers")) {
            configLoadError("multipliers");
        }

        if (!configurationSection.contains("ticks")) {
            configLoadError("ticks");
        }

        this.buffType = BuffType.valueOf(configurationSection.getString("buffType"));
        this.multiplier = configurationSection.getDoubleList("multipliers");
        this.ticks = configurationSection.contains("ticks") ? configurationSection.getIntegerList("ticks") : new ArrayList<>();
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        List<Player> buffedPlayers = new ArrayList<>();

        double multiplierToUse = multiplier.get(skillLevel - 1);
        //add +2 ticks to duration because of repeating buffs icons disappear otherwise. Amplifier 0 anyways
        int duration = ticks.isEmpty() ? Integer.MAX_VALUE : ticks.get(skillLevel - 1) + 2;
        PotionEffect potionEffect = new PotionEffect(buffType.getPotionEffectType(), duration, 0);

        for (LivingEntity ent : targets) {
            if (ent instanceof Player) {
                Player player = (Player) ent;
                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                        rpgCharacterStats.addToBuffMultiplier(buffType, multiplierToUse, potionEffect);


                        buffedPlayers.add(player);
                    }
                }
            }
        }

        if (buffedPlayers.isEmpty()) return false;

        if (this.ticks.isEmpty()) return true;
        new BukkitRunnable() { //remove buffs from buffed players after timeout

            @Override
            public void run() {
                for (Player player : buffedPlayers) {
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                            rpgCharacterStats.addToBuffMultiplier(buffType, -multiplierToUse, potionEffect);
                        }
                    }
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), ticks.get(skillLevel - 1));

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

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
