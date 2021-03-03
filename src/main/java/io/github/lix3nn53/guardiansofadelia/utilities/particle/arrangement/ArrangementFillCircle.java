package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.Direction;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

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
    public void play(Location location, @Nullable Vector offset) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        ParticleShapes.fillCircle(location1, particle, radius, amount, dustOptions, direction);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        ParticleShapes.fillCircle(location1, particle, radius, amount, dustOptions, direction);
    }
}
