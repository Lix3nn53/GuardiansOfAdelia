package io.github.lix3nn53.guardiansofadelia.utilities.particle;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class ParticleArrangementLoader {

    public static ParticleArrangement load(ConfigurationSection configurationSection) {
        String componentType = configurationSection.getString("arrangementType");

        if (componentType == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NULL ARRANGEMENT TYPE");
            return null;
        }

        componentType += "Arrangement" + componentType;

        if (componentType.equals(ArrangementDrawCylinder.class.getSimpleName())) {
            return new ArrangementDrawCylinder(configurationSection);
        } else if (componentType.equals(ArrangementDrawLine.class.getSimpleName())) {
            return new ArrangementDrawLine(configurationSection);
        } else if (componentType.equals(ArrangementDrawLineBetween.class.getSimpleName())) {
            return new ArrangementDrawLineBetween(configurationSection);
        } else if (componentType.equals(ArrangementDrawSphere.class.getSimpleName())) {
            return new ArrangementDrawSphere(configurationSection);
        } else if (componentType.equals(ArrangementFillCircle.class.getSimpleName())) {
            return new ArrangementFillCircle(configurationSection);
        } else if (componentType.equals(ArrangementFillHemisphere.class.getSimpleName())) {
            return new ArrangementFillHemisphere(configurationSection);
        } else if (componentType.equals(ArrangementFillSphere.class.getSimpleName())) {
            return new ArrangementFillSphere(configurationSection);
        } else if (componentType.equals(ArrangementSingle.class.getSimpleName())) {
            return new ArrangementSingle(configurationSection);
        }

        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NO SUCH ARRANGEMENT TYPE IN LOADER: " + componentType);
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NO SUCH ARRANGEMENT TYPE IN LOADER: " + componentType);
        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NO SUCH ARRANGEMENT TYPE IN LOADER: " + componentType);

        return null;
    }
}
