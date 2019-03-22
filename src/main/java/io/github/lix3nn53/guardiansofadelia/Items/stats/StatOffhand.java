package io.github.lix3nn53.guardiansofadelia.Items.stats;

public class StatOffhand implements Stat {

    int damage;

    public StatOffhand(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getOffHandDamage() {
        return (int) (((damage) * 0.6) + 0.5);
    }
}
