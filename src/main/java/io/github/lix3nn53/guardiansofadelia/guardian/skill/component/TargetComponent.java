package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;

import java.util.ArrayList;
import java.util.List;

public abstract class TargetComponent extends SkillComponent {

    final int max;
    final boolean self;
    private final boolean allies;
    private final boolean enemy;

    protected TargetComponent(boolean allies, boolean enemy, boolean self, int max) {
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

        EntityType type = target.getType();
        if (type.equals(EntityType.ARMOR_STAND)) return false; //ignore list of target mechanics

        if (caster == target) return self;

        if (allies && enemy) return true;

        if (allies) return isAlly(caster, target);

        return true;
    }

    /**
     * Checks whether or not something is an ally
     *
     * @param attacker the attacking entity
     * @param target   the target entity
     * @return true if an ally, false otherwise
     */

    private boolean isAlly(LivingEntity attacker, LivingEntity target) {

        return !canAttack(attacker, target);

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
                if (attacker.getWorld().getName().equals("arena")) {

                    Player attackerPlayer = (Player) attacker;

                    if (PartyManager.inParty(attackerPlayer)) {
                        Party party = PartyManager.getParty(attackerPlayer);
                        List<Player> members = party.getMembers();
                        return !members.contains(target);
                    }

                    return true;
                }
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
        }
        return true;
    }
}
