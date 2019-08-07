package io.github.lix3nn53.guardiansofadelia.quests;

import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.DroppedItemWatcher;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuestHologram {

    //Hologram to disguise, one per quest NPC

    private final Hologram holo;

    public QuestHologram(Location loc) {
        ArmorStand rider = new Hologram(loc).getArmorStand();

        holo = new Hologram(loc, rider);
    }

    public void disguiseToPlayer(QuestIconType type, Player p) {
        if (!holo.getArmorStand().isDead()) {
            if (!holo.getArmorStand().getPassengers().isEmpty()) {
                ItemStack holoItem = type.getHoloItem();

                MiscDisguise disguise = new MiscDisguise(DisguiseType.DROPPED_ITEM);
                DroppedItemWatcher watcher = (DroppedItemWatcher) disguise.getWatcher();
                watcher.setItemStack(holoItem);

                DisguiseAPI.disguiseToPlayers(holo.getArmorStand().getPassengers().get(0), disguise, p);
            }
        }
    }

    public Hologram getHolo() {
        return holo;
    }

    public boolean isDisguisedToPlayer(Player player) {
        if (!holo.getArmorStand().isDead()) {
            if (!holo.getArmorStand().getPassengers().isEmpty()) {
                return DisguiseAPI.isDisguised(player, holo.getArmorStand().getPassengers().get(0));
            }
        }
        return false;
    }
}
