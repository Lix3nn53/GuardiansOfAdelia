package io.github.lix3nn53.guardiansofadelia.utilities.hologram.packets;

import com.comphenix.protocol.PacketType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class EntityDestroyPacket extends AbstractPacket {

    public EntityDestroyPacket(int entityID) {
        super(entityID, PacketType.Play.Server.ENTITY_DESTROY);
    }

    @Override
    public @NotNull
    AbstractPacket load() {
        packetContainer.getIntLists().write(0, Collections.singletonList(this.entityID));

        return this;
    }
}