package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ArrangementDrawCone extends ArrangementWithData {

    private final int amount;
    private final int amounty;
    private final double angle;

    public ArrangementDrawCone(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        if (!configurationSection.contains("amounty")) {
            configLoadError("amounty");
        }

        if (!configurationSection.contains("phi")) {
            configLoadError("phi");
        }

        if (!configurationSection.contains("length")) {
            configLoadError("length");
        }

        this.amount = configurationSection.getInt("amount");
        this.amounty = configurationSection.getInt("amounty");
        this.angle = configurationSection.getInt("angle");

        // Data that animations can modify
        addData(configurationSection.getDouble("length"));
    }

    @Override
    public void play(Location location, @Nullable Vector offset) {
        double length = getData(0);

        ArrangementSingle arrangementSingle = new ArrangementSingle(particle, dustOptions, singleMinHeight, singleMaxHeight, singleGap);

        ParticleShapes.drawCone(location, arrangementSingle, length, amount, amounty, angle, false, 0, 0, offset);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        double length = getData(0);

        ArrangementSingle arrangementSingle = new ArrangementSingle(particle, dustOptions, singleMinHeight, singleMaxHeight, singleGap);

        ParticleShapes.drawCone(location, arrangementSingle, length, amount, amounty, angle, true, yaw, pitch, offset);
    }
}
