package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChestType;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.quests.QuestIconType;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.DroppedItemWatcher;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;

public class DungeonPrizeChestManager {

    private static final double RADIUS = 4;
    private static final double HEIGHT = 1.5;

    private static final HashMap<ArmorStand, PrizeChest> entityToPrizeChest = new HashMap<>();
    private static final HashMap<ArmorStand, ArmorStand> entityToIcon = new HashMap<>();

    public static void onSpawn(ArmorStand armorStand, PrizeChest prizeChest, ArmorStand icon) {
        entityToPrizeChest.put(armorStand, prizeChest);
        entityToIcon.put(armorStand, icon);

        new BukkitRunnable() {

            @Override
            public void run() {
                entityToPrizeChest.remove(armorStand);
                entityToIcon.remove(armorStand);

                if (armorStand.isValid()) {
                    armorStand.remove();
                }
                if (icon.isValid()) {
                    icon.remove();
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 60L);
    }

    public static void loot(ArmorStand armorStand, Player player) {
        if (!entityToPrizeChest.containsKey(armorStand)) return;

        PrizeChest prizeChest = entityToPrizeChest.get(armorStand);

        if (!prizeChest.canLoot(player)) {
            player.sendMessage(ChatPalette.RED + "You already got this prize chest");
            return;
        }

        if (MiniGameManager.isInMinigame(player)) {
            Minigame minigame = MiniGameManager.playerToMinigame(player);
            if (minigame instanceof DungeonInstance) {
                DungeonInstance dungeonInstance = (DungeonInstance) minigame;

                if (dungeonInstance.canLootPrizeChest(player)) {
                    dungeonInstance.onLootPrizeChest(player);
                } else {
                    player.sendMessage(ChatPalette.RED + "You don't have any key to unlock this prize chest.");
                    return;
                }
            }
        }
        // Can loot
        prizeChest.onLoot(player);

        ItemStack chest = prizeChest.getChest();

        InventoryUtils.giveItemToPlayer(player, chest);
        player.sendMessage(ChatPalette.GOLD + "Prize chest added to your inventory");

        MiscDisguise disguise = new MiscDisguise(DisguiseType.DROPPED_ITEM);
        DroppedItemWatcher watcher = (DroppedItemWatcher) disguise.getWatcher();
        watcher.setItemStack(QuestIconType.EMPTY.getHoloItem());

        DisguiseAPI.disguiseToPlayers(armorStand, disguise, player);
        if (entityToIcon.containsKey(armorStand)) {
            ArmorStand icon = entityToIcon.get(armorStand);
            DisguiseAPI.disguiseToPlayers(icon, disguise, player);
        }
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

            Vector centerVector = location.toVector();
            Vector target = center.toVector();

            Vector subtract = target.subtract(centerVector);
            location = location.setDirection(subtract);

            prizeChest.spawnChestModel(location);
        }
    }

    private static Vector[] calculateLocations(Location center, int amount) {
        double fullRadian = Math.toRadians(360);

        Vector centerVector = center.toVector().add(new Vector(0, HEIGHT, 0));

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
