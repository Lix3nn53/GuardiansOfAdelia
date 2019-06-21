package io.github.lix3nn53.guardiansofadelia.guardian.skill.component;

import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;

import java.util.List;

public abstract class TargetComponent extends SkillComponent {

    private final boolean throughWall = false;
    private final boolean allies = false;
    private final boolean enemy = false;


    /**
     * Executes the component
     *
     * @param caster  caster of the skill
     * @param level   level of the skill
     * @param targets targets to apply to
     * @return true if applied to something, false otherwise
     */

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {

        return (list.size() > 0 && executeChildren(caster, skillLevel, targets));

    }

    boolean isValidTarget(final LivingEntity caster, final LivingEntity from, final LivingEntity target) {

        return target != caster

                && (throughWall || !TargetHelper.isObstructed(from.getEyeLocation(), target.getEyeLocation()))

                && (everyone || allies == isAlly(caster, target));

    }

    /**
     * Checks whether or not something is an ally
     *
     * @param attacker the attacking entity
     * @param target   the target entity
     * @return true if an ally, false otherwise
     */

    public boolean isAlly(LivingEntity attacker, LivingEntity target) {

        return !canAttack(attacker, target);

    }

    /**
     * Checks whether or not something can be attacked
     *
     * @param attacker the attacking entity
     * @param target   the target entity
     * @return true if can be attacked, false otherwise
     */

    public boolean canAttack(LivingEntity attacker, LivingEntity target) {
        if (attacker instanceof Player) {
            if (target instanceof Player) {
                if (attacker.getWorld().getName().equals("arena")) {

                    Player attackerPlayer = (Player) attacker;

                    if (PartyManager.inParty(attackerPlayer)) {
                        Party party = PartyManager.getParty(attackerPlayer);
                        List<Player> members = party.getMembers();
                        return !members.contains((Player) target);
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
            Tameable tameable = (Tameable) attacker;
            if (tameable.isTamed() && (tameable.getOwner() instanceof LivingEntity)) {
                if (tameable.getOwner().equals(target)) return false;

                return canAttack((LivingEntity) tameable.getOwner(), target);
            }
        }
        return true;
    }
}
