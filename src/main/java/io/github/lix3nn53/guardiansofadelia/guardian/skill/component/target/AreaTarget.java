package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
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
public class AreaTarget extends TargetComponent {

    private final List<Float> radiusList;
    private final List<Integer> amountList;
    private final List<Float> offset_xList;
    private final List<Float> offset_yList;
    private final List<Float> offset_zList;
    // PARTICLE
    private final float particleHeight;
    private final ArrangementSingle arrangementSingle;

    private final String multiplyWithValue;

    public AreaTarget(boolean addLore, boolean allies, boolean enemy, boolean self, int max, boolean armorStand, boolean keepCurrent,
                      boolean addToBeginning, List<Float> radiusList, List<Integer> amountList, List<Float> offset_xList,
                      List<Float> offset_yList, List<Float> offset_zList, float particleHeight, ArrangementSingle arrangementSingle, String multiplyWithValue) {
        super(addLore, allies, enemy, self, max, armorStand, keepCurrent, addToBeginning);
        this.radiusList = radiusList;
        this.amountList = amountList;
        this.offset_xList = offset_xList;
        this.offset_yList = offset_yList;
        this.offset_zList = offset_zList;
        this.particleHeight = particleHeight;
        this.arrangementSingle = arrangementSingle;
        this.multiplyWithValue = multiplyWithValue;
    }

    public AreaTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("radiusList")) {
            configLoadError("radiusList");
        }

        this.radiusList = configurationSection.getFloatList("radiusList");
        this.amountList = configurationSection.contains("amountList") ? configurationSection.getIntegerList("amountList") : null;

        this.offset_xList = configurationSection.contains("offset_xList") ? configurationSection.getFloatList("offset_xList") : null;
        this.offset_yList = configurationSection.contains("offset_yList") ? configurationSection.getFloatList("offset_yList") : null;
        this.offset_zList = configurationSection.contains("offset_zList") ? configurationSection.getFloatList("offset_zList") : null;

        // PARTICLE
        if (!configurationSection.contains("particle")) {
            configLoadError("particle");
        }

        ConfigurationSection particleSection = configurationSection.getConfigurationSection("particle");

        this.arrangementSingle = new ArrangementSingle(particleSection);

        this.particleHeight = particleSection.contains("height") ? (float) particleSection.getDouble("height") : 0;

        if (configurationSection.contains("multiplyWithValue")) {
            this.multiplyWithValue = configurationSection.getString("multiplyWithValue");
        } else {
            this.multiplyWithValue = null;
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> nearby = new ArrayList<>();

        Vector offset = new Vector(0, 0.5, 0); // Default offset is 0.5 since it is most used value
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

        float radius = radiusList.get(skillLevel - 1);
        if (multiplyWithValue != null) {
            int value = SkillDataManager.getValue(caster, multiplyWithValue);
            if (value > 0) {
                radius *= value;
            }
        }

        int amount = amountList.get(skillLevel - 1);

        for (LivingEntity target : targets) {
            Location location = target.getLocation();
            location.add(offset);
            List<LivingEntity> nearbyTarget = TargetHelper.getNearbySphere(location, radius);

            ParticleShapes.drawCylinder(location, arrangementSingle, radius, amount, particleHeight, false, 0, 0, new Vector());

            nearby.addAll(nearbyTarget);
        }

        if (nearby.isEmpty()) return true;

        nearby = determineTargets(caster, nearby);

        if (nearby.isEmpty()) return true;

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

        return executeChildren(caster, skillLevel, targetsNew, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatPalette.YELLOW + "Radius: " + radiusList.get(skillLevel));
        } else if (skillLevel == radiusList.size()) {
            additions.add(ChatPalette.YELLOW + "Radius: " + radiusList.get(skillLevel - 1));
        } else {
            additions.add(ChatPalette.YELLOW + "Radius: " + radiusList.get(skillLevel - 1) + " -> " + radiusList.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
