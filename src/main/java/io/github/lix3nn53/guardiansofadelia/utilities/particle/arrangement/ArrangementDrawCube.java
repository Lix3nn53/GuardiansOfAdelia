package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ArrangementDrawCube extends ArrangementWithLength {

    private final int gap;

    public ArrangementDrawCube(Particle particle, double radius, int gap, Particle.DustOptions dustOptions, double height) {
        super(particle, dustOptions, radius);
        this.gap = gap;
    }

    public ArrangementDrawCube(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("gap")) {
            configLoadError("gap");
        }

        this.gap = configurationSection.getInt("gap");
    }

    @Override
    public void play(Location location, @Nullable Vector offset) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        ParticleShapes.drawCube(location1, particle, dustOptions, length, gap, false, 0, 0);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        ParticleShapes.drawCube(location1, particle, dustOptions, length, gap, true, yaw, pitch);
    }
}
