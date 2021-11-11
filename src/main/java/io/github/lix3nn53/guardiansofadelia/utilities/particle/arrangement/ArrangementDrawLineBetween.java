package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public class ArrangementDrawLineBetween extends ArrangementSingle implements ParticleArrangementMultipleLocation {
    protected final float gap;

    protected ArrangementDrawLineBetween(Particle particle, Particle.DustOptions dustOptions, float gap, float singleMinHeight, float singleMaxHeight, float singleGap) {
        super(particle, dustOptions, singleMinHeight, singleMaxHeight, singleGap);
        this.gap = gap;
    }

    public ArrangementDrawLineBetween(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("gap")) {
            configLoadError("gap");
        }

        this.gap = (float) configurationSection.getDouble("gap");
    }

    @Override
    public void play(Location start, Location end) {
        ParticleShapes.drawLineBetween(start.getWorld(), start.toVector(), particle, dustOptions, end.toVector(), gap);
    }
}
