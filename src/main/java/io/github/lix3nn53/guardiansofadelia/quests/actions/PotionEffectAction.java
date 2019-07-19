package io.github.lix3nn53.guardiansofadelia.quests.actions;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectAction implements Action {

    private final PotionEffectType potionEffectType;
    private final int duration;
    private final int amplifier;

    public PotionEffectAction(PotionEffectType potionEffectType, int duration, int amplifier) {
        this.potionEffectType = potionEffectType;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    public void perform(Player player) {
        org.bukkit.potion.PotionEffect potionEffect = new org.bukkit.potion.PotionEffect(potionEffectType, duration, amplifier);
        player.addPotionEffect(potionEffect, true);
    }
}
