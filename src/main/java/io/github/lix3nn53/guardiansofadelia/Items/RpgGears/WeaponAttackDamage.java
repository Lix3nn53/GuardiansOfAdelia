package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;


public enum WeaponAttackDamage {
    LOW,
    NORMAL,
    HIGH,
    MAXIMUM;

    public double getDamageReduction() {
        switch (this) {
            case LOW:
                return 0.4;
            case NORMAL:
                return 0.6;
            case HIGH:
                return 0.8;
            case MAXIMUM:
                return 1;
        }

        return 0.2;
    }
}
