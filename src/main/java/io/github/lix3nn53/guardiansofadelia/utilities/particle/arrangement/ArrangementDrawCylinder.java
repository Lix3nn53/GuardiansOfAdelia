package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public class ArrangementDrawCylinder extends ArrangementSingle {

    private final double radius;
    private final int amount;
    private final double height;

    public ArrangementDrawCylinder(Particle particle, double radius, int amount, Particle.DustOptions dustOptions, double height) {
        super(particle, dustOptions);
        this.radius = radius;
        this.amount = amount;
        this.height = height;
    }

    public ArrangementDrawCylinder(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("radius")) {
            configLoadError("radius");
        }

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        this.radius = configurationSection.getDouble("radius");
        this.amount = configurationSection.getInt("amount");
        this.height = configurationSection.contains("height") ? configurationSection.getDouble("height") : 0;

    }

    @Override
    public void play(Location location) {
        ParticleShapes.drawCylinder(location, particle, radius, amount, dustOptions, height);
    }

    @Override
    public void play(Location location, double radius) {
        ParticleShapes.drawCylinder(location, particle, radius, amount, dustOptions, height);
    }
}
