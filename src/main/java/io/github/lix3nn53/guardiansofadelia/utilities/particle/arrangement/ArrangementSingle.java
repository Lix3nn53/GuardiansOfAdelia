package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleUtil;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

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
    public void play(Location location) {
        ParticleUtil.playSingleParticle(location, particle, dustOptions);
    }

    @Override
    public void play(Location location, double extra) {
        ParticleUtil.playSingleParticle(location, particle, dustOptions);
    }

    public void configLoadError(String section) {
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "ERROR WHILE LOADING PARTICLE: ");
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "Section: " + section);
    }
}
