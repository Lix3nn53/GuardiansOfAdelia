package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;
import org.bukkit.Material;

public class ArmorItemTemplate {
    private final String name;
    private final int level;
    private final int health;
    private final int defense;
    private final int magicDefense;
    private final Material material;
    private final RPGGearType gearType;

    public ArmorItemTemplate(String name, int level, int health, int defense, int magicDefense, Material material, RPGGearType gearType) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.material = material;
        this.gearType = gearType;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public int getMagicDefense() {
        return magicDefense;
    }

    public Material getMaterial() {
        return material;
    }

    public RPGGearType getGearType() {
        return gearType;
    }
}
