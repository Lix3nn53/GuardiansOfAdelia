package io.github.lix3nn53.guardiansofadelia.items.RpgGears;


import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;

public enum WeaponAttackSpeed {
    SLOW,
    NORMAL,
    FAST;

    public String getLoreString() {
        ChatPalette color = ChatPalette.GRAY;
        String name = "Slow";
        if (this == WeaponAttackSpeed.NORMAL) {
            color = ChatPalette.GRAY;
            name = "Normal";
        } else if (this == WeaponAttackSpeed.FAST) {
            color = ChatPalette.GRAY;
            name = "Fast";
        }/* else if (this == AttackSpeed.RAPID) {
            color = ChatPalette.GRAY;
            name = "Rapid";
        }*/
        return color + name;
    }

    public double getSpeedValue() {
        double speed = -3;
        if (this == WeaponAttackSpeed.NORMAL) {
            speed = -2.75;
        } else if (this == WeaponAttackSpeed.FAST) {
            speed = -2.5;
        }/* else if (this == AttackSpeed.RAPID) {
            speed = -2.25;
        }*/

        return speed;
    }
}
