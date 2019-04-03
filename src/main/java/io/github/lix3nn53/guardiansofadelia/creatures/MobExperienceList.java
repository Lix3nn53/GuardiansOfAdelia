package io.github.lix3nn53.guardiansofadelia.creatures;

import org.bukkit.ChatColor;

public class MobExperienceList {

    public static int getExperience(String displayName) {
        if (displayName.equals(ChatColor.GREEN + "Wild Lizard")) {
            return 10;
        } else if (displayName.equals(ChatColor.DARK_GREEN + "Poisonous Lizard")) {
            return 20;
        } else if (displayName.equals(ChatColor.GREEN + "Wild Turtle")) {
            return 30;
        } else if (displayName.equals(ChatColor.AQUA + "Water Turtle")) {
            return 40;
        } else if (displayName.equals(ChatColor.GREEN + "Baby Slime")) {
            return 50;
        }
        return 10;
    }
}
