package io.github.lix3nn53.guardiansofadelia.guardian.attribute;

import org.bukkit.ChatColor;

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
                return ChatColor.RED + "Bonus Element Damage";
            case BONUS_ELEMENT_DEFENSE:
                return ChatColor.AQUA + "Bonus Element Defense";
            case BONUS_MAX_HEALTH:
                return ChatColor.DARK_GREEN + "Bonus Max Health";
            case BONUS_MAX_MANA:
                return ChatColor.BLUE + "Bonus Max Mana";
            case BONUS_CRITICAL_CHANCE:
                return ChatColor.GOLD + "Bonus Critical Chance";
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

    public double getIncrementPerPoint() {
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
                return 0.001;
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
