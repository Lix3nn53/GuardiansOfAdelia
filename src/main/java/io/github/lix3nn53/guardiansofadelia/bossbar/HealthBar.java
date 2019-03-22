package io.github.lix3nn53.guardiansofadelia.bossbar;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class HealthBar {

    private final BossBar bar;

    public HealthBar(String title, double progress) {
        BarColor color = BarColor.GREEN;

        if (progress < 0.6 && progress > 0.25) {
            color = BarColor.YELLOW;
        } else if (progress <= 0.25) {
            color = BarColor.RED;
        }

        this.bar = Bukkit.createBossBar(title, color, BarStyle.SEGMENTED_10);

        setProgress(progress);
        setVisible();
    }

    public HealthBar(LivingEntity livingTarget, int damage) {
        double maxHealth = livingTarget.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double currentHealth = livingTarget.getHealth() - damage;
        double progress = 0;
        if (currentHealth <= 0) {
            currentHealth = 0;
        } else {
            progress = currentHealth / maxHealth;
        }

        String name = "Target";
        if (livingTarget.isCustomNameVisible()) {
            name = livingTarget.getCustomName();
        }

        String title = (ChatColor.RED.toString() + damage + ChatColor.RED + "➹ " + name + " " + ChatColor.GREEN + (int) (currentHealth + 0.5) +
                ChatColor.GRAY + "/" + ChatColor.GREEN + (int) (maxHealth + 0.5) + ChatColor.RED + "❤");

        BarColor color = BarColor.GREEN;

        if (progress < 0.6 && progress > 0.25) {
            color = BarColor.YELLOW;
        } else if (progress <= 0.25) {
            color = BarColor.RED;
        }

        this.bar = Bukkit.createBossBar(title, color, BarStyle.SEGMENTED_10);

        setProgress(progress);
        setVisible();
    }

    public void addPlayer(Player player) {
        this.bar.addPlayer(player);
    }

    public void setVisible() {
        this.bar.setVisible(true);
    }

    public void setProgress(double progress) {
        this.bar.setProgress(progress);
    }

    public void destroy() {
        this.bar.removeAll();
    }
}