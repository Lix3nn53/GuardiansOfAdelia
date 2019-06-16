package io.github.lix3nn53.guardiansofadelia.Items.stats;

public class StatHybrid implements Stat {

    private int meleeDamage;
    private int rangedDamage;

    public StatHybrid(final int meleeDamage, final int rangedDamage) {
        this.meleeDamage = meleeDamage;
        this.rangedDamage = rangedDamage;
    }

    public int getRangedDamage() {
        return rangedDamage;
    }

    public int getMeleeDamage() {
        return meleeDamage;
    }
}
