package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class FakeHologram {

    private final EntityArmorStand entityArmorStand;

    public FakeHologram(Location loc, String displayName) {
        EntityArmorStand entity = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ());
        entity.setCustomName(new ChatComponentText(displayName));
        entity.setCustomNameVisible(true);
        entity.setInvisible(true);
        entity.setNoGravity(true);
        entity.setMarker(true);

        this.entityArmorStand = entity;
    }

    public void showToPlayer(Player p) {
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(this.entityArmorStand);

        PacketPlayOutEntityMetadata entityMetaPacket = new PacketPlayOutEntityMetadata(this.entityArmorStand.getId(),
                this.entityArmorStand.getDataWatcher(), true);

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        try {
            protocolManager.sendServerPacket(p, PacketContainer.fromPacket(packet));
            protocolManager.sendServerPacket(p, PacketContainer.fromPacket(entityMetaPacket));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(Player p) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(this.entityArmorStand.getId());

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, PacketContainer.fromPacket(packet));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
