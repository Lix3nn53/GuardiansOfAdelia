package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonInstance;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class DungeonDarknessMechanic extends MechanicComponent {

    private final List<Integer> amounts;

    public DungeonDarknessMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("amounts")) {
            configLoadError("amounts");
        }

        this.amounts = configurationSection.getIntegerList("amounts");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean worked = false;
        int amount = amounts.get(skillLevel - 1);

        for (LivingEntity ent : targets) {
            if (ent instanceof Player) {
                Player player = (Player) ent;

                if (MiniGameManager.isInMinigame(player)) {
                    Minigame minigame = MiniGameManager.playerToMinigame(player);
                    if (minigame instanceof DungeonInstance) {
                        DungeonInstance dungeonInstance = (DungeonInstance) minigame;

                        dungeonInstance.addDarkness(amount);
                        worked = true;
                    }
                }
            }
        }

        return worked;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatPalette.PURPLE + "Dungeon darkness: " + amounts.get(skillLevel));
        } else if (skillLevel == amounts.size()) {
            additions.add(ChatPalette.PURPLE + "Dungeon darkness: " + amounts.get(skillLevel - 1));
        } else {
            additions.add(ChatPalette.PURPLE + "Dungeon darkness: " + amounts.get(skillLevel - 1) + " -> " + amounts.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
