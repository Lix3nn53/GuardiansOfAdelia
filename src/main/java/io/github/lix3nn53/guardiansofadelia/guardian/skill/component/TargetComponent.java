package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class TargetComponent extends SkillComponent {

    private final int max;
    private final boolean self;
    private final boolean allies;
    private final boolean enemy;
    private final boolean armorStand;
    private final boolean keepCurrent;
    private final boolean addToBeginning;

    protected TargetComponent(boolean addLore, boolean allies, boolean enemy, boolean self, int max, boolean armorStand,
                              boolean keepCurrent, boolean addToBeginning) {
        super(addLore);
        this.allies = allies;
        this.enemy = enemy;
        this.max = max;
        this.self = self;
        this.armorStand = armorStand;
        this.keepCurrent = keepCurrent;
        this.addToBeginning = addToBeginning;
    }

    protected TargetComponent(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("allies")) {
            configLoadError("allies");
        }

        if (!configurationSection.contains("enemy")) {
            configLoadError("enemy");
        }

        if (!configurationSection.contains("self")) {
            configLoadError("self");
        }

        if (!configurationSection.contains("max")) {
            configLoadError("max");
        }

        if (configurationSection.contains("armorStand")) {
            this.armorStand = configurationSection.getBoolean("armorStand");
        } else {
            this.armorStand = false;
        }

        if (configurationSection.contains("keepCurrent")) {
            this.keepCurrent = configurationSection.getBoolean("keepCurrent");
        } else {
            this.keepCurrent = false;
        }

        if (configurationSection.contains("addToBeginning")) {
            this.addToBeginning = configurationSection.getBoolean("addToBeginning");
        } else {
            this.addToBeginning = false;
        }

        boolean allies = configurationSection.getBoolean("allies");
        boolean enemy = configurationSection.getBoolean("enemy");
        boolean self = configurationSection.getBoolean("self");
        int max = configurationSection.getInt("max");

        this.allies = allies;
        this.enemy = enemy;
        this.max = max;
        this.self = self;
    }

    /**
     * Method to use on current targets to eliminate not wanted targets.
     * For example we want to eliminate allies on offensive skills. (allies = false)
     *
     * @param caster
     * @param targets
     * @return
     */
    protected List<LivingEntity> determineTargets(final LivingEntity caster, final List<LivingEntity> targets) {
        final List<LivingEntity> list = new ArrayList<>();

        for (LivingEntity target : targets) {
            if (isValidTarget(caster, target)) {
                list.add(target);
                if (list.size() >= max) {
                    break;
                }
            }
        }

        return list;
    }

    /**
     * Target filter.
     *
     * @param caster
     * @param target
     * @return
     */
    private boolean isValidTarget(final LivingEntity caster, final LivingEntity target) {
        if (target == null) return false;
        if (CitizensAPI.getNPCRegistry().isNPC(target)) return false;
        EntityType type = target.getType();
        if (type.equals(EntityType.ARMOR_STAND)) {
            return this.armorStand;
        }

        if (caster == target) return self;

        if (allies == enemy) return allies;

        boolean isEnemy = EntityUtils.canAttack(caster, target);
        if (allies) {
            return !isEnemy;
        } else { // enemy == true
            return isEnemy;
        }
    }

    public int getMax() {
        return max;
    }

    public boolean isSelf() {
        return self;
    }

    public boolean isAllies() {
        return allies;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public boolean isArmorStand() {
        return armorStand;
    }

    public boolean isKeepCurrent() {
        return keepCurrent;
    }

    public boolean isAddToBeginning() {
        return addToBeginning;
    }
}
