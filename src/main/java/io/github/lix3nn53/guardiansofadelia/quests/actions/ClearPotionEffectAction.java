package io.github.lix3nn53.guardiansofadelia.quests.actions;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class ClearPotionEffectAction implements Action {

    private final PotionEffectType potionEffectType;

    public ClearPotionEffectAction(PotionEffectType potionEffectType) {
        this.potionEffectType = potionEffectType;
    }

    @Override
    public void perform(Player player, int questID, int taskIndex) {
        player.removePotionEffect(potionEffectType);
    }

    @Override
    public boolean preventTaskCompilation() {
        return false;
    }
}
