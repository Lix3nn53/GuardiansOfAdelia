package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TriggerListener {
    //Every player can have only one trigger of a type

    private static final HashMap<Player, List<TriggerComponent>> playerToTriggerList = new HashMap<>();

    public static void onPlayerQuit(Player player) {
        playerToTriggerList.remove(player);
    }

    public static void add(Player player, TriggerComponent triggerComponent) {
        List<TriggerComponent> list = new ArrayList<>();
        if (playerToTriggerList.containsKey(player)) {
            list = playerToTriggerList.get(player);
        }
        list.add(triggerComponent);
        playerToTriggerList.put(player, list);
        player.sendMessage("ADD, new size: " + list.size());
    }

    public static void remove(Player player, TriggerComponent toRemove) {
        if (playerToTriggerList.containsKey(player)) {
            List<TriggerComponent> list = playerToTriggerList.get(player);

            list.remove(toRemove);
            if (list.isEmpty()) {
                playerToTriggerList.remove(player);
            } else {
                playerToTriggerList.put(player, list);
            }
            player.sendMessage("REMOVE trigger, new size: " + list.size());
        }
    }

    public static void onPlayerLandGround(Player player) {
        if (playerToTriggerList.containsKey(player)) {
            List<TriggerComponent> list = playerToTriggerList.get(player);

            List<LandTrigger> toRemove = new ArrayList<>();

            for (TriggerComponent triggerComponent : list) {
                if (!(triggerComponent instanceof LandTrigger)) continue;
                LandTrigger trigger = (LandTrigger) triggerComponent;
                trigger.callback(player);

                toRemove.add(trigger); // always remove this trigger even if child fails somehow
            }

            for (LandTrigger trigger : toRemove) {
                TriggerListener.remove(player, trigger);
            }
        }
    }

    public static void onPlayerTookDamage(Player player, LivingEntity attacker) {
        if (playerToTriggerList.containsKey(player)) {
            List<TriggerComponent> list = playerToTriggerList.get(player);

            List<TookDamageTrigger> toRemove = new ArrayList<>();

            for (TriggerComponent triggerComponent : list) {
                if (!(triggerComponent instanceof TookDamageTrigger)) continue;
                TookDamageTrigger trigger = (TookDamageTrigger) triggerComponent;
                boolean callback = trigger.callback(player, attacker);

                if (callback) {
                    toRemove.add(trigger);
                }
            }

            for (TookDamageTrigger trigger : toRemove) {
                int skillLevel = trigger.getSkillLevel();
                List<Integer> cooldowns = trigger.getCooldowns();

                if (!cooldowns.isEmpty()) {
                    TriggerListener.remove(player, trigger);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            TriggerListener.add(player, trigger);
                        }
                    }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldowns.get(skillLevel - 1));
                }
            }
        }
    }

    public static void onPlayerNormalAttack(Player player, LivingEntity target, boolean isProjectile) {
        if (playerToTriggerList.containsKey(player)) {
            List<TriggerComponent> list = playerToTriggerList.get(player);

            List<NormalAttackTrigger> toRemove = new ArrayList<>();

            for (TriggerComponent triggerComponent : list) {
                if (!(triggerComponent instanceof NormalAttackTrigger)) continue;
                NormalAttackTrigger trigger = (NormalAttackTrigger) triggerComponent;
                boolean callback = trigger.callback(player, target, isProjectile);

                if (callback) {
                    toRemove.add(trigger);
                }
            }

            for (NormalAttackTrigger trigger : toRemove) {
                int skillLevel = trigger.getSkillLevel();
                List<Integer> cooldowns = trigger.getCooldowns();

                if (!cooldowns.isEmpty()) {
                    TriggerListener.remove(player, trigger);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            TriggerListener.add(player, trigger);
                        }
                    }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldowns.get(skillLevel - 1));
                }
            }
        }
    }

    public static void onPlayerSkillAttack(Player player, LivingEntity target) {
        if (playerToTriggerList.containsKey(player)) {
            List<TriggerComponent> list = playerToTriggerList.get(player);

            List<SkillAttackTrigger> toRemove = new ArrayList<>();

            for (TriggerComponent triggerComponent : list) {
                if (!(triggerComponent instanceof SkillAttackTrigger)) continue;
                SkillAttackTrigger trigger = (SkillAttackTrigger) triggerComponent;
                boolean callback = trigger.callback(player, target);

                if (callback) {
                    toRemove.add(trigger);
                }
            }

            for (SkillAttackTrigger trigger : toRemove) {
                int skillLevel = trigger.getSkillLevel();
                List<Integer> cooldowns = trigger.getCooldowns();

                if (!cooldowns.isEmpty()) {
                    TriggerListener.remove(player, trigger);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            TriggerListener.add(player, trigger);
                        }
                    }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldowns.get(skillLevel - 1));
                }
            }
        }
    }

    public static void onPlayerSkillCast(Player player) {
        if (playerToTriggerList.containsKey(player)) {
            List<TriggerComponent> list = playerToTriggerList.get(player);

            List<SkillCastTrigger> toRemove = new ArrayList<>();

            for (TriggerComponent triggerComponent : list) {
                if (!(triggerComponent instanceof SkillCastTrigger)) continue;
                SkillCastTrigger trigger = (SkillCastTrigger) triggerComponent;
                boolean callback = trigger.callback(player);

                if (callback) {
                    toRemove.add(trigger);
                }
            }

            for (SkillCastTrigger trigger : toRemove) {
                int skillLevel = trigger.getSkillLevel();
                List<Integer> cooldowns = trigger.getCooldowns();

                if (!cooldowns.isEmpty()) {
                    TriggerListener.remove(player, trigger);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            TriggerListener.add(player, trigger);
                        }
                    }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldowns.get(skillLevel - 1));
                }
            }
        }
    }

    public static void onPlayerShootCrossbow(Player player, Arrow arrow) {
        if (playerToTriggerList.containsKey(player)) {
            List<TriggerComponent> list = playerToTriggerList.get(player);

            for (TriggerComponent triggerComponent : list) {
                if (!(triggerComponent instanceof AddPiercingToArrowShootFromCrossbowTrigger)) continue;
                AddPiercingToArrowShootFromCrossbowTrigger trigger = (AddPiercingToArrowShootFromCrossbowTrigger) triggerComponent;
                trigger.callback(arrow);
            }
        }
    }

    public static void onPlayerSavedEntitySpawn(Player player, LivingEntity created) {
        if (playerToTriggerList.containsKey(player)) {
            List<TriggerComponent> list = playerToTriggerList.get(player);

            List<SavedEntitySpawnTrigger> toRemove = new ArrayList<>();

            for (TriggerComponent triggerComponent : list) {
                if (!(triggerComponent instanceof SavedEntitySpawnTrigger)) continue;
                SavedEntitySpawnTrigger trigger = (SavedEntitySpawnTrigger) triggerComponent;
                boolean callback = trigger.callback(player, created);

                if (callback) {
                    toRemove.add(trigger);
                }
            }

            for (SavedEntitySpawnTrigger trigger : toRemove) {
                int skillLevel = trigger.getSkillLevel();
                List<Integer> cooldowns = trigger.getCooldowns();

                if (!cooldowns.isEmpty()) {
                    TriggerListener.remove(player, trigger);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            TriggerListener.add(player, trigger);
                        }
                    }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldowns.get(skillLevel - 1));
                }
            }
        }
    }

    public static void onPlayerCompanionSpawn(Player player, LivingEntity spawned) {
        if (playerToTriggerList.containsKey(player)) {
            List<TriggerComponent> list = playerToTriggerList.get(player);

            List<CompanionSpawnTrigger> toRemove = new ArrayList<>();

            for (TriggerComponent triggerComponent : list) {
                if (!(triggerComponent instanceof CompanionSpawnTrigger)) continue;
                CompanionSpawnTrigger trigger = (CompanionSpawnTrigger) triggerComponent;
                boolean callback = trigger.callback(player, spawned);

                if (callback) {
                    toRemove.add(trigger);
                }
            }

            for (CompanionSpawnTrigger trigger : toRemove) {
                int skillLevel = trigger.getSkillLevel();
                List<Integer> cooldowns = trigger.getCooldowns();

                if (!cooldowns.isEmpty()) {
                    TriggerListener.remove(player, trigger);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            TriggerListener.add(player, trigger);
                        }
                    }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldowns.get(skillLevel - 1));
                }
            }
        }
    }

    public static void onSkillUpgrade(Player player, InitializeTrigger initializeTrigger, int skillIndex, int nextSkillLevel, int castCounter) {
        stopInit(player, skillIndex); //stop old init
        initializeTrigger.stopPreviousEffects(player);

        List<LivingEntity> targets = new ArrayList<>();
        targets.add(player);

        initializeTrigger.startEffects(player, nextSkillLevel, targets, castCounter, skillIndex);
    }

    public static void onSkillDowngrade(Player player, InitializeTrigger initializeTrigger, int skillIndex, int nextSkillLevel, int castCounter) {
        stopInit(player, skillIndex);
        initializeTrigger.stopPreviousEffects(player);
        if (nextSkillLevel == 0) return;

        List<LivingEntity> targets = new ArrayList<>();
        targets.add(player);

        initializeTrigger.startEffects(player, nextSkillLevel, targets, castCounter, skillIndex);
    }

    private static void stopInit(Player player, int skillIndex) {
        if (playerToTriggerList.containsKey(player)) {
            List<TriggerComponent> list = playerToTriggerList.get(player);

            List<TriggerComponent> toRemove = new ArrayList<>();

            for (TriggerComponent trigger : list) {
                int triggerSkillIndex = trigger.getSkillIndex();

                if (skillIndex != triggerSkillIndex) continue;

                toRemove.add(trigger);
            }

            // TODO trigger clear
            // trigger.stopEffects(player);
            // SkillDataManager.onPlayerQuit(player); // stop effects

            list.removeAll(toRemove);
            if (list.isEmpty()) {
                playerToTriggerList.remove(player);
            } else {
                playerToTriggerList.put(player, list);
            }
        }
    }
}
