package io.github.lix3nn53.guardiansofadelia.Items.stats;

public class StatHybrid implements Stat {

    int damage;
    int rangedDamage;

    public StatHybrid(int damage, int rangedDamage) {
        this.damage = damage;
        this.rangedDamage = rangedDamage;
    }

    public int getRangedDamage() {
        return rangedDamage;
    }

    public int getDamage() {
        return damage;
    }
}
