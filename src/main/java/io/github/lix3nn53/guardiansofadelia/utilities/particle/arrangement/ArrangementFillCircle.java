package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.Direction;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public class ArrangementFillCircle extends ArrangementSingle {

    private final double radius;
    private final int amount;
    private final Direction direction;

    public ArrangementFillCircle(Particle particle, double radius, int amount, Particle.DustOptions dustOptions, Direction direction) {
        super(particle, dustOptions);
        this.radius = radius;
        this.amount = amount;
        this.direction = direction;
    }

    public ArrangementFillCircle(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("radius")) {
            configLoadError("radius");
        }

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        if (!configurationSection.contains("direction")) {
            configLoadError("direction");
        }

        this.radius = configurationSection.getDouble("radius");
        this.amount = configurationSection.getInt("amount");
        this.direction = Direction.valueOf(configurationSection.getString("direction"));
    }

    @Override
    public void play(Location location) {
        ParticleShapes.fillCircle(location, particle, radius, amount, dustOptions, direction);
    }

    @Override
    public void play(Location location, double radius) {
        ParticleShapes.fillCircle(location, particle, radius, amount, dustOptions, direction);
    }
}
