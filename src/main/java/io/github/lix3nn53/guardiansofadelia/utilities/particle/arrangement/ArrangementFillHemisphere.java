package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ArrangementFillHemisphere extends ArrangementWithData {

    private final int amount;

    public ArrangementFillHemisphere(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("radius")) {
            configLoadError("radius");
        }

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        this.amount = configurationSection.getInt("amount");

        // Data that animations can modify
        addData((float) configurationSection.getDouble("radius"));
    }

    @Override
    public void play(Location location, @Nullable Vector offset) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        float radius = getData(0);
        ParticleShapes.fillHemisphere(location1, particle, radius, amount, dustOptions);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        float radius = getData(0);
        ParticleShapes.fillHemisphere(location1, particle, radius, amount, dustOptions);
    }
}
