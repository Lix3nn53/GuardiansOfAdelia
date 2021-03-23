package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;


import org.bukkit.ChatColor;

public enum WeaponAttackSpeed {
    SLOW,
    NORMAL,
    FAST;

    public String getLoreString() {
        ChatColor color = ChatColor.GRAY;
        String name = "Slow";
        if (this == WeaponAttackSpeed.NORMAL) {
            color = ChatColor.GRAY;
            name = "Normal";
        } else if (this == WeaponAttackSpeed.FAST) {
            color = ChatColor.GRAY;
            name = "Fast";
        }/* else if (this == AttackSpeed.RAPID) {
            color = ChatColor.GRAY;
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
