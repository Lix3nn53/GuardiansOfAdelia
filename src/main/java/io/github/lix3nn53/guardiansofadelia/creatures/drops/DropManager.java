package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DropManager {

    private static HashMap<LivingEntity, DropDamage> dropDamages = new HashMap<>();
    private static HashMap<ItemStack, List<Player>> droppedItemOwners = new HashMap<>();

    public static void onPlayerDealDamageToMob(Player attacker, LivingEntity damaged, double damage) {
        if (dropDamages.containsKey(damaged)) {
            DropDamage dropDamage = dropDamages.get(damaged);
            dropDamage.dealDamage(attacker, damage);
        } else {
            DropDamage dropDamage = new DropDamage();
            dropDamage.dealDamage(attacker, damage);
            dropDamages.put(damaged, dropDamage);
        }
    }

    public static boolean canPickUp(Player player, ItemStack itemStack) {
        if (droppedItemOwners.containsKey(itemStack)) {
            List<Player> players = droppedItemOwners.get(itemStack);
            return players.contains(player);
        }
        return true;
    }

    public static void setItem(ItemStack itemStack, List<Player> players) {
        droppedItemOwners.put(itemStack, players);
        startItemTimer(itemStack);
    }

    public static void setItem(ItemStack itemStack, Player player) {
        List<Player> players = new ArrayList<>();
        players.add(player);
        droppedItemOwners.put(itemStack, players);
        startItemTimer(itemStack);
    }

    public static void onMobDeath(LivingEntity entity, EntityDeathEvent event) {
        if (dropDamages.containsKey(entity)) {
            DropDamage dropDamage = dropDamages.get(entity);
            List<Player> bestPlayers = dropDamage.getBestPlayers();
            List<ItemStack> drops = MobDropGenerator.getDrops(entity);
            for (ItemStack itemStack : drops) {
                droppedItemOwners.put(itemStack, bestPlayers);
                startItemTimer(itemStack);
            }
            event.getDrops().addAll(drops);
        }
    }

    private static void startItemTimer(ItemStack itemStack) {
        new BukkitRunnable() {
            @Override
            public void run() {
                droppedItemOwners.remove(itemStack);
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 30L);
    }
}
