package io.github.lix3nn53.guardiansofadelia.utilities.hologram.packets;

import com.comphenix.protocol.PacketType;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class EntityTeleportPacket extends AbstractPacket {

    private final Location location;

    public EntityTeleportPacket(int entityID, @NotNull Location location) {
        super(entityID, PacketType.Play.Server.ENTITY_TELEPORT);
        this.location = location;
    }

    @Override
    public @NotNull
    AbstractPacket load() {
        packetContainer.getIntegers().write(0, this.entityID);

        packetContainer.getDoubles().write(0, location.getX());
        packetContainer.getDoubles().write(1, location.getY());
        packetContainer.getDoubles().write(2, location.getZ());

        packetContainer.getBytes().write(0, this.getCompressAngle(location.getYaw()));
        packetContainer.getBytes().write(1, this.getCompressAngle(location.getPitch()));

        packetContainer.getBooleans().write(0, false);
        return this;
    }

    protected byte getCompressAngle(double angle) {
        return (byte) (angle * 256F / 360F);
    }
}