package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;

import java.util.ArrayList;
import java.util.List;

public abstract class TargetComponent extends SkillComponent {

    private final int max;
    private final boolean self;
    private final boolean allies;
    private final boolean enemy;

    protected TargetComponent(boolean allies, boolean enemy, boolean self, int max) {
        this.allies = allies;
        this.enemy = enemy;
        this.max = max;
        this.self = self;
    }

    protected TargetComponent(ConfigurationSection configurationSection) {
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
        if (type.equals(EntityType.ARMOR_STAND)) return false; //ignore list of target mechanics

        if (caster == target) return self;

        if (allies == enemy) return allies;

        boolean isEnemy = canAttack(caster, target);
        if (allies) {
            return !isEnemy;
        } else { //enemy = true
            return isEnemy;
        }
    }

    /**
     * Checks whether or not something can be attacked
     *
     * @param attacker the attacking entity
     * @param target   the target entity
     * @return true if can be attacked, false otherwise
     */

    private boolean canAttack(LivingEntity attacker, LivingEntity target) {
        if (attacker instanceof Player) {
            if (target instanceof Player) {
                if (attacker == target) return false;
                if (attacker.getWorld().getName().equals("arena")) {

                    Player attackerPlayer = (Player) attacker;

                    if (PartyManager.inParty(attackerPlayer)) {
                        Party party = PartyManager.getParty(attackerPlayer);
                        List<Player> members = party.getMembers();
                        return !members.contains(target);
                    }

                    return true;
                }

                return false;
            } else if (target instanceof Tameable) {
                Tameable tameable = (Tameable) target;
                if (tameable.isTamed() && (tameable.getOwner() instanceof LivingEntity)) {
                    if (tameable.getOwner().equals(attacker)) return false;

                    return canAttack(attacker, (LivingEntity) tameable.getOwner());
                }
            }
        } else if (attacker instanceof Tameable) {
            if (target instanceof Player) {
                Tameable tameable = (Tameable) attacker;
                if (tameable.isTamed() && (tameable.getOwner() instanceof LivingEntity)) {
                    if (tameable.getOwner().equals(target)) return false;

                    return canAttack((LivingEntity) tameable.getOwner(), target);
                }
            }
        } else return target instanceof Player || target instanceof Tameable;

        return true;
    }
}
