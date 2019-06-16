package io.github.lix3nn53.guardiansofadelia.guardian.attribute;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;

public class Attribute {
    private final AttributeType attributeType;
    private int invested;
    private int bonus;

    public Attribute(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public int getInvested() {
        return invested;
    }

    public void setInvested(int invested, RPGCharacterStats rpgCharacterStats) {
        this.invested = invested;
        onValueChange(rpgCharacterStats);
    }

    public int getBonus() {
        return bonus;
    }

    public void clearBonus(RPGCharacterStats rpgCharacterStats) {
        this.bonus = 0;
        onValueChange(rpgCharacterStats);
    }

    public void removeBonus(int remove, RPGCharacterStats rpgCharacterStats) {
        this.bonus -= remove;
        onValueChange(rpgCharacterStats);
    }

    public void addBonus(int add, RPGCharacterStats rpgCharacterStats) {
        this.bonus += add;
        onValueChange(rpgCharacterStats);
    }

    public void setBonus(int set, RPGCharacterStats rpgCharacterStats) {
        this.bonus = set;
        onValueChange(rpgCharacterStats);
    }

    public double getIncrement() {
        return (invested + bonus) * attributeType.getIncrement();
    }

    private void onValueChange(RPGCharacterStats rpgCharacterStats) {
        switch (attributeType) {
            case FIRE:
                break;
            case LIGHTNING:
                break;
            case EARTH:
                rpgCharacterStats.onMaxHealthChange();
                break;
            case WATER:
                rpgCharacterStats.onCurrentManaChange();
                break;
            case WIND:
                break;
        }
    }
}
