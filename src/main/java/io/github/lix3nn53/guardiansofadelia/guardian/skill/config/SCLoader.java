package io.github.lix3nn53.guardiansofadelia.guardian.skill.config;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.ParticleMechanic;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class SCLoader {

    public static SkillComponent loadSection(ConfigurationSection configurationSection, List<SkillComponent> children) {
        String componentType = configurationSection.getString("componentType");

        SCType scType = SCType.valueOf(componentType);

        if (scType.equals(SCType.AOE_AROUND)) {
            String warnText = configurationSection.getString("warnText");
            int delay = configurationSection.getInt("delay");
            double radius = configurationSection.getDouble("radius");
            String soundStr = configurationSection.getString("sound");
            boolean targetEnemy = configurationSection.getBoolean("targetEnemy");
            ConfigurationSection particleSection = configurationSection.getConfigurationSection("particle");
            ParticleMechanic particleMechanic = loadParticleSection(particleSection);

            return SCMain.getSkillAoeAround(warnText, delay, children, radius, GoaSound.valueOf(soundStr), particleMechanic, targetEnemy);
        }

        return null;
    }

    public static ParticleMechanic loadParticleSection(ConfigurationSection configurationSection) {
        String typeStr = configurationSection.getString("type");

        double radius = configurationSection.getDouble("radius");
        int amount = configurationSection.getInt("amount");

        Particle.DustOptions dustOptions = null;

        if (configurationSection.contains("dustColor")) {
            int dustColor = configurationSection.getInt("dustColor");
            int dustSize = configurationSection.getInt("dustSize");

            dustOptions = new Particle.DustOptions(Color.fromRGB(dustColor), dustSize);
        }

        return SCSmall.getComponentParticleMechanic(Particle.valueOf(typeStr), radius, amount, dustOptions);
    }

}
