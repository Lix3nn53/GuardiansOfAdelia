package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

public enum BuffType {
    ELEMENT_DAMAGE,
    ELEMENT_DEFENSE,
    CRIT_DAMAGE,
    CRIT_CHANCE,
    COOLDOWN_REDUCTION;

    @Override
    public String toString() {
        switch (this) {
            case ELEMENT_DAMAGE:
                return ChatColor.RED + "Element Damage Buff";
            case ELEMENT_DEFENSE:
                return ChatColor.AQUA + "Element Defense Buff";
            case CRIT_DAMAGE:
                return ChatColor.GOLD + "Critical Damage Buff";
            case CRIT_CHANCE:
                return ChatColor.DARK_PURPLE + "Critical Chance Buff";
            case COOLDOWN_REDUCTION:
                return ChatColor.LIGHT_PURPLE + "Cooldown Reduction Buff";
        }
        return "";
    }

    /**
     * @return potion effect type to let player know he got a buff
     */
    public PotionEffectType getPotionEffectType() {
        switch (this) {
            case ELEMENT_DAMAGE:
                return PotionEffectType.INCREASE_DAMAGE;
            case ELEMENT_DEFENSE:
                return PotionEffectType.DAMAGE_RESISTANCE;
            /*case MAGIC_DAMAGE:
                return PotionEffectType.DOLPHINS_GRACE;
            case MAGIC_DEFENSE:
                return PotionEffectType.WEAKNESS;*/
            case CRIT_DAMAGE:
                return PotionEffectType.UNLUCK;
            case CRIT_CHANCE:
                return PotionEffectType.LUCK;
            case COOLDOWN_REDUCTION:
                return PotionEffectType.DOLPHINS_GRACE;
        }
        return null;
    }
}
