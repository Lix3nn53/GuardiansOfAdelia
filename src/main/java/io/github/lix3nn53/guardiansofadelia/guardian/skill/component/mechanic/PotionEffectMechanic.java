package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
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

        PotionEffect potionEffect = new PotionEffect(type, ticks.get(skillLevel - 1), amplifier.get(skillLevel - 1));

        for (LivingEntity target : targets) {
            target.addPotionEffect(potionEffect, true);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        if (skillLevel == 0 || skillLevel == ticks.size()) {
            lore.add(getEffectString() + " duration: " + (ticks.get(skillLevel) / 20));
            lore.add(getEffectString() + " tier: " + amplifier.get(skillLevel));
        } else {
            lore.add(getEffectString() + " duration: " + (ticks.get(skillLevel - 1) / 20) + " -> " + (ticks.get(skillLevel) / 20));
            lore.add(getEffectString() + " tier: " + amplifier.get(skillLevel - 1) + " -> " + amplifier.get(skillLevel));
        }
        return lore;
    }

    private String getEffectString() {
        if (type.equals(PotionEffectType.SLOW)) {
            return ChatColor.GRAY + "Slow";
        } else if (type.equals(PotionEffectType.JUMP)) {
            return ChatColor.YELLOW + "Jump";
        } else if (type.equals(PotionEffectType.SPEED)) {
            return ChatColor.LIGHT_PURPLE + "Speed";
        } else if (type.equals(PotionEffectType.LEVITATION)) {
            return ChatColor.AQUA + "Levitation";
        }
        return "NULL EFFECT TYPE";
    }
}
