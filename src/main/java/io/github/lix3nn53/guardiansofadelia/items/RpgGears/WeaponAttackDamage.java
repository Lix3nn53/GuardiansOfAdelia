package io.github.lix3nn53.guardiansofadelia.items.RpgGears;


public enum WeaponAttackDamage {
    LOW,
    NORMAL,
    HIGH,
    MAXIMUM;

    public float getDamageReduction() {
        switch (this) {
            case LOW:
                return 0.4f;
            case NORMAL:
                return 0.6f;
            case HIGH:
                return 0.8f;
            case MAXIMUM:
                return 1;
        }

        return 0.2f;
    }
}
