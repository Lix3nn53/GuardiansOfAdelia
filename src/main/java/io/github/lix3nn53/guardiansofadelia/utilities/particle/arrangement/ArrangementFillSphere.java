package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public class ArrangementFillSphere extends ArrangementSingle {

    private final double radius;
    private final int amount;

    public ArrangementFillSphere(Particle particle, double radius, int amount, Particle.DustOptions dustOptions) {
        super(particle, dustOptions);
        this.radius = radius;
        this.amount = amount;
    }

    public ArrangementFillSphere(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("radius")) {
            configLoadError("radius");
        }

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        this.radius = configurationSection.getDouble("radius");
        this.amount = configurationSection.getInt("amount");
    }

    @Override
    public void play(Location location) {
        ParticleShapes.fillSphere(location, particle, radius, amount, dustOptions);
    }

    @Override
    public void play(Location location, double radius) {
        ParticleShapes.fillSphere(location, particle, radius, amount, dustOptions);
    }
}
