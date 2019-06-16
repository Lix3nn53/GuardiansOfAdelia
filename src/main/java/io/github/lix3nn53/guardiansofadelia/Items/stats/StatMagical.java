package io.github.lix3nn53.guardiansofadelia.Items.stats;

public class StatMagical implements Stat {

    int meleeDamage;
    int magicDamage;

    public StatMagical(int meleeDamage, int magicDamage) {
        this.meleeDamage = meleeDamage;
        this.magicDamage = magicDamage;
    }

    public int getMagicDamage() {
        return magicDamage;
    }

    public int getMeleeDamage() {
        return meleeDamage;
    }
}
