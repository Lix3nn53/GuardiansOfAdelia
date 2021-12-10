package io.github.lix3nn53.guardiansofadelia.utilities.particle;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.*;
import org.bukkit.configuration.ConfigurationSection;

public class ParticleArrangementLoader {

    public static ParticleArrangement load(ConfigurationSection configurationSection) {
        String componentType = configurationSection.getString("arrangementType");

        if (componentType == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "NULL ARRANGEMENT TYPE");
            return null;
        }

        componentType = "Arrangement" + componentType;

        if (componentType.equalsIgnoreCase(ArrangementDrawCone.class.getSimpleName())) {
            return new ArrangementDrawCone(configurationSection);
        } else if (componentType.equalsIgnoreCase(ArrangementDrawCube.class.getSimpleName())) {
            return new ArrangementDrawCube(configurationSection);
        } else if (componentType.equalsIgnoreCase(ArrangementDrawCylinder.class.getSimpleName())) {
            return new ArrangementDrawCylinder(configurationSection);
        } else if (componentType.equalsIgnoreCase(ArrangementDrawLine.class.getSimpleName())) {
            return new ArrangementDrawLine(configurationSection);
        } else if (componentType.equalsIgnoreCase(ArrangementDrawLineBetween.class.getSimpleName())) {
            return new ArrangementDrawLineBetween(configurationSection);
        } else if (componentType.equalsIgnoreCase(ArrangementDrawSphere.class.getSimpleName())) {
            return new ArrangementDrawSphere(configurationSection);
        } else if (componentType.equalsIgnoreCase(ArrangementFillCircle.class.getSimpleName())) {
            return new ArrangementFillCircle(configurationSection);
        } else if (componentType.equalsIgnoreCase(ArrangementFillCone.class.getSimpleName())) {
            return new ArrangementFillCone(configurationSection);
        } else if (componentType.equalsIgnoreCase(ArrangementFillHemisphere.class.getSimpleName())) {
            return new ArrangementFillHemisphere(configurationSection);
        } else if (componentType.equalsIgnoreCase(ArrangementFillSphere.class.getSimpleName())) {
            return new ArrangementFillSphere(configurationSection);
        } else if (componentType.equalsIgnoreCase(ArrangementSingle.class.getSimpleName())) {
            return new ArrangementSingle(configurationSection);
        }

        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "NO SUCH ARRANGEMENT TYPE IN LOADER: " + componentType);
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "NO SUCH ARRANGEMENT TYPE IN LOADER: " + componentType);
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "NO SUCH ARRANGEMENT TYPE IN LOADER: " + componentType);

        return null;
    }
}
