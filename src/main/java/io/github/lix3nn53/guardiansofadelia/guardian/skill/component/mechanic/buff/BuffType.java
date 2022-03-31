package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff;


import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import org.bukkit.potion.PotionEffectType;

public enum BuffType {
    ELEMENT_DAMAGE,
    ELEMENT_DEFENSE,
    CRIT_DAMAGE,
    CRIT_CHANCE,
    ABILITY_HASTE,
    LIFE_STEAL,
    ELEMENT_FIRE,
    ELEMENT_WATER,
    ELEMENT_EARTH,
    ELEMENT_LIGHTNING,
    ELEMENT_AIR;

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
            case LIFE_STEAL:
                return ChatPalette.GOLD + "Life Steal Buff";
            case ELEMENT_FIRE:
                return ElementType.FIRE.getFullName(Translation.DEFAULT_LANG) + " Buff";
            case ELEMENT_WATER:
                return ElementType.WATER.getFullName(Translation.DEFAULT_LANG) + " Buff";
            case ELEMENT_EARTH:
                return ElementType.EARTH.getFullName(Translation.DEFAULT_LANG) + " Buff";
            case ELEMENT_LIGHTNING:
                return ElementType.LIGHTNING.getFullName(Translation.DEFAULT_LANG) + " Buff";
            case ELEMENT_AIR:
                return ElementType.AIR.getFullName(Translation.DEFAULT_LANG) + " Buff";
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
            case LIFE_STEAL:
                return PotionEffectType.WEAKNESS;
        }
        return null;
    }
}
