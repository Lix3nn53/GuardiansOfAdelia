package io.github.lix3nn53.guardiansofadelia.utilities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedParticle;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.Particle;

public class MyPacketListeners {

    public static void addPacketListeners() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(new PacketAdapter(GuardiansOfAdelia.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Server.WORLD_PARTICLES) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrappedParticle wrappedParticle = event.getPacket().getNewParticles().read(0);
                Particle particle = wrappedParticle.getParticle();
                if (particle.equals(Particle.DAMAGE_INDICATOR)) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
