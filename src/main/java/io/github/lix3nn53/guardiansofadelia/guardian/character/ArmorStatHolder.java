package io.github.lix3nn53.guardiansofadelia.guardian.character;

public class ArmorStatHolder {

    private final int maxHealth;
    private final int defense;
    private final int magicDefense;

    public ArmorStatHolder(int maxHealth, int defense, int magicDefense) {
        this.maxHealth = maxHealth;
        this.defense = defense;
        this.magicDefense = magicDefense;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDefense() {
        return defense;
    }

    public int getMagicDefense() {
        return magicDefense;
    }
}
