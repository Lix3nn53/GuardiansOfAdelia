package io.github.lix3nn53.guardiansofadelia.guardian.element;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import org.bukkit.inventory.EquipmentSlot;

public class Element {
    private final ElementType elementType;

    private int bonusFromHelmet;
    private int bonusFromChestplate;
    private int bonusFromLeggings;
    private int bonusFromBoots;
    private int bonusFromMainhand;
    private int bonusFromOffhand;

    private int bonusFromPassive;

    public Element(ElementType elementType) {
        this.elementType = elementType;
    }

    public int getBonusFromEquipment() {
        return bonusFromHelmet + bonusFromChestplate + bonusFromLeggings + bonusFromBoots + bonusFromMainhand + bonusFromOffhand + bonusFromPassive;
    }

    public void clearEquipment(RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        this.bonusFromHelmet = 0;
        this.bonusFromChestplate = 0;
        this.bonusFromLeggings = 0;
        this.bonusFromBoots = 0;
        this.bonusFromMainhand = 0;
        this.bonusFromOffhand = 0;

        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public void clearPassive(RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        this.bonusFromPassive = 0;

        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public void removeBonus(EquipmentSlot equipmentSlot, RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        switch (equipmentSlot) {
            case HAND:
                this.bonusFromMainhand = 0;
                break;
            case OFF_HAND:
                this.bonusFromOffhand = 0;
                break;
            case FEET:
                this.bonusFromBoots = 0;
                break;
            case LEGS:
                this.bonusFromLeggings = 0;
                break;
            case CHEST:
                this.bonusFromChestplate = 0;
                break;
            case HEAD:
                this.bonusFromHelmet = 0;
                break;
        }

        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public void setBonus(EquipmentSlot equipmentSlot, RPGCharacterStats rpgCharacterStats, int bonus, boolean fixDisplay) {
        switch (equipmentSlot) {
            case HAND:
                this.bonusFromMainhand = bonus;
                break;
            case OFF_HAND:
                this.bonusFromOffhand = bonus;
                break;
            case FEET:
                this.bonusFromBoots = bonus;
                break;
            case LEGS:
                this.bonusFromLeggings = bonus;
                break;
            case CHEST:
                this.bonusFromChestplate = bonus;
                break;
            case HEAD:
                this.bonusFromHelmet = bonus;
                break;
        }

        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
        //onBonusChangeFillEmpty(player, rpgCharacterStats, bonusPointDifference);
    }

    public void addBonusToPassive(int bonus, RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        bonusFromPassive += bonus;
        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    public void removeBonusFromPassive(int remove, RPGCharacterStats rpgCharacterStats, boolean fixDisplay) {
        bonusFromPassive -= remove;
        if (fixDisplay) {
            onValueChange(rpgCharacterStats);
        }
    }

    private void onValueChange(RPGCharacterStats rpgCharacterStats) {
        switch (elementType) {
            case BONUS_ELEMENT_DAMAGE:
                break;
            case BONUS_ELEMENT_DEFENSE:
                break;
            case BONUS_MAX_HEALTH:
                rpgCharacterStats.onMaxHealthChange();
                break;
            case BONUS_MAX_MANA:
                rpgCharacterStats.onCurrentManaChange();
                break;
            case BONUS_CRITICAL_CHANCE:
                break;
        }
    }

    public ElementType getElementType() {
        return elementType;
    }
}
