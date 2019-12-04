package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.WrappedDataWatcherObject;
import io.github.lix3nn53.guardiansofadelia.utilities.packetwrapper.WrapperPlayServerEntityDestroy;
import io.github.lix3nn53.guardiansofadelia.utilities.packetwrapper.WrapperPlayServerEntityMetadata;
import io.github.lix3nn53.guardiansofadelia.utilities.packetwrapper.WrapperPlayServerSpawnEntity;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;

public class FakeHologram {

    private final int ENTITY_ID;
    private final WrapperPlayServerSpawnEntity wrapperPlayServerSpawnEntity = new WrapperPlayServerSpawnEntity();
    private final WrapperPlayServerEntityMetadata wrapperPlayServerEntityMetadata = new WrapperPlayServerEntityMetadata();

    public FakeHologram(Location loc, String displayName) {
        this.ENTITY_ID = FakeEntityManager.getNextEntityID();
        FakeEntityManager.onFakeEntityCreate();

        this.wrapperPlayServerSpawnEntity.setEntityID(this.ENTITY_ID);
        this.wrapperPlayServerSpawnEntity.setType(WrapperPlayServerSpawnEntity.ObjectTypes.ARMORSTAND);
        this.wrapperPlayServerSpawnEntity.setX(loc.getX());
        this.wrapperPlayServerSpawnEntity.setY(loc.getY());
        this.wrapperPlayServerSpawnEntity.setZ(loc.getZ());

        //Set the entity to associate the packet with
        this.wrapperPlayServerEntityMetadata.setEntityID(this.ENTITY_ID);

        Optional<?> opt = Optional
                .of(WrappedChatComponent
                        .fromChatMessage(displayName)[0].getHandle());

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(this.wrapperPlayServerEntityMetadata.getMetadata());
        dataWatcher.setObject(new WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20); //invis
        dataWatcher.setObject(new WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
        dataWatcher.setObject(new WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true); //custom name visible
        dataWatcher.setObject(new WrappedDataWatcherObject(5, WrappedDataWatcher.Registry.get(Boolean.class)), true); //no gravity
        dataWatcher.setObject(new WrappedDataWatcherObject(11, WrappedDataWatcher.Registry.get(Byte.class)), (byte) (0x01 | 0x08 | 0x10)); //isSmall, noBasePlate, set Marker

        this.wrapperPlayServerEntityMetadata.setMetadata(dataWatcher.getWatchableObjects());
    }

    public void showToPlayer(Player p) {
        this.wrapperPlayServerSpawnEntity.sendPacket(p);
        this.wrapperPlayServerEntityMetadata.sendPacket(p);
    }

    public void destroy(Player p) {
        WrapperPlayServerEntityDestroy destroy = new WrapperPlayServerEntityDestroy();
        destroy.setEntityIds(new int[]{ENTITY_ID});
        destroy.sendPacket(p);
    }
}
