package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ArrangementDrawLine extends ArrangementSingle {

    private final double length;
    private final double gap;

    public ArrangementDrawLine(Particle particle, Particle.DustOptions dustOptions, double length, double gap) {
        super(particle, dustOptions);
        this.length = length;
        this.gap = gap;
    }

    public ArrangementDrawLine(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("length")) {
            configLoadError("length");
        }

        if (!configurationSection.contains("gap")) {
            configLoadError("gap");
        }

        this.length = configurationSection.getDouble("length");
        this.gap = configurationSection.getDouble("gap");
    }

    @Override
    public void play(Location location, @Nullable Vector offset) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        ParticleShapes.drawLine(location1, particle, dustOptions, length, gap);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        ParticleShapes.drawLine(location1, particle, dustOptions, length, gap);
    }
}
