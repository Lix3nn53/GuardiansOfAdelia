package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ArrangementSingle;
import org.bukkit.Location;
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

    protected final List<Float> forwardList;
    protected final List<Float> upwardList;
    protected final List<Float> rightList;
    protected final boolean resetY;
    protected final boolean centerEye;
    private final List<Float> length_xList;
    private final List<Float> length_yList;
    private final List<Float> length_zList;
    private final List<Float> offset_xList;
    private final List<Float> offset_yList;
    private final List<Float> offset_zList;
    //ROTATION
    private final boolean rotation;
    private final boolean rotationMatchEye;
    private final float yaw;
    private final float pitch;

    // Particle custom
    private final float gap;
    // PARTICLE
    private final ArrangementSingle arrangementSingle;

    public CubeTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("length_xList")) configLoadError("length_xList");
        if (!configurationSection.contains("length_yList")) configLoadError("length_yList");
        if (!configurationSection.contains("length_zList")) configLoadError("length_zList");

        this.length_xList = configurationSection.getFloatList("length_xList");
        this.length_yList = configurationSection.getFloatList("length_yList");
        this.length_zList = configurationSection.getFloatList("length_zList");

        this.offset_xList = configurationSection.contains("offset_xList") ? configurationSection.getFloatList("offset_xList") : null;
        this.offset_yList = configurationSection.contains("offset_yList") ? configurationSection.getFloatList("offset_yList") : null;
        this.offset_zList = configurationSection.contains("offset_zList") ? configurationSection.getFloatList("offset_zList") : null;

        this.forwardList = configurationSection.contains("forwardList") ? configurationSection.getFloatList("forwardList") : null;
        this.upwardList = configurationSection.contains("upwardList") ? configurationSection.getFloatList("upwardList") : null;
        this.rightList = configurationSection.contains("rightList") ? configurationSection.getFloatList("rightList") : null;

        this.resetY = configurationSection.contains("resetY") && configurationSection.getBoolean("resetY");
        this.centerEye = configurationSection.contains("centerEye") && configurationSection.getBoolean("centerEye");

        this.rotation = configurationSection.getBoolean("rotation");
        this.rotationMatchEye = configurationSection.getBoolean("rotationMatchEye");

        this.yaw = configurationSection.contains("yaw") ? (float) (float) configurationSection.getDouble("yaw") : 0;
        this.pitch = configurationSection.contains("pitch") ? (float) (float) configurationSection.getDouble("pitch") : 0;

        // Particle custom
        this.gap = (float) configurationSection.getDouble("gap");

        // PARTICLE
        if (!configurationSection.contains("particle")) {
            configLoadError("particle");
        }

        ConfigurationSection particleSection = configurationSection.getConfigurationSection("particle");

        this.arrangementSingle = new ArrangementSingle(particleSection);
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> cube = new ArrayList<>();

        Vector offset = new Vector(0, 0, 0);
        if (offset_xList != null) {
            float offset_x = offset_xList.get(skillLevel - 1);
            offset.setX(offset_x);
        }
        if (offset_xList != null) {
            float offset_y = offset_yList.get(skillLevel - 1);
            offset.setY(offset_y);
        }
        if (offset_xList != null) {
            float offset_z = offset_zList.get(skillLevel - 1);
            offset.setZ(offset_z);
        }
        for (LivingEntity ent : targets) {
            Location location = centerEye ? ent.getEyeLocation() : ent.getLocation();
            // Offset
            location.add(offset);
            // Length
            float length_x = length_xList.get(skillLevel - 1);
            float length_y = length_yList.get(skillLevel - 1);
            float length_z = length_zList.get(skillLevel - 1);
            Vector lengthV = new Vector(length_x, length_y, length_z);

            Vector dir = location.getDirection();
            if (resetY) dir.setY(0);
            dir.normalize();
            Vector side = dir.clone().crossProduct(new Vector(0, 1, 0));
            Vector upwardly = dir.clone().crossProduct(side);
            float forward = 0;
            float upward = 0;
            float right = 0;
            if (forwardList != null) forward = forwardList.get(skillLevel - 1);
            if (upwardList != null) forward = upwardList.get(skillLevel - 1);
            if (rightList != null) forward = rightList.get(skillLevel - 1);
            location.add(dir.multiply(forward)).subtract(upwardly.multiply(upward)).add(side.multiply(right));

            Vector[] vectors;
            if (rotation) {
                Location eyeLocation = ent.getEyeLocation();
                float yaw2 = rotationMatchEye ? eyeLocation.getYaw() + yaw : yaw;
                float pitch2 = rotationMatchEye ? eyeLocation.getPitch() + pitch : pitch;
                vectors = ParticleShapes.calculateCubeCorners(location, lengthV, true, yaw2, pitch2);
            } else {
                vectors = ParticleShapes.calculateCubeCorners(location, lengthV, false, 0, 0);
            }

            /*Vector b1 = vectors[0];
            Vector b2 = vectors[1];
            Vector b4 = vectors[4];
            Vector t1 = vectors[3];
            Vector t3 = vectors[6];*/

            // List<LivingEntity> boxTargets = TargetHelper.getBoxTargets(location.getWorld(), b1, b2, b4, t1, t3);
            List<LivingEntity> boxTargets = TargetHelper.getBoxTargets(location.getWorld(), vectors, length_x, length_y, length_z);

            ParticleShapes.drawCubeEdges(targets.get(0).getWorld(), vectors, arrangementSingle, gap);

            cube.addAll(boxTargets);
        }

        if (cube.isEmpty()) return true;

        cube = determineTargets(caster, cube);

        if (cube.isEmpty()) return true;

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

        return executeChildren(caster, skillLevel, targetsNew, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatPalette.YELLOW + "Cube Area: " + length_xList.get(skillLevel) + ", " + length_yList.get(skillLevel) + ", " + length_zList.get(skillLevel));
        } else if (skillLevel == length_xList.size()) {
            additions.add(ChatPalette.YELLOW + "Cube Area: " + length_xList.get(skillLevel - 1) + ", " + length_yList.get(skillLevel - 1) + ", " + length_zList.get(skillLevel - 1));
        } else {
            additions.add(ChatPalette.YELLOW + "Cube Area: " + length_xList.get(skillLevel - 1) + ", " + length_yList.get(skillLevel - 1) + ", " + length_zList.get(skillLevel - 1)
                    + " -> " + length_xList.get(skillLevel - 1) + ", " + length_yList.get(skillLevel - 1) + ", " + length_zList.get(skillLevel - 1));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
