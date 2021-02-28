package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public class ArrangementDrawSphere extends ArrangementSingle {

    private final double radius;
    private final int amount;
    private final int amounty;

    public ArrangementDrawSphere(Particle particle, double radius, int amount, int amounty, Particle.DustOptions dustOptions) {
        super(particle, dustOptions);
        this.radius = radius;
        this.amount = amount;
        this.amounty = amounty;
    }

    public ArrangementDrawSphere(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("radius")) {
            configLoadError("radius");
        }

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        if (!configurationSection.contains("amounty")) {
            configLoadError("amounty");
        }

        this.radius = configurationSection.getDouble("radius");
        this.amount = configurationSection.getInt("amount");
        this.amounty = configurationSection.getInt("amounty");
    }

    @Override
    public void play(Location location) {
        ParticleUtil.drawSphere(location, particle, radius, amount, amounty, dustOptions);
    }

    @Override
    public void play(Location location, double radius) {
        ParticleUtil.drawSphere(location, particle, radius, amount, amounty, dustOptions);
    }
}
