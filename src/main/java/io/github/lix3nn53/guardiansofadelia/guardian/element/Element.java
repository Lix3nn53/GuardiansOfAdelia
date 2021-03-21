package io.github.lix3nn53.guardiansofadelia.guardian.element;

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

    public void clearEquipment() {
        this.bonusFromHelmet = 0;
        this.bonusFromChestplate = 0;
        this.bonusFromLeggings = 0;
        this.bonusFromBoots = 0;
        this.bonusFromMainhand = 0;
        this.bonusFromOffhand = 0;
    }

    public void clearPassive() {
        this.bonusFromPassive = 0;
    }

    public void removeBonus(EquipmentSlot equipmentSlot) {
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
    }

    public void setBonus(EquipmentSlot equipmentSlot, int bonus) {
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

    }

    public void addBonusToPassive(int bonus) {
        bonusFromPassive += bonus;
    }

    public void removeBonusFromPassive(int remove) {
        bonusFromPassive -= remove;
    }

    public ElementType getElementType() {
        return elementType;
    }
}
