package io.github.lix3nn53.guardiansofadelia.jobs;

import net.md_5.bungee.api.ChatColor;

public enum JobType {
    WEAPONSMITH,
    ARMORSMITH,
    ALCHEMIST,
    JEWELLER;

    public String getName() {
        if (this.equals(JobType.ALCHEMIST)) {
            return ChatColor.LIGHT_PURPLE + "Alchemist";
        } else if (this.equals(JobType.ARMORSMITH)) {
            return ChatColor.AQUA + "Armorsmith";
        } else if (this.equals(JobType.JEWELLER)) {
            return ChatColor.GOLD + "Jeweller";
        }
        return ChatColor.RED + "Weaponsmith";
    }


}
