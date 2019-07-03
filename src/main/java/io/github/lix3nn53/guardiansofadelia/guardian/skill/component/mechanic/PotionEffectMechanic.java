package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectMechanic extends MechanicComponent {

    private final PotionEffect potionEffect;

    public PotionEffectMechanic(PotionEffectType type, int ticks, int amplifier) {
        potionEffect = new PotionEffect(type, ticks, amplifier, false, false, true);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            target.addPotionEffect(potionEffect, true);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        lore.add(potionEffect.getType() + " duration: " + (potionEffect.getDuration() / 20));
        lore.add(potionEffect.getType() + " tier: " + potionEffect.getAmplifier());
        return lore;
    }
}
