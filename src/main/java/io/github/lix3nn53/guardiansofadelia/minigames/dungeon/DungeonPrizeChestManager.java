package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChestType;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;

public class DungeonPrizeChestManager {

    private static final double RADIUS = 2;

    private static final HashMap<ArmorStand, PrizeChest> entityToPrizeChest = new HashMap<>();

    public static void onSpawn(ArmorStand armorStand, PrizeChest prizeChest) {
        entityToPrizeChest.put(armorStand, prizeChest);

        new BukkitRunnable() {

            @Override
            public void run() {
                entityToPrizeChest.remove(armorStand);

                if (armorStand.isValid()) {
                    armorStand.remove();
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 60L);
    }

    public static void loot(ArmorStand armorStand, Player player) {
        if (!entityToPrizeChest.containsKey(armorStand)) return;

        PrizeChest prizeChest = entityToPrizeChest.get(armorStand);

        if (!prizeChest.canLoot(player)) {
            player.sendMessage(ChatColor.RED + "You already got this prize chest");
            return;
        }

        ItemStack chest = prizeChest.getChest();

        InventoryUtils.giveItemToPlayer(player, chest);
        player.sendMessage(ChatColor.GOLD + "Prize chest added to your inventory");
    }

    public static boolean isPrizeChest(ArmorStand armorStand) {
        return entityToPrizeChest.containsKey(armorStand);
    }

    public static void spawnPrizeChests(DungeonTheme theme, Location center, int amount) {
        Vector[] vectors = calculateLocations(center, amount);

        Random rand = new Random();
        for (Vector vector : vectors) {
            PrizeChestType[] values = PrizeChestType.values();
            int length = values.length;
            int chestTypeIndex = rand.nextInt(length);
            PrizeChestType prizeChestType = values[chestTypeIndex];

            PrizeChest prizeChest = new PrizeChest(theme, prizeChestType);

            Location location = vector.toLocation(center.getWorld());
            prizeChest.spawnChestModel(location);
        }
    }

    private static Vector[] calculateLocations(Location center, int amount) {
        double fullRadian = Math.toRadians(360);

        Vector centerVector = center.toVector();

        Vector[] points = new Vector[amount];
        int index = 0;
        for (double i = 0; i < amount; i++) {
            double percent = i / amount;
            double theta = fullRadian * percent;
            double dx = RADIUS * Math.cos(theta);
            double dy = 0;
            double dz = RADIUS * Math.sin(theta);
            //double dy = radius * Math.sin(theta);
            points[index] = centerVector.clone().add(new Vector(dx, dy, dz));
            index++;
        }

        return points;
    }

}
