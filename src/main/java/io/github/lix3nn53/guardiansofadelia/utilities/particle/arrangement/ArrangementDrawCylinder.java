package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ArrangementDrawCylinder extends ArrangementWithData {

    private final int amount;
    private final float height;

    public ArrangementDrawCylinder(Particle particle, Particle.DustOptions dustOptions, float minHeight, float maxHeight, float gap, int amount, float height) {
        super(particle, dustOptions, minHeight, maxHeight, gap);
        this.amount = amount;
        this.height = height;
    }

    public ArrangementDrawCylinder(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("amount")) {
            configLoadError("amount");
        }

        this.amount = configurationSection.getInt("amount");
        this.height = configurationSection.contains("height") ? (float) configurationSection.getDouble("height") : 0;

        // Data that animations can modify
        addData((float) configurationSection.getDouble("radius"));
    }

    @Override
    public void play(Location location, @Nullable Vector offset) {
        float radius = getData(0);

        ArrangementSingle arrangementSingle = new ArrangementSingle(particle, dustOptions, singleMinHeight, singleMaxHeight, singleGap);

        ParticleShapes.drawCylinder(location, arrangementSingle, radius, amount, height, false, 0, 0, offset);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        float radius = getData(0);

        ArrangementSingle arrangementSingle = new ArrangementSingle(particle, dustOptions, singleMinHeight, singleMaxHeight, singleGap);

        ParticleShapes.drawCylinder(location, arrangementSingle, radius, amount, height, true, yaw, pitch, offset);
    }
}
