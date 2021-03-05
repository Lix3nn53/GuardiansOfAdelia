package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.math.RotationHelper;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

public class ArrangementSingle implements ParticleArrangement {
    protected final Particle particle;
    protected final Particle.DustOptions dustOptions;

    protected ArrangementSingle(Particle particle, Particle.DustOptions dustOptions) {
        this.particle = particle;
        this.dustOptions = dustOptions;
    }

    public ArrangementSingle(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("particleType")) {
            configLoadError("particleType");
        }

        this.particle = Particle.valueOf(configurationSection.getString("particleType"));


        if (configurationSection.contains("dustColor")) {
            if (!this.particle.getDataType().equals(Particle.DustOptions.class)) {
                configLoadError("WRONG DUST OPTIONS");
            }

            int dustColor = configurationSection.getInt("dustColor");
            int dustSize = configurationSection.getInt("dustSize");

            dustOptions = new Particle.DustOptions(Color.fromRGB(dustColor), dustSize);
        } else {
            dustOptions = null;
        }
    }

    @Override
    public void play(Location location, Vector offset) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }
        ParticleShapes.playSingleParticle(location1, particle, dustOptions);
    }

    @Override
    public void play(Location location, Vector offset, float yaw, float pitch) {
        Vector vector = location.clone().add(offset).toVector();

        RotationHelper.rotateYawPitch(vector, yaw, pitch);

        ParticleShapes.playSingleParticle(location.getWorld(), vector, particle, dustOptions);
    }

    public void configLoadError(String section) {
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "ERROR WHILE LOADING PARTICLE: ");
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "Section: " + section);
    }
}
