package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TriggerListener {
    //Every player can have only one trigger of a type

    private static HashMap<Player, InitializeTrigger> playerToInitializeTrigger = new HashMap<>();

    private static HashMap<Player, LandTrigger> playerToLandTrigger = new HashMap<>();

    private static HashMap<Player, TookPhysicalDamageTrigger> playerToTookPhysicalDamageTrigger = new HashMap<>();
    private static HashMap<Player, TookMagicalDamageTrigger> playerToTookMagicalDamageTrigger = new HashMap<>();
    private static HashMap<Player, TookMeleeDamageTrigger> playerToTookMeleeDamageTrigger = new HashMap<>();

    private static HashMap<Player, MeleeAttackTrigger> playerToMeleeAttackTrigger = new HashMap<>();
    private static HashMap<Player, RangedAttackTrigger> playerToRangedAttackTrigger = new HashMap<>();
    private static HashMap<Player, MagicAttackTrigger> playerToMagicAttackTrigger = new HashMap<>();

    private static HashMap<Player, AddPiercingToArrowShootFromCrossbowTrigger> playerToAddPiercingToArrowShootFromCrossbowTrigger = new HashMap<>();

    public static void onPlayerQuit(Player player) {
        playerToInitializeTrigger.remove(player);

        playerToLandTrigger.remove(player);

        playerToTookPhysicalDamageTrigger.remove(player);
        playerToTookMagicalDamageTrigger.remove(player);

        playerToMeleeAttackTrigger.remove(player);
        playerToRangedAttackTrigger.remove(player);
        playerToMagicAttackTrigger.remove(player);

        playerToAddPiercingToArrowShootFromCrossbowTrigger.remove(player);
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
        player.sendMessage("startListeningMeleeAttack");
        playerToMeleeAttackTrigger.put(player, meleeAttackTrigger);
    }

    public static void onPlayerMeleeAttack(Player player, LivingEntity target) {
        player.sendMessage("onPlayerMeleeAttack-1");
        if (playerToMeleeAttackTrigger.containsKey(player)) {
            player.sendMessage("onPlayerMeleeAttack-2");
            LivingEntity caster = playerToMeleeAttackTrigger.get(player).getCaster();
            if (caster instanceof Player) {
                player.sendMessage("Caster of triggered passive: " + caster.getName());
            }
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

    public static void onSkillUpgrade(Player player, InitializeTrigger initializeTrigger, int nextSkillLevel) {
        stopInit(player); //stop old init

        List<LivingEntity> targets = new ArrayList<>();
        targets.add(player);

        initializeTrigger.startEffects(player, nextSkillLevel, targets);
        playerToInitializeTrigger.put(player, initializeTrigger);
    }

    public static void onSkillDowngrade(Player player, InitializeTrigger initializeTrigger, int nextSkillLevel) {
        stopInit(player);
        if (nextSkillLevel == 0) return;

        List<LivingEntity> targets = new ArrayList<>();
        targets.add(player);

        initializeTrigger.startEffects(player, nextSkillLevel, targets);
        playerToInitializeTrigger.put(player, initializeTrigger);
    }

    private static void stopInit(Player player) {
        if (playerToInitializeTrigger.containsKey(player)) {
            player.sendMessage("StopEffects");
            playerToInitializeTrigger.get(player).stopEffects(player);
            playerToInitializeTrigger.remove(player);
        }
    }
}
