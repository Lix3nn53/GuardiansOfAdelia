package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class AreaTarget extends TargetComponent {

    private final List<Double> radiusList;
    private final List<Integer> amountList;
    private final double particleHeight;
    // PARTICLE
    private final Particle particle;
    private final Particle.DustOptions dustOptions;

    public AreaTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("radiusList")) {
            configLoadError("radiusList");
        }

        this.radiusList = configurationSection.getDoubleList("radiusList");
        this.amountList = configurationSection.contains("amountList") ? configurationSection.getIntegerList("amountList") : null;
        this.particleHeight = configurationSection.contains("particleHeight") ? configurationSection.getDouble("particleHeight") : 0;

        ConfigurationSection particleSection = configurationSection.getConfigurationSection("particle");

        this.particle = Particle.valueOf(particleSection.getString("particleType"));

        if (particleSection.contains("dustColor")) {
            if (!this.particle.getDataType().equals(Particle.DustOptions.class)) {
                configLoadError("WRONG DUST OPTIONS");
            }

            int dustColor = particleSection.getInt("dustColor");
            int dustSize = particleSection.getInt("dustSize");

            dustOptions = new Particle.DustOptions(Color.fromRGB(dustColor), dustSize);
        } else {
            dustOptions = null;
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> nearby = new ArrayList<>();

        ArrayList<Location> sphereCentersToDraw = new ArrayList<>();
        double radius = radiusList.get(skillLevel - 1);
        for (LivingEntity target : targets) {
            Location location = target.getLocation();
            List<LivingEntity> nearbyTarget = TargetHelper.getNearbySphere(location, radius);

            if (nearbyTarget.isEmpty()) continue;

            nearby.addAll(nearbyTarget);
            sphereCentersToDraw.add(location);
        }

        if (nearby.isEmpty()) return false;

        nearby = determineTargets(caster, nearby);

        if (nearby.isEmpty()) return false;

        for (Location center : sphereCentersToDraw) {
            int amount = amountList.get(skillLevel - 1);
            ParticleShapes.drawCylinder(center, particle, radius, amount, dustOptions, particleHeight, false, 0, 0, new Vector());
        }

        List<LivingEntity> targetsNew = new ArrayList<>();
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                nearby.addAll(targets);
                targetsNew = nearby;
            } else {
                targetsNew.addAll(targets);
                targetsNew.addAll(nearby);
            }
        } else {
            targetsNew = nearby;
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatColor.YELLOW + "Radius: " + radiusList.get(skillLevel));
        } else if (skillLevel == radiusList.size()) {
            additions.add(ChatColor.YELLOW + "Radius: " + radiusList.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.YELLOW + "Radius: " + radiusList.get(skillLevel - 1) + " -> " + radiusList.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
