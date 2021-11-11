package io.github.lix3nn53.guardiansofadelia.utilities;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class EntityUtils {

    /*
    public static LivingEntity create(Location loc, String name, float hp, EntityType type) {
        LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc, type);
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);
        entity.setHealth(hp);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.7D);
        entity.setCanPickupItems(false);

        return entity;
    }

     */

    public static void delayedRemove(Entity entity, long delayTicks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isValid()) {
                    entity.remove();
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), delayTicks);
    }

    public static void delayedRemove(List<Entity> entities, long delayTicks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity entity : entities) {
                    if (entity.isValid()) {
                        entity.remove();
                    }
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), delayTicks);
    }

    /**
     * Checks whether or not something can be attacked
     *
     * @param attacker the attacking entity
     * @param target   the target entity
     * @return true if can be attacked, false otherwise
     */

    public static boolean canAttack(LivingEntity attacker, LivingEntity target) {
        if (attacker instanceof Player) { //attacker is a player
            boolean pvp = target.getWorld().getPVP();

            if (target instanceof Player) { //player attack player
                if (attacker == target) return false;
                if (pvp) {
                    Player attackerPlayer = (Player) attacker;

                    if (PartyManager.inParty(attackerPlayer)) {
                        Party party = PartyManager.getParty(attackerPlayer);
                        List<Player> members = party.getMembers();
                        return !members.contains(target);
                    }

                    return true;
                }

                return false; //pvp is off
            } else if (PetManager.isCompanion(target)) { //player attack pet
                if (pvp) {
                    Player owner = PetManager.getOwner(target);

                    if (attacker == owner) return false;

                    return canAttack(attacker, owner);
                }

                return false; //pvp is off
            }

            return true; //player attack monster
        } else if (PetManager.isCompanion(attacker)) {  //attacker is a pet
            Player attackerOwner = PetManager.getOwner(attacker);
            if (PetManager.isCompanion(target)) { //target is also a pet
                Player targetOwner = PetManager.getOwner(target);
                return canAttack(attackerOwner, targetOwner);
            } else if (target instanceof Player) { //pet attack player
                return canAttack(attackerOwner, target);
            }

            return true; // pet attack monster
        }

        //attacker is monster
        return target instanceof Player || PetManager.isCompanion(target); //monster can only attack players or pets
    }
}
