package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import io.github.lix3nn53.guardiansofadelia.utilities.hologram.packets.EntityDestroyPacket;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.packets.EntityMetadataPacket;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.packets.SpawnEntityLivingPacket;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FakeHologram {

    protected final int entityID;
    protected final String text;
    private final Location location;
    private final EntityDestroyPacket entityDestroyPacket;

    public FakeHologram(int entityID, Location location, String text) {
        this.entityID = entityID;
        this.location = location;
        this.text = text;
        entityDestroyPacket = new EntityDestroyPacket(entityID);
        entityDestroyPacket.load();
    }

    public void hide(@NotNull Player player) {
        entityDestroyPacket.send(player);
    }

    public void show(@NotNull Player player) {
        new SpawnEntityLivingPacket(entityID, location)
                .load()
                .send(player);
        new EntityMetadataPacket(entityID, text, true)
                .load()
                .send(player);
    }

}
