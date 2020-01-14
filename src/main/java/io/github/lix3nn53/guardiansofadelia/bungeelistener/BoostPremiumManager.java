package io.github.lix3nn53.guardiansofadelia.bungeelistener;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class BoostPremiumManager {

    private final static Set<BoostPremium> activeBoosts = new HashSet<>();
    private final static HashMap<BoostPremium, BukkitTask> boostTimers = new HashMap<>();

    public static void tellBungeeToActivateBoost(Player player, BoostPremium boostType) {
        List<String> args = new ArrayList<>();
        args.add(boostType.name());

        GuardiansOfAdelia.pluginChannelListener.sendToBungeeCord(player, "premiumBoostActivate", args);
    }

    public static void activateBoostOnThisServer(BoostPremium boostType) {
        activeBoosts.add(boostType);

        BukkitTask bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                activeBoosts.remove(boostType);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 60 * 40L); //40 minutes

        if (boostTimers.containsKey(boostType)) {
            boostTimers.get(boostType).cancel();
        }

        boostTimers.put(boostType, bukkitTask);
    }

    public static boolean isBoostActive(BoostPremium boostType) {
        return activeBoosts.contains(boostType);
    }
}
