package io.github.lix3nn53.guardiansofadelia.bungeelistener;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class BoostPremiumManager {

    private final static Set<BoostPremium> activeBoosts = new HashSet<>();

    public static boolean activateBoost(BoostPremium boostType) {
        if (!activeBoosts.contains(boostType)) {
            activeBoosts.add(boostType);

            new BukkitRunnable() {
                @Override
                public void run() {
                    activeBoosts.remove(boostType);
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 60 * 20L); //20 minutes

            return true;
        }

        return false;
    }

    public static boolean isBoostActive(BoostPremium boostType) {
        return activeBoosts.contains(boostType);
    }
}
