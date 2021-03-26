package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ArrangementDrawCylinder extends ArrangementWithData {

    private final int amount;
    private final double height;

    public ArrangementDrawCylinder(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        this.amount = configurationSection.getInt("amount");
        this.height = configurationSection.contains("height") ? configurationSection.getDouble("height") : 0;

        // Data that animations can modify
        addData(configurationSection.getDouble("radius"));
    }

    @Override
    public void play(Location location, @Nullable Vector offset) {
        double radius = getData(0);
        ParticleShapes.drawCylinder(location, particle, radius, amount, dustOptions, height, false, 0, 0, offset);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        double radius = getData(0);
        ParticleShapes.drawCylinder(location, particle, radius, amount, dustOptions, height, true, yaw, pitch, offset);
    }
}
