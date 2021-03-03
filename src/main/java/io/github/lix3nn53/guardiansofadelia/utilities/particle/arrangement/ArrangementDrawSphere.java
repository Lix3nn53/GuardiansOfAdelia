package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ArrangementDrawSphere extends ArrangementWithRadius {

    private final int amount;
    private final int amounty;

    public ArrangementDrawSphere(Particle particle, double radius, int amount, int amounty, Particle.DustOptions dustOptions) {
        super(particle, dustOptions, radius);
        this.amount = amount;
        this.amounty = amounty;
    }

    public ArrangementDrawSphere(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        if (!configurationSection.contains("amounty")) {
            configLoadError("amounty");
        }

        this.amount = configurationSection.getInt("amount");
        this.amounty = configurationSection.getInt("amounty");
    }

    @Override
    public void play(Location location, @Nullable Vector offset) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        ParticleShapes.drawSphere(location1, particle, radius, amount, amounty, dustOptions);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        ParticleShapes.drawSphere(location1, particle, radius, amount, amounty, dustOptions);
    }
}
