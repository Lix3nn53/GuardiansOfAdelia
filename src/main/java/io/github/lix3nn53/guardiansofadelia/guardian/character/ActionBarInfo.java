package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class ActionBarInfo {

    private final ActionBarInfoType actionBarInfoType;
    private final String actionBarIcon;

    private final String key;

    public ActionBarInfo(@Nullable ActionBarInfoType actionBarInfoType, String actionBarIcon, @Nullable String key) {
        this.actionBarInfoType = actionBarInfoType;
        this.actionBarIcon = actionBarIcon != null ? ChatColor.translateAlternateColorCodes('&', actionBarIcon) : "";
        this.key = key;
    }

    public String getActionBarBetween(Player player) {
        if (actionBarInfoType == null) return "                    ";

        if (actionBarInfoType.equals(ActionBarInfoType.VARIABLE)) {
            int value = SkillDataManager.getValue(player, key);

            return "        " + actionBarIcon + " " + value + "        ";
        }

        return "                    ";
    }
}
