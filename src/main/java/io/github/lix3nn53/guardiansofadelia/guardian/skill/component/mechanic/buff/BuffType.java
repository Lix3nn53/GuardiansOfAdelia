package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

public enum BuffType {
    PHYSICAL_DAMAGE,
    PHYSICAL_DEFENSE,
    MAGIC_DAMAGE,
    MAGIC_DEFENSE,
    CRIT_DAMAGE,
    CRIT_CHANCE;

    @Override
    public String toString() {
        switch (this) {
            case PHYSICAL_DAMAGE:
                return ChatColor.RED + "Physical Damage Buff";
            case PHYSICAL_DEFENSE:
                return ChatColor.AQUA + "Physical Defense Buff";
            case MAGIC_DAMAGE:
                return ChatColor.DARK_AQUA + "Magical Damage Buff";
            case MAGIC_DEFENSE:
                return ChatColor.BLUE + "Magical Defense Buff";
            case CRIT_DAMAGE:
                return ChatColor.GOLD + "Critical Damage Buff";
            case CRIT_CHANCE:
                return ChatColor.LIGHT_PURPLE + "Critical Chance Buff";
        }
        return "";
    }

    /**
     * @return potion effect type to let player know he got a buff
     */
    public PotionEffectType getPotionEffectType() {
        switch (this) {
            case PHYSICAL_DAMAGE:
                return PotionEffectType.INCREASE_DAMAGE;
            case PHYSICAL_DEFENSE:
                return PotionEffectType.DAMAGE_RESISTANCE;
            case MAGIC_DAMAGE:
                return PotionEffectType.DOLPHINS_GRACE;
            case MAGIC_DEFENSE:
                return PotionEffectType.WEAKNESS;
            case CRIT_DAMAGE:
                return PotionEffectType.UNLUCK;
            case CRIT_CHANCE:
                return PotionEffectType.LUCK;
        }
        return null;
    }
}
