package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;
import org.bukkit.Material;

public class ArmorSet {
    private final String name;
    private final int baseReqLevel;
    private final int health;
    private final int defense;
    private final int magicDefense;
    private final ArmorMaterial armorMaterial;
    private final RPGGearType gearType;

    public ArmorSet(String name, int baseReqLevel, int health, int defense, int magicDefense, ArmorMaterial armorMaterial, RPGGearType gearType) {
        this.name = name;
        this.baseReqLevel = baseReqLevel;
        this.health = health;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.armorMaterial = armorMaterial;
        this.gearType = gearType;
    }

    public String getName(ArmorType armorType) {
        return name + " " + armorType.getDisplayName();
    }

    public int getReqLevel(ArmorType armorType) {
        return baseReqLevel + armorType.getReqLevelAddition();
    }

    public int getHealth(ArmorType armorType) {
        return (int) (health * armorType.getAttributeReduction() + 0.5);
    }

    public int getDefense(ArmorType armorType) {
        return (int) (defense * armorType.getAttributeReduction() + 0.5);
    }

    public int getMagicDefense(ArmorType armorType) {
        return (int) (magicDefense * armorType.getAttributeReduction() + 0.5);
    }

    public Material getMaterial(ArmorType armorType) {
        return armorMaterial.getMaterial(armorType);
    }

    public RPGGearType getGearType() {
        return gearType;
    }

    public int getBaseReqLevel() {
        return baseReqLevel;
    }
}
