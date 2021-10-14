package io.github.lix3nn53.guardiansofadelia.bossbar;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class HealthBar {

    private final BossBar bar;
    private final HashMap<Player, BukkitTask> playerToCooldown = new HashMap<>();

    /*public HealthBar(String title, double progress) {
        BarColor color = BarColor.GREEN;

        if (progress < 0.6 && progress > 0.25) {
            color = BarColor.YELLOW;
        } else if (progress <= 0.25) {
            color = BarColor.RED;
        }

        this.bar = Bukkit.createBossBar(title, color, BarStyle.SEGMENTED_10);

        setProgress(progress);
        setVisible();
    }*/

    public HealthBar(LivingEntity livingTarget, int damage, ChatPalette damageColor, String damageIcon) {
        double maxHealth = livingTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double currentHealth = livingTarget.getHealth() - damage;

        double progress = getProgress(maxHealth, currentHealth);
        BarColor color = getColor(progress);
        String title = getTitle(livingTarget, damage, damageColor, damageIcon, maxHealth, currentHealth);

        this.bar = Bukkit.createBossBar(title, color, BarStyle.SEGMENTED_10);
        this.bar.setProgress(progress);
        this.bar.setVisible(true);
    }

    public void showToPlayer(Player player) {
        this.bar.addPlayer(player);

        if (playerToCooldown.containsKey(player)) {
            BukkitTask bukkitTask = playerToCooldown.get(player);
            bukkitTask.cancel();
        }

        BukkitTask bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                bar.removePlayer(player);
                playerToCooldown.remove(player);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 15L);

        playerToCooldown.put(player, bukkitTask);
    }

    public void destroy() {
        this.bar.removeAll();
    }

    private double getProgress(double maxHealth, double currentHealth) {
        double progress = 0;
        if (!(currentHealth <= 0)) {
            progress = currentHealth / maxHealth;
        }

        return progress;
    }

    private String getName(LivingEntity livingTarget) {
        String targetName = "Target";
        if (livingTarget.isCustomNameVisible()) {
            targetName = livingTarget.getCustomName();
        }

        return targetName;
    }

    private BarColor getColor(double progress) {
        BarColor color = BarColor.GREEN;

        if (progress < 0.6 && progress > 0.25) {
            color = BarColor.YELLOW;
        } else if (progress <= 0.25) {
            color = BarColor.RED;
        }

        return color;
    }

    private String getTitle(LivingEntity livingTarget, int damage, ChatPalette damageColor, String damageIcon,
                            double maxHealth, double currentHealth) {
        String targetName = getName(livingTarget);

        return (damageColor.toString() + damage + damageColor + damageIcon + " " + targetName + " " + ChatPalette.GREEN_DARK + (int) (currentHealth + 0.5) +
                ChatPalette.GRAY + "/" + ChatPalette.GREEN_DARK + (int) (maxHealth + 0.5) + ChatPalette.RED + "❤");
    }

    public void update(LivingEntity livingTarget, int damage, ChatPalette damageColor, String damageIcon) {
        double maxHealth = livingTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double currentHealth = livingTarget.getHealth() - damage;

        double progress = getProgress(maxHealth, currentHealth);
        BarColor color = getColor(progress);
        String title = getTitle(livingTarget, damage, damageColor, damageIcon, maxHealth, currentHealth);

        this.bar.setProgress(progress);
        this.bar.setColor(color);
        this.bar.setTitle(title);
    }

    public void removePlayer(Player player) {
        bar.removePlayer(player);
        BukkitTask remove = playerToCooldown.remove(player);

        if (remove != null) remove.cancel();
    }
}