package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;


import org.bukkit.ChatColor;

public enum AttackSpeed {
    SLOW,
    NORMAL,
    FAST,
    RAPID;

    public String getLoreString() {
        ChatColor color = ChatColor.GRAY;
        String name = "Slow";
        if (this == AttackSpeed.NORMAL) {
            color = ChatColor.GRAY;
            name = "Normal";
        } else if (this == AttackSpeed.FAST) {
            color = ChatColor.GRAY;
            name = "Fast";
        } else if (this == AttackSpeed.RAPID) {
            color = ChatColor.GRAY;
            name = "Rapid";
        }
        return color + name;
    }

    public double getSpeedValue() {
        double speed = -3.4D;
        if (this == AttackSpeed.NORMAL) {
            speed = -3.05;
        } else if (this == AttackSpeed.FAST) {
            speed = -2.6;
        } else if (this == AttackSpeed.RAPID) {
            speed = -2.1;
        }
        return speed;
    }
}
