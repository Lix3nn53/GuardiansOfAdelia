package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity.ImmunityListener;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class MyEntityDamageEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity)) return;

        EntityDamageEvent.DamageCause cause = event.getCause();
        if (entity instanceof Player) {
            Player player = (Player) entity;

            CraftPlayer craftPlayer = (CraftPlayer) player;
            EntityPlayer entityPlayer = craftPlayer.getHandle();

            float absorptionHearts = entityPlayer.getAbsorptionHearts();

            if (absorptionHearts > 0) {
                event.setCancelled(true);
                absorptionHearts -= 2;
                if (absorptionHearts < 0) {
                    absorptionHearts = 0;
                }
                entityPlayer.setAbsorptionHearts(absorptionHearts);
                return;
            }
        }

        if (cause.equals(EntityDamageEvent.DamageCause.VOID)) {
            event.setDamage(10000D);
        } else if (cause.equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
            entity.teleport(entity.getLocation().clone().add(0, 5, 0));
            event.setCancelled(true);
            return;
        } else if (cause.equals(EntityDamageEvent.DamageCause.STARVATION)) {
            event.setCancelled(true);
            return;
        } else if (cause.equals(EntityDamageEvent.DamageCause.FALL)) {
            if (entity.getType().equals(EntityType.HORSE)) {
                event.setCancelled(true);
                return;
            }
        }

        if (ImmunityListener.isImmune((LivingEntity) entity, cause)) {
            event.setCancelled(true);
            return;
        }

        double customNaturalDamage = getCustomNaturalDamage(cause, (LivingEntity) entity);
        if (customNaturalDamage > 0) {
            event.setDamage(customNaturalDamage);
        }

        EntityType entityType = event.getEntityType();

        if (entityType.equals(EntityType.PLAYER)) {
            Player player = (Player) entity;

            double finalDamage = event.getFinalDamage();
            if (PartyManager.inParty(player)) {
                Party party = PartyManager.getParty(player);
                party.getBoard().updateHP(player.getName(), (int) (player.getHealth() - finalDamage + 0.5));
            }
        } else {
            LivingEntity livingEntity = (LivingEntity) entity;
            PetManager.onTakeDamage(livingEntity, livingEntity.getHealth(), event.getFinalDamage());
        }
    }

    private double getCustomNaturalDamage(EntityDamageEvent.DamageCause cause, LivingEntity entity) {
        double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (cause.equals(EntityDamageEvent.DamageCause.FALL) && entity instanceof Player) {
            float fallDistance = entity.getFallDistance();

            return (fallDistance - 3) * (maxHealth / 40); // Vanilla fall damage is 1 damage each block after the third
        } else if (cause.equals(EntityDamageEvent.DamageCause.POISON)) {
            return maxHealth / 100;
        } else if (cause.equals(EntityDamageEvent.DamageCause.WITHER)) {
            return maxHealth / 100;
        } else if (cause.equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
            return maxHealth / 100;
        } else if (cause.equals(EntityDamageEvent.DamageCause.FIRE)) {
            return maxHealth / 50;
        } else if (cause.equals(EntityDamageEvent.DamageCause.DROWNING)) {
            return maxHealth / 50;
        } else if (cause.equals(EntityDamageEvent.DamageCause.HOT_FLOOR)) {
            return maxHealth / 100;
        } else if (cause.equals(EntityDamageEvent.DamageCause.LAVA)) {
            return maxHealth / 50;
        } else if (cause.equals(EntityDamageEvent.DamageCause.CONTACT)) {
            return maxHealth / 50;
        }
        return 0;
    }

}
