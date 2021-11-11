package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ArrangementFillSphere extends ArrangementWithData {

    private final int amount;
    private final int amounty;

    public ArrangementFillSphere(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        if (!configurationSection.contains("amounty")) {
            configLoadError("amounty");
        }

        this.amount = configurationSection.getInt("amount");
        this.amounty = configurationSection.getInt("amounty");

        // Data that animations can modify
        addData((float) configurationSection.getDouble("radius"));
    }

    @Override
    public void play(Location location, @Nullable Vector offset) {
        float radius = getData(0);
        ParticleShapes.fillSphere(location, particle, radius, amount, amounty, dustOptions, false, 0, 0, offset);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        float radius = getData(0);
        ParticleShapes.fillSphere(location, particle, radius, amount, amounty, dustOptions, true, yaw, pitch, offset);
    }
}
