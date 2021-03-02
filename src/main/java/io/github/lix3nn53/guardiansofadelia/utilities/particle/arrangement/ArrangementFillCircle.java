package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.Direction;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public class ArrangementFillCircle extends ArrangementWithRadius {

    private final int amount;
    private final Direction direction;

    public ArrangementFillCircle(Particle particle, double radius, int amount, Particle.DustOptions dustOptions, Direction direction) {
        super(particle, dustOptions, radius);
        this.amount = amount;
        this.direction = direction;
    }

    public ArrangementFillCircle(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        if (!configurationSection.contains("direction")) {
            configLoadError("direction");
        }

        this.amount = configurationSection.getInt("amount");
        this.direction = Direction.valueOf(configurationSection.getString("direction"));
    }

    @Override
    public void play(Location location) {
        ParticleShapes.fillCircle(location, particle, radius, amount, dustOptions, direction);
    }
}
