package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DropManager {

    private static HashMap<Entity, DropDamage> dropDamages = new HashMap<Entity, DropDamage>();
    private static HashMap<ItemStack, List<Player>> droppedItemOwners = new HashMap<ItemStack, List<Player>>();

    public static void dealDamage(Entity entity, Player p, int damage) {
        if (dropDamages.containsKey(entity)) {
            DropDamage dm = dropDamages.get(entity);
            dm.addDamage(p, damage);
        } else {
            DropDamage dm = new DropDamage(entity);
            dm.addDamage(p, damage);
            dropDamages.put(entity, dm);
        }
    }

    public static void onDeathDropItems(Entity entity) {
        if (dropDamages.containsKey(entity)) {
            List<ItemStack> drops = MobDropGenerator.getDrops(entity);
            DropDamage dropDamage = dropDamages.get(entity);
            dropDamages.remove(entity);
            if (dropDamage.isMostDamageDealerParty()) {
                Party party = dropDamage.getBestParty();
                for (ItemStack drop : drops) {
                    droppedItemOwners.put(drop, party.getMembers());
                }
                startTimer(drops);
            } else {
                Player player = dropDamage.getBestPlayer();
                List<Player> onelist = new ArrayList<Player>();
                onelist.add(player);
                for (ItemStack drop : drops) {
                    droppedItemOwners.put(drop, onelist);
                }
                startTimer(drops);
            }
        }
    }

    public static boolean canPickUp(ItemStack item, Player player) {
        if (droppedItemOwners.containsKey(item)) {
            List<Player> plist = droppedItemOwners.get(item);
            return plist.contains(player);
        }
        return true;
    }

    private static void startTimer(List<ItemStack> drops) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (ItemStack drop : drops) {
                    droppedItemOwners.remove(drop);
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 30);
    }

    public static void setItem(ItemStack drop, Player player) {
        List<Player> onelist = new ArrayList<Player>();
        onelist.add(player);
        droppedItemOwners.put(drop, onelist);
    }
}
