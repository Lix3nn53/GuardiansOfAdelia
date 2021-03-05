package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public abstract class ArrangementWithLength extends ArrangementSingle {
    protected double length;

    protected ArrangementWithLength(Particle particle, Particle.DustOptions dustOptions, double length) {
        super(particle, dustOptions);

        this.length = length;
    }

    public ArrangementWithLength(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("length")) {
            configLoadError("length");
        }

        this.length = configurationSection.getDouble("length");
    }

    public void setlength(double length) {
        this.length = length;
    }
}
