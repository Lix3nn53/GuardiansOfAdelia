package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;

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
        } else if (actionBarInfoType.equals(ActionBarInfoType.PASSIVE_COOLDOWN)) {

        } else if (actionBarInfoType.equals(ActionBarInfoType.COMPANION_COUNT)) {
            List<LivingEntity> companions = PetManager.getCompanions(player);
            int value = companions != null ? companions.size() : 0;

            return "        " + actionBarIcon + " " + value + "        ";
        }

        return "                    ";
    }
}
