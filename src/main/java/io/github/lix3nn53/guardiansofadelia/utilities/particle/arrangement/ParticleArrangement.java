package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import org.bukkit.Location;

public interface ParticleArrangement {
    void play(Location location);

    void play(Location location, double extra);
}
