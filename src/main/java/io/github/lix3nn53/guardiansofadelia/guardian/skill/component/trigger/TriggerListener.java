package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TriggerListener {

    private static HashMap<Player, InitializeTrigger> playerToInitializeTrigger = new HashMap<>();

    private static HashMap<Player, LandTrigger> playerToLandTrigger = new HashMap<>();
    private static HashMap<Player, TookPhysicalDamageTrigger> playerToTookPhysicalDamageTrigger = new HashMap<>();
    private static HashMap<Player, TookMagicalDamageTrigger> playerToTookMagicalDamageTrigger = new HashMap<>();

    public static void onPlayerQuit(Player player) {
        playerToLandTrigger.remove(player);
        playerToTookPhysicalDamageTrigger.remove(player);
        playerToTookMagicalDamageTrigger.remove(player);
    }

    public static void startListeningLandTrigger(Player player, LandTrigger landTrigger) {
        playerToLandTrigger.put(player, landTrigger);
    }

    public static void onPlayerLandGround(Player player) {
        if (playerToLandTrigger.containsKey(player)) {
            playerToLandTrigger.get(player).callback(player);
            playerToLandTrigger.remove(player);
        }
    }

    public static void startListeningPhysicalDamage(Player player, TookPhysicalDamageTrigger tookPhysicalDamageTrigger) {
        playerToTookPhysicalDamageTrigger.put(player, tookPhysicalDamageTrigger);
    }

    public static void onPlayerTookPhysicalDamage(Player player) {
        if (playerToTookPhysicalDamageTrigger.containsKey(player)) {
            playerToTookPhysicalDamageTrigger.get(player).callback(player);
            playerToTookPhysicalDamageTrigger.remove(player);
        }
    }

    public static void startListeningMagicalDamage(Player player, TookMagicalDamageTrigger tookPhysicalDamageTrigger) {
        playerToTookMagicalDamageTrigger.put(player, tookPhysicalDamageTrigger);
    }

    public static void onPlayerTookMagicalDamage(Player player) {
        if (playerToTookMagicalDamageTrigger.containsKey(player)) {
            playerToTookMagicalDamageTrigger.get(player).callback(player);
            playerToTookMagicalDamageTrigger.remove(player);
        }
    }

    public static void onSkillUpgrade(Player player, InitializeTrigger initializeTrigger, int currentSkillLevel) {
        onSkillUnlearn(player); //stop old init

        int nextCastNumber = SkillDataManager.getNextCastNumber(player);
        String castKey = SkillDataManager.getCastKey(player, nextCastNumber);

        List<LivingEntity> targets = new ArrayList<>();
        targets.add(player);

        initializeTrigger.startEffects(player, currentSkillLevel, targets, castKey);
        playerToInitializeTrigger.put(player, initializeTrigger);
    }

    public static void onSkillUnlearn(Player player) {
        if (playerToInitializeTrigger.containsKey(player)) {
            playerToInitializeTrigger.get(player).stopEffects(player);
            playerToInitializeTrigger.remove(player);
        }
    }
}
