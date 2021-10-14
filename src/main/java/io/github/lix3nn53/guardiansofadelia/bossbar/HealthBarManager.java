package io.github.lix3nn53.guardiansofadelia.bossbar;

import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class HealthBarManager {

    private static final HashMap<LivingEntity, HealthBar> targetToHealthBar = new HashMap<>();

    public static void onPlayerDamageEntity(Player player, LivingEntity livingTarget, int damage,
                                            ChatPalette indicatorColor, String indicatorIcon) {
        // remove current health bar
        for (HealthBar healthBar : targetToHealthBar.values()) {
            healthBar.removePlayer(player);
        }

        // find or create health bar for target
        HealthBar healthBar;
        if (targetToHealthBar.containsKey(livingTarget)) {
            healthBar = targetToHealthBar.get(livingTarget);
            healthBar.update(livingTarget, damage, indicatorColor, indicatorIcon);
        } else {
            healthBar = new HealthBar(livingTarget, damage, indicatorColor, indicatorIcon);
            targetToHealthBar.put(livingTarget, healthBar);
        }

        // show to player
        healthBar.showToPlayerFor10Seconds(player);
    }

    public static void onLivingTargetHealthChange(LivingEntity livingTarget, int damage, ChatPalette indicatorColor, String indicatorIcon) {
        if (targetToHealthBar.containsKey(livingTarget)) {
            HealthBar healthBar = targetToHealthBar.get(livingTarget);

            healthBar.update(livingTarget, damage, indicatorColor, indicatorIcon);
        }
    }

    public static void onEntityDeath(LivingEntity entity) {
        targetToHealthBar.remove(entity);
    }
}
