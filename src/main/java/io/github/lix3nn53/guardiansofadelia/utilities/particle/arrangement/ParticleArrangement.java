package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public interface ParticleArrangement {
    void play(Location location, Vector offset);

    void play(Location location, Vector offset, float yaw, float pitch);
}
