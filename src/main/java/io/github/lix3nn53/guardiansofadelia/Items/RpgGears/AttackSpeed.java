package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;


import org.bukkit.ChatColor;

public enum AttackSpeed {
    SLOW,
    NORMAL,
    FAST;

    public String getLoreString() {
        ChatColor color = ChatColor.GRAY;
        String name = "Slow";
        if (this == AttackSpeed.NORMAL) {
            color = ChatColor.GRAY;
            name = "Normal";
        } else if (this == AttackSpeed.FAST) {
            color = ChatColor.GRAY;
            name = "Fast";
        }
        return color + name;
    }

    public double getSppedValue() {
        double speed = -3D;
        if (this == AttackSpeed.NORMAL) {
            speed = -2.6;
        } else if (this == AttackSpeed.FAST) {
            speed = -2.1;
        }
        return speed;
    }
}
