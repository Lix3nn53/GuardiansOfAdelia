package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectMechanic extends MechanicComponent {

    private final PotionEffect potionEffect;

    public PotionEffectMechanic(PotionEffectType type, int duration, int amplifier) {
        potionEffect = new PotionEffect(type, duration, amplifier, false);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            target.addPotionEffect(potionEffect);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        lore.add(potionEffect.getType() + " duration: " + potionEffect.getDuration());
        lore.add(potionEffect.getType() + " tier: " + potionEffect.getAmplifier());
        return lore;
    }
}
