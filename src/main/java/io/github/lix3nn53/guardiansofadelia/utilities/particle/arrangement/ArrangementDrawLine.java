package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

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
    public void play(Location location) {
        ParticleShapes.drawLine(location, particle, dustOptions, length, gap);
    }
}
