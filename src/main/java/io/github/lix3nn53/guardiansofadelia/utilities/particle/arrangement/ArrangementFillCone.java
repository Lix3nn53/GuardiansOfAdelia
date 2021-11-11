package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ArrangementFillCone extends ArrangementWithData {

    private final int amount;
    private final int amounty;
    private final float angle;

    public ArrangementFillCone(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        if (!configurationSection.contains("amounty")) {
            configLoadError("amounty");
        }

        if (!configurationSection.contains("angle")) {
            configLoadError("angle");
        }

        this.amount = configurationSection.getInt("amount");
        this.amounty = configurationSection.getInt("amounty");
        this.angle = configurationSection.getInt("angle");

        // Data that animations can modify
        addData((float) configurationSection.getDouble("length"));

    }

    @Override
    public void play(Location location, @Nullable Vector offset) {
        float length = getData(0);
        ParticleShapes.fillCone(location, particle, length, amount, amounty, angle, dustOptions, false, 0, 0, offset);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        float length = getData(0);
        ParticleShapes.fillCone(location, particle, length, amount, amounty, angle, dustOptions, true, yaw, pitch, offset);
    }
}
