package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ManaMechanic extends MechanicComponent {

    private final List<Integer> manaAmount;
    private final List<Double> manaPercent;

    public ManaMechanic(List<Integer> manaAmount, List<Double> manaPercent) {
        this.manaAmount = manaAmount;
        this.manaPercent = manaPercent;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        boolean manaFilled = false;

        for (LivingEntity ent : targets) {
            if (ent instanceof Player) {
                Player player = (Player) ent;
                UUID uuid = player.getUniqueId();
                if (GuardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                        int currentMana = rpgCharacterStats.getCurrentMana();

                        double maxMana = rpgCharacterStats.getTotalMaxMana();

                        if (currentMana == maxMana) continue;

                        double nextMana = currentMana + manaAmount.get(skillLevel - 1);

                        if (!manaPercent.isEmpty()) {
                            if (manaPercent.get(skillLevel - 1) > 0) {
                                nextMana = nextMana + (maxMana * manaPercent.get(skillLevel - 1));
                            }
                        }

                        if (nextMana > maxMana) {
                            nextMana = maxMana;
                        }

                        rpgCharacterStats.setCurrentMana((int) nextMana);
                        manaFilled = true;
                    }
                }
            }
        }

        return manaFilled;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        if (manaAmount.get(skillLevel - 1) > 0) {
            if (skillLevel == 0 || skillLevel == manaAmount.size()) {
                lore.add(ChatColor.AQUA + "Health regen: " + manaAmount.get(skillLevel));
            } else {
                lore.add(ChatColor.AQUA + "Health regen: " + manaAmount.get(skillLevel - 1) + " -> " + manaAmount.get(skillLevel));
            }
        }
        if (manaPercent.get(skillLevel - 1) > 0) {
            if (skillLevel == 0 || skillLevel == manaPercent.size()) {
                lore.add(ChatColor.AQUA + "Health regen: " + manaPercent.get(skillLevel) + "%");
            } else {
                lore.add(ChatColor.AQUA + "Health regen: " + manaPercent.get(skillLevel - 1) + "%" + " -> " + manaPercent.get(skillLevel) + "%");
            }
        }
        return lore;
    }
}
