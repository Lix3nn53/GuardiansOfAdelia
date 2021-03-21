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

    private static final HashMap<Player, TookDamageTrigger> playerToTookDamageTrigger = new HashMap<>();
    private static final HashMap<Player, NormalAttackTrigger> playerToNormalAttackTrigger = new HashMap<>();
    private static final HashMap<Player, SkillAttackTrigger> playerToSkillAttackTrigger = new HashMap<>();

    private static final HashMap<Player, AddPiercingToArrowShootFromCrossbowTrigger> playerToAddPiercingToArrowShootFromCrossbowTrigger = new HashMap<>();

    private static final HashMap<Player, SavedEntitySpawnTrigger> playerToSavedEntitySpawnTrigger = new HashMap<>();
    private static final HashMap<Player, CompanionSpawnTrigger> playerToCompanionSpawnTrigger = new HashMap<>();

    private static final HashMap<Player, SkillCastTrigger> playerToSkillCastTrigger = new HashMap<>();

    public static void onPlayerQuit(Player player) {
        playerToInitializeTrigger.remove(player);

        playerToLandTrigger.remove(player);
        playerToNormalAttackTrigger.remove(player);
        playerToSkillAttackTrigger.remove(player);

        playerToAddPiercingToArrowShootFromCrossbowTrigger.remove(player);

        playerToSavedEntitySpawnTrigger.remove(player);
        playerToCompanionSpawnTrigger.remove(player);

        playerToSkillCastTrigger.remove(player);
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

    public static void startListeningTookDamage(Player player, TookDamageTrigger tookDamageTrigger) {
        playerToTookDamageTrigger.put(player, tookDamageTrigger);
    }

    public static void onPlayerTookDamage(Player player, LivingEntity attacker) {
        if (playerToTookDamageTrigger.containsKey(player)) {
            boolean callback = playerToTookDamageTrigger.get(player).callback(player, attacker);
            if (callback) {
                playerToTookDamageTrigger.remove(player);
            }
        }
    }

    public static void startListeningNormalAttack(Player player, NormalAttackTrigger normalAttackTrigger) {
        playerToNormalAttackTrigger.put(player, normalAttackTrigger);
    }

    public static void onPlayerNormalAttack(Player player, LivingEntity target) {
        if (playerToNormalAttackTrigger.containsKey(player)) {
            boolean callback = playerToNormalAttackTrigger.get(player).callback(player, target);
            if (callback) {
                playerToNormalAttackTrigger.remove(player);
            }
        }
    }

    public static void startListeningSkillAttack(Player player, SkillAttackTrigger skillAttackTrigger) {
        playerToSkillAttackTrigger.put(player, skillAttackTrigger);
    }

    public static void onPlayerSkillAttack(Player player, LivingEntity target) {
        if (playerToSkillAttackTrigger.containsKey(player)) {
            boolean callback = playerToSkillAttackTrigger.get(player).callback(player, target);
            if (callback) {
                playerToSkillAttackTrigger.remove(player);
            }
        }
    }

    public static void startListeningSkillCast(Player player, SkillCastTrigger skillCastTrigger) {
        playerToSkillCastTrigger.put(player, skillCastTrigger);
    }

    public static void onPlayerSkillCast(Player player) {
        if (playerToSkillCastTrigger.containsKey(player)) {
            player.sendMessage("onPlayerSkillCast");
            boolean callback = playerToSkillCastTrigger.get(player).callback(player);
            if (callback) {
                playerToSkillCastTrigger.remove(player);
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
