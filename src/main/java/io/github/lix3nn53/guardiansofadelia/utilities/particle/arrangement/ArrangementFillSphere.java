package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public class ArrangementFillSphere extends ArrangementWithRadius {

    private final int amount;

    public ArrangementFillSphere(Particle particle, double radius, int amount, Particle.DustOptions dustOptions) {
        super(particle, dustOptions, radius);
        this.amount = amount;
    }

    public ArrangementFillSphere(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        this.amount = configurationSection.getInt("amount");
    }

    @Override
    public void play(Location location) {
        ParticleShapes.fillSphere(location, particle, radius, amount, dustOptions);
    }
}
