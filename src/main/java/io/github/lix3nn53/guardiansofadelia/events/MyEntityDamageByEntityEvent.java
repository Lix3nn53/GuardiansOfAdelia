package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.bossbar.HealthBar;
import io.github.lix3nn53.guardiansofadelia.bossbar.HealthBarManager;
import io.github.lix3nn53.guardiansofadelia.creatures.killProtection.KillProtectionManager;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.DamageMechanic;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.DamageIndicator;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.List;
import java.util.UUID;

public class MyEntityDamageByEntityEvent implements Listener {

    private static int getCustomDamage(Entity entity) {
        if (PersistentDataContainerUtil.hasInteger(entity, "customDamage")) {
            return PersistentDataContainerUtil.getInteger(entity, "customDamage");
        }
        return 0;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEvent(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        if (damager instanceof LivingEntity) {
            if (StatusEffectManager.isDisarmed((LivingEntity) damager)) {
                event.setCancelled(true);
                return;
            }
        }

        Entity target = event.getEntity();
        boolean isSkill = false;

        DamageMechanic.DamageType damageType = DamageMechanic.DamageType.MELEE;
        if (SkillUtils.isSkillDamage()) {
            isSkill = true;
            damageType = SkillUtils.getDamageType();
            SkillUtils.clearSkillDamage();
        }

        if (target instanceof LivingEntity) {
            boolean isEventCanceled = false;
            boolean isAttackerPlayer = false;
            LivingEntity livingTarget = (LivingEntity) target;

            //DAMAGER
            if (damager.getType().equals(EntityType.PLAYER)) { //player is attacker
                Player player = (Player) damager;
                isEventCanceled = onPlayerAttackEntity(event, player, livingTarget, false, damageType, isSkill);
                isAttackerPlayer = true;
            } else if (damager instanceof Projectile) { //projectile is attacker
                Projectile projectile = (Projectile) damager;
                ProjectileSource shooter = projectile.getShooter();

                if (shooter instanceof LivingEntity) {
                    if (StatusEffectManager.isDisarmed((LivingEntity) shooter)) {
                        event.setCancelled(true);
                        return;
                    }
                }

                if (PersistentDataContainerUtil.hasInteger(projectile, "rangedDamage")) { //projectile is fired by player without skills involved
                    int rangedDamage = PersistentDataContainerUtil.getInteger(projectile, "rangedDamage");
                    event.setDamage(rangedDamage);
                    damageType = DamageMechanic.DamageType.RANGED;
                } else if (PersistentDataContainerUtil.hasInteger(projectile, "skillLevel")) {
                    //projectile is a skill so cancel event and let children mechanics of this projectile do their things
                    event.setCancelled(true);
                    return;
                }

                if (shooter instanceof Player) {
                    Player player = (Player) shooter;
                    isEventCanceled = onPlayerAttackEntity(event, player, livingTarget, false, damageType, isSkill);
                    isAttackerPlayer = true;
                }
            } else if (damager instanceof LivingEntity) {
                if (damager instanceof Wolf) {
                    Wolf wolf = (Wolf) damager;
                    if (PetManager.isPet(wolf)) { //pet is attacker
                        Player owner = PetManager.getOwner(wolf);
                        isEventCanceled = onPlayerAttackEntity(event, owner, livingTarget, true, damageType, isSkill);
                        isAttackerPlayer = true;
                    }
                }
            }

            //TARGET
            if (!isEventCanceled) {
                if (target.getType().equals(EntityType.PLAYER)) { //player is target

                    Player playerTarget = (Player) target;
                    LivingEntity damageSource = null;
                    if (damager instanceof Projectile) { //projectile is attacker
                        Projectile projectile = (Projectile) damager;
                        ProjectileSource shooter = projectile.getShooter();
                        if (shooter instanceof LivingEntity) damageSource = (LivingEntity) shooter;
                    } else if (damager instanceof LivingEntity) {
                        damageSource = (LivingEntity) damager;
                    }

                    double damage = event.getDamage();

                    if (!isSkill) { //deal mob damage if melee or projectile
                        int customDamage = getCustomDamage(damageSource);
                        if (customDamage > 0) {
                            event.setDamage(customDamage);
                            damage = customDamage; //so vanilla def is not included if target is player
                        }
                    }

                    if (damageSource != null) {
                        if (damageType.equals(DamageMechanic.DamageType.MAGIC)) {
                            TriggerListener.onPlayerTookMagicalDamage(playerTarget, damageSource); //TookMagicalDamageTrigger
                        } else {
                            TriggerListener.onPlayerTookPhysicalDamage(playerTarget, damageSource); //TookPhysicalDamageTrigger

                            if (damageType.equals(DamageMechanic.DamageType.MELEE)) {
                                TriggerListener.onPlayerTookMeleeDamage(playerTarget, damageSource); //TookMeleeDamageTrigger
                            }
                        }
                    }

                    if (!isAttackerPlayer) { //we are managing this on onPlayerAttackEntity() method if attacker is player
                        //custom defense formula if target is another player attacked by mob
                        UUID uniqueId = playerTarget.getUniqueId();
                        if (GuardianDataManager.hasGuardianData(uniqueId)) {
                            GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
                            if (guardianData.hasActiveCharacter()) {

                                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                                RPGCharacterStats targetRpgCharacterStats = activeCharacter.getRpgCharacterStats();
                                int totalDefense = targetRpgCharacterStats.getTotalDefense();

                                if (damageType.equals(DamageMechanic.DamageType.MAGIC)) {
                                    totalDefense = targetRpgCharacterStats.getTotalMagicDefense();
                                }

                                double reduction = StatUtils.getDefenseReduction(totalDefense);

                                damage = damage * reduction;

                                event.setDamage(damage);
                            }
                        }
                    }

                    //manage target player's pet's target
                    if (damager instanceof LivingEntity) {
                        LivingEntity livingDamager = (LivingEntity) damager;
                        if (PetManager.hasActivePet(playerTarget)) {
                            LivingEntity activePet = PetManager.getActivePet(playerTarget);
                            if (activePet instanceof Wolf) {
                                Wolf wolf = (Wolf) activePet;
                                if (wolf.getTarget() == null) {
                                    wolf.setTarget(livingDamager);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param event
     * @param player
     * @param livingTarget
     * @param isPet        isAttackerPet else attacker is Player
     * @return isEventCanceled
     */
    private boolean onPlayerAttackEntity(EntityDamageByEntityEvent event, Player player, LivingEntity livingTarget, boolean isPet, DamageMechanic.DamageType damageType, boolean isSkill) {
        UUID uniqueId = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uniqueId)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                double damage = event.getDamage();
                boolean isCritical = false;
                Location targetLocation = livingTarget.getLocation();

                if (!isPet) { //attacker is not player's pet
                    if (livingTarget.getType().equals(EntityType.WOLF) || livingTarget.getType().equals(EntityType.HORSE)) { //on player attack to pet
                        boolean pvp = livingTarget.getWorld().getPVP();
                        if (pvp) {
                            if (PetManager.isPet(livingTarget)) {
                                Player owner = PetManager.getOwner(livingTarget);
                                //attack own pet
                                if (owner.equals(player)) {
                                    event.setCancelled(true);
                                    return true;
                                } else {
                                    //attack pet of party member
                                    if (PartyManager.inParty(player)) {
                                        if (PartyManager.getParty(player).getMembers().contains(owner)) {
                                            event.setCancelled(true);
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else {
                            event.setCancelled(true);
                            return true;
                        }
                    }

                    if (PetManager.hasActivePet(player)) { //if player has active pet manage pet's target
                        LivingEntity activePet = PetManager.getActivePet(player);
                        if (activePet instanceof Wolf) {
                            Wolf wolf = (Wolf) activePet;
                            if (wolf.getTarget() == null) {
                                wolf.setTarget(livingTarget);
                            }
                        }
                    }

                    //custom damage modifiers
                    RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                    RPGClass rpgClass = activeCharacter.getRpgClass();
                    if (damageType.equals(DamageMechanic.DamageType.MAGIC)) { //Ranged overrides Magic so check magic first. You can not deal Magic damage without skills.
                        damage += rpgCharacterStats.getTotalMagicDamage(player, rpgClass); //add to spell damage
                        TriggerListener.onPlayerMagicAttack(player, livingTarget);
                    } else if (damageType.equals(DamageMechanic.DamageType.RANGED)) {
                        if (isSkill) { //add full ranged damage to skills
                            damage += rpgCharacterStats.getTotalRangedDamage(player, rpgClass);
                            TriggerListener.onPlayerMagicAttack(player, livingTarget);
                        } else { //add fire element and physical damage buff to projectiles fired without skills involved
                            damage += rpgCharacterStats.getFire().getIncrement(player.getLevel(), rpgClass);
                            damage *= rpgCharacterStats.getBuffMultiplier(BuffType.PHYSICAL_DAMAGE);
                            TriggerListener.onPlayerRangedAttack(player, livingTarget);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 0.4F);
                        }
                    } else { //melee
                        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                        Material type = itemInMainHand.getType();

                        if (isSkill) { //Add full melee damage to skills
                            damage += rpgCharacterStats.getTotalMeleeDamage(player, rpgClass);
                            TriggerListener.onPlayerMagicAttack(player, livingTarget);
                        } else if (type.equals(Material.DIAMOND_SWORD) || type.equals(Material.DIAMOND_HOE) || type.equals(Material.DIAMOND_SHOVEL) || type.equals(Material.DIAMOND_AXE)
                                || type.equals(Material.DIAMOND_PICKAXE) || type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
                            //Normal melee attack. Check for requirements then add fire and offhand bonus

                            if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "reqLevel")) {
                                int reqLevel = PersistentDataContainerUtil.getInteger(itemInMainHand, "reqLevel");
                                if (player.getLevel() < reqLevel) {
                                    event.setCancelled(true);
                                    player.sendMessage("Required level for this weapon is " + reqLevel);
                                    return false;
                                }
                            }

                            if (PersistentDataContainerUtil.hasString(itemInMainHand, "reqClass")) {
                                String reqClassString = PersistentDataContainerUtil.getString(itemInMainHand, "reqClass");
                                RPGClass reqClass = RPGClass.valueOf(reqClassString);
                                if (!rpgClass.equals(reqClass)) {
                                    event.setCancelled(true);
                                    player.sendMessage("Required class for this weapon is " + reqClass.getClassString());
                                    return false;
                                }
                            }

                            damage += rpgCharacterStats.getFire().getIncrement(player.getLevel(), rpgClass); //add to weapon damage

                            //add damage bonus from offhand
                            ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
                            if (!InventoryUtils.isAirOrNull(itemInMainHand)) {
                                if (itemInOffHand.getType().equals(Material.DIAMOND_HOE)) {
                                    damage += rpgCharacterStats.getTotalDamageBonusFromOffhand();
                                }
                            }

                            damage *= rpgCharacterStats.getBuffMultiplier(BuffType.PHYSICAL_DAMAGE);
                            TriggerListener.onPlayerMeleeAttack(player, livingTarget);
                        }
                    }

                    //add critical damage right before defense
                    double totalCriticalChance = rpgCharacterStats.getTotalCriticalChance();
                    double random = Math.random();
                    if (random <= totalCriticalChance) {
                        damage += damage * rpgCharacterStats.getTotalCriticalDamageBonus();
                        isCritical = true;
                        Particle particle = Particle.CRIT;
                        targetLocation.getWorld().spawnParticle(particle, targetLocation.clone().add(0, 0.25, 0), 6);
                    }
                }

                //custom defense formula if target is another player
                if (livingTarget.getType().equals(EntityType.PLAYER)) {
                    Player playerTarget = (Player) livingTarget;
                    UUID targetUniqueId = playerTarget.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(targetUniqueId)) {
                        GuardianData targetGuardianData = GuardianDataManager.getGuardianData(targetUniqueId);
                        if (targetGuardianData.hasActiveCharacter()) {
                            RPGCharacter targetActiveCharacter = targetGuardianData.getActiveCharacter();

                            RPGCharacterStats targetRpgCharacterStats = targetActiveCharacter.getRpgCharacterStats();
                            int totalDefense = targetRpgCharacterStats.getTotalDefense();

                            if (damageType.equals(DamageMechanic.DamageType.MAGIC)) {
                                totalDefense = targetRpgCharacterStats.getTotalMagicDefense();
                            }

                            double reduction = StatUtils.getDefenseReduction(totalDefense);

                            damage = damage * reduction;
                        }
                    }
                }

                event.setDamage(damage);

                double finalDamage = event.getFinalDamage();

                double protectionDamage = finalDamage;
                double livingTargetHealth = livingTarget.getHealth();
                //on Kill
                if (finalDamage >= livingTargetHealth) {
                    protectionDamage = livingTargetHealth;
                    /*onKill mechanics moved to KillProtectionManager#onMobDeath
                    but minigame mechanics is still here because we don't want KillProtection for them*/
                    if (MiniGameManager.isInMinigame(player)) {
                        if (livingTarget.getType().equals(EntityType.PLAYER)) {
                            MiniGameManager.onPlayerKill(player);
                        } else {
                            MiniGameManager.onMobKill(player, livingTarget);
                        }
                    }
                }
                KillProtectionManager.onPlayerDealDamageToLivingEntity(player, livingTarget, (int) (protectionDamage + 0.5));

                //progress deal damage tasks
                List<Quest> questList = activeCharacter.getQuestList();
                for (Quest quest : questList) {
                    quest.progressDealDamageTasks(player, livingTarget, (int) (protectionDamage + 0.5));
                }
                PartyManager.progressDealDamageTasksOfOtherMembers(player, livingTarget, protectionDamage);

                //indicator
                ChatColor indicatorColor = ChatColor.RED;
                String indicatorIcon = "⸸";
                if (damageType.equals(DamageMechanic.DamageType.RANGED)) {
                    indicatorIcon = "➹";
                } else if (damageType.equals(DamageMechanic.DamageType.MAGIC)) {
                    indicatorColor = ChatColor.AQUA;
                    indicatorIcon = "✧";
                } else if (isPet) {
                    indicatorColor = ChatColor.LIGHT_PURPLE;
                    indicatorIcon = ">.<";
                }

                if (isCritical) {
                    indicatorColor = ChatColor.GOLD;
                }
                String text = indicatorColor.toString() + (int) (finalDamage + 0.5) + " " + indicatorIcon;
                double targetHeight = livingTarget.getHeight();
                DamageIndicator.spawnNonPacket(text, targetLocation.clone().add(0, targetHeight + 0.5, 0));
                //TODO make indicator via packets

                //show bossbar
                HealthBar healthBar = new HealthBar(livingTarget, (int) (finalDamage + 0.5), indicatorColor, indicatorIcon);
                HealthBarManager.showToPlayerFor10Seconds(player, healthBar);
            }
        }
        return false;
    }
}
