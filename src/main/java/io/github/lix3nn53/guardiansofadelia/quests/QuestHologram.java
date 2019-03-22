package io.github.lix3nn53.guardiansofadelia.quests;

import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.DroppedItemWatcher;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuestHologram {

    //Hologram to disguise, one per quest NPC

    Hologram holo;

    public QuestHologram(Location loc) {
        QuestIconType questIconType = QuestIconType.EMPTY;
        ItemStack holoItem = questIconType.getHoloItem();

        holo = new Hologram(loc, holoItem);
    }

    public void disguiseToPlayer(QuestIconType type, Player p) {
        ItemStack holoItem = type.getHoloItem();

        MiscDisguise disguise = new MiscDisguise(DisguiseType.DROPPED_ITEM);
        DroppedItemWatcher watcher = (DroppedItemWatcher) disguise.getWatcher();
        watcher.setItemStack(holoItem);

        DisguiseAPI.disguiseToPlayers(holo.getArmorStand().getPassengers().get(0), disguise, p);
    }

    public Hologram getHolo() {
        return holo;
    }

    public boolean isDisguisedToPlayer(Player player) {
        return DisguiseAPI.isDisguised(player, holo.getArmorStand().getPassengers().get(0));
    }
}
