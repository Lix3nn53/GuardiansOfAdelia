package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.WrappedDataWatcherObject;
import io.github.lix3nn53.guardiansofadelia.utilities.packetwrapper.WrapperPlayServerEntityDestroy;
import io.github.lix3nn53.guardiansofadelia.utilities.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class FakeHologram {

    private int ENTITY_ID;
    private WrapperPlayServerSpawnEntityLiving armorStand;

    public FakeHologram(Location loc, String displayName) {
        this.ENTITY_ID = FakeHologramManager.getNextEntityID();

        this.armorStand = new WrapperPlayServerSpawnEntityLiving();

        this.armorStand.getHandle().getIntegers().write(1, 1); //set EntityType, https://wiki.vg/Entity_metadata#Mobs
        this.armorStand.setEntityID(ENTITY_ID);
        this.armorStand.setX(loc.getX());
        this.armorStand.setY(loc.getY());
        this.armorStand.setZ(loc.getZ());

        FakeHologramManager.onFakeEntitySpawn();

        Optional<?> opt = Optional
                .of(WrappedChatComponent
                        .fromChatMessage(displayName)[0].getHandle());

        WrappedDataWatcher metadata = new WrappedDataWatcher();
        metadata.setObject(new WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20); //invis
        metadata.setObject(new WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
        metadata.setObject(new WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true); //custom name visible
        metadata.setObject(new WrappedDataWatcherObject(5, WrappedDataWatcher.Registry.get(Boolean.class)), true); //no gravity
        metadata.setObject(new WrappedDataWatcherObject(11, WrappedDataWatcher.Registry.get(Byte.class)), (byte) (0x01 | 0x08 | 0x10)); //isSmall, noBasePlate, set Marker

        this.armorStand.setMetadata(metadata);
    }

    public boolean showToPlayer(Player p, ProtocolManager manager) {
        try {
            manager.sendServerPacket(p, armorStand.getHandle());
            return true;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean remove(Player p, ProtocolManager manager) {
        WrapperPlayServerEntityDestroy destroy = new WrapperPlayServerEntityDestroy();
        destroy.setEntityIds(new int[]{ENTITY_ID});
        try {
            manager.sendServerPacket(p, destroy.getHandle());
            return true;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
