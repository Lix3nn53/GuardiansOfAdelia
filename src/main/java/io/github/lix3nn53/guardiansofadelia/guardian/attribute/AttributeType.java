package io.github.lix3nn53.guardiansofadelia.guardian.attribute;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;

import java.text.DecimalFormat;

public enum AttributeType {
    BONUS_ELEMENT_DAMAGE,
    BONUS_ELEMENT_DEFENSE,
    BONUS_MAX_HEALTH,
    BONUS_MAX_MANA,
    BONUS_CRITICAL_CHANCE;

    public String getCustomName() {
        switch (this) {
            case BONUS_ELEMENT_DAMAGE:
                return ChatPalette.RED + "Bonus Element Damage";
            case BONUS_ELEMENT_DEFENSE:
                return ChatPalette.BLUE_LIGHT + "Bonus Element Defense";
            case BONUS_MAX_HEALTH:
                return ChatPalette.GREEN_DARK + "Bonus Max Health";
            case BONUS_MAX_MANA:
                return ChatPalette.BLUE + "Bonus Max Mana";
            case BONUS_CRITICAL_CHANCE:
                return ChatPalette.GOLD + "Bonus Critical Chance";
        }
        return "attributeCustomName";
    }

    public String getShortName() {
        switch (this) {
            case BONUS_ELEMENT_DAMAGE:
                return ChatPalette.RED + "Dmg";
            case BONUS_ELEMENT_DEFENSE:
                return ChatPalette.BLUE_LIGHT + "Def";
            case BONUS_MAX_HEALTH:
                return ChatPalette.GREEN_DARK + "HP";
            case BONUS_MAX_MANA:
                return ChatPalette.BLUE + "MP";
            case BONUS_CRITICAL_CHANCE:
                return ChatPalette.GOLD + "Crit%";
        }
        return "attributeCustomName";
    }

    public String getDescription() {
        switch (this) {
            case BONUS_ELEMENT_DAMAGE:
                return getIncrementPerPoint() + " bonus element damage per point";
            case BONUS_ELEMENT_DEFENSE:
                return getIncrementPerPoint() + " bonus element defense per point";
            case BONUS_MAX_HEALTH:
                return getIncrementPerPoint() + " bonus max health per point";
            case BONUS_MAX_MANA:
                return getIncrementPerPoint() + " bonus max mana per point";
            case BONUS_CRITICAL_CHANCE:
                return getIncrementPerPoint() * 100 + "% bonus critical chance per point";
        }
        return "attributeDescription";
    }

    public float getIncrementPerPoint() {
        switch (this) {
            case BONUS_ELEMENT_DAMAGE:
                return 1;
            case BONUS_ELEMENT_DEFENSE:
                return 1;
            case BONUS_MAX_HEALTH:
                return 10;
            case BONUS_MAX_MANA:
                return 1;
            case BONUS_CRITICAL_CHANCE:
                return 0.0005f;
        }
        return 1;
    }

    public float getBonusFromLevelReduction() {
        switch (this) {
            case BONUS_ELEMENT_DAMAGE:
                return 0.1f;
            case BONUS_ELEMENT_DEFENSE:
                return 0.2f;
            case BONUS_MAX_HEALTH:
                return 0.1f;
            case BONUS_MAX_MANA:
                return 0.05f;
            case BONUS_CRITICAL_CHANCE:
                return 0.05f;
        }
        return 1;
    }

    public String getIncrementLore(int value) {
        switch (this) {
            case BONUS_ELEMENT_DAMAGE:
            case BONUS_MAX_MANA:
            case BONUS_MAX_HEALTH:
            case BONUS_ELEMENT_DEFENSE:
                return (int) (value * getIncrementPerPoint() + 0.5) + "";
            case BONUS_CRITICAL_CHANCE:
                return new DecimalFormat("##.##").format(value * getIncrementPerPoint() * 100) + "%";
        }

        return "getIncrementLore";
    }

    public boolean getFixDisplayOnChange() {
        return this == AttributeType.BONUS_MAX_HEALTH || this == AttributeType.BONUS_MAX_MANA;
    }
}
