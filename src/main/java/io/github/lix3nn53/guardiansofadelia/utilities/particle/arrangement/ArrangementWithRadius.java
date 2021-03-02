package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public abstract class ArrangementWithRadius extends ArrangementSingle {
    protected double radius;

    protected ArrangementWithRadius(Particle particle, Particle.DustOptions dustOptions, double radius) {
        super(particle, dustOptions);

        this.radius = radius;
    }

    public ArrangementWithRadius(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("radius")) {
            configLoadError("radius");
        }

        this.radius = configurationSection.getDouble("radius");
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
