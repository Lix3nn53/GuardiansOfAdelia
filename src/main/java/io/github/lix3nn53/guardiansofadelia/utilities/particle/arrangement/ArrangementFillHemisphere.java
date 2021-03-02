package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public class ArrangementFillHemisphere extends ArrangementWithRadius {

    private final int amount;

    public ArrangementFillHemisphere(Particle particle, double radius, int amount, Particle.DustOptions dustOptions) {
        super(particle, dustOptions, radius);
        this.amount = amount;
    }

    public ArrangementFillHemisphere(ConfigurationSection configurationSection) {
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
        ParticleShapes.fillHemisphere(location, particle, radius, amount, dustOptions);
    }
}
