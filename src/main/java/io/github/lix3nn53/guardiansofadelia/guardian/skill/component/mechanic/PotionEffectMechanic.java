package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectMechanic extends MechanicComponent {

    private final PotionEffectType type;
    private final List<Integer> ticks;
    private final List<Integer> amplifier;

    public PotionEffectMechanic(PotionEffectType type, List<Integer> ticks, List<Integer> amplifier) {
        this.type = type;
        this.ticks = ticks;
        this.amplifier = amplifier;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        PotionEffect potionEffect = new PotionEffect(type, ticks.get(skillLevel - 1), amplifier.get(skillLevel - 1),
                false, false, true);

        for (LivingEntity target : targets) {
            target.addPotionEffect(potionEffect, true);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        if (skillLevel == 0 || skillLevel == ticks.size()) {
            lore.add(type.getName() + " duration: " + (ticks.get(skillLevel) / 20));
            lore.add(type.getName() + " tier: " + amplifier.get(skillLevel));
        } else {
            lore.add(type.getName() + " duration: " + (ticks.get(skillLevel - 1) / 20) + " -> " + (ticks.get(skillLevel) / 20));
            lore.add(type.getName() + " tier: " + amplifier.get(skillLevel - 1) + " -> " + amplifier.get(skillLevel));
        }
        return lore;
    }
}
