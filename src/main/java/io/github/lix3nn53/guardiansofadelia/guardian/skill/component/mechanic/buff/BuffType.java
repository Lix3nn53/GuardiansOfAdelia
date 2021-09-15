package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff;


import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.potion.PotionEffectType;

public enum BuffType {
    ELEMENT_DAMAGE,
    ELEMENT_DEFENSE,
    CRIT_DAMAGE,
    CRIT_CHANCE,
    ABILITY_HASTE;

    @Override
    public String toString() {
        switch (this) {
            case ELEMENT_DAMAGE:
                return ChatPalette.RED + "Element Damage Buff";
            case ELEMENT_DEFENSE:
                return ChatPalette.BLUE_LIGHT + "Element Defense Buff";
            case CRIT_DAMAGE:
                return ChatPalette.GOLD + "Critical Damage Buff";
            case CRIT_CHANCE:
                return ChatPalette.PURPLE + "Critical Chance Buff";
            case ABILITY_HASTE:
                return ChatPalette.PURPLE_LIGHT + "Ability Haste Buff";
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
            case ABILITY_HASTE:
                return PotionEffectType.DOLPHINS_GRACE;
        }
        return null;
    }
}
