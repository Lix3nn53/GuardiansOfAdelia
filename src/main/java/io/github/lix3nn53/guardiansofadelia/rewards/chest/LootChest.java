package io.github.lix3nn53.guardiansofadelia.rewards.chest;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.Direction;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleArrangement;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;
import java.util.List;

public class LootChest {

    private static final long COOLDOWN_IN_MINUTES = 1;

    private final Location location;
    private final LootChestTier lootChestTier;

    private boolean isOnCooldown = false;
    private BukkitTask particleTask;

    public LootChest(Location location, LootChestTier lootChestTier) {
        this.location = location;
        this.lootChestTier = lootChestTier;
        startPlayingParticles();
    }

    public void openLootInventory(Player player) {
        if (isOnCooldown) {
            CustomSound customSound = new CustomSound(Sound.BLOCK_CHEST_LOCKED, 0.5f, 1f);
            customSound.play(player.getLocation());
            return;
        }

        CustomSound customSound = new CustomSound(Sound.BLOCK_CHEST_OPEN, 0.5f, 1f);
        customSound.play(player.getLocation());

        List<ItemStack> loots = lootChestTier.getLoot();

        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.BLACK + "Loot Chest #" + lootChestTier.ordinal(), 0);
        for (int i = 0; i < 100; i++) {
            loots.add(new ItemStack(Material.AIR));
            if (loots.size() == 27) break;
        }

        Collections.shuffle(loots);

        for (int i = 0; i < 27; i++) {
            ItemStack itemStack = loots.get(i);
            if (itemStack.getType().equals(Material.AIR)) continue;

            guiGeneric.setItem(i, itemStack);
        }

        guiGeneric.setLocked(false);
        guiGeneric.openInventory(player);

        isOnCooldown = true;
        stopPlayingParticles();

        new BukkitRunnable() {
            @Override
            public void run() {
                isOnCooldown = false;
                if (location.getChunk().isLoaded()) {
                    startPlayingParticles();
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20 * 60 * COOLDOWN_IN_MINUTES);
    }

    public Location getLocation() {
        return location;
    }

    public LootChestTier getLootChestTier() {
        return lootChestTier;
    }

    public void startPlayingParticles() {
        if (isOnCooldown) return;

        if (particleTask != null) {
            particleTask.cancel();
        }

        Location add = location.clone().add(0.5, 1.2, 0.5);

        particleTask = new BukkitRunnable() {
            @Override
            public void run() {
                ParticleUtil.play(add, Particle.CRIT, ParticleArrangement.CIRCLE, 0.5, 4,
                        Direction.XZ, 0, 0, 0, 0, null);
            }
        }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 20, 20L);
    }

    public void stopPlayingParticles() {
        if (particleTask != null) {
            particleTask.cancel();
        }
    }
}
