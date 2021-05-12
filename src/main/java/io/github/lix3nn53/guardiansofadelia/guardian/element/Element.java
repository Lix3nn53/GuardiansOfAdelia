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

    private int bonusTotalPassive;

    public Element(ElementType elementType) {
        this.elementType = elementType;
    }

    public int getTotal() {
        return bonusFromHelmet + bonusFromChestplate + bonusFromLeggings + bonusFromBoots + bonusFromMainhand + bonusFromOffhand + bonusTotalPassive;
    }

    public void clearAllEquipment() {
        this.bonusFromHelmet = 0;
        this.bonusFromChestplate = 0;
        this.bonusFromLeggings = 0;
        this.bonusFromBoots = 0;
        this.bonusFromMainhand = 0;
        this.bonusFromOffhand = 0;
    }

    public void clearEquipmentBonus(EquipmentSlot equipmentSlot) {
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

    public void setEquipmentBonus(EquipmentSlot equipmentSlot, int bonus) {
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

    public void addToTotalPassive(int value) {
        this.bonusTotalPassive += value;
    }

    public void removeFromTotalPassive(int value) {
        this.bonusTotalPassive -= value;
    }

    public void clearTotalPassive() {
        this.bonusTotalPassive = 0;
    }

    public ElementType getElementType() {
        return elementType;
    }
}
