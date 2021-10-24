package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.particles;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleArrangementLoader;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ArrangementWithData;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ParticleArrangement;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleMechanic extends MechanicComponent {

    protected final ParticleArrangement particleArrangement;
    protected final List<List<Double>> dataIndexToDataList; // Data index is index of data while data list contains values according to skill level

    protected final List<Double> forwardList;
    protected final double upward;
    protected final double right;

    protected final boolean resetY;
    protected final boolean centerEye;
    protected final boolean rotation;
    protected final boolean rotationMatchEye;

    protected final float yaw;
    protected final float pitch;

    protected final double offsetx;
    protected final double offsety;
    protected final double offsetz;

    public ParticleMechanic(boolean addLore, ParticleArrangement particleArrangement, List<List<Double>> dataIndexToDataList,
                            List<Double> forwardList, double upward, double right, boolean resetY, boolean centerEye, boolean rotation,
                            boolean rotationMatchEye, float yaw, float pitch, double offsetx, double offsety, double offsetz) {
        super(addLore);
        this.particleArrangement = particleArrangement;
        this.dataIndexToDataList = dataIndexToDataList;
        this.forwardList = forwardList;
        this.upward = upward;
        this.right = right;
        this.resetY = resetY;
        this.centerEye = centerEye;
        this.rotation = rotation;
        this.rotationMatchEye = rotationMatchEye;
        this.yaw = yaw;
        this.pitch = pitch;
        this.offsetx = offsetx;
        this.offsety = offsety;
        this.offsetz = offsetz;
    }

    public ParticleMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        ConfigurationSection particle = configurationSection.getConfigurationSection("particle");
        this.particleArrangement = ParticleArrangementLoader.load(particle);

        this.dataIndexToDataList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            if (!configurationSection.contains("data" + i)) break;
            this.dataIndexToDataList.add(configurationSection.getDoubleList("data" + i));
        }

        this.forwardList = configurationSection.contains("forwardList") ? configurationSection.getDoubleList("forwardList") : null;
        this.upward = configurationSection.contains("upward") ? configurationSection.getDouble("upward") : 0;
        this.right = configurationSection.contains("right") ? configurationSection.getDouble("right") : 0;

        this.resetY = configurationSection.contains("resetY") && configurationSection.getBoolean("resetY");
        this.centerEye = configurationSection.contains("centerEye") && configurationSection.getBoolean("centerEye");
        this.rotation = configurationSection.contains("rotation") && configurationSection.getBoolean("rotation");
        this.rotationMatchEye = configurationSection.contains("rotationMatchEye") && configurationSection.getBoolean("rotationMatchEye");

        this.yaw = configurationSection.contains("yaw") ? (float) configurationSection.getDouble("yaw") : 0;
        this.pitch = configurationSection.contains("pitch") ? (float) configurationSection.getDouble("pitch") : 0;

        this.offsetx = configurationSection.contains("offsetx") ? configurationSection.getDouble("offsetx") : 0;
        this.offsety = configurationSection.contains("offsety") ? configurationSection.getDouble("offsety") : 0;
        this.offsetz = configurationSection.contains("offsetz") ? configurationSection.getDouble("offsetz") : 0;
    }

    public static void playParticle(LivingEntity ent, int skillLevel, boolean centerEye, boolean resetY, List<Double> forwardList, double upward,
                                    double right, List<List<Double>> dataIndexToDataList, ParticleArrangement particleArrangement, boolean rotation,
                                    boolean rotationMatchEye, float yaw, float pitch, double offsetx, double offsety, double offsetz,
                                    List<Double> dataIncrements, int counter) {
        Location location = centerEye ? ent.getEyeLocation() : ent.getLocation();

        Vector dir = location.getDirection();
        if (resetY) dir.setY(0);
        dir.normalize();
        Vector side = dir.clone().crossProduct(new Vector(0, 1, 0));
        Vector upwardly = dir.clone().crossProduct(side);
        double forward = 0;
        if (forwardList != null) forward = forwardList.get(skillLevel - 1);
        location.add(dir.multiply(forward)).subtract(upwardly.multiply(upward)).add(side.multiply(right));

        if (!dataIndexToDataList.isEmpty()) {
            ArrangementWithData arrangementWithRadius = (ArrangementWithData) particleArrangement;

            List<Double> currentDataList = new ArrayList<>(); // get current
            for (int i = 0; i < dataIndexToDataList.size(); i++) {
                List<Double> list = dataIndexToDataList.get(i);
                double current = list.get(skillLevel - 1);
                if (i < dataIncrements.size()) {
                    current += dataIncrements.get(i) * counter;
                }
                currentDataList.add(current);
            }

            arrangementWithRadius.setDataList(currentDataList);
        }

        if (rotation) {
            Location eyeLocation = ent.getEyeLocation();
            float yaw2 = rotationMatchEye ? eyeLocation.getYaw() + yaw : yaw;
            float pitch2 = rotationMatchEye ? eyeLocation.getPitch() + pitch : pitch;

            particleArrangement.play(location, new Vector(offsetx, offsety, offsetz),
                    yaw2, pitch2);
        } else {
            particleArrangement.play(location, new Vector(offsetx, offsety, offsetz));
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            playParticle(ent, skillLevel, centerEye, resetY, forwardList, upward, right, dataIndexToDataList, particleArrangement, rotation,
                    rotationMatchEye, yaw, pitch, offsetx, offsety, offsetz, new ArrayList<>(), 0);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
