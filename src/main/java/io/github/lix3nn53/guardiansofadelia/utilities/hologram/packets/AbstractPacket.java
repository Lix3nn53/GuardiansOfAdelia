package io.github.lix3nn53.guardiansofadelia.utilities.hologram.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractPacket {

    protected final int entityID;
    protected final PacketContainer packetContainer;

    public AbstractPacket(int entityID, @NotNull PacketType packetType) {
        this.entityID = entityID;
        this.packetContainer = new PacketContainer(packetType);
    }

    public abstract @NotNull
    AbstractPacket load();

    public void send(@NotNull Player player) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, this.packetContainer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}