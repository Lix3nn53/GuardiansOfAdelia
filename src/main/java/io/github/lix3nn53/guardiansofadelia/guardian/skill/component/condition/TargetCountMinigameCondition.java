package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Check if target players is in minigame. If true, work if all players in minigame is in target list.
 * If none of the target players is in minigame, just work. So skills with this component is usable out of minigames too.
 */
public class TargetCountMinigameCondition extends ConditionComponent {

    public TargetCountMinigameCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        int size = targets.size();

        boolean isInMinigame = false;
        boolean successPlayerCount = false;

        for (LivingEntity ent : targets) {
            if (ent instanceof Player) {
                Player player = (Player) ent;

                if (MiniGameManager.isInMinigame(player)) {
                    isInMinigame = true;
                    Minigame minigame = MiniGameManager.playerToMinigame(player);

                    List<Player> playersInGame = minigame.getPlayersInGame();
                    int playerCount = playersInGame.size();

                    if (size >= playerCount) {
                        successPlayerCount = true;
                        break;
                    }

                    break;
                }
            }
        }

        if (isInMinigame && !successPlayerCount) {
            for (LivingEntity ent : targets) {
                if (ent instanceof Player) {
                    Player player = (Player) ent;

                    player.sendMessage(ChatPalette.RED + "All players must be in the area");
                }
            }

            return false;
        }

        return executeChildren(caster, skillLevel, targets, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
