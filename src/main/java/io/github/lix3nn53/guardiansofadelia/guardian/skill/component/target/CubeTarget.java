package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.math.RotationHelper;
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
public class CubeTarget extends TargetComponent {

    protected final List<Double> forwardList;
    protected final List<Double> upwardList;
    protected final List<Double> rightList;
    protected final boolean resetY;
    protected final boolean centerEye;
    private final List<Double> length_xList;
    private final List<Double> length_yList;
    private final List<Double> length_zList;
    private final List<Double> offset_xList;
    private final List<Double> offset_yList;
    private final List<Double> offset_zList;
    //ROTATION
    private final boolean rotation;
    private final boolean rotationMatchEye;
    private final float yaw;
    private final float pitch;

    // Particle custom
    private final double gap;
    // PARTICLE
    private final Particle particle;
    private final Particle.DustOptions dustOptions;

    public CubeTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("length_xList")) configLoadError("length_xList");
        if (!configurationSection.contains("length_yList")) configLoadError("length_yList");
        if (!configurationSection.contains("length_zList")) configLoadError("length_zList");

        this.length_xList = configurationSection.getDoubleList("length_xList");
        this.length_yList = configurationSection.getDoubleList("length_yList");
        this.length_zList = configurationSection.getDoubleList("length_zList");

        this.offset_xList = configurationSection.contains("offset_xList") ? configurationSection.getDoubleList("offset_xList") : null;
        this.offset_yList = configurationSection.contains("offset_yList") ? configurationSection.getDoubleList("offset_yList") : null;
        this.offset_zList = configurationSection.contains("offset_zList") ? configurationSection.getDoubleList("offset_zList") : null;

        this.forwardList = configurationSection.contains("forwardList") ? configurationSection.getDoubleList("forwardList") : null;
        this.upwardList = configurationSection.contains("upwardList") ? configurationSection.getDoubleList("upwardList") : null;
        this.rightList = configurationSection.contains("rightList") ? configurationSection.getDoubleList("rightList") : null;

        this.resetY = configurationSection.contains("resetY") && configurationSection.getBoolean("resetY");
        this.centerEye = configurationSection.contains("centerEye") && configurationSection.getBoolean("centerEye");

        this.rotation = configurationSection.getBoolean("rotation");
        this.rotationMatchEye = configurationSection.getBoolean("rotationMatchEye");

        this.yaw = configurationSection.contains("yaw") ? (float) configurationSection.getDouble("yaw") : 0;
        this.pitch = configurationSection.contains("pitch") ? (float) configurationSection.getDouble("pitch") : 0;

        // Particle custom
        this.gap = configurationSection.getDouble("gap");

        // PARTICLE
        if (!configurationSection.contains("particle")) {
            configLoadError("particle");
        }

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

        List<LivingEntity> cube = new ArrayList<>();

        Vector offset = new Vector(0, 0, 0);
        if (offset_xList != null) {
            double offset_x = offset_xList.get(skillLevel - 1);
            offset.setX(offset_x);
        }
        if (offset_xList != null) {
            double offset_y = offset_yList.get(skillLevel - 1);
            offset.setY(offset_y);
        }
        if (offset_xList != null) {
            double offset_z = offset_zList.get(skillLevel - 1);
            offset.setZ(offset_z);
        }
        for (LivingEntity ent : targets) {
            Location location = centerEye ? ent.getEyeLocation() : ent.getLocation();
            // Offset
            location.add(offset);
            // Length
            double length_x = length_xList.get(skillLevel - 1);
            double length_y = length_yList.get(skillLevel - 1);
            double length_z = length_zList.get(skillLevel - 1);
            Vector lengthV = new Vector(length_x, length_y, length_z);

            Vector dir = location.getDirection();
            if (resetY) dir.setY(0);
            dir.normalize();
            Vector side = dir.clone().crossProduct(new Vector(0, 1, 0));
            Vector upwardly = dir.clone().crossProduct(side);
            double forward = 0;
            double upward = 0;
            double right = 0;
            if (forwardList != null) forward = forwardList.get(skillLevel - 1);
            if (upwardList != null) forward = upwardList.get(skillLevel - 1);
            if (rightList != null) forward = rightList.get(skillLevel - 1);
            location.add(dir.multiply(forward)).subtract(upwardly.multiply(upward)).add(side.multiply(right));

            Vector[] vectors;
            if (rotation) {
                Location eyeLocation = ent.getEyeLocation();
                float yaw2 = rotationMatchEye ? eyeLocation.getYaw() + yaw : yaw;
                float pitch2 = rotationMatchEye ? eyeLocation.getPitch() + pitch : pitch;
                vectors = ParticleShapes.drawCube(location, particle, dustOptions, lengthV, gap, true, yaw2, pitch2);
            } else {
                vectors = ParticleShapes.drawCube(location, particle, dustOptions, lengthV, gap, false, 0, 0);
            }

            Vector vector = location.toVector();
            RotationHelper.rotateYawPitch(vector, yaw, pitch);
            /* TODO List<LivingEntity> cubeTargets = TargetHelper.getConeTargets(target, angleList.get(skillLevel - 1), rangeList.get(skillLevel - 1));

            cube.addAll(cubeTargets);*/
        }

        if (cube.isEmpty()) return false;

        cube = determineTargets(caster, cube);

        if (cube.isEmpty()) return false;

        List<LivingEntity> targetsNew = new ArrayList<>();
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                cube.addAll(targets);
                targetsNew = cube;
            } else {
                targetsNew.addAll(targets);
                targetsNew.addAll(cube);
            }
        } else {
            targetsNew = cube;
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        additions.add(ChatColor.YELLOW + "Cube: " + length_xList.get(skillLevel - 1) + ", " + length_yList.get(skillLevel - 1) + ", " + length_zList.get(skillLevel - 1));
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
