package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TriggerListener {
    //Every player can have only one trigger of a type

    private static final HashMap<Player, InitializeTrigger> playerToInitializeTrigger = new HashMap<>();

    private static final HashMap<Player, LandTrigger> playerToLandTrigger = new HashMap<>();

    private static final HashMap<Player, TookPhysicalDamageTrigger> playerToTookPhysicalDamageTrigger = new HashMap<>();
    private static final HashMap<Player, TookMagicalDamageTrigger> playerToTookMagicalDamageTrigger = new HashMap<>();
    private static final HashMap<Player, TookMeleeDamageTrigger> playerToTookMeleeDamageTrigger = new HashMap<>();

    private static final HashMap<Player, MeleeAttackTrigger> playerToMeleeAttackTrigger = new HashMap<>();
    private static final HashMap<Player, RangedAttackTrigger> playerToRangedAttackTrigger = new HashMap<>();
    private static final HashMap<Player, MagicAttackTrigger> playerToMagicAttackTrigger = new HashMap<>();

    private static final HashMap<Player, AddPiercingToArrowShootFromCrossbowTrigger> playerToAddPiercingToArrowShootFromCrossbowTrigger = new HashMap<>();

    private static final HashMap<Player, SavedEntitySpawnTrigger> playerToSavedEntitySpawnTrigger = new HashMap<>();
    private static final HashMap<Player, CompanionSpawnTrigger> playerToCompanionSpawnTrigger = new HashMap<>();

    public static void onPlayerQuit(Player player) {
        playerToInitializeTrigger.remove(player);

        playerToLandTrigger.remove(player);

        playerToTookPhysicalDamageTrigger.remove(player);
        playerToTookMagicalDamageTrigger.remove(player);

        playerToMeleeAttackTrigger.remove(player);
        playerToRangedAttackTrigger.remove(player);
        playerToMagicAttackTrigger.remove(player);

        playerToAddPiercingToArrowShootFromCrossbowTrigger.remove(player);

        playerToSavedEntitySpawnTrigger.remove(player);
        playerToCompanionSpawnTrigger.remove(player);
    }

    public static void startListeningLandTrigger(Player player, LandTrigger landTrigger) {
        playerToLandTrigger.put(player, landTrigger);
    }

    public static void onPlayerLandGround(Player player) {
        if (playerToLandTrigger.containsKey(player)) {
            boolean callback = playerToLandTrigger.get(player).callback(player);
            if (callback) {
                playerToLandTrigger.remove(player);
            }
        }
    }

    public static void startListeningTookPhysicalDamage(Player player, TookPhysicalDamageTrigger tookPhysicalDamageTrigger) {
        playerToTookPhysicalDamageTrigger.put(player, tookPhysicalDamageTrigger);
    }

    public static void onPlayerTookPhysicalDamage(Player player, LivingEntity attacker) {
        if (playerToTookPhysicalDamageTrigger.containsKey(player)) {
            boolean callback = playerToTookPhysicalDamageTrigger.get(player).callback(player, attacker);
            if (callback) {
                playerToTookPhysicalDamageTrigger.remove(player);
            }
        }
    }

    public static void startListeningTookMeleeDamage(Player player, TookMeleeDamageTrigger tookMeleeDamageTrigger) {
        playerToTookMeleeDamageTrigger.put(player, tookMeleeDamageTrigger);
    }

    public static void onPlayerTookMeleeDamage(Player player, LivingEntity attacker) {
        if (playerToTookMeleeDamageTrigger.containsKey(player)) {
            boolean callback = playerToTookMeleeDamageTrigger.get(player).callback(player, attacker);
            if (callback) {
                playerToTookMeleeDamageTrigger.remove(player);
            }
        }
    }

    public static void startListeningTookMagicalDamage(Player player, TookMagicalDamageTrigger tookPhysicalDamageTrigger) {
        playerToTookMagicalDamageTrigger.put(player, tookPhysicalDamageTrigger);
    }

    public static void onPlayerTookMagicalDamage(Player player, LivingEntity attacker) {
        if (playerToTookMagicalDamageTrigger.containsKey(player)) {
            boolean callback = playerToTookMagicalDamageTrigger.get(player).callback(player, attacker);
            if (callback) {
                playerToTookMagicalDamageTrigger.remove(player);
            }
        }
    }

    public static void startListeningMeleeAttack(Player player, MeleeAttackTrigger meleeAttackTrigger) {
        playerToMeleeAttackTrigger.put(player, meleeAttackTrigger);
    }

    public static void onPlayerMeleeAttack(Player player, LivingEntity target) {
        if (playerToMeleeAttackTrigger.containsKey(player)) {
            boolean callback = playerToMeleeAttackTrigger.get(player).callback(player, target);
            if (callback) {
                playerToMeleeAttackTrigger.remove(player);
            }
        }
    }

    public static void startListeningRangedAttack(Player player, RangedAttackTrigger rangedAttackTrigger) {
        playerToRangedAttackTrigger.put(player, rangedAttackTrigger);
    }

    public static void onPlayerRangedAttack(Player player, LivingEntity target) {
        if (playerToRangedAttackTrigger.containsKey(player)) {
            boolean callback = playerToRangedAttackTrigger.get(player).callback(player, target);
            if (callback) {
                playerToRangedAttackTrigger.remove(player);
            }
        }
    }

    public static void startListeningMagicAttack(Player player, MagicAttackTrigger magicAttackTrigger) {
        playerToMagicAttackTrigger.put(player, magicAttackTrigger);
    }

    public static void onPlayerMagicAttack(Player player, LivingEntity target) {
        if (playerToMagicAttackTrigger.containsKey(player)) {
            boolean callback = playerToMagicAttackTrigger.get(player).callback(player, target);
            if (callback) {
                playerToMagicAttackTrigger.remove(player);
            }
        }
    }

    public static void startListeningAddPiercingToArrowShootFromCrossbowTrigger(Player player, AddPiercingToArrowShootFromCrossbowTrigger addPiercingToArrowShootFromCrossbowTrigger) {
        playerToAddPiercingToArrowShootFromCrossbowTrigger.put(player, addPiercingToArrowShootFromCrossbowTrigger);
    }

    public static void onPlayerShootCrossbow(Player player, Arrow arrow) {
        if (playerToAddPiercingToArrowShootFromCrossbowTrigger.containsKey(player)) {
            playerToAddPiercingToArrowShootFromCrossbowTrigger.get(player).callback(arrow);
            //playerToAddPiercingToArrowShootFromCrossbowTrigger.remove(player); DO NOT REMOVE SINCE THIS MECHANIC HAS NO COOLDOWN AND DOES NOT ADD ITSELF BACK
        }
    }

    public static void startListeningSavedEntitySpawn(Player player, SavedEntitySpawnTrigger trigger) {
        player.sendMessage("SaveEntitySpawnTrigger start listening");
        playerToSavedEntitySpawnTrigger.put(player, trigger);
    }

    public static void onPlayerSavedEntitySpawn(Player player, LivingEntity created) {
        player.sendMessage("SaveEntitySpawnTrigger activation 0");
        if (playerToSavedEntitySpawnTrigger.containsKey(player)) {
            player.sendMessage("SaveEntitySpawnTrigger activation 1");
            boolean callback = playerToSavedEntitySpawnTrigger.get(player).callback(player, created);
            if (callback) {
                player.sendMessage("SaveEntitySpawnTrigger activation 2");
                playerToSavedEntitySpawnTrigger.remove(player);
            }
        }
    }

    public static void startListeningCompanionSpawn(Player player, CompanionSpawnTrigger trigger) {
        player.sendMessage("CompanionSpawnTrigger start listening");
        playerToCompanionSpawnTrigger.put(player, trigger);
    }

    public static void onPlayerCompanionSpawn(Player player, LivingEntity spawned) {
        player.sendMessage("CompanionSpawnTrigger activation 0");
        if (playerToCompanionSpawnTrigger.containsKey(player)) {
            player.sendMessage("CompanionSpawnTrigger activation 1");
            boolean callback = playerToCompanionSpawnTrigger.get(player).callback(player, spawned);
            if (callback) {
                player.sendMessage("CompanionSpawnTrigger activation 2");
                playerToCompanionSpawnTrigger.remove(player);
            }
        }
    }

    public static void onSkillUpgrade(Player player, InitializeTrigger initializeTrigger, int nextSkillLevel, int castCounter) {
        stopInit(player); //stop old init

        List<LivingEntity> targets = new ArrayList<>();
        targets.add(player);

        initializeTrigger.startEffects(player, nextSkillLevel, targets, castCounter);
        playerToInitializeTrigger.put(player, initializeTrigger);
    }

    public static void onSkillDowngrade(Player player, InitializeTrigger initializeTrigger, int nextSkillLevel, int castCounter) {
        stopInit(player);
        if (nextSkillLevel == 0) return;

        List<LivingEntity> targets = new ArrayList<>();
        targets.add(player);

        initializeTrigger.startEffects(player, nextSkillLevel, targets, castCounter);
        playerToInitializeTrigger.put(player, initializeTrigger);
    }

    private static void stopInit(Player player) {
        player.sendMessage("test1");
        if (playerToInitializeTrigger.containsKey(player)) {
            //debug player.sendMessage("StopEffects");
            playerToInitializeTrigger.get(player).stopEffects(player);
            playerToInitializeTrigger.remove(player);
        }
        player.sendMessage("test2");
    }
}
