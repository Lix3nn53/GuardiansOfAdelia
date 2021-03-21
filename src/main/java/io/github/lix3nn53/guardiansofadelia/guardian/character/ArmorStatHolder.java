package io.github.lix3nn53.guardiansofadelia.guardian.character;

public class ArmorStatHolder {

    private final int maxHealth;
    private final int defense;

    public ArmorStatHolder(int maxHealth, int defense) {
        this.maxHealth = maxHealth;
        this.defense = defense;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDefense() {
        return defense;
    }
}
