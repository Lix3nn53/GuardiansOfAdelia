package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.bossbar.HealthBar;
import io.github.lix3nn53.guardiansofadelia.bossbar.HealthBarManager;
import io.github.lix3nn53.guardiansofadelia.creatures.killProtection.KillProtectionManager;
import io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.MMManager;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.DamageIndicator;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class MyEntityDamageByEntityEvent implements Listener {

    private static int getCustomDamage(Entity entity) {
        if (PersistentDataContainerUtil.hasInteger(entity, "customDamage")) {
            return PersistentDataContainerUtil.getInteger(entity, "customDamage");
        }
        return 0;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        Entity target = event.getEntity();
        if (target.getType().equals(EntityType.ITEM_FRAME)) {
            event.setCancelled(true);
            return;
        }
        boolean isSkill = false;

        ElementType damageType = null;
        if (SkillUtils.isSkillDamage()) { //For own skill system
            isSkill = true;
            damageType = SkillUtils.getDamageType();
            SkillUtils.clearSkillDamage();
        } else if (damager instanceof LivingEntity) { // check disarm status only if is not skill damage
            if (StatusEffectManager.isDisarmed((LivingEntity) damager)) {
                event.setCancelled(true);
                return;
            }
        }

        // Disable vanilla knockback if isSkill
        if (isSkill) {
            if (target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) target;

                AttributeInstance attribute = livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
                if (attribute != null) {
                    attribute.setBaseValue(1);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            attribute.setBaseValue(0);
                        }
                    }.runTaskLater(GuardiansOfAdelia.getInstance(), 1L);
                }
            }
        }

        if (target instanceof LivingEntity) {
            boolean isEventCanceled = false;
            boolean isAttackerPlayer = false;
            LivingEntity livingTarget = (LivingEntity) target;

            //DAMAGER
            if (damager.getType().equals(EntityType.PLAYER)) { //player is attacker
                Player player = (Player) damager;
                isEventCanceled = onPlayerAttackEntity(event, player, livingTarget, null, damageType, isSkill, false);
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

                if (PersistentDataContainerUtil.hasInteger(projectile, "rangedDamage")) {
                    int rangedDamage = PersistentDataContainerUtil.getInteger(projectile, "rangedDamage");
                    event.setDamage(rangedDamage);
                } else if (PersistentDataContainerUtil.hasInteger(projectile, "skillLevel")) {
                    //projectile is a skill so cancel event and let children mechanics of this projectile do their things
                    event.setCancelled(true);
                    return;
                }

                if (shooter instanceof Player) {
                    Player player = (Player) shooter;
                    isEventCanceled = onPlayerAttackEntity(event, player, livingTarget, null, damageType, isSkill, true);
                    isAttackerPlayer = true;
                }
            } else if (damager instanceof LivingEntity) {
                if (PetManager.isCompanion((LivingEntity) damager)) { //damager is pet
                    Player owner = PetManager.getOwner((LivingEntity) damager);
                    boolean canAttack = EntityUtils.canAttack(owner, livingTarget);
                    if (!canAttack) {
                        event.setCancelled(true);
                        return;
                    }
                    isEventCanceled = onPlayerAttackEntity(event, owner, livingTarget, (LivingEntity) damager, damageType, isSkill, false);
                    isAttackerPlayer = true;
                }
            }

            //TARGET
            if (!isEventCanceled) {
                if (target.getType().equals(EntityType.PLAYER)) { //player is target

                    Player playerTarget = (Player) target;
                    LivingEntity damageSource = null;
                    playerTarget.sendMessage("damager: " + damager.getType());
                    if (damager instanceof Projectile) { //projectile is attacker
                        Projectile projectile = (Projectile) damager;
                        ProjectileSource shooter = projectile.getShooter();
                        if (shooter instanceof LivingEntity) damageSource = (LivingEntity) shooter;
                    } else if (damager instanceof LivingEntity) {
                        damageSource = (LivingEntity) damager;
                    }
                    if (damager instanceof EvokerFangs) { //evokerFangs is attacker
                        EvokerFangs evokerFangs = (EvokerFangs) damager;
                        LivingEntity owner = evokerFangs.getOwner();
                        if (owner != null) damageSource = owner;
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
                        TriggerListener.onPlayerTookDamage(playerTarget, damageSource); // TookDamageTrigger

                        // Manage target player's pet's target
                        if (PetManager.hasPet(playerTarget) || PetManager.hasCompanion(playerTarget)) {
                            PetManager.setPetAndCompanionsTarget(playerTarget, damageSource);
                        }
                    }

                    if (!isAttackerPlayer) { //we are managing this on onPlayerAttackEntity() method if attacker is player
                        //custom defense formula if target is another player attacked by mob
                        if (GuardianDataManager.hasGuardianData(playerTarget)) {
                            GuardianData guardianData = GuardianDataManager.getGuardianData(playerTarget);
                            if (guardianData.hasActiveCharacter()) {
                                ActiveMob activeMob = MythicMobs.inst().getMobManager().getMythicMobInstance(damageSource);

                                if (activeMob != null) {
                                    String internalName = activeMob.getType().getInternalName();

                                    if (MMManager.hasElementType(internalName)) {
                                        damageType = MMManager.getElementType(internalName);
                                    } else {
                                        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "damageType of mob NULL, internalName: " + internalName);
                                        damageType = ElementType.FIRE;
                                    }
                                }

                                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                                RPGCharacterStats targetRpgCharacterStats = activeCharacter.getRpgCharacterStats();
                                int totalDefense = targetRpgCharacterStats.getTotalElementDefense();
                                totalDefense += targetRpgCharacterStats.getElement(damageType).getBonusFromEquipment(); // Element is added to defense

                                double reduction = StatUtils.getDefenseReduction(totalDefense);

                                damage = damage * reduction;

                                event.setDamage(damage);
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
     * @param pet          = player's pet if attacker is the pet
     * @return isEventCanceled
     */
    private boolean onPlayerAttackEntity(EntityDamageByEntityEvent event, Player player, LivingEntity livingTarget, LivingEntity pet, ElementType damageType, boolean isSkill, boolean isProjectile) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                double damage = event.getDamage();
                boolean isCritical = false;
                Location targetLocation = livingTarget.getLocation();

                if (pet == null) { // attacker is not a pet
                    if (PetManager.isCompanion(livingTarget)) { // on player attack to pet
                        boolean canAttack = EntityUtils.canAttack(player, livingTarget);

                        if (!canAttack) {
                            event.setCancelled(true);
                            return true;
                        }
                    }

                    if (PetManager.hasPet(player) || PetManager.hasCompanion(player)) { // If player has active pet manage pet's target
                        PetManager.setPetAndCompanionsTarget(player, livingTarget);
                    }

                    //custom damage modifiers
                    RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                    String rpgClassStr = activeCharacter.getRpgClassStr();

                    if (isSkill) {
                        TriggerListener.onPlayerSkillAttack(player, livingTarget);
                    } else {
                        // If attack is not a skill, element type of attack is element of rpgClass of player
                        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                        damageType = rpgClass.getMainElement();

                        if (isProjectile) { // NonSkill projectile like arrow from bow
                            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.6F, 0.4F);
                        } else { // melee
                            if (player.getInventory().getHeldItemSlot() != 4) {
                                event.setCancelled(true);
                                player.sendMessage(ChatColor.RED + "You can only attack with weapon slot(5)");
                                return false;
                            }
                            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
                            if (!StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClassStr)) {
                                return false;
                            }

                            Material type = itemInMainHand.getType();
                            WeaponGearType weaponGearType = WeaponGearType.fromMaterial(type);

                            if (weaponGearType != null) {
                                double meleeDamageReduction = weaponGearType.getMeleeDamageReduction();
                                damage *= meleeDamageReduction;

                                weaponGearType.onHitEffect(player, livingTarget);
                            }

                            /* DO NOT add damage bonus from offhand manually, it is added via vanilla attributes
                            //add damage bonus from offhand
                            ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
                            if (!InventoryUtils.isAirOrNull(itemInMainHand)) {
                                if (itemInOffHand.getType().equals(Material.DIAMOND_HOE)) {
                                    damage += rpgCharacterStats.getTotalDamageBonusFromOffhand();
                                }
                            }*/
                        }

                        // Add bonus damage to normal attack, both melee and ranged
                        damage += rpgCharacterStats.getAttribute(AttributeType.BONUS_ELEMENT_DAMAGE).getIncrement(player.getLevel(), rpgClassStr); // bonus from attribute
                        damage += rpgCharacterStats.getElement(damageType).getBonusFromEquipment(); // bonus from element

                        TriggerListener.onPlayerNormalAttack(player, livingTarget);
                    }

                    // BuffMechanic
                    damage *= rpgCharacterStats.getBuffMultiplier(BuffType.ELEMENT_DAMAGE);

                    //add critical damage right before defense
                    double totalCriticalChance = rpgCharacterStats.getTotalCriticalChance();
                    double random = Math.random();
                    if (random <= totalCriticalChance) {
                        damage *= rpgCharacterStats.getTotalCriticalDamage();
                        isCritical = true;
                        Particle particle = Particle.CRIT;
                        // ParticleShapes.playSingleParticle(targetLocation, particle, null);
                        ParticleShapes.fillHemisphere(targetLocation.clone().add(0, 0.25, 0), particle, 0.2, 48, null);
                        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.6F, 2.0F);
                    }
                } else {
                    ActiveMob activeMob = MythicMobs.inst().getMobManager().getMythicMobInstance(pet);
                    String faction = activeMob.getFaction();

                    damageType = ElementType.valueOf(faction.toUpperCase());

                    int customDamage = getCustomDamage(pet);
                    if (customDamage > 0) {
                        event.setDamage(customDamage);
                        damage = customDamage; //so vanilla def is not included if target is player
                    }

                    CustomSound customSound = GoaSound.PET_ATTACK.getCustomSound();
                    customSound.play(pet.getEyeLocation());
                }

                //custom defense formula if target is another player
                if (livingTarget.getType().equals(EntityType.PLAYER)) {
                    Player playerTarget = (Player) livingTarget;

                    //minigame deal damage listener
                    if (MiniGameManager.isInMinigame(player)) {
                        if (livingTarget.getType().equals(EntityType.PLAYER)) {
                            MiniGameManager.onPlayerDealDamageToPlayer(player, playerTarget);
                        }
                    }

                    if (GuardianDataManager.hasGuardianData(playerTarget)) {
                        GuardianData targetGuardianData = GuardianDataManager.getGuardianData(playerTarget);
                        if (targetGuardianData.hasActiveCharacter()) {
                            RPGCharacter targetActiveCharacter = targetGuardianData.getActiveCharacter();

                            RPGCharacterStats targetRpgCharacterStats = targetActiveCharacter.getRpgCharacterStats();
                            int totalDefense = targetRpgCharacterStats.getTotalElementDefense();
                            totalDefense += targetRpgCharacterStats.getElement(damageType).getBonusFromEquipment();

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
                    //onKill mechanics moved to KillProtectionManager#onMobDeath
                }
                KillProtectionManager.onPlayerDealDamageToLivingEntity(player, livingTarget, (int) (protectionDamage + 0.5));

                //progress deal damage tasks
                List<Quest> questList = activeCharacter.getQuestList();
                ActiveMob mythicMobInstance = MythicMobs.inst().getMobManager().getMythicMobInstance(livingTarget);
                if (mythicMobInstance != null) {
                    MythicMob type = mythicMobInstance.getType();
                    String internalName = type.getInternalName();
                    for (Quest quest : questList) {
                        quest.progressDealDamageTasks(player, internalName, (int) (protectionDamage + 0.5));
                    }
                    PartyManager.progressDealDamageTasksOfOtherMembers(player, internalName, protectionDamage);
                }

                //indicator
                ChatColor indicatorColor = damageType.getChatColor();
                String indicatorIcon = damageType.getIcon() + "";
                if (isCritical) {
                    indicatorColor = ChatColor.GOLD;
                }

                String text = indicatorColor.toString() + (int) (finalDamage + 0.5) + " " + indicatorIcon;
                /*if (isCritical) {
                    text = "☆" + text;
                }*/
                double targetHeight = livingTarget.getHeight();
                DamageIndicator.spawnNonPacket(text, targetLocation.clone().add(0, targetHeight + 0.5, 0));
                //TODO make indicator via packets
                //DamageIndicator.showPlayer(player, text, targetLocation.clone().add(0, targetHeight + 0.5, 0));

                //show bossbar
                HealthBar healthBar = new HealthBar(livingTarget, (int) (finalDamage + 0.5), indicatorColor, indicatorIcon);
                HealthBarManager.showToPlayerFor10Seconds(player, healthBar);
            }
        }
        return false;
    }
}
