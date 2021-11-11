package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ArrangementDrawCube extends ArrangementWithData {

    private final float gap;

    public ArrangementDrawCube(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("gap")) {
            configLoadError("gap");
        }

        this.gap = (float) configurationSection.getDouble("gap");

        // Data that animations can modify
        addData((float) configurationSection.getDouble("length_x"));
        addData((float) configurationSection.getDouble("length_y"));
        addData((float) configurationSection.getDouble("length_z"));
    }

    @Override
    public void play(Location location, @Nullable Vector offset) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        Vector vector = new Vector(getData(0), getData(1), getData(2));

        ArrangementSingle arrangementSingle = new ArrangementSingle(particle, dustOptions, singleMinHeight, singleMaxHeight, singleGap);

        ParticleShapes.drawCube(location1, arrangementSingle, vector, gap, false, 0, 0);
    }

    @Override
    public void play(Location location, @Nullable Vector offset, float yaw, float pitch) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        Vector vector = new Vector(getData(0), getData(1), getData(2));

        ArrangementSingle arrangementSingle = new ArrangementSingle(particle, dustOptions, singleMinHeight, singleMaxHeight, singleGap);

        ParticleShapes.drawCube(location1, arrangementSingle, vector, gap, true, yaw, pitch);
    }
}
